<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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

  <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
  <script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

  <script type="text/javascript" src="charts/sources/jscharts.js"></script>

  <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.min.js"></script>

  <script type="text/javascript">

    function getEquipment(){
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
          $("#params_t").append("<tr><td>" + data[i].inventoryNumber + "</td><td>" + data[i].equipmentName + "</td><td>" + data[i].typeOfEquipment.type_of_equipment_name + "</td><td>" + data[i].subdivision.subdivision_name + "</td><td>" + data[i].workingHours +"</td></tr>");
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

  </script>

  <script>
    $(document).ready(function() {
      $("#datepicker1").datepicker({ dateFormat: 'yy' });
      $("#datepicker2").datepicker({ dateFormat: 'yy-mm-dd' });

    });
  </script>

  <style>
    .error {
      color: red; font-weight: bold;
    }
  </style>

</head>
<body onload="getEquipment();">
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
            <form:form method="POST" modelAttribute="equipmentForm" >
              <tr>
                <td><label><spring:message text="Наименование "/></label></td>
                <td><form:input path="equipmentName" id="work_hour1" />
                <p id ="error1" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Тип оборудования "/></label></td>
                <td><form:select path="typeOfEquipment.type_of_equipment_id" class="form-control"  name="maintenanceType" id="work_hour2">
                    <c:forEach items="${eqType}" var="item">
                      <form:option value="${item.type_of_equipment_id}">${item.type_of_equipment_name}</form:option>
                    </c:forEach>
                  </form:select>
                <p id ="error2" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Цех "/></label></td>
                <td>
                  <form:select path="subdivision.subdivision_id"  class="form-control"  name="maintenanceType" id="work_hour3">
                    <c:forEach items="${eqSub}" var="item">
                      <form:option value="${item.subdivision_id}">${item.subdivision_name}</form:option>
                    </c:forEach>
                  </form:select>
                <p id ="error3" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Инвентарный номер "/></label></td>
                <td><form:input  path="inventoryNumber" id="work_hour4" type="number"/>
                <p id ="error4" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Год выпуска "/></label></td>
                <td><form:input path="graduationYear" id="datepicker1" readonly="true"/>
                <p id ="error5" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Изготовитель "/></label></td>
                <td><form:input path="producerOfEquipment" id="work_hour6"/>
                <p id ="error6" style="color: red"></p>
                </td>
              </tr>
              <tr>
                <td><label><spring:message text="Описание "/></label></td>
                <td><form:input  path="description" id="work_hour9" />
                  <p id ="error9" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Наработка "/></label></td>
                <td><form:input path="workingHours" id="work_hour7" type="number"/>
                <p id ="error7" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Статус "/></label></td>
                <td>
                  <form:select path="status.status_id" class="form-control"  name="maintenanceType" id="work_hour8">
                    <c:forEach items="${eqStatus}" var="item">
                      <form:option value="${item.status_id}">${item.status}</form:option>
                    </c:forEach>
                  </form:select>
                <p id ="error8" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td align="center"><input type="submit" value="<spring:message text="Добавить"/>"/></td>
                <td align="center"><input type="button"
                         value="<spring:message text="Закрыть"/>" onclick="jQuery('#new_equipment').dialog('close');"/></td>

              </tr>
              <tr><td><br/></td><td><br/></td></tr>
            </form:form>
          </table>
        </div>
      </div>
</body>