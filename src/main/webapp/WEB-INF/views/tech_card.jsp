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

    function openTechCard(techCardId){
        $.ajax({
            type: "GET",
            contentType: 'application/json',
            url: "/cards/"+techCardId,
            dataType: 'json',
            mimeType: 'application/json',
        }).done(function( data ) {
            jQuery("#techCardDialog").html("          <div class=\"col-md-4\" style=\"margin: 25px;\">\n\
            <p class=\"form-control-static\"> <b>Номер карты: </b></p>\n\
            <p class=\"form-control-static\"> <b>Оборудование: </b></p>\n\
            <p class=\"form-control-static\"> <b>Тип ремонта: </b></p>\n\
            <p class=\"form-control-static\"> <b>Дата:</b></p>\n\
            <p class=\"form-control-static\"> <b>Ответственный:</b></p>\n\
            <p class=\"form-control-static\"> <b>Описание:</b></p>\n\
          </div>\n\
\n\
          <div id = \"info\" class=\"col-md-3\" style=\"margin: 25px;\" align=\"left\">\n\
            <p class=\"form-control-static\" id=\"tech_card_id\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"equipment_id\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"type_of_maintenance_id\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"start_date\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"responsible_for_delivery\"></p>\n\
            \n\
            <p class=\"form-control-static\" id=\"tech_card_title\"></p>");

            desc = data.description;
            jQuery("#tech_card_id").text(data.technological_card_number);
            jQuery("#equipment_id").text(data.equipment.equipmentName);
            jQuery("#type_of_maintenance_id").text(data.type_of_maintenance.type_of_maintenance_name);
            jQuery("#start_date").text(dateConvert(data.start_date));
            jQuery("#responsible_for_delivery").text(data.responsible_for_delivery.responsible);
            jQuery("#tech_card_title").text(desc);
            jQuery("#techCardDialog").dialog({
                title: "Технологическая карта №"+ data.technological_card_number,
                width:650,
                height: 570,
                resizable:false,
                modal: true,
                buttons: [
                    {
                        text: 'Закрыть',
                        click: function() {
                            jQuery("#techCardDialog").dialog('close');
                        }}
                ]
            });
        });
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
            <th>Тип ТО</th>
            <th>Ответственный</th>
            <th>Описание</th>
        </tr>
        </thead>
        <c:forEach items="${listTechCard}" var="tech_card">
            <tr onclick="openTechCard(${tech_card.technological_card_id})">
                <td><span class="glyphicon glyphicon-file"></span></td>
                <td>${tech_card.technological_card_number}</td>
                <c:if test="${tech_card.status.status_id==1}">
                    <td><span class="word">${tech_card.status.status}</span></td>
                </c:if>
                <c:if test="${tech_card.status.status_id==2||tech_card.status.status_id==3}">
                    <td><span class="word2">${tech_card.status.status}</span></td>
                </c:if>
                <c:if test="${tech_card.status.status_id==4}">
                    <td><span class="word4">${tech_card.status.status}</span></td>
                </c:if>
                <c:if test="${repair.status.status_id==5}">
                    <td><span class="word3">${tech_card.status.status}</span></td>
                </c:if>
                <td><fmt:formatDate value="${tech_card.start_date}" pattern="dd-MM-yyyy" /></td>
                <td>${tech_card.typeOfMaintenance.type_of_maintenance_name}</td>
                <td>${tech_card.responsible_for_delivery.name}</td>
                <td>${tech_card.description}</td>
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