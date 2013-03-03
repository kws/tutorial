<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head></head>
<body>
<h1>Parameters</h1>

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

<p>Back to our <a href="<c:url value="/"/>">welcome page</a>.

</body>
</html>