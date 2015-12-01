<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
body>
	<h1>Users</h1>
	<table>
	    <thead><tr><td>Equipment</td><td>ID</td></tr></thead>
	    <tbody>
	        <c:forEach var="equipment" items="${equipment}">
	        <tr><td>${equipment.equipment_name}</td><td>${equipment.equipment_id}</td></tr>
	        </c:forEach>
	    </tbody>
	</table>
</body>
</html>