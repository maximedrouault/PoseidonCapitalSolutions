package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * UserController is a Spring MVC Controller that handles HTTP requests related to User.
 * It uses UserRepository to interact with the database.
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    /**
     * UserRepository instance for interacting with the database.
     */
    private final UserRepository userRepository;


    /**
     * Handles the request to get the list of all Users.
     * @param model the Model instance
     * @param principal the Principal instance
     * @return the view name
     */
    @RequestMapping("/user/list")
    public String home(Model model, Principal principal)
    {
        model.addAttribute("username", principal.getName());
        model.addAttribute("users", userRepository.findAll());

        return "user/list";
    }

    /**
     * Handles the request to show the form for adding a new User.
     * @param model the Model instance
     * @return the view name
     */
    @GetMapping("/user/add")
    public String addUser(Model model) {
        model.addAttribute("user", User.builder().build());

        return "user/add";
    }

    /**
     * Handles the request to validate and save a new User.
     * @param user the User instance
     * @param result the BindingResult instance
     * @param model the Model instance
     * @return the view name
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            model.addAttribute("users", userRepository.findAll());

            return "redirect:/user/list";
        }
        return "user/add";
    }

    /**
     * Handles the request to show the form for updating a User.
     * @param id the id of the User
     * @param model the Model instance
     * @return the view name
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);

        return "user/update";
    }

    /**
     * Handles the request to update a User.
     * @param id the id of the User
     * @param user the User instance
     * @param result the BindingResult instance
     * @param model the Model instance
     * @return the view name
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());

        return "redirect:/user/list";
    }

    /**
     * Handles the request to delete a User.
     * @param id the id of the User
     * @param model the Model instance
     * @return the view name
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());

        return "redirect:/user/list";
    }
}
