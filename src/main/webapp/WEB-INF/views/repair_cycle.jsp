<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 26.04.2018
  Time: 7:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ремонтный цикл</title>
</head>
<body>

<ul>
    <li>ТКРАО-31</li>
    <li>РРП34-1</li>
    <li>Станок 345</li>
</ul>

</br>

<div class="btn-group btn-group-lg" role="group" aria-label="Large button group">
    <button type="button" class="btn btn-default">ТО-1</button>
    <button type="button" class="btn btn-default">ТО-1</button>
    <button type="button" class="btn btn-success">ТО-2</button>
    <button type="button" class="btn btn-default">ТО-1</button>
    <button type="button" class="btn btn-default">ТО-1</button>
    <button type="button" class="btn btn-default">ТР</button>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<script src="<c:url value='/resources/angular/repair_cycle/cycle_service.js' />"></script>
<script src="<c:url value='/resources/angular/repair_cycle/cycle_controller.js' />"></script>
</body>
</html>
