<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

  <title>ИС ПТОР Заявки</title>

  <style>
    .word
    {
      color:blue;
    }
    .word2
    {
      color:orange;
    }
    .word3
    {
      color:red;
    }
    .word4
    {
      color:green;
    }
  </style>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script type="text/javascript">

    function openRepair(repairId){
      $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/repair/"+repairId,
        dataType: 'json',
        mimeType: 'application/json',
      }).done(function( data ) {

        jQuery("#repairDialog").html("<div class=\"col-md-6\" style=\"margin: 8px;\">\n\
            <p class=\"form-control-static\"> <b>Номер заявки: </b></p>\n\
            <p class=\"form-control-static\"> <b>Цех: </b></p>\n\
            <p class=\"form-control-static\"> <b>Оборудование: </b></p>\n\
            <p class=\"form-control-static\"> <b>Тип ремонта: </b></p>\n\
            <p class=\"form-control-static\"> <b>Дата заявки:</b></p>\n\
            <p class=\"form-control-static\"> <b>Ответственный:</b></p>\n\
            <p class=\"form-control-static\"> <b>Описание:</b></p>\n\
            <p class=\"form-control-static\"> <b>Статус:</b></p>\n\
          </div>\n\
\n\
          <div id = \"info\" class=\"col-md-5\" style=\"margin: 8px;\" align=\"left\">\n\
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
            <p class=\"form-control-static\" id=\"status\"></p></div>");

        var date = new Date(data.start_date);
        year = date.getFullYear();
        month = date.getMonth() + 1;
        day = date.getDate();
        if(day<10){
          day="0"+day;
        }
        if(month<10){
          month ="0" +month;
        }
        var dtade = day + "-" + month + "-" + date.getFullYear();
        desc = data.description;
        repair_id = data.repair_sheet_id;
        status = data.status.status_id;
        start_date = dtade;
        toir_type = data.type_of_maintenance.type_of_maintenance_name;
        toir_id = data.type_of_maintenance.type_of_maintenance_id;
        jQuery("#repair_sheet_id").text(data.sheet_number);
        jQuery("#subdivision_id").text(data.subdivision.subdivision_name);
        jQuery("#equipment_id").text(data.equipment.equipmentName);
        jQuery("#type_of_maintenance_id").text(data.type_of_maintenance.type_of_maintenance_name);
        jQuery("#start_date").text(dtade);
        jQuery("#responsible_for_delivery").text(data.responsibleForDelivery.name);
        jQuery("#repair_title").text(desc);
        jQuery("#status").text(data.status.status);

        <security:authorize access="hasRole('ROLE_REPAIR')">
        if(status==1) {
          jQuery("#repairDialog").append("</br><center><div><p><b>Дата начала работ:</b></p><input id=\"datepicker3\" readonly=\"true\"  tabindex=\"-1\" /><p id =\"errordate\" style=\"color: red\"></p>\n\
            <p><b>Комментарий:</b></p><input type=\"text\" id=\"reason\"/><p id =\"errorreason\" style=\"color: red\"></p></br><button onclick=\"confirmRepair(1);\">Перевести в обработку</button>" +
                  "<button onclick=\"rejectRepair(1);\">Отклонить</button></div></center>");
        }

        if(status==3) {

          jQuery("#repairDialog").append("</br><center><div><p><b>Дата окончания работ:</b></p><input id=\"datepicker4\" readonly=\"true\"  tabindex=\"-1\" /><p id =\"errordate2\" style=\"color: red\"></p>\n\
            </br><button onclick=\"confirmRepair(3);\">Заявка выполнена</button>");
        }
        </security:authorize>

        $("#datepicker3").datepicker({ dateFormat: 'yy-mm-dd', minDate: new Date(year,month-1,day)  });
        $("#datepicker4").datepicker({ dateFormat: 'yy-mm-dd', minDate: new Date(year,month-1,day)  });

        <security:authorize access="hasRole('ROLE_ADMIN')">
        if(status==2) {
          jQuery("#repairDialog").append("<center><div style=\"align-content: center;\"><p style=\"align-content: center;\"> <b>Комментарий:</b></p><input id=\"manager_comment\"/>\n\
                <p id =\"manager_error\" style=\"color: red\"></p></br><button onclick=\"confirmRepair(2);\">Подтвердить</button><spacer width=\"100\" type=\"block\">" +
                  "<button onclick=\"rejectRepair(2);\">Отправить на пересмотр</button></div></center>");
        }
        if(status==3&&(toir_id==3||toir_id==4||toir_id==5)){
          jQuery("#repairDialog").append("<center><div style=\"align-content: center;\"><button onclick=\"generateActIn();\">Сформировать акт передачи</button></div></center>");
        }
        if(status==5&&(toir_id==3||toir_id==4||toir_id==5)){
          jQuery("#repairDialog").append("<center><div style=\"align-content: center;\"><button onclick=\"generateActOut();\">Сформировать акт приемки</button></div></center>");
        }
        </security:authorize>

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
              }
            }
          ]
        });
      });
    }

    function confirmRepair(statusId){
      if(statusId==1) {
        $("#errordate").text("");
        $("#errorreason").text("");

        if ($("#datepicker3").val() == "") {
          $("#errordate").text("Укажите дату");
        }
        else {
          var date = $("#datepicker3").val();
          var description = $("#reason").val();
          var status = 2;
          var that = this;
          $.ajax({
            type: "POST",
            url: "/repair/" + repair_id,
            content: "application/json",
            dataType: "json",
            data: {
              date: date,
              status: status,
              description: description
            },
            success: function (returnData) {
              alert("Заявка успешно обработана");
              $(that).dialog("close");
            }
          });
          refreshContent();
        }
      }
      else if(statusId==2){
        $("#manager_error").text("");

        var description = $("#manager_comment").val();
        var status = 3;
        var that = this;
        $.ajax({
          type: "POST",
          url: "/repair/confirm/" + repair_id,
          content: "application/json",
          dataType: "json",
          data: {
            status: status,
            description: description
          },
          success: function (returnData) {
            alert("Заявка подтверждена");
            $(that).dialog("close");
          }
        });
        refreshContent();
      }
      else if(statusId==3) {

        $("#errordate2").text("");
        if ($("#datepicker4").val() == "") {
          $("#errordate2").text("Укажите дату");
        }
        else {
          var date = $("#datepicker4").val();
          var description = "";
          var status = 4;
          var that = this;
          $.ajax({
            type: "POST",
            url: "/repair/" + repair_id,
            content: "application/json",
            dataType: "json",
            data: {
              date: date,
              status: status,
              description: description
            },
            success: function (returnData) {
              alert("Заявка выполнена");
              $(that).dialog("close");
            }
          });
          refreshContent();
        }
      }
    }

    function rejectRepair(statusId){

      if(statusId==1) {
        $("#errordate").text("");
        $("#errorreason").text("");

        if ($("#datepicker3").val() == "") {
          $("#errordate").text("Укажите дату");
        }
        else if ($("#reason").val() == "") {
          $("#errorreason").text("Укажите описание");
        }
        else {
          var date = $("#datepicker3").val();
          var description = $("#reason").val();
          var status = 5;
          var that = this;
          $.ajax({
            type: "POST",
            url: "/repair/" + repair_id,
            content: "application/json",
            dataType: "json",
            data: {
              date: date,
              status: status,
              description: description
            },
            success: function (returnData) {
              alert("Заявка отклонена");
              $(that).dialog("close");
            }
          });
          refreshContent();
        }
      }
      else if(statusId==2){
        $("#manager_error").text("");

        if ($("#manager_comment").val() == "") {
          $("#manager_error").text("Укажите причину");
        }
        else{
          var description = $("#manager_comment").val();
          var status = 1;
          var that = this;
          $.ajax({
            type: "POST",
            url: "/repair/confirm/" + repair_id,
            content: "application/json",
            dataType: "json",
            data: {
              status: status,
              description: description
            },
            success: function (returnData) {
              alert("Заявка отправлена на пересмотр");
              $(that).dialog("close");
            }
          });
          refreshContent();
        }
      }
    }

    function generateActIn(){
        $.ajax({
          type: "POST",
          url: "/report/repair/act_in/" + repair_id,
          content: "application/json",
          dataType: "json",
          data: {
            toir_type: toir_type
          },
          success: function (returnData) {
              alert("Акт сформирован");
            $(that).dialog("close");
          }
        });
    }
    function generateActOut(){
        $.ajax({
          type: "POST",
          url: "/report/repair/act_out/" + repair_id,
          content: "application/json",
          dataType: "json",
          data: {
            toir_type: toir_type
          },
          success: function (returnData) {
              alert("Акт сформирован");
            $(that).dialog("close");
          }
        });
    }
    function refreshContent() {
      history.go(0);
    }

  </script>

