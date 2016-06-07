<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>

<html>
<head>
  <title>ИС ПТОР Заявки</title>
  <!-- Latest compiled and minified CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

  <!-- Optional theme -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>

  <!-- Latest compiled and minified JavaScript -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>

  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>

  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/css/bootstrap-select.min.css">

  <!-- Latest compiled and minified JavaScript -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/js/bootstrap-select.min.js"></script>

  <script type="text/javascript">

    function addRepairSheet () {
      jQuery("#new_repair").dialog({
        title: "Добавление новой заявки",
        width:550,
        height: 470,
        modal: true,
        resizable:false
      });
      $('#subdivisions').change(function () {
        var subdivisions = $(this).val();
        if (subdivisions == '0') {
          $('#equipments').html('<option>- Выберите оборудование -</option>');
          $('#equipments').attr('disabled', true);
          $('#components').html('<option>- Выберите комплектующие -</option>');
          $('#components').attr('disabled', true);
          return(false);
        }
        $('#equipments').attr('disabled', true);
        $('#equipments').html('<option>загрузка...</option>');
        $.ajax({
          type: "GET",
          contentType: 'application/json',
          url: "/equipments/subdivisions/"+subdivisions,
          dataType: 'json',
        }).done(function( data ) {
          var options = '';
          for(var i =0;i<data.length;i++) {
            options += '<option value="' + data[i].equipment_id + '">' + data[i].equipment_name + '</option>';
          }
          $('#equipments').html(options);
          $('#equipments').attr('disabled', false);
          $('#components').html('<option>- Выберите комплектующие -</option>');
          $('#components').attr('disabled', true);
        });
      });

      //type_of_equipment = 0;
      $('#equipments').change(function () {
        equipments = $('#equipments :selected').val();
        if (equipments == '0') {
          $('#components').html('<option>- Выберите комплектующие -</option>');
          $('#components').attr('disabled', true);
          return (false);
        }
        $('#components').attr('disabled', true);
        $('#components').html('<option>загрузка...</option>');
        $.ajax({
          type: "GET",
          contentType: 'application/json',
          url: "/components/" + equipments,
          dataType: 'json',
        }).done(function (data) {
          var options = '';
          for(var i =0;i<data.length;i++) {
            options += '<option value="' + data[i].component_id + '">' + data[i].spare.spare_name + '</option>';
          }
          $('#components').html(options);
          $('#components').attr('disabled', false);
        });
      });
    }

    function openRepair(repairId){
      $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/repair/"+repairId,
        dataType: 'json',
        mimeType: 'application/json',
      }).done(function( data ) {

        jQuery("#repairDialog").html("          <div class=\"col-md-4\" style=\"margin: 5px;\">\n\
            <p class=\"form-control-static\"> <b>Номер заявки: </b></p>\n\
            <p class=\"form-control-static\"> <b>Цех: </b></p>\n\
            <p class=\"form-control-static\"> <b>Оборудование: </b></p>\n\
            <p class=\"form-control-static\"> <b>Тип ремонта: </b></p>\n\
            <p class=\"form-control-static\"> <b>Дата заявки:</b></p>\n\
            <p class=\"form-control-static\"> <b>Ответственный:</b></p>\n\
            <p class=\"form-control-static\"> <b>Описание:</b></p>\n\
            <p class=\"form-control-static\"> <b>Статус:</b></p>\n\
            <p class=\"form-control-static\"> <b>Дата начала работ:</b></p>\n\
          </div>\n\
\n\
          <div id = \"info\" class=\"col-md-4\" style=\"margin: 5px;\" align=\"left\">\n\
            <p class=\"form-control-static\" id=\"repair_sheet_id\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"subdivision_id\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"equipment_id\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"type_of_maintenance_id\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"start_date\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"responsible_for_delivery\"></p>\n\
            \n\
            <p class=\"form-control-static\" id=\"repair_title\"></p>\n\
            \n\
            <p class=\"form-control-static\" id=\"status\"></p>\n\
            \n\
            <input id=\"datepicker3\" readonly=\"true\"  tabindex=\"-1\" />\n\
            <p id =\"errordate\" style=\"color: red\"></p>\n\
            <input type=\"text\" id=\"reason\"></p>\n\
            <p id =\"errorreason\" style=\"color: red\"></p>\n\
            <p id=\"buttons\"></p>");

        var date = new Date(data.start_date);
        var month = date.getMonth() + 1;
        var day = date.getDate();
        if(day<10){
          day="0"+day;
        }
        if(month<10){
          month ="0" +month;
        }
        var dtade = day + "-" + month + "-" + date.getFullYear();
        console.log(data.description);
        desc = data.description;
        jQuery("#repair_sheet_id").text(data.sheet_number);
        jQuery("#subdivision_id").text(data.subdivision.subdivision_name);
        jQuery("#equipment_id").text(data.equipment.equipment_name);
        jQuery("#type_of_maintenance_id").text(data.type_of_maintenance.type_of_maintenance_name);
        jQuery("#start_date").text(dtade);
        jQuery("#responsible_for_delivery").text(data.responsible_for_delivery.last_name);
        jQuery("#repair_title").text(desc);
        jQuery("#status").text(data.status.status);
        jQuery("#buttons").append("<button onclick=\"confirmRepair();\">Перевести в обработку</button>" +
                "<button onclick=\"rejectRepair();\">Отклонить</button> ");

        jQuery("#repairDialog").dialog({
          title: "Заявка №"+ data.sheet_number,
          width:650,
          height: 570,
          resizable:false,
          modal: true,
          buttons: [
            {
              text: 'Закрыть',
              click: function() {
                jQuery("#repairDialog").dialog('close');
                refreshContent();
              }}
          ]
        });
      });
    }

    function confirmRepair(){
      if($("#datepicker1").val()==""){
        $("#errordate").text("Укажите дату");
      }
      else if($("#reason").val()==""){
        $("#errorreason").text("Укажите описание");
      }
      else{
        alert("Заявка подтверждена");
      }
    }

    function rejectRepair(){
      if($("#datepicker1").val()==""){
        $("#errordate").text("Укажите дату");
      }
      else if($("#reason").val()==""){
        $("#errorreason").text("Укажите описание");
      }
      else{
        alert("Заявка отклонена");
      }
    }

    function refreshContent() {
      history.go(0);
    }

    function addRepair(){
      $("#error1").text("");
      $("#error2").text("");
      $("#error3").text("");
      $("#error4").text("");
      $("#error5").text("");
      $("#error6").text("");

      if($("#equipments").val()=="0"){
        $("#error2").text("Укажите оборудование");
      }
      else if($("#components").val()=="0"||$("#components").val()=="- Выберите комплектующие -"){
        $("#error3").text("Укажите компоненты");
      }

      else if($("#datepicker1").val()==""){
        $("#error5").text("Укажите дату");
      }
      else if($("#description").val()==""){
        $("#error6").text("Укажите описание");
      }
      else {
        var subdivision_id = $("#subdivisions").val();
        var equipment_id = $("#equipments").val();
        var type_of_maintenance_id = $("#maintenanceType").val();
        var start_date = $("#datepicker1").val();
        var description = $("#description").val();

        var that = this;
        $.ajax({
          type: "POST",
          url: "/repair/add",
          content: "application/json",
          dataType: "json",
          data: {
            subdivision_id: subdivision_id,
            equipment_id: equipment_id,
            type_of_maintenance_id: type_of_maintenance_id,
            start_date: start_date,
            description: description
          },
          success: function (returnData) {
            $(that).dialog("close");
          }
        });
        refreshContent();
      }
    }
  </script>

  <script>
    $(document).ready(function() {
      $("#datepicker1").datepicker({ dateFormat: 'yy-mm-dd'});
    });
  </script>

