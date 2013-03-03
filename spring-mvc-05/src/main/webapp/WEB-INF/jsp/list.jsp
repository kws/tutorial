<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head></head>
<body>
<h1>People Editor</h1>

<c:if test="${not empty errorMessage}">
<div style="background:pink;width:100%;">
	<c:out value="${errorMessage }"/>
</div>
</c:if>

<a href="<c:url value="/person/add"/>">Add a new person</a>
<ul>
	<c:forEach var="person" items="${persons}">
		<li><c:out value="${person.lastName}" />, <c:out value="${person.firstName}" /> 
			<a href="<c:url value="/person/${person.id}/edit"/>">[edit]</a> 
			<a href="<c:url value="/person/${person.id}/delete"/>">[delete]</a> 
		</li>
	</c:forEach>
</ul>
 
<a href="<c:url value="/person/add"/>">Add a new person</a>

</body>
</html>