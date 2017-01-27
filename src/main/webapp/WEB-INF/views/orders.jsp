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

  <link rel='stylesheet' href='http://fullcalendar.io/js/fullcalendar-2.6.1/lib/cupertino/jquery-ui.min.css' />
  <link href='http://fullcalendar.io/js/fullcalendar-2.6.1/fullcalendar.css' rel='stylesheet' />
  <link href='http://fullcalendar.io/js/fullcalendar-2.6.1/fullcalendar.print.css' rel='stylesheet' media='print' />
  <script src='http://fullcalendar.io/js/fullcalendar-2.6.1/lib/moment.min.js'></script>
  <script src='http://fullcalendar.io/js/fullcalendar-2.6.1/lib/jquery.min.js'></script>
  <script src='http://fullcalendar.io/js/fullcalendar-2.6.1/fullcalendar.min.js'></script>
  <script src='http://fullcalendar.io/js/fullcalendar-2.6.1/lang-all.js'></script>


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

</head>
<body>
<div class="col-md-12">
  <!-- Static navbar -->
  <nav class="navbar navbar-default">
    <div class="container-fluid">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href=""><b>ИС СПТОР</b></a>
      </div>
      <div id="navbar" class="navbar-collapse collapse">
        <ul class="nav navbar-nav">
          <li><a href="<c:url value="/sptor"/>">Главная</a></li>
          <security:authorize access="hasRole('ROLE_ADMIN')">
            <li><a href="<c:url value="/equipments"/>">Оборудование</a></li>
          </security:authorize>
          <li><a href="<c:url value="/repair"/>">Заявки</a></li>
          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">План-график<span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="<c:url value="/graphics"/>">Месячный</a></li>
            </ul>
          </li>
          <security:authorize access="hasRole('ROLE_ADMIN')">
            <li><a href="<c:url value="/users"/>">Справочники</a></li>
          </security:authorize>
        </ul>
        <ul class="nav navbar-nav navbar-right">
          <security:authorize access="hasRole('ROLE_REPAIR')">
            <li>
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                <i class="fa fa-bell-o"></i>
                <span class="glyphicon glyphicon-envelope" style="color: red">${active_req}</span>
              </a>
              <ul class="dropdown-menu" style="padding: 15px; ">
                <li class="header">Поступило новых заявок: <b>${active_req}</b></li>
                <li>
                  <!-- inner menu: contains the actual data -->
                  <div class="slimScrollDiv"><ul class="menu">
                    <li>
                      <a href="<c:url value="/repair"/>">
                        <p>перейти к заявкам</p>
                      </a>
                    </li>
                  </ul>
                  </div>
                </li>
              </ul>
            </li>
          </security:authorize>
          <security:authorize access="hasRole('ROLE_ADMIN')">
            <li>
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                <i class="fa fa-bell-o"></i>
                <span class="glyphicon glyphicon-envelope" style="color: red">${confirm_req}</span>
              </a>
              <ul class="dropdown-menu" style="padding: 15px; ">
                <li class="header">Заявок на подтверждении: <b>${confirm_req}</b></li>
                <li>
                  <!-- inner menu: contains the actual data -->
                  <div class="slimScrollDiv"><ul class="menu">
                    <li>
                      <a href="<c:url value="/repair"/>">
                        <p>перейти к заявкам</p>
                      </a>
                    </li>
                  </ul>
                  </div>
                </li>
              </ul>
            </li>
          </security:authorize>
          <li><a href="<c:url value="/about"/>">О системе</a></li>

          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="true">${user}<span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="<c:url value="/logout"/>">Выйти</a></li>
            </ul>
          </li>
        </ul>
      </div><!--/.nav-collapse -->
    </div><!--/.container-fluid -->
  </nav>
