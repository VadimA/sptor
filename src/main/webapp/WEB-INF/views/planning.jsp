<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body ng-app="planApp" class="ng-cloak">
<div class="generic-container" ng-controller="PlanController as ctrl">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Список оборудования, нуждаещегося в ремонте: </span></div>
        <div class="tablecontainer">
            <table ng-table="ctrl.tableParams" class="table table-condensed table-bordered table-striped" show-filter="true">
                <tr ng-repeat="eqp in ctrl.equipments">
                    <td title="'ИД оборудования'" filter="{ name: 'text'}" sortable="'ИД'"><span ng-bind="eqp.equipmentId"></span></td>
                    <td title="'ИД типа ремонта'"><span ng-bind="eqp.typeOfMaintenanceId"></span></td>
                    <td title="'Последняя дата ремонта'"><span ng-bind="eqp.lastDateOfMaintenance"></span></td>
                    <td title="'Следующая дата ремонта'"><span ng-bind="eqp.nextDateOfMaintenance"></span></td>
                    <td title="'Текущая выработка м/ч'"><span ng-bind="eqp.current_working_hours"></span></td>
                    <td title="'Выработка м/ч'"><span ng-bind="eqp.last_working_hours"></span></td>
                </tr>
            </table>
        </div>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<link rel="stylesheet"; href="https://unpkg.com/ng-table@2.0.2/bundles/ng-table.min.css">
<script src="https://unpkg.com/ng-table@2.0.2/bundles/ng-table.min.js"></script>
<script src="<c:url value='/resources/angular/planning/plan_service.js' />"></script>
<script src="<c:url value='/resources/angular/planning/plan_controller.js' />"></script>
</body>
</html>
