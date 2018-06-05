<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>

<head>
  <title>ИС ПТОР Оборудование</title>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <style>
        .about {
            display:inline-block;
        }
    </style>

    <script type="text/javascript">
        google.load("visualization", "1", {packages:["corechart"]});
        google.setOnLoadCallback(drawChart);
        function drawChart() {
            var data = google.visualization.arrayToDataTable([
                ['Состояние', 'Количество'],
                ['в эксплуатации',     ${allEquipment}],
                ['на ТО',      ${TOEquipment}],
                ['в ремонте',  ${repairEquipment}],
                ['на консервации', ${ConservEquipment}]
            ]);

            var options = {
                title: 'Состояние оборудования',
                colors: ['#228B22', '#FFA500', '#FF4500', '#B0C4DE']
            };

            var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
            chart.draw(data, options);
        }
        if (window.location.hash) {
            var hashJson = window.location.hash.substring(1);
            window.location.hash = "";
            // Restore the deserialized data to memory
            myData = decodeURIComponent(hashJson).split('##')[3];
            current_equipment = decodeURIComponent(hashJson).split('##')[0];
            current_equipment_name = decodeURIComponent(hashJson).split('##')[1];
            current_equipment_type = decodeURIComponent(hashJson).split('##')[2];
            console.log("BBBB ===== ",myData);
            console.log("EQUIPMENT ===== ",current_equipment);
            jQuery("#result").html(myData);
        }
  </script>


