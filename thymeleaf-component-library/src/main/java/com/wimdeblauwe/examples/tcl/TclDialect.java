package com.wimdeblauwe.examples.tcl;

import com.wimdeblauwe.examples.tcl.processor.AttrsExceptAttributeProcessor;
import com.wimdeblauwe.examples.tcl.processor.ComponentElementProcessor;
import com.wimdeblauwe.examples.tcl.processor.SlotElementProcessor;
import java.util.Set;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

public class TclDialect extends AbstractProcessorDialect {

    public static final String PREFIX = "tcl";
    private static final String NAME = "Thymeleaf Component Library";

    public TclDialect() {
        super(NAME, PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }

  @Override
  public Set<IProcessor> getProcessors(String dialectPrefix) {
    return Set.of(new ComponentElementProcessor(dialectPrefix),
        new AttrsExceptAttributeProcessor(dialectPrefix),
        new SlotElementProcessor(dialectPrefix));
  }
}
