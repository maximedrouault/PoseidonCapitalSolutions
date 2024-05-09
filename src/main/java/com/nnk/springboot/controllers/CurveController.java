package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
 * CurveController is a Spring MVC Controller that handles HTTP requests related to CurvePoint.
 * It uses CurvePointRepository to interact with the database.
 */
@Controller
@RequiredArgsConstructor
public class CurveController {

    /**
     * CurvePointRepository instance for interacting with the database.
     */
    private final CurvePointRepository curvePointRepository;


    /**
     * Handles the request to get the list of all CurvePoints.
     * @param model the Model instance
     * @param principal the Principal instance
     * @return the view name
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model, Principal principal)
    {
        model.addAttribute("username", principal.getName());
        model.addAttribute("curvePoints", curvePointRepository.findAll());

        return "curvePoint/list";
    }

    /**
     * Handles the request to show the form for adding a new CurvePoint.
     * @param curvePoint the CurvePoint instance
     * @return the view name
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    /**
     * Handles the request to validate and save a new CurvePoint.
     * @param curvePoint the CurvePoint instance
     * @param result the BindingResult instance
     * @param model the Model instance
     * @return the view name
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            curvePointRepository.save(curvePoint);
            model.addAttribute("curvePoints", curvePointRepository.findAll());

            return "redirect:/curvePoint/list";
        }

        return "curvePoint/add";
    }

    /**
     * Handles the request to show the form for updating a CurvePoint.
     * @param id the id of the CurvePoint
     * @param model the Model instance
     * @return the view name
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
        model.addAttribute("curvePoint", curvePoint);

        return "curvePoint/update";
    }

    /**
     * Handles the request to update a CurvePoint.
     * @param id the id of the CurvePoint
     * @param curvePoint the CurvePoint instance
     * @param result the BindingResult instance
     * @param model the Model instance
     * @return the view name
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/update";
        }

        curvePoint.setId(id);
        curvePointRepository.save(curvePoint);
        model.addAttribute("curvePoints", curvePointRepository.findAll());

        return "redirect:/curvePoint/list";
    }

    /**
     * Handles the request to delete a CurvePoint.
     * @param id the id of the CurvePoint
     * @param model the Model instance
     * @return the view name
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
        curvePointRepository.delete(curvePoint);
        model.addAttribute("curvePoints", curvePointRepository.findAll());

        return "redirect:/curvePoint/list";
    }
}
