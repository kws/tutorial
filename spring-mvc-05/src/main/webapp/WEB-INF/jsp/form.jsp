<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head></head>
<body>

<form:form commandName="person" method="POST">
	<c:if test="${not empty person.id}"><form:hidden path="id"/></c:if>
	<label for="firstName">First name:</label><form:input path="firstName" /> <br />
	<label for="lastName">Last name:</label><form:input path="lastName" /> <br />
	<label for="dateOfBirth">Date of birth:</label><form:input path="dateOfBirth" /> <br />
	<label for="hobbies">Hobbies:</label><form:checkboxes items="${hobbies}" path="hobbies"/>
	<button type="submit">Submit</button>
</form:form>

</body>
</html>