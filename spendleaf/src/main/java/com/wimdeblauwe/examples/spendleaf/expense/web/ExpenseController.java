package com.wimdeblauwe.examples.spendleaf.expense.web;

import com.wimdeblauwe.examples.spendleaf.expense.usecase.GetExpenses;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Set;

@Controller
@RequestMapping("/expenses")
class ExpenseController {
    // The properties of the <sl:table-head sort="..."> columns
    private static final Set<String> SORTABLE = Set.of("description", "date", "category", "project", "amount", "status");

    private final GetExpenses getExpenses;

    ExpenseController(GetExpenses getExpenses) {
        this.getExpenses = getExpenses;
    }

    @GetMapping
    public String index(@SortDefault(sort = "date", direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        if (!isSortable(pageable.getSort())) {
            return "redirect:" + ServletUriComponentsBuilder.fromCurrentRequest()
                    .replaceQueryParam("sort") // no values: removes the parameter, so @SortDefault applies
                    .replaceQueryParam("page") // a new sort starts on the first page
                    .build()
                    .toUriString();
        }
        model.addAttribute("page", getExpenses.execute(withStableOrder(pageable)));
        return "expenses/index";
    }

    private static boolean isSortable(Sort sort) {
        return sort.stream().allMatch(order -> SORTABLE.contains(order.getProperty()));
    }

    /**
     * Adds the id as the last sort key, so rows with the same value don't move between pages.
     */
    private static Pageable withStableOrder(Pageable pageable) {
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                              pageable.getSort().and(Sort.by("id")));
    }
}
