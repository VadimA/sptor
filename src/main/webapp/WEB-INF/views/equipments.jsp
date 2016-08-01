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

  <script type="text/javascript">

    function getUsers(){
      $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/equipment/all",
        dataType: 'json',
        mimeType: 'application/json',
      }).done(function( data ) {
        jQuery("#result").html("</br><button class=\"btn btn-info\"  type=\"button\" onclick=\"openEquipment()\">Добавить оборудование</button></br><table class=\"table table-hover\" id=\"params_t\">\n\
               </br>\
<thead>\n\
  <tr><th>Инвентарный номер</th><th>Наименование</th><th>Тип</th><th>Цех</th><th>Наработка</th></tr>\n\
  </thead>\n\
<tbody>");


        for(var i =0;i<=data.length-1;i++) {
          $("#params_t").append("<tr><td>" + data[i].inventory_number + "</td><td>" + data[i].equipment_name + "</td><td>" + data[i].type_of_equipment.type_of_equipment_name + "</td><td>" + data[i].subdivision.subdivision_name + "</td><td>" + data[i].working_hours +"</td></tr>");
        }
      });
    }

    function openEquipment() {
      jQuery("#new_equipment").dialog({
                title: "Добавление оборудования",
                width:500,
                height: 520,
                resizable:false,
                cache: false,
                modal: true
              }
      );
    }

    function refreshContent()
    {
      history.go(0);
    }

    function addEquipment() {

      $("#error1").text("");
      $("#error2").text("");
      $("#error3").text("");
      $("#error4").text("");
      $("#error6").text("");
      $("#error8").text("");

      if ($("#work_hour1").val() == "") {
        $("#error1").text("Укажите наименование");
      }
      else if($("#work_hour2").val() == "") {
        $("#error2").text("Укажите тип оборудования");
      }
      else if($("#work_hour3").val() == "") {
        $("#error3").text("Укажите цех");
      }
      else if($("#work_hour4").val() == "") {
        $("#error4").text("Укажите инвентарный номер");
      }
      else if($("#work_hour6").val() == "") {
        $("#error6").text("Укажите изготовителя");
      }
      else if($("#work_hour8").val() == "") {
        $("#error8").text("Укажите статус");
      }
      else {
         equipment_name = $("#work_hour1").val();
         type_of_equipment_id = $("#work_hour2").val();
         subdivision_id = $("#work_hour3").val();
         inventory_number = parseInt(document.getElementById("work_hour4").value);
         graduation_year = parseInt(document.getElementById("work_hour5").value);
         producer_of_equipment = $("#work_hour6").val();
         working_hours = parseFloat(document.getElementById("work_hour7").value);
         status = $("#work_hour8").val();
         description = $("#work_hour9").val();

        var that = this;
        $.ajax({
          type: "POST",
          url: "/equipment",
          content: "application/json",
          data: {
            equipment_name: equipment_name,
            type_of_equipment_id: type_of_equipment_id,
            subdivision_id: subdivision_id,
            inventory_number: inventory_number,
            graduation_year:graduation_year,
            producer_of_equipment: producer_of_equipment,
            working_hours: working_hours,
            status: status,
            description: description
          },
          success: function (returnData) {
            $(that).dialog("close");
          }
        });
        refreshContent();
      }
    }

    function refreshContent()
    {
      history.go(0);
    }

  </script>

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
    <div class="col-md-8" style="background:beige;min-height:600px; text-align:  center">
      </br>
      <center>
        <div class="text-center" style="display: table; margin: 0 auto; text-align: center;">
          <div class="btn-toolbar" \>
            <div class="btn-group">
              <button class="btn btn-default"><a href="/users">Пользователи</a></button>
              <button class="btn btn-default"><a href="/equipment">Оборудование</a></button>
              <button class="btn btn-default"><a href="/spare">Запчасти</a></button>
              <button class="btn btn-default"><a href="/subdivision">Цеха</a></button>
            </div>
          </div>
        </div>
      </center>
      <div id="result"></div>
      <div id="new_equipment"  title="Добавление оборудования"  style="display: none">
        <div class="col-md-5" style="margin-top: 45px;margin-left: 50px">
          <table>
            <form>
              <tr>
                <td><label><spring:message text="Наименование "/></label></td>
                <td><input id="work_hour1" />
                <p id ="error1" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Тип оборудования "/></label></td>
                <td><select class="form-control"  name="maintenanceType" id="work_hour2">
                    <c:forEach items="${eqType}" var="item">
                      <option value="${item.type_of_equipment_id}">${item.type_of_equipment_name}</option>
                    </c:forEach>
                  </select>
                <p id ="error2" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Цех "/></label></td>
                <td>
                  <select class="form-control"  name="maintenanceType" id="work_hour3">
                    <c:forEach items="${eqSub}" var="item">
                      <option value="${item.subdivision_id}">${item.subdivision_name}</option>
                    </c:forEach>
                  </select>
                <p id ="error3" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Инвентарный номер "/></label></td>
                <td><input id="work_hour4" type="number"/>
                <p id ="error4" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Год выпуска "/></label></td>
                <td><input id="work_hour5" type="number"/>
                <p id ="error5" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Изготовитель "/></label></td>
                <td><input id="work_hour6"/>
                <p id ="error6" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Наработка "/></label></td>
                <td><input id="work_hour7" type="number"/>
                <p id ="error7" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Статус "/></label></td>
                <td>
                  <select class="form-control"  name="maintenanceType" id="work_hour8">
                    <c:forEach items="${eqStatus}" var="item">
                      <option value="${item.status_id}">${item.status}</option>
                    </c:forEach>
                  </select>
                <p id ="error8" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Описание "/></label></td>
                <td><input id="work_hour9" />
                <p id ="error9" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td align="center"><input type="button" value="<spring:message text="Добавить"/>"
                                          onclick="addEquipment()"/></td>
                <td align="center"><input type="button"
                         value="<spring:message text="Закрыть"/>" onclick="jQuery('#new_equipment').dialog('close');"/></td>

              </tr>
              <tr><td><br/></td><td><br/></td></tr>
            </form>
          </table>
        </div>
      </div>

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