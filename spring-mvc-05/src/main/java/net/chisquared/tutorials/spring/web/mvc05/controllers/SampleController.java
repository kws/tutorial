package net.chisquared.tutorials.spring.web.mvc05.controllers;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SampleController {

	/**
	 * Example with basic parameters
	 * 
	 * @return
	 */
	@RequestMapping("/ex1")
	public ModelAndView example1(@RequestParam String firstName, @RequestParam String lastName) {
		ModelAndView mav = new ModelAndView("parameters");
		mav.addObject("firstName", firstName);
		mav.addObject("lastName", lastName);
		return mav;
	}

	/**
	 * Example with parameters and options
	 * 
	 * @return
	 */
	@RequestMapping("/ex2")
	public ModelAndView example2(@RequestParam(value = "first", required = false) String firstName,
			@RequestParam("last") String lastName, @RequestParam(defaultValue = "25") int age) {
		ModelAndView mav = new ModelAndView("parameters");
		mav.addObject("firstName", firstName);
		mav.addObject("lastName", lastName);
		mav.addObject("age", age);
		return mav;
	}

	/**
	 * Example with date format
	 * 
	 * @return
	 */
	@RequestMapping("/ex3")
	public ModelAndView example3(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date dateOfBirth) {
		ModelAndView mav = new ModelAndView("parameters");
		mav.addObject("firstName", firstName);
		mav.addObject("lastName", lastName);
		mav.addObject("dateOfBirth", dateOfBirth);
		return mav;
	}

}
