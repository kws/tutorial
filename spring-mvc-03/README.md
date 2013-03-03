Spring MVC 03
=============

In this example we will make the controller do some work for us.

# Return a random view

Well, not quite. We want the controller, based on some decision process, to return different views.
In our sample, we just pick a random and return either a red or a green view.

Before we do this though, we have to add a new dependency. Now that we are working with JSPs, we want
to use [JSTL (JavaServer Pages Standard Tag Library)](http://docs.oracle.com/javaee/5/tutorial/doc/bnakc.html).
This will help us structure our JSP views and making them less like to fail with NullPointerExceptions and such like.

We add the following maven dependency:

```xml
<dependency>
	<groupId>javax.servlet</groupId>
	<artifactId>jstl</artifactId>
	<version>1.2</version>
</dependency>

```

now we replace or default "/" handler and add the following to our controller:

```java
@RequestMapping("/")
public String welcomePage() {
	if (RND.nextBoolean()) {
		return "welcome-green";
	} else {
		return "welcome-red";
	}
}

```

Based on the random true/false we return either a red or a green welcome page. The JSPs are simple, and do nothing special. Except, 
in the green JSP there is possibly something that caught your eye:

```jsp
<ul>
	<li><a href="<c:url value="/parameters" />">Controller with parameters</a></li>
</ul>
```

Here we are using JSTL to output the URL of the parameters page. Ok, fair enough - but it doesn't really do much, does it? In this case no. 
BUT - consider the case where we deploy the WAR on a different servlet context, for example, change the pom.xml and modify `<contextPath>/</contextPath>`
to <contextPath>/my-context</contextPath>`. Now try <http://localhost:8080/>. This should now show a link to <http://localhost:8080/my-context/> which
hosts our application. If we are now lucky enough to get the green page and view the source of this, we now see:

```html
<p>Well done - you were lucky and got the right page. Try one of the following samples:</p>

<ul>
	<li><a href="/my-context/parameters">Controller with parameters</a></li>
</ul>
```

The `<c:url` tag has automatically added the context to the URL. Effectively what this means is that we can references resources with absoulute links
throughout our application, without worrying about what context it will eventually be deployed on. For a large application, this is essential.

Don't forget to change the pom back!

# Passing some model objects

Our second example in this tutorial creates some model objects and passes these to the view. There are several ways of doing this, but the easies is to
return the `ModelAndView` object. This is effectively a map with a name. The name is the view name as in the simple examples where we just returned a
string, and the map is our model. We create it like this:

```java
@RequestMapping("/parameters")
public ModelAndView parameters() {
	ModelAndView mav = new ModelAndView("parameters");
	mav.addObject("count", count++);
	mav.addObject("time", new Date());
	return mav;
}

```

We're creating a `ModelAndView` with the name "parameters", and adding two objects: count (an ever-increasing integer), and "time" holding the current 
timestamp. These now become visible to the view, and in the JSP these can be picked up in a number of ways:

```jsp
<ul>
	<li>Count: ${count}</li>
	<li>Time: <fmt:formatDate value="${time}" pattern="HH:mm:ss"/></li>
	<li>Date: <fmt:formatDate value="${time}" pattern="d EEEEE yyyy"/></li>
</ul>

```

For count, we simply use the [JSP Expression Language](http://docs.oracle.com/javaee/1.4/tutorial/doc/JSPIntro7.html). This value is an integer
so it doesn't need escaping, so it's safe to just print as is. For the date we use another JSTL tag, in this case to format the time 
and date components separately.

[Example 04](../spring-mvc-04/README.md) describes how to pass parameters to your controller.