</div>
<div class="row" style="background-color:lavender;min-height:800px;">
  <div class="col-md-2" style="overflow-y: scroll;">
    <div class="tree well" align="left">
      <ul id="all_equipments">
        <c:forEach items="${subdivisions}" var="subdivision" begin="0" end="${subdivisions.size()}" varStatus="status">
          <li>
            <span >${subdivisions.get(status.index).subdivision_name}</span>
          </li>
        </c:forEach>
      </ul>
    </div>
  </div>

  <div class="col-md-8" style="background:beige;min-height:600px;">
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

  </div>

  <div class="col-md-2">
    <input type="text" class="form-control input-sm" placeholder="Поиск">
    </br>
    <style>
      #calendar2 {
        width: 100%;
        font: monospace;
        line-height: 1.2em;
        font-size: 15px;
        text-align: center;
      }
      #calendar2 thead tr:last-child {
        font-size: small;
        color: rgb(85, 85, 85);
      }
      #calendar2 thead tr:nth-child(1) td:nth-child(2) {
        color: rgb(50, 50, 50);
      }
      #calendar2 thead tr:nth-child(1) td:nth-child(1):hover, #calendar2 thead tr:nth-child(1) td:nth-child(3):hover {
        cursor: pointer;
      }
      #calendar2 tbody td {
        color: rgb(44, 86, 122);
      }
      #calendar2 tbody td:nth-child(n+6), #calendar2 .holiday {
        color: rgb(231, 140, 92);
      }
      #calendar2 tbody td.today {
        background: rgb(220, 0, 0);
        color: #fff;
      }
    </style>

    <table id="calendar2">
      <thead>
      <tr><td>‹<td colspan="5"><td>›
        <tr><td>Пн<td>Вт<td>Ср<td>Чт<td>Пт<td>Сб<td>Вс
      <tbody>
    </table>

    <script>
      function Calendar2(id, year, month) {
        var Dlast = new Date(year,month+1,0).getDate(),
                D = new Date(year,month,Dlast),
                DNlast = new Date(D.getFullYear(),D.getMonth(),Dlast).getDay(),
                DNfirst = new Date(D.getFullYear(),D.getMonth(),1).getDay(),
                calendar = '<tr>',
                month=["Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"];
        if (DNfirst != 0) {
          for(var  i = 1; i < DNfirst; i++) calendar += '<td>';
        }else{
          for(var  i = 0; i < 6; i++) calendar += '<td>';
        }
        for(var  i = 1; i <= Dlast; i++) {
          if (i == new Date().getDate() && D.getFullYear() == new Date().getFullYear() && D.getMonth() == new Date().getMonth()) {
            calendar += '<td class="today">' + i;
          }else{
            calendar += '<td>' + i;
          }
          if (new Date(D.getFullYear(),D.getMonth(),i).getDay() == 0) {
            calendar += '<tr>';
          }
        }
        for(var  i = DNlast; i < 7; i++) calendar += '<td>&nbsp;';
        document.querySelector('#'+id+' tbody').innerHTML = calendar;
        document.querySelector('#'+id+' thead td:nth-child(2)').innerHTML = month[D.getMonth()] +' '+ D.getFullYear();
        document.querySelector('#'+id+' thead td:nth-child(2)').dataset.month = D.getMonth();
        document.querySelector('#'+id+' thead td:nth-child(2)').dataset.year = D.getFullYear();
        if (document.querySelectorAll('#'+id+' tbody tr').length < 6) {  // чтобы при перелистывании месяцев не "подпрыгивала" вся страница, добавляется ряд пустых клеток. Итог: всегда 6 строк для цифр
          document.querySelector('#'+id+' tbody').innerHTML += '<tr><td>&nbsp;<td>&nbsp;<td>&nbsp;<td>&nbsp;<td>&nbsp;<td>&nbsp;<td>&nbsp;';
        }
      }
      Calendar2("calendar2", new Date().getFullYear(), new Date().getMonth());
      // переключатель минус месяц
      document.querySelector('#calendar2 thead tr:nth-child(1) td:nth-child(1)').onclick = function() {
        Calendar2("calendar2", document.querySelector('#calendar2 thead td:nth-child(2)').dataset.year, parseFloat(document.querySelector('#calendar2 thead td:nth-child(2)').dataset.month)-1);
      }
      // переключатель плюс месяц
      document.querySelector('#calendar2 thead tr:nth-child(1) td:nth-child(3)').onclick = function() {
        Calendar2("calendar2", document.querySelector('#calendar2 thead td:nth-child(2)').dataset.year, parseFloat(document.querySelector('#calendar2 thead td:nth-child(2)').dataset.month)+1);
      }
    </script>
  </div>



</body>