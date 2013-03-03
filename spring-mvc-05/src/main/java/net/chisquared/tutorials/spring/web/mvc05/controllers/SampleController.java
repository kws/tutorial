package net.chisquared.tutorials.spring.web.mvc05.controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.chisquared.tutorials.spring.web.mvc05.model.Person;
import net.chisquared.tutorials.spring.web.mvc05.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SampleController {
	private static final Comparator<Person> LAST_FIRST_NAME_COMP = new Comparator<Person>() {
		@Override
		public int compare(Person o1, Person o2) {
			int rv = o1.getLastName().compareToIgnoreCase(o2.getLastName());
			if (rv == 0)
				rv = o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
			return rv;
		}
	};

	@Autowired
	private PersonService personService;

	@ModelAttribute("hobbies")
	public String[] hobbies() {
		return new String[] { "Football", "Cinema", "Music" };
	}

	@RequestMapping("/")
	public ModelAndView listPersons() {
		// We return the 'list' view with all persons
		List<Person> persons = personService.listAllPersons();
		Collections.sort(persons, LAST_FIRST_NAME_COMP);
		return new ModelAndView("list", "persons", persons);
	}

	@RequestMapping(value = "/person/add", method = RequestMethod.GET)
	public ModelAndView addGet() {
		// we return the form view with a new person
		return new ModelAndView("form", "person", new Person());
	}

	@RequestMapping(value = "/person/add", method = RequestMethod.POST)
	public String addPost(@ModelAttribute("person") Person person) {
		personService.addPerson(person);
		return "redirect:/";
	}

	@RequestMapping(value = "/person/{personId}/edit", method = RequestMethod.GET)
	public ModelAndView editGet(@PathVariable String personId, final RedirectAttributes redirectAttrs) {
		Person person = personService.getPerson(personId);
		if (person == null) {
			redirectAttrs.addFlashAttribute("errorMessage", "Person not found");
			return new ModelAndView("redirect:/");
		}

		// we return the form view with a new person
		return new ModelAndView("form", "person", person);
	}

	@RequestMapping(value = "/person/{personId}/edit", method = RequestMethod.POST)
	public String editPost(@ModelAttribute("person") Person person) {
		// This person only has the fields posted from the form - make sure not
		// to overwrite or null any important values
		personService.updatePerson(person);
		return "redirect:/";
	}

	@RequestMapping(value = "/person/{personId}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable String personId, final RedirectAttributes redirectAttrs) {
		personService.deletePerson(personId);
		redirectAttrs.addFlashAttribute("errorMessage", "Person deleted");
		return "redirect:/";
	}

}
