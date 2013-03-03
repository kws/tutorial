package net.chisquared.tutorials.spring.web.mvc05.service;

import java.util.List;

import net.chisquared.tutorials.spring.web.mvc05.model.Person;

public interface PersonService {
	List<Person> listAllPersons();

	List<Person> findPersons(String searchStr);

	Person addPerson(Person person);

	Person updatePerson(Person person);

	Person getPerson(String id);

	void deletePerson(String id);
}
