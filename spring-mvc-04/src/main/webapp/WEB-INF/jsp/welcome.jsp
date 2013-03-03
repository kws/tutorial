<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head></head>
<body>
<h1>Welcome to our app</h1>


<form action="<c:url value="/ex1" />" method="get">
	<fieldset> 
		<legend>Example 1 - simple properties</legend>
		<label for="firstName">First name:</label><input type="text" name="firstName" value="Kaj"/> <br />
		<label for="lastName">Last name:</label><input type="text" name="lastName" value="Siebert"/> <br />
		<button type="submit">Submit</button>
	</fieldset>	
</form>

<form action="<c:url value="/ex2" />" method="get">
	<fieldset> 
		<legend>Example 2 - parameter options</legend>
		<label for="first">First name:</label><input type="text" name="first" value="Kaj"/> <br />
		<label for="last">Last name:</label><input type="text" name="last" value="Siebert"/> <br />
		<label for="age">Age:</label><input type="text" name="age" value="22"/> <br />
		<button type="submit">Submit</button>
	</fieldset>	
</form>

<form action="<c:url value="/ex3" />" method="get">
	<fieldset> 
		<legend>Example 3 - date format</legend>
		<label for="firstName">First name:</label><input type="text" name="firstName" value="Kaj"/> <br />
		<label for="lastName">Last name:</label><input type="text" name="lastName" value="Siebert"/> <br />
		<label for="dateOfBirth">Date of Birth:</label><input type="text" name="dateOfBirth" value="1/4/1990"/> <br />
		<button type="submit">Submit</button>
	</fieldset>	
</form>

</body>
</html>