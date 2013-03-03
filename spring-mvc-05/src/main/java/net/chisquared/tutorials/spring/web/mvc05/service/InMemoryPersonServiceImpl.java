package net.chisquared.tutorials.spring.web.mvc05.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.chisquared.tutorials.spring.web.mvc05.model.Person;

import org.springframework.stereotype.Repository;

/**
 * A basic {@link PersonService} based on map
 */
@Repository
public class InMemoryPersonServiceImpl implements PersonService {
	private final Map<String, Person> repo = new HashMap<>();

	public List<Person> listAllPersons() {
		return new ArrayList<>(repo.values());
	}

	public Person addPerson(Person person) {
		if (person.getId() != null)
			throw new IllegalArgumentException("New object must have null id");
		String id = UUID.randomUUID().toString();
		person.setId(id);
		repo.put(id, person);
		return person;
	}

	public Person getPerson(String id) {
		return repo.get(id);
	}

	public void deletePerson(String id) {
		repo.remove(id);
	}

	public List<Person> findPersons(String searchStr) {
		searchStr = searchStr.toLowerCase();
		List<Person> persons = new ArrayList<>();
		for (Person p : listAllPersons()) {
			if (p.getFirstName().toLowerCase().contains(searchStr) || p.getLastName().toLowerCase().contains(searchStr)) {
				persons.add(p);
			}
		}
		return persons;
	}

	public Person updatePerson(Person person) {
		if (person.getId() == null)
			throw new IllegalArgumentException("Update object must not have null id");
		repo.put(person.getId(), person);
		return person;
	}

}
