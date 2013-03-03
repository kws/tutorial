Spring MVC 04
=============

In this tutorial we will look more at how to pass parameters to our controller. 

# Mapping simple views

Since we want to set parameters, we want our "welcome" view to be a basic form
with no functionality behind it. We could create a controller method to handle
this and just return the view name as in [sample 02](../spring-mvc-02/), but
Spring allows us a little shortcut. We can add the following line to 
`application-servlet.xml`:

```xml
<mvc:view-controller path="/" view-name="welcome"/>
``` 

which maps the request "/" to the view name "welcome".

# Passing simple properties

We now want to use a simple form to send some parameters to our controller.
Let's say we want to capture first and last names of a person, so we simply
create our controller method like this:

```java
@RequestMapping("/ex1")
public ModelAndView example1(@RequestParam String firstName, @RequestParam String lastName) {
	ModelAndView mav = new ModelAndView("parameters");
	mav.addObject("firstName", firstName);
	mav.addObject("lastName", lastName);
	return mav;
}
```

This creates a controller mapped to both GET and POST on "/ex1" (you can also in the 
@RequestMapping add conditions that will map to one or the other, this is useful
for having different logic behind GET (usually display a form), and POST (submit the
form). 

For this simple example we just add the parameters to the model, but we would normally
do some processing, such as populating a database, first.

The `parameters.jsp` iterates over all attributes in the JSP "request" scope, and prints
out those that do not contain a "." in the attribute name. This is because there are lots
of additional attributes set by the container and framework. Remove this condition to see the
whole list.

```jsp
<table>  
    <c:forEach items="${requestScope}" var="p">  
    	<c:if test="${not fn:contains(p.key,'.')}">
	        <tr>  
	            <th>${p.key}:</th>  
	            <td>"${p.value}"</td>  
	        </tr>  
        </c:if>
    </c:forEach>  
</table>
``` 

This uses another part of JSTL, the [function library](http://docs.oracle.com/javaee/5/jstl/1.1/docs/tlddocs/index.html).

This should now print out the values from the form on the welcome page.

# Tweaking parameter options

In the previous example we saw how we can use the controller to pick up simple values. However, if we try
<http://localhost:8080/ex1?firstName=Kaj> we get the following error message:

```
Problem accessing /ex1. Reason:

    Required String parameter 'lastName' is not present
```

We can make parameters optional, as well as providing default values:

```java
@RequestMapping("/ex2")
public ModelAndView example2(
		@RequestParam(value = "first", required = false) String firstName,
		@RequestParam("last") String lastName, 
		@RequestParam(defaultValue = "25") int age) {
	ModelAndView mav = new ModelAndView("parameters");
	mav.addObject("firstName", firstName);
	mav.addObject("lastName", lastName);
	mav.addObject("age", age);
	return mav;
}
```

In this example we have renamed the expected variables, instead of `firstName=Kaj` as in the previous
example, we are now expecting `first=Kaj`. We have also made firstName optional, and we have added
an integer value (notice how this is automatically converted) with a default value. All java built-in types
are automatically converted.

# Setting formatting options

In our third example we want to bind a date, and spring also provides default formats for this. But often
we will want to tweak this. We can specify the actual date format to use by using another annotation:

```java
@RequestMapping("/ex3")
public ModelAndView example3(@RequestParam String firstName, @RequestParam String lastName,
		@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date dateOfBirth) {
	ModelAndView mav = new ModelAndView("parameters");
	mav.addObject("firstName", firstName);
	mav.addObject("lastName", lastName);
	mav.addObject("dateOfBirth", dateOfBirth);
	return mav;
}
```

In the [next tutorial](../spring-mvc-05/README.md) we will use all of this to build a full CRUD application.
