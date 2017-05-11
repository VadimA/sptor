<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>

<html>
<head>
  <title>ИС ПТОР План-график</title>
  <!-- Latest compiled and minified CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

  <!-- Optional theme -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

  <!-- Latest compiled and minified JavaScript -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>


  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/css/bootstrap-select.min.css">

  <!-- Latest compiled and minified JavaScript -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/js/bootstrap-select.min.js"></script>

    <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
    <script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <link rel='stylesheet' href='https://fullcalendar.io/js/fullcalendar-2.6.1/lib/cupertino/jquery-ui.min.css' />
  <link href='https://fullcalendar.io/js/fullcalendar-2.6.1/fullcalendar.css' rel='stylesheet' />
  <link href='https://fullcalendar.io/js/fullcalendar-2.6.1/fullcalendar.print.css' rel='stylesheet' media='print' />
  <script src='https://fullcalendar.io/js/fullcalendar-2.6.1/lib/moment.min.js'></script>
  <script src='https://fullcalendar.io/js/fullcalendar-2.6.1/lib/jquery.min.js'></script>
  <script src='https://fullcalendar.io/js/fullcalendar-2.6.1/fullcalendar.min.js'></script>
  <script src='https://fullcalendar.io/js/fullcalendar-2.6.1/lang-all.js'></script>


  <style>

    body {
      margin: 0;
      padding: 0;
      font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
      font-size: 14px;
    }

    #top {
      background: #eee;
      border-bottom: 1px solid #ddd;
      padding: 0 10px;
      line-height: 40px;
      font-size: 12px;
    }

    #calendar {
      max-width: 900px;
      margin: 40px auto;
      padding: 0 10px;
    }

  </style>

    <script type="text/javascript">

        function openUsers() {
            jQuery("#formcontainer").dialog({
                        title: "Добавление пользователя",
                        width:500,
                        height: 520,
                        resizable:false,
                        cache: false,
                        modal: true
                    }
            );
        }
    </script>
</head>

<body ng-app="userApp" class="ng-cloak">
<div class="generic-container" ng-controller="UserController as ctrl">
    <div class="panel panel-default" style="display: none">
        <div class="panel-heading"><span class="lead">User Registration Form </span></div>
        <div class="formcontainer">
            <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
                <input type="hidden" ng-model="ctrl.user.id" />
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="first_name">First Name</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="ctrl.user.first_name" id="first_name" class="username form-control input-sm" placeholder="Enter your name" required ng-minlength="3"/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.first_name.$error.required">This is a required field</span>
                                <span ng-show="myForm.first_name.$error.minlength">Minimum length required is 3</span>
                                <span ng-show="myForm.first_name.$invalid">This field is invalid </span>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="last_name">Last Name</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="ctrl.user.last_name" id="last_name" class="form-control input-sm" placeholder="Enter your Last Name. [This field is validation free]"/>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="email">Email</label>
                        <div class="col-md-7">
                            <input type="email" ng-model="ctrl.user.email" id="email" class="email form-control input-sm" placeholder="Enter your Email" required/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.email.$error.required">This is a required field</span>
                                <span ng-show="myForm.email.$invalid">This field is invalid </span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="password">Password</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="ctrl.user.password" id="password" class="form-control input-sm" placeholder="Enter your Password. [This field is validation free]"/>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="ssoid">Ssoid</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="ctrl.user.ssoid" id="ssoid" class="form-control input-sm" placeholder="Enter your ssoid. [This field is validation free]"/>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-actions floatRight">
                        <input type="submit"  value="{{!ctrl.user.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
                        <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <button class="btn btn-info"  type="button" onclick="openUsers()">Добавить пользователя</button>
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of Users </span></div>
        <div class="tablecontainer">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>ID.</th>
                    <th>Name</th>
                    <th>Last</th>
                    <th>Email</th>
                    <th width="20%"></th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="u in ctrl.users">
                    <td><span ng-bind="u.id"></span></td>
                    <td><span ng-bind="u.first_name"></span></td>
                    <td><span ng-bind="u.last_name"></span></td>
                    <td><span ng-bind="u.email"></span></td>
                    <td><button type="button" confirmed-click="ctrl.remove(u.id)" class="btn btn-danger custom-width" ng-confirm-click="Вы действительно хотите удалить пользователя {{u.last_name}} ?">Удалить</button></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

    </br>
    В системе управления планированием технического обслуживания и ремонта «СПТОР» реализован следующий функционал:</br>
    –	учет состава оборудования, ведение эксплуатационной и ремонтной документации по нему (паспортов оборудования, ремонтных карт);</br>
    –	ведение структуры и состава ремонтных работ, нормативной базы  ресурсов по их проведению;</br>
    –	формирование и составление графиков ППР (месячных, годовых);</br>
    –	ввод, хранение и корректировка данных об оборудовании, комплектующих, запчастях, поставщиках;</br>
    –	 формирование потребности в ресурсах на проведение ремонтных работ;</br>
    –	учет и контроль процесса формирования и выполнения заявок на техническое обслуживания и ремонт оборудования;</br>
    –	учет и анализ работы оборудования (учет наработки и простоя, параметров работы оборудования);</br>
    –	учет состава и истории замен деталей и узлов оборудования и его элементов;</br>
    –	формирование отчетности (акты ввода/вывода оборудования в/из ре-монта, отчеты о проведенных ТОиР);</br>
    –	регистрация и авторизация пользователей. В веб-приложении суще-ствует возможность добавления, удаления, редактирования пользователей (сотрудников предприятия);</br>


<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<script src="<c:url value='/resources/angular/users/user_service.js' />"></script>
<script src="<c:url value='/resources/angular/users/user_controller.js' />"></script>
</body>