</head>
<body>
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
              <li><a href="<c:url value="/manual"/>">Справочники</a></li>
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
                  <span class="glyphicon glyphicon-envelope" style="color: red">${active_req}</span>
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
              <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${user}<span class="caret"></span></a>
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

    <div class="col-md-8" style="background:beige;min-height:600px;">

      <div class="content-box-large">
        <div class="panel-heading">
          <div class="panel-title">Заявки</div>
          <security:authorize access="hasRole('ROLE_ADMIN')">
            </br>
            <button class="btn btn-default" onclick="addRepairSheet()">Новая заявка</button>
          </security:authorize>
        </div>

        <div id="new_repair"  title="Добавление новой заявки" style="display: none">

          <form:form  modelAttribute="repairSheet" >
            <table>
              <tr>
                <td><label><spring:message text="Цех: "/></label></td>
                <td>
                  <select class="form-control" path="subdivision.subdivision_id" name="subdivisions" id="subdivisions">
                    <c:forEach items="${subdivisions}" var="subdivisions">
                      <option value="${subdivisions.subdivision_id}">${subdivisions.subdivision_name}</option>
                    </c:forEach>
                  </select>
                  <p id ="error1" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Оборудование: "/></label></td>
                <td>
                  <select class="form-control" path="equipment.equipment_id" name="equipments" id="equipments" disabled="disabled">
                    <option value="0">- Выберите оборудование -</option>
                  </select>
                  <p id ="error2" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Вид ремонта"/></label></td>
                <td>
                  <select class="form-control" path="type_of_maintenance.type_of_maintenance_id" name="maintenanceType" id="maintenanceType">
                    <c:forEach items="${listTypeOfMaintenance}" var="maintenance">
                      <option value="${maintenance.type_of_maintenance_id}">${maintenance.type_of_maintenance_name}</option>
                    </c:forEach>
                  </select>
                  <p id ="error4" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Дата"/></label></td>
                <td><form:input path="start_date" id="datepicker1" readonly="true"/>
                  <p id ="error5" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Описание"/></label></td>
                <td><form:input path="description" id="description"/>
                  <p id ="error6" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td colspan="2">
                  <input type="button"
                         value="<spring:message text="Добавить"/>" onclick="addRepair()"/>
                  <input type="button"
                         value="<spring:message text="Закрыть"/>" onclick="jQuery('#new_repair').dialog('close');"/>

                </td>
              </tr>
            </table>
          </form:form>
        </div>
        <div class="panel-body" id="allrepair">
          <div class="progress">
            <div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="${status_three * 10}" aria-valuemin="0" aria-valuemax="100" style="width: ${status_three/status_all*100}%">
              <span>Выполненные(${status_three})</span>
            </div>
          </div>
          <div class="progress">
            <div class="progress-bar progress-bar-info progress-bar-striped" role="progressbar" aria-valuenow="${status_one * 10}" aria-valuemin="0" aria-valuemax="100" style="width: ${status_one/status_all*100}%">
              <span>Новые(${status_one})</span>
            </div>
          </div>
          <div class="progress">
            <div class="progress-bar progress-bar-warning progress-bar-striped" role="progressbar" aria-valuenow="${status_two * 10}" aria-valuemin="0" aria-valuemax="100" style="width: ${status_two/status_all*100}%">
              <span>В обработке(${status_two})</span>
            </div>
          </div>
          <div class="progress">
            <div class="progress-bar progress-bar-danger progress-bar-striped" role="progressbar" aria-valuenow="${status_four * 10}" aria-valuemin="0" aria-valuemax="100" style="width: ${status_four/status_all*100}%">
              <span>Отмененные(${status_four})</span>
            </div>
          </div>
        </div>

      </div>
      <div id="repair">
        <table class="table table-hover">
          <thead>
          <tr>
            <th></th>
            <th>Номер заявки</th>
            <th>Дата начала</th>
            <th>Цех</th>
            <th>Ответственный</th>
            <th>Описание</th>
          </tr>
          </thead>
          <c:forEach items="${listRepair}" var="repair">
            <tr>
              <td onclick="openRepair(${repair.repair_sheet_id})"><span class="glyphicon glyphicon-file"></span></td>
              <td>${repair.sheet_number}</td>
              <td><fmt:formatDate value="${repair.start_date}" pattern="dd-MM-yyyy" /></td>
              <td>${repair.subdivision.subdivision_name}</td>
              <td>${repair.responsible_for_delivery.last_name}</td>
              <td>${repair.description}</td>
            </tr>
          </c:forEach>
        </table>
      </div>
      <div id="repairDialog" style="display: none"></div>
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