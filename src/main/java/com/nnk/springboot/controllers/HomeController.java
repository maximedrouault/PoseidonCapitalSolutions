package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * HomeController is a Spring MVC Controller that handles HTTP requests related to the home page.
 */
@Controller
public class HomeController {

	/**
	 * Handles the request to show the home page.
	 * @param model the Model instance
	 * @return the view name
	 */
	@RequestMapping("/")
	public String home(Model model) {
		return "home";
	}

	/**
	 * Handles the request to show the admin home page.
	 * Redirects to the bid list page.
	 * @param model the Model instance
	 * @return the view name
	 */
	@RequestMapping("/admin/home")
	public String adminHome(Model model) {
		return "redirect:/bidList/list";
	}
}
