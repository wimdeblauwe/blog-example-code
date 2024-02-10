package io.bootify.taming_thymeleaf.team.controller;

import io.bootify.taming_thymeleaf.model.SimplePage;
import io.bootify.taming_thymeleaf.model.UserRole;
import io.bootify.taming_thymeleaf.team.model.TeamDTO;
import io.bootify.taming_thymeleaf.team.service.TeamService;
import io.bootify.taming_thymeleaf.user.domain.User;
import io.bootify.taming_thymeleaf.user.repos.UserRepository;
import io.bootify.taming_thymeleaf.util.CustomCollectors;
import io.bootify.taming_thymeleaf.util.ReferencedWarning;
import io.bootify.taming_thymeleaf.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/teams")
@PreAuthorize("hasAuthority('" + UserRole.Fields.USER + "')")
public class TeamController {

    private final TeamService teamService;
    private final UserRepository userRepository;

    public TeamController(final TeamService teamService, final UserRepository userRepository) {
        this.teamService = teamService;
        this.userRepository = userRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("coachValues", userRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(User::getId, User::getFirstName)));
    }

    @GetMapping
    public String list(@RequestParam(name = "filter", required = false) final String filter,
            @SortDefault(sort = "id") @PageableDefault(size = 20) final Pageable pageable,
            final Model model) {
        final SimplePage<TeamDTO> teams = teamService.findAll(filter, pageable);
        model.addAttribute("teams", teams);
        model.addAttribute("filter", filter);
        model.addAttribute("paginationModel", WebUtils.getPaginationModel(teams));
        return "team/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("team") final TeamDTO teamDTO) {
        return "team/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("team") @Valid final TeamDTO teamDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "team/add";
        }
        teamService.create(teamDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("team.create.success"));
        return "redirect:/teams";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("team", teamService.get(id));
        return "team/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("team") @Valid final TeamDTO teamDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "team/edit";
        }
        teamService.update(id, teamDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("team.update.success"));
        return "redirect:/teams";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = teamService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            teamService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("team.delete.success"));
        }
        return "redirect:/teams";
    }

}
