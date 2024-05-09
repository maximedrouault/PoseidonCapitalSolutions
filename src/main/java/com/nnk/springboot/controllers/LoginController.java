package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * LoginController is a Spring MVC Controller that handles HTTP requests related to User login.
 * It uses UserRepository to interact with the database.
 */
@Controller
@RequestMapping("app")
@AllArgsConstructor
public class LoginController {

    /**
     * UserRepository instance for interacting with the database.
     */
    private UserRepository userRepository;


    /**
     * Handles the request to show the login page.
     * @return ModelAndView instance containing the view name
     */
    @GetMapping("login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");

        return mav;
    }

    /**
     * Handles the request to get all User articles.
     * @return ModelAndView instance containing the list of users and the view name
     */
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");

        return mav;
    }

    /**
     * Handles the request to show the error page when a user is not authorized for the requested data.
     * @param principal the Principal instance
     * @return ModelAndView instance containing the error message, username and the view name
     */
    @GetMapping("error")
    public ModelAndView error(Principal principal) {
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.addObject("username", principal.getName());
        mav.setViewName("403");

        return mav;
    }
}
