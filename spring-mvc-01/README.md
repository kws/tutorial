Spring and related tools tutorial
==================================

This project contains some really simple examples to show some of the features of Spring MVC.

The structure is somewhat linear - so example 02 may build on 01 for example, but this may not
always be the case.

# Spring MVC 01

The first sample shows about as basic a setup as is possible. A maven project with embedded jetty
running a single Spring Dispatcher servlet with a single controller.

```
.
├── pom.xml
└── src
    └── main
        ├── java
        │   └── net
        │       └── chisquared
        │           └── tutorials
        │               └── spring
        │                   └── web
        │                       └── mvc
        │                           └── controllers
        │                               └── SampleController.java
        └── webapp
            └── WEB-INF
                ├── application-servlet.xml
                └── web.xml

```

First of all, the pom includes a single dependency: the Spring MVC framework jar. The only other item
is the jetty plugin. Notice how the project follows standard maven project layout: `src/main/java` for 
java code, and `src/main/webapp/` for the WAR contents.

The compulsory web.xml is also about as simple as it can get. It declares a single servlet, and binds this
to the root url:

```
<servlet>
	<servlet-name>application</servlet-name>
	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	<load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
	<servlet-name>application</servlet-name>
	<url-pattern>/</url-pattern>
</servlet-mapping>
```

What happens now is another bit of convention. Because we named the servlet "application" (`<servlet-name>application</servlet-name>`)
the Spring DispatcherServlet will try to load configuration from the file `/WEB-INF/application.servlet.xml`. This can be changed
with servlet init parameters, but we are happy to stick to the default behaviour.

The [spring manual](http://static.springsource.org/spring/docs/current/spring-framework-reference/html/) is the best place to go for information
about how the configuration files work. However, our file is about as simple as it can get:

```
<!-- Tell spring to configure itself based on class annotations and look for components in the package below -->
<context:annotation-config/>
<context:component-scan base-package="net.chisquared.tutorials.spring.web.mvc"/>

<!-- Now configure the MVC framework -->
<mvc:annotation-driven/>
<mvc:default-servlet-handler/>

```

These tells the dispatcher servlet to auto-configure itself by looking for components in `net.chisquared.tutorials.spring.web.mvc` 
and also to register itself as the "default" servlet, i.e. handle all unhandled requests. This allows us to use the very simple
"url-pattern" in web.xml above, but still let the default servlet behaviour serve up static resources such as images and javascript.