</head>
      <div class="text-center" style="display: table; margin: 0 auto; text-align: center;">
        <div class="btn-toolbar" \>
          <div class="btn-group">
            <button class="btn btn-default" onclick="SendGet(current_equipment);">Общее</button>
            <button class="btn btn-default" onclick="getParams(current_equipment_type);">Характеристики</button>
            <button class="btn btn-default" onclick="getSpare(current_equipment);">Комплектующие</button>
            <button class="btn btn-default" onclick="workedHours(current_equipment);">Наработка</button>
            <button class="btn btn-default" onclick="downTime(current_equipment);">Простой</button>
            <button class="btn btn-default" onclick="getTechCard(current_equipment);">Технологические карты</button>
            <button class="btn btn-default"  onclick="getDocuments(current_equipment);">Документы</button>
           <!--   <script type="text/javascript">
                  document.getElementById("doc_but").onclick = function () {
                      location.href = "/documents/"+current_equipment;
                  };
              </script>-->
          </div>
        </div>
      </div>
      <form class="form-horizontal">
        <div class="form-group" id="result" align="left" style="margin-left: 50px;margin-right: 50px;">
            <div style="display: table; margin: 0 auto; text-align: center;">
                <div id="chart_div" style="width: 600px; height: 450px;margin-top: 20px"></div>

                </div>
        </div>
        <div id='timeline-embed' style="display: none; width: 100%; height: 600px"></div>
        <div id="new_working_hours"  title="Добавление наработки" style="display: none">

            <form id="work_form" >
                <div class="form-group">
                    <label class="control-label" for="work_hour">
                        Наработка
                    </label>
                    <input type="number" placeholder="0.0" name="work_hour" id="work_hour" class="form-control lgn">
                <p id="errormsg" style="color: red"></p>
                </div>
            </form>
        </div>

          <div id="new_downtime"  title="Добавление простоя оборудования" style="display: none">

              <form id="downtime_form" >
                  <div class="form-group">
                      <label class="control-label" for="downtime">
                          Простой
                      </label>
                      <input type="number" placeholder="0.0" name="downtime" id="downtime" class="form-control lgn">
                      <p id="errormsg2" style="color: red"></p>
                  </div>
              </form>
          </div>
      </form>

        <div id="new_doc" style="display: none">

            <form:form method="post" action="/documents/save" commandName="document" enctype="multipart/form-data">
                <form:errors path="*" cssClass="error"/>
                <table>
                    <tr><td><br/></td><td><br/></td></tr>
                    <tr>
                        <td><form:label path="document_name">Наименование &nbsp</form:label></td>
                        <td><form:input path="document_name" /></td>
                    </tr>
                    <tr><td><br/></td><td><br/></td></tr>
                    <tr>
                        <td><form:label path="description">Описание &nbsp</form:label></td>
                        <td><form:textarea rows="4" cols="30" path="description" id="description"/></td>
                    </tr>
                    <tr><td><br/></td><td><br/></td></tr>
                    <tr>
                        <td><form:label path="content">Документ &nbsp</form:label></td>
                        <td><input type="file" name="file" id="file"></input></td>
                    </tr>
                    <tr><td><br/></td><td><br/></td></tr>
                    <tr>
                        <td><br/></td>
                        <td>
                            <center>
                                <input type="submit" value="Добавить документ"/>
                            </center>
                        </td>
                    </tr>
                </table>
            </form:form>
        </div>

      <div id="new_tech_card"  title="Добавление новой технологической карты" style="display: none">

        <form:form  modelAttribute="techCard" >
          <table>
            <tr>
              <td><label><spring:message text="Вид ремонта"/></label></td>
              <td>
                <select class="form-control" path="type_of_maintenance.type_of_maintenance_id" name="maintenanceType" id="maintenanceType">
                  <c:forEach items="${listTypeOfMaintenance}" var="maintenance">
                    <option value="${maintenance.type_of_maintenance_id}">${maintenance.type_of_maintenance_name}</option>
                  </c:forEach>
                </select>
              </td>
            </tr>
            <tr><td><br/></td><td><br/></td></tr>
            <tr>
              <td><label><spring:message text="Дата"/></label></td>
              <td><form:input path="start_date" id="datepicker1" readonly="true"/></td>
            </tr>
            <tr><td><br/></td><td><br/></td></tr>
            <tr>
              <td><label><spring:message text="Описание"/></label></td>
              <td><form:textarea rows="4" cols="30" path="description" id="description_tech"/></td>
            </tr>
            <tr><td><br/></td><td><br/></td></tr>
            <tr>
              <td colspan="2">
                <input type="button"
                       value="<spring:message text="Добавить"/>" onclick="addTechnologicalCard()"/>
                <input type="button"
                       value="<spring:message text="Закрыть"/>" onclick="jQuery('#new_tech_card').dialog('close');"/>

              </td>
            </tr>
          </table>
        </form:form>
      </div>
      <div id="techCardDialog" style="display: none"></div>

        <div id="new_doc" style="display: none">

            <form:form method="post" action="/documents/save" commandName="document" enctype="multipart/form-data">
                <form:errors path="*" cssClass="error"/>
                <table>
                    <tr><td><br/></td><td><br/></td></tr>
                    <tr>
                        <td><form:label path="document_name">Наименование </form:label></td>
                        <td><form:input path="document_name" /></td>
                    </tr>
                    <tr><td><br/></td><td><br/></td></tr>
                    <tr>
                        <td><form:label path="description">Описание </form:label></td>
                        <td><form:textarea rows="4" cols="30" path="description" id="description"/></td>
                    </tr>
                    <tr><td><br/></td><td><br/></td></tr>
                    <tr>
                        <td><form:label path="content">Документ </form:label></td>
                        <td><input type="file" name="file" id="file"></input></td>
                    </tr>
                    <tr><td><br/></td><td><br/></td></tr>
                    <tr>
                        <td><br/></td>
                        <td>
                        <center>
                            <input type="submit" value="Добавить документ"/>
                        </center>
                        </td>
                    </tr>
                </table>
            </form:form>
        </div>


    <script src="js/vendor/jquery-1.9.1.min.js"></script>
    <script src="js/vendor/jquery-1.9.1.min.js"></script>
    <script src="js/vendor/bootstrap.min.js"></script>
    <script src="js/main.js"></script>

    <script src="js/jquery.ba-cond.min.js"></script>
    <script src="js/jquery.slitslider.js"></script>