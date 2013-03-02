package net.chisquared.tutorials.spring.web.mvc.controllers;

import java.io.PrintWriter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This is about as basic as a controller can get.
 */
@Controller
public class SampleController {

	/**
	 * We create a simple method, and map it to the URL /hello
	 */
	@RequestMapping("/hello")
	public void sampleControllerMethod(PrintWriter pw) {
		pw.println("World");
	}

}
