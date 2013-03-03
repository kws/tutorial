package net.chisquared.tutorials.spring.web.mvc04.controllers;

import java.security.SecureRandom;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SampleController {
	private static final SecureRandom RND = new SecureRandom();
	private int count;

	/**
	 * Return different views based on something happening in the controller
	 */
	@RequestMapping("/")
	public String welcomePage() {
		if (RND.nextBoolean()) {
			return "welcome-green";
		} else {
			return "welcome-red";
		}
	}

	/**
	 * Return a model with some parameters
	 * 
	 * @return
	 */
	@RequestMapping("/parameters")
	public ModelAndView parameters() {
		// Create a ModelAndView and give it a view name
		ModelAndView mav = new ModelAndView("parameters");
		mav.addObject("count", count++);
		mav.addObject("time", new Date());
		return mav;
	}

}
