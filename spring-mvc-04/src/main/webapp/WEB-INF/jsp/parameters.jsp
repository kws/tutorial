<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head></head>
<body>
<h1>Parameters</h1>

<ul>
	<li>Count: ${count}</li>
	<li>Time: <fmt:formatDate value="${time}" pattern="HH:mm:ss"/></li>
	<li>Date: <fmt:formatDate value="${time}" pattern="d EEEEE yyyy"/></li>
</ul>

<p><a href="javascript:location.reload(true);">Reload</a> to update.</p>

<p>Back to our <a href="<c:url value="/"/>">welcome page</a>.

</body>
</html>