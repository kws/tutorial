Spring MVC 02
=============

In the [first example] we saw how to create a very basic controller. But it wasn't very MVC-like. We're now going to look at
plugging in a view, and doing some simple operations in the controller.

The flow of a normal MVC application is something like:

```
request -> controller -> model -> view -> response
```

This is of course a schematic, and the ideal world rarely looks exactly like that, but it illustrates the basic ideas.

# Change the controller to leave rendering to the view

In our first example, the controller wrote straight to a PrintWriter, in a similar fashion to the way it's done in
a standard servlet's `doGet(HttpServletRequest req, HttpServletResponse resp)`. Insidentally, we could have used
the HttpServletResponse in the controller method instead of a PrintWriter.

However, this doesn't really fit the little schematic above. What happened to the model and view. Using Spring MVC, the
controller can return a value, and if we just return a String

```java
@RequestMapping("/")
public String welcomePage() {
	return "welcome";
}
```

then this String is taken to be the name of the view we want to handle our request - in this case, "welcome".

# Adding a view

The first thing we do is to add a "view resolver" to the Spring configuration. In the Spring framework each view has
a name, and the role of a view resolver is to pick the correct view to use for a display. It's usually up to the controller
to say what the view should be, so controllers will typically return something like "welcome" to use the view named "welcome".

In our example we add an `InternalResourceViewResolver`. This is a type of view resolver that looks for a resource inside the
war file, and uses that to render the view. In our case we are going to use a JSP, but we could use different templating
languages (such as Freemarker or Velocity), or even XML or JSON as our view. Here's our JSP configuration:

```xml
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
	<property name="prefix" value="/WEB-INF/jsp/" />
	<property name="suffix" value=".jsp" />
</bean>
```

this says that it should take the view name and stick `/WEB-INF/jsp/` in front of it, and `.jsp` at the end of it and see
if it finds a file to handle the view. If it doesn't find anything it will continue on to the next configured view resolver,
although in our case we just use one.

Ok, we now have to add a JSP at `/WEB-INF/jsp/welcome.jsp` to handle our "welcome" view. 

This is all we have to do to delegate the view rendering to a JSP.

For the [next tutorial](../spring-mvc-03/README.md) we'll try to do something a bit more interesting.


