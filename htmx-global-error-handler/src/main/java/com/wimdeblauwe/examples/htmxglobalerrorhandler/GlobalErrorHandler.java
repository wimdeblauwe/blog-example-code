package com.wimdeblauwe.examples.htmxglobalerrorhandler;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxReswap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(Exception.class)
    public HtmxResponse handleError(Exception ex) {
        return HtmxResponse.builder()
                           .reswap(HtmxReswap.none())
                           .view(new ModelAndView("fragments :: error-message", Map.of("message", ex.getMessage())))
                           .build();
    }

}
