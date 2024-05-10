package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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
 * RatingController is a Spring MVC Controller that handles HTTP requests related to Rating.
 * It uses RatingRepository to interact with the database.
 */
@Controller
@RequiredArgsConstructor
public class RatingController {

    /**
     * RatingRepository instance for interacting with the database.
     */
    private final RatingRepository ratingRepository;


    /**
     * Handles the request to get the list of all Ratings.
     * @param model the Model instance
     * @param principal the Principal instance
     * @return the view name
     */
    @RequestMapping("/rating/list")
    public String home(Model model, Principal principal)
    {
        model.addAttribute("username", principal.getName());
        model.addAttribute("ratings", ratingRepository.findAll());

        return "rating/list";
    }

    /**
     * Handles the request to show the form for adding a new Rating.
     * @param model the Model instance
     * @return the view name
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        model.addAttribute("rating", Rating.builder().build());

        return "rating/add";
    }

    /**
     * Handles the request to validate and save a new Rating.
     * @param rating the Rating instance
     * @param result the BindingResult instance
     * @param model the Model instance
     * @return the view name
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ratingRepository.save(rating);
            model.addAttribute("ratings", ratingRepository.findAll());

            return "redirect:/rating/list";
        }

        return "rating/add";
    }

    /**
     * Handles the request to show the form for updating a Rating.
     * @param id the id of the Rating
     * @param model the Model instance
     * @return the view name
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        model.addAttribute("rating", rating);

        return "rating/update";
    }

    /**
     * Handles the request to update a Rating.
     * @param id the id of the Rating
     * @param rating the Rating instance
     * @param result the BindingResult instance
     * @param model the Model instance
     * @return the view name
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/update";
        }

        rating.setId(id);
        ratingRepository.save(rating);
        model.addAttribute("ratings", ratingRepository.findAll());

        return "redirect:/rating/list";
    }

    /**
     * Handles the request to delete a Rating.
     * @param id the id of the Rating
     * @param model the Model instance
     * @return the view name
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        ratingRepository.delete(rating);
        model.addAttribute("ratings", ratingRepository.findAll());

        return "redirect:/rating/list";
    }
}
