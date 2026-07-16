package com.wimdeblauwe.examples.tcl.processor;

import java.util.Map;
import org.jspecify.annotations.Nullable;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementModelProcessor;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.templatemode.TemplateMode;

public class SlotElementProcessor extends AbstractElementModelProcessor {

  private static final String NAME_ATTRIBUTE = "name";

  public SlotElementProcessor(String dialectPrefix) {
    super(TemplateMode.HTML, dialectPrefix, "slot", true, null, false, StandardDialect.PROCESSOR_PRECEDENCE);
  }

  @Override
  protected void doProcess(ITemplateContext context, IModel model, IElementModelStructureHandler structureHandler) {
    String name = getSlotName(model);

    IModelFactory modelFactory = context.getModelFactory();

    // Capture the default content from the template itself
    IModel defaultContent = getDefaultContent(model, modelFactory);

    // Get the provided content from where the component is used
    Object providedContent = getProvidedContent(context, name);

    IModel content;
    if (providedContent instanceof IModel slot && slot.size() > 0) {
      content = slot;
    } else {
      content = defaultContent;
    }

    model.reset();
    for (int i = 0; i < content.size(); i++) {
      model.add(content.get(i));
    }
  }

  private static @Nullable Object getProvidedContent(ITemplateContext context, String name) {
    Object providedContent;
    if (StringUtils.hasText(name)) {
      Object namedSlots = context.getVariable("namedSlots");
      if (namedSlots instanceof Map<?, ?> namedSlotsMap) {
        providedContent = namedSlotsMap.get(name);
      } else {
        providedContent = null;
      }
    } else {
      providedContent = context.getVariable("defaultSlot");
    }
    return providedContent;
  }

  private static IModel getDefaultContent(IModel model, IModelFactory modelFactory) {
    IModel defaultContent = modelFactory.createModel();
    for (int i = 1; i < model.size() - 1; i++) {
      defaultContent.add(model.get(i));
    }
    return defaultContent;
  }

  private static @Nullable String getSlotName(IModel model) {
    return (model.get(0) instanceof IProcessableElementTag tag)
        ? tag.getAttributeValue(NAME_ATTRIBUTE)
        : null;
  }
}
