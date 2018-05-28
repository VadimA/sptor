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

</head>
<body ng-app="cycleApp" class="ng-cloak">

<div id="jstree_demo_div"></div>

<div class="col-md-4" ng-controller="CycleController as ctrl" >
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Список оборудования:</span></div>
        <div class="tablecontainer">
            <table class="table table-condensed table-bordered table-striped">
                    <tr ng-repeat="item in ctrl.equipments">
                        <td><a ng-href='#here' ng-click="getRepairCycleByEquipment('item.equipmentId')" ><span ng-bind="item.equipmentName"></span></a></td>
                    </tr>
            </table>
    </div>
    </div>
</div>

<div class="col-md-8" id="result">

</div>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<script src="<c:url value='/resources/angular/repair_cycle/cycle_service.js' />"></script>
<script src="<c:url value='/resources/angular/repair_cycle/cycle_controller.js' />"></script>
</body>
</html>
