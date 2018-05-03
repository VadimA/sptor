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
<body ng-app="cycleApp" class="ng-cloak">
<div class="col-md-4" ng-controller="CycleController as ctrl" >
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of Equipments </span></div>
        <div class="tablecontainer">
        <table class="table table-hover">
            <tr ng:repeat="item in ctrl.subdivisions">
                <td>
                    {{item.subdivision_name}}
                    <table ng-show="item.equipments_sub">
                        <tr ng:repeat="eq in item.equipments_sub">
                            <td><button type="button"  ng-click='getRepairCycleByEquipment(eq.equipmentId)' >{{eq.equipmentName}}</button></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
    </div>
</div>
<div class="col-md-8">
    <div class="btn-group btn-group-lg" role="group" aria-label="Large button group">
        <tr ng:repeat="item in ctrl.repair_cycle">
            <td>
                <button type="button" class="btn btn-default">{{item}}</button>
            </td>
        </tr>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<script src="<c:url value='/resources/angular/repair_cycle/cycle_service.js' />"></script>
<script src="<c:url value='/resources/angular/repair_cycle/cycle_controller.js' />"></script>
</body>
</html>
