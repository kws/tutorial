Spring MVC 05
=============

We are now going to take what we've learned and create
a little app to manage some simple objects. We are going
to use another useful spring component, which is the form binding.

We are going to create a small utility for maintaining a list of people.
A basic list view, with a simple form and basic CRUD functionality.

# The Object Model

First of all we are going to start off with a very simple object,
the Person: `net.chisquared.tutorials.spring.web.mvc05.model.Person`.

This is a standard JavaBean with a zero-args constructor and matching
getters and setters.

# The Service Layer

We also create a service interface, the 
`net.chisquared.tutorials.spring.web.mvc05.service.PersonService`. This
service expresses the basic CRUD operations:

```java
public interface PersonService {
	List<Person> listAllPersons();

	List<Person> findPersons(String searchStr);

	Person addPerson(Person person);

	Person updatePerson(Person person);

	Person getPerson(String id);

	void deletePerson(String id);
}
```

Why do we define a service interface? Couldn't we just implement these methods directly?
It's an interesting question, and not really a straight-forward answer. Basically, the
cost of creating an interface is negligible, but it future-proofs the code. With an interface
it opens up a lot of possibilities for later development, including multiple implementations,
service wrappers (business logic around DAO for example), remoting etc. I would usually
recommend creating an interface, even for a trivial implementation.

We do of course provide an implementation of the interface as well. In this case it's just
a basic implementation backed by a Map.

# The Web Layer

So this is where it all happens. Our controller is now quite a bit "heavier" than in previous examples.

We still implement our welcome view, which shows the list of persons, as well as "add" links for adding a new
person:

```java
@RequestMapping("/")
public ModelAndView listPersons() {
	List<Person> persons = personService.listAllPersons();
	Collections.sort(persons, LAST_FIRST_NAME_COMP);
	return new ModelAndView("list", "persons", persons);
}
```

Notice the short-hand there for returning a ModelAndView with a single attribute called "persons".
Most of the work is delegated to the service layer. But where does the personService come from?

If we look at the top of the class, we see the following:

```java
@Autowired
private PersonService personService;
```

Spring will look for the `@Autowired` annotation and insert any implementation of that service that 
it knows about. In this case, we made the `InMemoryPersonServiceImpl` discoverable to Spring by
adding the `@Repository` annotation to that class. That means it was picked up by the 
`<context:component-scan base-package="net.chisquared.tutorials.spring.web"/>` line in `application-servlet`.
There are many other ways we could have configured this as well, but for a small application, such
hands-off configuration greatly reduces the amount of code and configuration needed.

Anyway, having our list view isn't terribly exciting, as we don't have any data yet. We need to add a page
for adding new companies:

```java
@RequestMapping(value = "/person/add", method = RequestMethod.GET)
public ModelAndView addGet() {
	return new ModelAndView("form", "person", new Person());
}

```

As mentioned in an earlier tutorial, the `@RequestMapping` annotation takes several options,
one of those being the HTTP Method. In this case we want to apply different logic depending
on whether we GET the `/person/add` URL (in which case we display the form), or we POST it
(in which case we process the posted data).

Before we get on to the processing, let's look at the method in a bit more detail. It returns
a ModelAndView with the name "form". We will get back to this in a second. The other thing it
does it creates a single attribute with the name "person" and adds a new `Person` object. If
we wanted to pre-populate some fields of our brand-new object, this would be where we do that.

Now, for the form:

```jsp
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head></head>
<body>

<form:form commandName="person" method="POST">
	<c:if test="${not empty id}"><form:hidden path="id"/></c:if>
	<label for="firstName">First name:</label><form:input path="firstName" /> <br />
	<label for="lastName">Last name:</label><form:input path="lastName" /> <br />
	<label for="dateOfBirth">Date of birth:</label><form:input path="dateOfBirth" /> <br />
	<label for="hobbies">Hobbies:</label><form:checkboxes items="${hobbies}" path="hobbies"/>
	<button type="submit">Submit</button>
</form:form>

</body>
</html>
```

