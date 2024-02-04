package io.bootify.taming_thymeleaf.util;

import io.bootify.taming_thymeleaf.model.PaginationModel;
import io.bootify.taming_thymeleaf.model.PaginationStep;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;


@Component
public class WebUtils {

    public static final String MSG_SUCCESS = "MSG_SUCCESS";
    public static final String MSG_INFO = "MSG_INFO";
    public static final String MSG_ERROR = "MSG_ERROR";
    private static MessageSource messageSource;
    private static LocaleResolver localeResolver;

    public WebUtils(final MessageSource messageSource, final LocaleResolver localeResolver) {
        WebUtils.messageSource = messageSource;
        WebUtils.localeResolver = localeResolver;
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static String getMessage(final String code, final Object... args) {
        return messageSource.getMessage(code, args, code, localeResolver.resolveLocale(getRequest()));
    }

    public static boolean isRequiredField(final Object dto, final String fieldName) throws
            NoSuchFieldException {
        return dto.getClass().getDeclaredField(fieldName).getAnnotation(NotNull.class) != null;
    }

    private static String getStepUrl(final Page<?> page, final int targetPage) {
        String stepUrl = "?page=" + targetPage + "&size=" + page.getSize();
        if (getRequest().getParameter("sort") != null) {
            stepUrl += "&sort=" + getRequest().getParameter("sort");
        }
        if (getRequest().getParameter("filter") != null) {
            stepUrl += "&filter=" + getRequest().getParameter("filter");
        }
        return stepUrl;
    }

    public static PaginationModel getPaginationModel(final Page<?> page) {
        if (page.isEmpty()) {
            return null;
        }

        final ArrayList<PaginationStep> steps = new ArrayList<>();
        PaginationStep step = new PaginationStep();
        step.setDisabled(!page.hasPrevious());
        step.setLabel(getMessage("pagination.previous"));
        step.setUrl(getStepUrl(page, page.previousOrFirstPageable().getPageNumber()));
        steps.add(step);
        // find a range of up to 5 pages around the current active page
        final int startAt = Math.max(0, Math.min(page.getNumber() - 2, page.getTotalPages() - 5));
        final int endAt = Math.min(startAt + 5, page.getTotalPages());
        for (int i = startAt; i < endAt; i++) {
            step = new PaginationStep();
            step.setActive(i == page.getNumber());
            step.setLabel("" + (i + 1));
            step.setUrl(getStepUrl(page, i));
            steps.add(step);
        }
        step = new PaginationStep();
        step.setDisabled(!page.hasNext());
        step.setLabel(getMessage("pagination.next"));
        step.setUrl(getStepUrl(page, page.nextOrLastPageable().getPageNumber()));
        steps.add(step);

        final long startElements = page.getNumber() * page.getSize() + 1l;
        final long endElements = Math.min(startElements + page.getSize() - 1, page.getTotalElements());
        final String range = startElements == endElements ? "" + startElements : startElements + " - " + endElements;
        final PaginationModel paginationModel = new PaginationModel();
        paginationModel.setSteps(steps);
        paginationModel.setElements(getMessage("pagination.elements", range, page.getTotalElements()));
        return paginationModel;
    }

}
