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

    function addRepairSheet () {
      jQuery("#new_repair").dialog({
        title: "Добавление новой заявки",
        width:550,
        height: 470,
        modal: true,
        resizable:false
      });


      $.datepicker.setDefaults(
              $.extend(
                      {'dateFormat':'yy-mm-dd'},
                      $.datepicker.regional['ru']
              )
      );
      $("#datepicker1").datepicker({ dateFormat: 'yy-mm-dd' });

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
        $('#equipments ').html('<option>загрузка...</option>');
        $.ajax({
          type: "GET",
          contentType: 'application/json',
          url: "/equipments/work/subdivisions/"+subdivisions,
          dataType: 'json',
        }).done(function( data ) {
          var options = '';
          for(var i =0;i<data.length;i++) {
            options += '<option value="' + data[i].equipmentId + '">' + data[i].equipmentName + '</option>';
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
            options += '<option value="' + data[i].componentId + '">' + data[i].spare.spare_name + '</option>';
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

    function addRepair(){
      $("#error1").text("");
      $("#error2").text("");
      $("#error3").text("");
      $("#error4").text("");
      $("#error5").text("");
      $("#error6").text("");
      $("#error10").text("");

      if($("#equipments").val()=="0"){
        $("#error2").text("Укажите оборудование");
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
        var components = $("#components").val();
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
            components:components,
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

</head>
      <div class="content-box-large">
        <div class="panel-heading">
          <security:authorize access="hasRole('ROLE_ADMIN')">
            </br>
            <button class="btn btn-info" onclick="addRepairSheet()">Новая заявка</button>
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
                  <select class="form-control" path="equipment.equipmentId" name="equipments" id="equipments" disabled="disabled">
                    <option value="0">- Выберите оборудование -</option>
                  </select>
                  <p id ="error2" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Комплектующие: "/></label></td>
                <td>
                  <select class="form-control" path="component.componentId" name="components" id="components" disabled="disabled">
                    <c:forEach items="${components}" var="components">
                      <option value="${components.componentId}">${components.spare.spare_name}</option>
                    </c:forEach>
                  </select>
                  <p id ="error10" style="color: red"></p>
                </td>
              </tr>
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
                <div style="align-content: center;">
                <center>
                <td colspan="2">
                  <input type="button"
                         value="<spring:message text="Добавить"/>" onclick="addRepair()"/>
                  <input type="button"
                         value="<spring:message text="Закрыть"/>" onclick="jQuery('#new_repair').dialog('close');"/>
                </center>
                </div>
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
            <th>Статус</th>
            <th>Дата начала</th>
            <th>Цех</th>
            <th>Ответственный</th>
            <th>Описание</th>
          </tr>
          </thead>
          <c:forEach items="${listRepair}" var="repair">
            <tr onclick="openRepair(${repair.repair_sheet_id})">
              <td><span class="glyphicon glyphicon-file"></span></td>
              <td>${repair.sheet_number}</td>
              <c:if test="${repair.status.status_id==1}">
              <td><span class="word">${repair.status.status}</span></td>
              </c:if>
              <c:if test="${repair.status.status_id==2||repair.status.status_id==3}">
                <td><span class="word2">${repair.status.status}</span></td>
              </c:if>
              <c:if test="${repair.status.status_id==4}">
                <td><span class="word4">${repair.status.status}</span></td>
              </c:if>
              <c:if test="${repair.status.status_id==5}">
                <td><span class="word3">${repair.status.status}</span></td>
              </c:if>
              <td><fmt:formatDate value="${repair.start_date}" pattern="dd-MM-yyyy" /></td>
              <td>${repair.subdivision.subdivision_name}</td>
              <td>${repair.responsibleForDelivery.name}</td>
              <td>${repair.description}</td>
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