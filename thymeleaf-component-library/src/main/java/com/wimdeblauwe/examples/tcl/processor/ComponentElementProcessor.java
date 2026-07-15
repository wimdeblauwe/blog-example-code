package com.wimdeblauwe.examples.tcl.processor;

import java.util.LinkedHashMap;
import java.util.Map;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IOpenElementTag;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.ITemplateEvent;
import org.thymeleaf.processor.element.IElementModelProcessor;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.processor.element.MatchingAttributeName;
import org.thymeleaf.processor.element.MatchingElementName;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.templatemode.TemplateMode;

public class ComponentElementProcessor implements IElementModelProcessor {

  private final String dialectPrefix;
  private final MatchingElementName matchingElementName;

  public ComponentElementProcessor(String dialectPrefix) {
    this.dialectPrefix = dialectPrefix;
    this.matchingElementName = MatchingElementName.forAllElementsWithPrefix(TemplateMode.HTML, dialectPrefix);
  }

  @Override
  public void process(ITemplateContext context, IModel model, IElementModelStructureHandler structureHandler) {
    IModelFactory modelFactory = context.getModelFactory();

    ITemplateEvent first = model.get(0);
    if (!(first instanceof IProcessableElementTag openTag)) {
      return;
    }

    String name = componentName(openTag);

    Map<String, String> attrs = getAttributesAsMap(openTag);

    structureHandler.setLocalVariable("attrs", attrs);

    // Replace the element with a fragment call to the component template, e.g. <tcl:button> ->
    // ~{tcl/components/button :: button}.
    final String fragmentExpression = "~{tcl/components/" + name + " :: " + name + "}";
    IOpenElementTag block = modelFactory.createOpenElementTag("th:block");
    block = modelFactory.setAttribute(block, "th:replace", fragmentExpression);

    model.reset();
    model.add(block);
    model.add(modelFactory.createCloseElementTag("th:block"));
  }

  @Override
  public MatchingElementName getMatchingElementName() {
    return matchingElementName;
  }

  @Override
  public MatchingAttributeName getMatchingAttributeName() {
    return null;
  }

  @Override
  public TemplateMode getTemplateMode() {
    return TemplateMode.HTML;
  }

  @Override
  public int getPrecedence() {
    return StandardDialect.PROCESSOR_PRECEDENCE;
  }

  private static Map<String, String> getAttributesAsMap(IProcessableElementTag openTag) {
    Map<String, String> attrs = new LinkedHashMap<>();
    for (var attribute : openTag.getAllAttributes()) {
      attrs.put(attribute.getAttributeCompleteName(), attribute.getValue());
    }
    return attrs;
  }

  /** Derives the component name from the tag, e.g. {@code tcl:button -> button}. */
  private String componentName(IProcessableElementTag openTag) {
    String complete = openTag.getElementCompleteName();
    String prefix = dialectPrefix + ":";
    return complete.startsWith(prefix) ? complete.substring(prefix.length()) : complete;
  }

}