Notice how we have included a new taglib (`http://www.springframework.org/tags/form`) with the prefix `form`. We don't create a normal
HTML form, instead we use the `<form:form>` element, and all the inputs are from the form taglib as well. This basically handles the binding
to the Person object for us. We tell the form that the `commandName` is person. This is the name of the attribute we populated in our controller.
Each of the form inputs take a path that is equivalent to the bean property accessors. 

The only exception is the checkboxes - it references an attribute called "hobbies", but where is this coming from? We have to go back into 
our controller, where we see:

```java
@ModelAttribute("hobbies")
public String[] hobbies() {
	return new String[] { "Football", "Cinema", "Music" };
}
```

This populates the attribute hobbies on all requests to this controller, and is used to provide the checkbox values.

Now for the next stage of the process, we need to handle the form submission. We create another controller method to handle the POST
request to the add person:

```java
@RequestMapping(value = "/person/add", method = RequestMethod.POST)
public String addPost(@ModelAttribute("person") Person person) {
	personService.addPerson(person);
	return "redirect:/";
}
```

We simply tell the controller that we want the 'person' object as populated by the form and all the binding is taken care of. 
We then pass the person object to the service, and that's it. The method only returns a String, and we remember from earlier
tutorials that it is the name of the view. Spring supports a few 'special' view names, and one of these is the "redirect:" prefix. 
Return any view name prefixed with "redirect:" and the controller will return a redirect response to the given URL, in this case
back to the welcome page.

## U in CRUD

So much for adding and listing - how about updating? Well, all the hard work has already been done. We can use the same form,
the only little condition is that when we add a new object, we require the ID to be null, but when editing we need the ID. We
could achieve this in multiple ways, but one of the simplest is just to keep a hidden field on the form which is only 
present if we are editing an existing value:

```jsp
<c:if test="${not empty person.id}"><form:hidden path="id"/></c:if>
```

Notice how in our condition we test for `person.id`, the full path to the property we need, but in the form we just refer to it 
as `id`. That is because we already set the command object on the form wrapper, and the field paths are all relative to this.

The controller methods for handling the display of the form and the form submission, are very similar to the add ones:

```java
@RequestMapping(value = "/person/{personId}/edit", method = RequestMethod.GET)
public ModelAndView editGet(@PathVariable String personId, final RedirectAttributes redirectAttrs) {
	Person person = personService.getPerson(personId);
	if (person == null) {
		redirectAttrs.addFlashAttribute("errorMessage", "Person not found");
		return new ModelAndView("redirect:/");
	}
	return new ModelAndView("form", "person", person);
}

@RequestMapping(value = "/person/{personId}/edit", method = RequestMethod.POST)
public String editPost(@ModelAttribute("person") Person person) {
	personService.updatePerson(person);
	return "redirect:/";
}
```

I got bored of using `@RequestParam`, so instead we're specifying the person ID using a new method. We're embedding it in the URL, so 
we get URLs that look something like:

`http://localhost:8080/person/9ff0a5a8-975e-46d5-bdd6-b77a4183a3f8/edit`

and instead of `@RequestParam String personId` we use `@PathVariable String personId` where the "personId" part matches the placeholder 
in the RequestMapping URL: `{personId}`.

In the 'GET' method we perform an additional bit of validation. We make sure the person we want to edit, actually exists, and if 
she doesn't then we redirect to the welcome list, but including an error message. We do this using a feature called
"flash attributes". These are basically session attributes that are only valid for one request. To set them, make sure the 
method includes "RedirectAttributes" in the call. Spring will automatically populate this with whatever is needed to set
flash attributes. We set the message, and the redirect to the welcome page.

## And finally the big D

This bit should be pretty obvious by now:

```java
@RequestMapping(value = "/person/{personId}/delete", method = RequestMethod.GET)
public String delete(@PathVariable String personId, final RedirectAttributes redirectAttrs) {
	personService.deletePerson(personId);
	redirectAttrs.addFlashAttribute("errorMessage", "Person deleted");
	return "redirect:/";
}
```

A pathvariable, a flash attribute. Nothing particularly exciting here.


