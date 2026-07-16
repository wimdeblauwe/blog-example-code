package com.wimdeblauwe.examples.tcl.processor;

import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;
import org.thymeleaf.model.ICloseElementTag;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IOpenElementTag;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.ITemplateEvent;

/**
 * Splits a component element's body into the <strong>default slot</strong> and the
 * <strong>named</strong> {@code <tcl:slot name="...">} blocks.
 *
 * <p>The body is walked exactly once. Every event is routed to a single "target" model: normally
 * the default slot, or &ndash; between a top-level {@code <tcl:slot name="x">} and its matching
 * close tag &ndash; the named slot being captured. The {@code <tcl:slot>} wrapper tags themselves
 * are consumed, never emitted.
 *
 * <p>Two nesting rules keep ownership straight:
 *
 * <ul>
 *   <li>A named slot found inside a <em>nested component</em> ({@code componentDepth > 0}) belongs
 *       to that inner component, so it stays in the default slot untouched.
 *   <li>A {@code <tcl:slot>} found inside a slot being <em>captured</em> ({@code slotDepth}) is
 *       part of that slot's content, so it is copied verbatim.
 * </ul>
 */
final class SlotContentSplitter {

  private final String dialectPrefix;

  SlotContentSplitter(String dialectPrefix) {
    this.dialectPrefix = dialectPrefix;
  }

  /**
   * Splits the body of {@code element} (everything between its open and close tag) returning the
   * default slot and the named slots, in authoring order.
   */
  SlotContent split(IModel element, IModelFactory modelFactory) {
    return new ElementBodyWalker(modelFactory).split(element);
  }

  /** The result of a split: the default slot body and the named slots, in authoring order. */
  record SlotContent(IModel defaultSlot, Map<String, IModel> namedSlots) {}

  /** The mutable state of a single walk over a component body. */
  private final class ElementBodyWalker {

    private final IModelFactory modelFactory;
    private final IModel defaultSlot;
    private final Map<String, IModel> namedSlots = new LinkedHashMap<>();

    /** The named slot currently being captured, or {@code null} while routing to the default. */
    private @Nullable IModel capturedSlot;

    // Depth of nested tcl:* component elements while routing to the default slot.
    private int componentDepth;
    // Depth of nested <tcl:slot> elements while capturing a named slot's content.
    private int slotDepth;

    private ElementBodyWalker(IModelFactory modelFactory) {
      this.modelFactory = modelFactory;
      this.defaultSlot = modelFactory.createModel();
    }

    private SlotContent split(IModel element) {
      for (int i = 1; i < element.size() - 1; i++) {
        ITemplateEvent event = element.get(i);
        if (capturedSlot != null) {
          // We are currently inside a named slot being captured.
          // Capture everything up to the matching </tcl:slot> tag.
          capture(event, capturedSlot);
        } else {
          route(event);
        }
      }
      return new SlotContent(defaultSlot, namedSlots);
    }

    /** Capturing a named slot: copy everything up to the matching {@code </tcl:slot>}. */
    private void capture(ITemplateEvent event, IModel slot) {
      if (isSlotClose(event) && slotDepth == 0) {
        capturedSlot = null; // matching close tag; consume it and stop capturing
        return;
      }
      if (isSlotOpen(event)) {
        slotDepth++;
      } else if (isSlotClose(event)) {
        slotDepth--;
      }
      slot.add(event);
    }

    /**
     * Routing to the default slot: divert to a new named slot on a top-level {@code <tcl:slot>}.
     */
    private void route(ITemplateEvent event) {
      boolean shouldSearchForNamedSlots = componentDepth == 0;
      String slotName = shouldSearchForNamedSlots ? namedSlotName(event) : null;
      if (slotName != null) {
        // A named slot was found, create a new empty model for the body of the named slot.
        IModel content = modelFactory.createModel();
        namedSlots.put(slotName, content);
        if (event instanceof IOpenElementTag) {
          capturedSlot = content; // <tcl:slot name="x">...</tcl:slot>: capture until the close
        }
        // No capture for a standalone <tcl:slot name="x"/>: it stays an empty named slot.
      } else {
        if (isComponentOpen(event)) {
          componentDepth++;
        } else if (isComponentClose(event)) {
          componentDepth--;
        }
        defaultSlot.add(event);
      }
    }

    /** Returns the {@code name} of an {@code <tcl:slot name="...">} event, or {@code null}. */
    private @Nullable String namedSlotName(ITemplateEvent event) {
      if (event instanceof IProcessableElementTag tag
          && isSlotElement(tag.getElementCompleteName())) {
        String name = tag.getAttributeValue("name");
        return (name == null || name.isBlank()) ? null : name;
      }
      return null;
    }

    private boolean isSlotOpen(ITemplateEvent event) {
      return event instanceof IOpenElementTag open && isSlotElement(open.getElementCompleteName());
    }

    private boolean isSlotClose(ITemplateEvent event) {
      return event instanceof ICloseElementTag close
          && isSlotElement(close.getElementCompleteName());
    }

    private boolean isSlotElement(String completeName) {
      return completeName.equals(dialectPrefix + ":slot");
    }

    /** An {@code <tcl:NAME>} open tag for a nested component (anything but {@code <tcl:slot>}). */
    private boolean isComponentOpen(ITemplateEvent event) {
      return event instanceof IOpenElementTag open
          && isComponentElement(open.getElementCompleteName());
    }

    /**
     * An {@code </tcl:NAME>} close tag for a nested component (anything but {@code </tcl:slot>}).
     */
    private boolean isComponentClose(ITemplateEvent event) {
      return event instanceof ICloseElementTag close
          && isComponentElement(close.getElementCompleteName());
    }

    private boolean isComponentElement(String completeName) {
      return completeName.startsWith(dialectPrefix + ":") && !isSlotElement(completeName);
    }
  }
}
