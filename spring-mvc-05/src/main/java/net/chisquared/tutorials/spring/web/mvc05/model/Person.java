package net.chisquared.tutorials.spring.web.mvc05.model;

import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

public class Person {
	private String id, firstName, lastName;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dateOfBirth;
	private Set<String> hobbies;

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public String getFirstName() {
		return firstName;
	}

	public Set<String> getHobbies() {
		return hobbies;
	}

	public String getId() {
		return id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setHobbies(Set<String> hobbies) {
		this.hobbies = hobbies;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDay=" + dateOfBirth
				+ ", hobbies=" + hobbies + "]";
	}

}
