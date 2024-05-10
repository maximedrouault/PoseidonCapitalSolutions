package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * HomeController is a Spring MVC Controller that handles HTTP requests related to the home page.
 */
@Controller
public class HomeController {

	/**
	 * Handles the request to show the home page.
	 * @return the view name
	 */
	@RequestMapping("/")
	public String home() {
		return "redirect:/bidList/list";
	}
}
