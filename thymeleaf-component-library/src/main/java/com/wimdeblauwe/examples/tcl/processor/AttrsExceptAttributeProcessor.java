package com.wimdeblauwe.examples.tcl.processor;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class AttrsExceptAttributeProcessor extends AbstractAttributeTagProcessor {

  private static final String ATTR_NAME = "attrsExcept";
  private static final String ATTRIBUTE_CLASS = "class";
  private static final Set<String> ATTRIBUTES_TO_MERGE = Set.of(ATTRIBUTE_CLASS);

  // Runs after th:with (600), so component props are available, but before the standard default
  // attribute processor (1000) and th:text (1300) so injected th:* attributes are still evaluated.
  private static final int PRECEDENCE = 750;

  public AttrsExceptAttributeProcessor(String dialectPrefix) {
    super(TemplateMode.HTML, dialectPrefix, null, false, ATTR_NAME, true, PRECEDENCE, true);
  }

  @Override
  protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName, String attributeValue,
      IElementTagStructureHandler structureHandler) {
    // Evaluate the value of the mtl:attrsExcept attribute, which should be a comma-separated list of attribute names to exclude.
    Set<String> excludedAttributes = Arrays.stream(attributeValue.split(",\\s*")).collect(Collectors.toSet());

    @SuppressWarnings("unchecked")
    Map<String, String> attrs = (Map<String, String>) context.getVariable("attrs");
    if (attrs == null) {
      return;
    }

    for (Map.Entry<?, ?> entry : attrs.entrySet()) {
      String key = String.valueOf(entry.getKey());
      if (excludedAttributes.contains(key)) {
        continue;
      }
      String value = entry.getValue() == null ? "" : String.valueOf(entry.getValue());
      if (ATTRIBUTES_TO_MERGE.contains(key)) {
        String existing = tag.getAttributeValue(key);
        structureHandler.setAttribute(
            key, existing == null || existing.isBlank() ? value : existing + " " + value);
      } else {
        structureHandler.setAttribute(key, value);
      }
    }
  }
}
