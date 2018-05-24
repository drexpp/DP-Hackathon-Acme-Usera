/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomisationService;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {
	
	@Autowired
	CustomisationService customisationService;

	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, value = "language", defaultValue = "en") final String language) {
		ModelAndView result;
		final String welcomeMessage;

		if (language.endsWith("s"))
			welcomeMessage = this.customisationService.find().getBannerEs();
		else
			welcomeMessage = this.customisationService.find().getBannerEn();


		result = new ModelAndView("welcome/index");
		result.addObject("welcomeMessage", welcomeMessage);

		return result;
	}
}
