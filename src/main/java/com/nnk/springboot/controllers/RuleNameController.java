package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * RuleNameController is a Spring MVC Controller that handles HTTP requests related to RuleName.
 * It uses RuleNameRepository to interact with the database.
 */
@Controller
@RequiredArgsConstructor
public class RuleNameController {

    /**
     * RuleNameRepository instance for interacting with the database.
     */
    private final RuleNameRepository ruleNameRepository;


    /**
     * Handles the request to get the list of all RuleNames.
     * @param model the Model instance
     * @param principal the Principal instance
     * @return the view name
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model, Principal principal)
    {
        model.addAttribute("username", principal.getName());
        model.addAttribute("ruleNames", ruleNameRepository.findAll());

        return "ruleName/list";
    }

    /**
     * Handles the request to show the form for adding a new RuleName.
     * @param model the Model instance
     * @return the view name
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        model.addAttribute("ruleName", RuleName.builder().build());

        return "ruleName/add";
    }

    /**
     * Handles the request to validate and save a new RuleName.
     * @param ruleName the RuleName instance
     * @param result the BindingResult instance
     * @param model the Model instance
     * @return the view name
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ruleNameRepository.save(ruleName);
            model.addAttribute("ruleNames", ruleNameRepository.findAll());

            return "redirect:/ruleName/list";
        }

        return "ruleName/add";
    }

    /**
     * Handles the request to show the form for updating a RuleName.
     * @param id the id of the RuleName
     * @param model the Model instance
     * @return the view name
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        model.addAttribute("ruleName", ruleName);

        return "ruleName/update";
    }

    /**
     * Handles the request to update a RuleName.
     * @param id the id of the RuleName
     * @param ruleName the RuleName instance
     * @param result the BindingResult instance
     * @param model the Model instance
     * @return the view name
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/update";
        }

        ruleName.setId(id);
        ruleNameRepository.save(ruleName);
        model.addAttribute("ruleNames", ruleNameRepository.findAll());

        return "redirect:/ruleName/list";
    }

    /**
     * Handles the request to delete a RuleName.
     * @param id the id of the RuleName
     * @param model the Model instance
     * @return the view name
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        ruleNameRepository.delete(ruleName);
        model.addAttribute("ruleNames", ruleNameRepository.findAll());

        return "redirect:/ruleName/list";
    }
}
