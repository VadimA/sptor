<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 04.06.2018
  Time: 0:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Склад</title>
</head>
<body ng-app="stockApp" class="ng-cloak">
<div class="generic-container" ng-controller="StockController as ctrl">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Список запчастей, находящихся на складе: </span></div>
        <div class="tablecontainer">
            <table ng-table="ctrl.tableParams" class="table table-condensed table-bordered table-striped">
                <tr ng-repeat="spare in ctrl.spares">
                    <td title="'Наименование'"><span ng-bind="spare.spare_name"></span></td>
                    <td title="'Производитель'"><span ng-bind="spare.spare_producer"></span></td>
                    <td title="'Кол-во на складе'"><span ng-bind="spare.amount_in_stock"></span></td>
                    <td title="'Цена'"><span ng-bind="spare.price"></span></td>
                    <td title="'Описание'"><span ng-bind="spare.description"></span></td>
                </tr>
            </table>
        </div>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.2/angular.js"></script>
<link rel="stylesheet"; href="https://unpkg.com/ng-table@2.0.2/bundles/ng-table.min.css">
<script src="https://unpkg.com/ng-table@2.0.2/bundles/ng-table.min.js"></script>
<script src="<c:url value='/resources/angular/stock/stock_service.js' />"></script>
<script src="<c:url value='/resources/angular/stock/stock_controller.js' />"></script>
</body>
</html>
