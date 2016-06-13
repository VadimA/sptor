<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>

<html>
<head>
  <title>ИС ПТОР Оборудование</title>
  <!-- Latest compiled and minified CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

  <!-- Optional theme -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>

  <!-- Latest compiled and minified JavaScript -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>


  <script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.min.js"></script>

  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

  <script type="text/javascript" src="charts/sources/jscharts.js"></script>

  <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.css">
  <script src="//cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
  <script src="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.min.js"></script>
  

  <script>
    $(document).ready(function() {
      $("#datepicker1").datepicker({ dateFormat: 'yy-mm-dd' });
      $("#datepicker2").datepicker({ dateFormat: 'yy-mm-dd' });

    });
  </script>

  <style>
    .error {
      color: red; font-weight: bold;
    }
  </style>

</head>
<body onload="getUsers();">
<div id="container">
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
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">План-график<span class="caret"></span></a>
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
  <div class="row" style="background-color:lavender;min-height:600px;">
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
      <div id="result"></div>
      <h3>Добавить новый документ</h3>
      <form:form method="post" action="/documents/save" commandName="document" enctype="multipart/form-data">
        <form:errors path="*" cssClass="error"/>
        <table>
          <tr>
            <td><form:label path="document_name">Name</form:label></td>
            <td><form:input path="document_name" /></td>
          </tr>
          <tr>
            <td><form:label path="description">Description</form:label></td>
            <td><form:textarea path="description" /></td>
          </tr>
          <tr>
            <td><form:label path="content">Document</form:label></td>
            <td><input type="file" name="file" id="file"></input></td>
          </tr>
          <tr>
            <td colspan="2">
              <input type="submit" value="Add Document"/>
            </td>
          </tr>
        </table>
      </form:form>

      <br/>
      <h3>Список документов</h3>
      <c:if  test="${!empty documentList}">
        <table class="data">
          <tr>
            <th>Имя</th>
            <th>Описание</th>
            <th>&nbsp;</th>
          </tr>
          <c:forEach items="${documentList}" var="document">
            <tr>
              <td width="100px">${document.document_name}</td>
              <td width="250px">${document.description}</td>
              <td width="20px">
                <a href="/documents/download/${document.document_id}">
                        border="0"
                        title="Загрузить документ"/><span class="glyphicon glyphicon-file"></span></a>

                <a href="/documents/remove/${document.document_id}"
                   onclick="return confirm('Are you sure you want to delete this document?')"><img
                         border="0"
                        title="Удалить документ"/><span class="glyphicon glyphicon-file"></span></a>
              </td>
            </tr>
          </c:forEach>
        </table>
      </c:if>

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

    <script src="js/vendor/jquery-1.9.1.min.js"></script>
    <script src="js/vendor/jquery-1.9.1.min.js"></script>
    <script src="js/vendor/bootstrap.min.js"></script>
    <script src="js/main.js"></script>

    <script src="js/jquery.ba-cond.min.js"></script>
    <script src="js/jquery.slitslider.js"></script>
  </div>
</body>