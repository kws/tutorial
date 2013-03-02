package net.chisquared.tutorials.spring.web.mvc02.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This is about as basic as a controller can get.
 */
@Controller
public class SampleController {

	@RequestMapping("/")
	public String welcomePage() {
		return "welcome";
	}
	
	

}