</head>
      <div class="content-box-large">
        <div class="panel-body" id="allrepair">
          <div class="progress">
            <div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="${tech_status_three * 10}" aria-valuemin="0" aria-valuemax="100" style="width: ${tech_status_three/tech_status_all*100}%">
              <span>Выполненные(${tech_status_three})</span>
            </div>
          </div>
          <div class="progress">
            <div class="progress-bar progress-bar-info progress-bar-striped" role="progressbar" aria-valuenow="${tech_status_one * 10}" aria-valuemin="0" aria-valuemax="100" style="width: ${tech_status_one/tech_status_all*100}%">
              <span>Новые(${tech_status_one})</span>
            </div>
          </div>
          <div class="progress">
            <div class="progress-bar progress-bar-warning progress-bar-striped" role="progressbar" aria-valuenow="${tech_status_two * 10}" aria-valuemin="0" aria-valuemax="100" style="width: ${tech_status_two/tech_status_all*100}%">
              <span>В обработке(${tech_status_two})</span>
            </div>
          </div>
          <div class="progress">
            <div class="progress-bar progress-bar-danger progress-bar-striped" role="progressbar" aria-valuenow="${tech_status_four * 10}" aria-valuemin="0" aria-valuemax="100" style="width: ${tech_status_four/tech_status_all*100}%">
              <span>Отмененные(${tech_status_four})</span>
            </div>
          </div>
        </div>

      </div>
      <div id="repair">
        <table class="table table-hover">
          <thead>
          <tr>
            <th></th>
            <th>Номер тех.карты</th>
            <th>Статус</th>
            <th>Дата начала</th>
            <th>Ответственный</th>
            <th>Описание</th>
          </tr>
          </thead>
          <c:forEach items="${listTechCard}" var="techCard">
            <tr onclick="openRepair(${techCard.technological_card_id})">
              <td><span class="glyphicon glyphicon-file"></span></td>
              <td>${techCard.technological_card_number}</td>
              <c:if test="${techCard.status.status_id==1}">
              <td><span class="word">${techCard.status.status}</span></td>
              </c:if>
              <c:if test="${techCard.status.status_id==2||repair.status.status_id==3}">
                <td><span class="word2">${techCard.status.status}</span></td>
              </c:if>
              <c:if test="${techCard.status.status_id==4}">
                <td><span class="word4">${techCard.status.status}</span></td>
              </c:if>
              <c:if test="${techCard.status.status_id==5}">
                <td><span class="word3">${techCard.status.status}</span></td>
              </c:if>
              <td><fmt:formatDate value="${techCard.start_date}" pattern="dd-MM-yyyy" /></td>
              <td>${techCard.responsible_for_delivery.name}</td>
              <td>${techCard.description}</td>
            </tr>
          </c:forEach>
        </table>
      </div>
      <div id="repairDialog" style="display: none"></div>
      <div id="saveDialog" style="display: none"></div>


    <script src="js/vendor/jquery-1.9.1.min.js"></script>
    <script src="js/vendor/jquery-1.9.1.min.js"></script>
    <script src="js/vendor/bootstrap.min.js"></script>
    <script src="js/main.js"></script>

    <script src="js/jquery.ba-cond.min.js"></script>
    <script src="js/jquery.slitslider.js"></script>
</body>