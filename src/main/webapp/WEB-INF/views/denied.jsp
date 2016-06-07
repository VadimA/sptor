<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

  <script>
    $(document).ready(function() {

      $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/repair/all",
        dataType: 'json',
        mimeType: 'application/json',
      }).done(function( data ) {

        mas = [];
        for (var i = 0; i <= data.length - 1; i++) {
          var id = data[i].repair_sheet_id;
          var date = new Date(data[i].start_date);
          var mm = date.getMonth() + 1;
          var dtade = data[i].start_date;
          var desc = data[i].type_of_maintenance.type_of_maintenance_name + " " + data[i].equipment.type_of_equipment.type_of_equipment_name + " " + data[i].equipment.equipment_name;
          var t = {id : id,start: dtade, title: desc, allDay: true, url: "/repair"};
          mas.push(t);
        }
        console.log(mas);

        var date = new Date();
        var d = date.getDate();
        var m = date.getMonth();
        var y = date.getFullYear();

        $('#calendar').fullCalendar({
          header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
          },
          defaultDate: new Date(y, m, d),
          lang: 'ru',
          buttonIcons: false, // show the prev/next text
          weekNumbers: true,
          editable: true,
          eventLimit: true, // allow "more" link when too many events
          events: mas
        });


        //renderCalendar();
      });
    });
  </script>

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
          <li><a href="<c:url value="/equipments"/>">Оборудование</a></li>
          <li><a href="<c:url value="/repair"/>">Заявки</a></li>
          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="true">План-график<span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="<c:url value="/graphics"/>">Годовой</a></li>
              <li><a href="<c:url value="/graphics"/>">Месячный</a></li>
            </ul>
          </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
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
          <li><a href="<c:url value="/subdivisions"/>">О системе</a></li>

          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${user}<span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="<c:url value="/profile"/>">Профиль</a></li>
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

  <div class="col-md-8" style="background:beige;min-height:600px;">
    </br>
    <center><h2>Недостаточно прав, для доступа к данному модулю.</h2></center>
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