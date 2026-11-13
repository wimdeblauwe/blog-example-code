package com.wimdeblauwe.examples.spendleaf.expense.web;

import com.wimdeblauwe.examples.spendleaf.expense.Expense;
import com.wimdeblauwe.examples.spendleaf.expense.ExpenseCategory;
import com.wimdeblauwe.examples.spendleaf.expense.usecase.CreateExpense;
import com.wimdeblauwe.examples.spendleaf.expense.usecase.GetExpenses;
import com.wimdeblauwe.examples.spendleaf.infrastructure.web.IdAndName;
import com.wimdeblauwe.examples.spendleaf.infrastructure.web.IdAndNameCollection;
import com.wimdeblauwe.examples.spendleaf.project.usecase.GetProjects;
import io.github.wimdeblauwe.shadleaf.toast.Toast;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/expenses")
class ExpenseController {
    // The properties of the <sl:table-head sort="..."> columns
    private static final Set<String> SORTABLE = Set.of("description", "date", "category", "project", "amount", "status");

    private final GetExpenses getExpenses;
    private final CreateExpense createExpense;
    private final GetProjects getProjects;

    ExpenseController(GetExpenses getExpenses, CreateExpense createExpense, GetProjects getProjects) {
        this.getExpenses = getExpenses;
        this.createExpense = createExpense;
        this.getProjects = getProjects;
    }

    @InitBinder
    void initBinder(WebDataBinder binder) {
        // Trims text input, and turns an empty field into null
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute("projects")
    public IdAndNameCollection projects() {
        return new IdAndNameCollection(getProjects.execute().stream()
                                               .map(project -> new IdAndName(project.getId().getId(), project.getName()))
                                               .toList());
    }

    @ModelAttribute("categories")
    public List<ExpenseCategory> categories() {
        return List.of(ExpenseCategory.values());
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

    @GetMapping("/create")
    public String createExpenseForm(Model model) {
        model.addAttribute("expense", CreateExpenseFormData.withDefaults());
        return "expenses/edit";
    }

    @PostMapping("/create")
    public String doCreateExpense(@Valid @ModelAttribute("expense") CreateExpenseFormData formData,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "expenses/edit";
        }

        Expense expense = createExpense.execute(formData.toParameters());
        redirectAttributes.addFlashAttribute("toasts", List.of(
                Toast.success("Expense created").withDescription(expense.getDescription())));
        return "redirect:/expenses";
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
