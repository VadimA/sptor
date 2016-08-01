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

 <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/i18n/jquery-ui-i18n.min.js"></script>

    <script src="http://canvg.googlecode.com/svn/trunk/canvg.js"></script>
    <script src="http://canvg.googlecode.com/svn/trunk/rgbcolor.js"></script>
    <script src="http://www.google.com/jsapi?fake=.js"></script>


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

        function getList () {
      $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Свернуть');
      $('.tree li.parent_li > span').on('click', function (e) {
        var children = $(this).parent('li.parent_li').find(' > ul > li');
        if (children.is(":visible")) {
          children.hide('fast');
          $(this).attr('title', 'Развернуть');
        } else {
          children.show('fast');
          $(this).attr('title', 'Свернуть');
        }
        e.stopPropagation();
      });
    }

    function SendGet(equipment_id) {

      $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/equipments/"+equipment_id,
        dataType: 'json',
        mimeType: 'application/json',
      }).done(function( data ) {
        jQuery("#result").html("          <div class='about' style=\"min-width:200px;margin-top: 45px;margin-left: 50px\">\n\
            <p class=\"form-control-static\"><b>Наименование:</b></p>\n\
            <p class=\"form-control-static\"><b>Тип оборудования:</b></p>\n\
            <p class=\"form-control-static\"><b>Местонахождение:</b></p>\n\
            <p class=\"form-control-static\"><b>Инвентарный номер:</b></p>\n\
            <p class=\"form-control-static\"><b>Год выпуска:</b></p>\n\
            <p class=\"form-control-static\"><b>Изготовитель:</b></p>\n\
            <p class=\"form-control-static\"><b>Описание:</b></p>\n\
            <p class=\"form-control-static\"><b>Статус:</b></p>\n\
          </div>\n\
\n\
          <div class='about' style=\"min-width:200px;margin-top: 45px;\" align=\"left\">\n\
            <p class=\"form-control-static\" id=\"eq_name\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"eq_type\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"eq_sub\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"eq_inv\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"eq_year\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"eq_prod\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"eq_desc\"></p>\n\
            <p class=\"form-control-static\" id=\"eq_status\"></p>\n\
          </div>");
          var date = new Date(data.graduation_year);
          var year = date.getFullYear();
        jQuery("#eq_name").text(data.equipment_name);
        jQuery("#eq_type").text(data.type_of_equipment.type_of_equipment_name);
        jQuery("#eq_sub").text(data.subdivision.subdivision_name);
        jQuery("#eq_inv").text(data.inventory_number);
        jQuery("#eq_year").text(year);
        jQuery("#eq_prod").text(data.producer_of_equipment);

        if(data.description == null || data.description ==""){
            jQuery("#eq_desc").text("Отсутствует");
        }
          else{
            jQuery("#eq_desc").text(data.description);
        }
        jQuery("#eq_status").text(data.status.status);

        current_equipment = equipment_id;
        current_equipment_name = data.equipment_name;
        current_equipment_type = data.type_of_equipment.type_of_equipment_id;
      });
    }

    function getTechCard(equipment_id){
      $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/repair/equipment/"+equipment_id,
        dataType: 'json',
        mimeType: 'application/json',
      }).done(function( data ) {
        cards = data;
        jQuery("#result").html("<center></br><h5>Оборудование: " + current_equipment_name + "</h5></center></br><table class=\"table table-hover\" id=\"params_t\">\n\
<thead>\n\
  <tr><th>Номер тех. карты</th><th>Тип ТО</th><th>Дата начала</th><th>Дата окончания</th><th>Статус</th></tr>\n\
  </thead>\n\
<tbody>\n");
        for(var i =0;i<cards.length;i++) {
            var start_date = new Date(cards[i].start_date);
            var mm = start_date.getMonth() + 1;
            var dtade = start_date.getFullYear() + "-" + 0 + mm + "-" + start_date.getDate();
            var end_date = new Date(cards[i].end_date);
            var end_mm = end_date.getMonth() + 1;
            var end_dtade = end_date.getFullYear() + "-" + 0 + end_mm + "-" + end_date.getDate();
            jQuery("#cardd").text(cards[i].technological_card_id);
            $("#params_t").append("<tr><td onclick=\"openTechCard(this.innerHTML)\">" + cards[i].repair_sheet_id + "</td><td>" + cards[i].type_of_maintenance.type_of_maintenance_name + "</td><td>" + dtade + "</td><td>" + end_dtade +"</td><td>" + cards[i].status.status +"</td></tr>");
        }
      });
    }

    function getSpare(equipment_id){
      $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/components/"+equipment_id,
        dataType: 'json',
        mimeType: 'application/json',
      }).done(function( data ) {
        jQuery("#result").html("<center></br><h5>Оборудование: " + current_equipment_name + "</h5></center><table class=\"table table-hover\" id=\"params_t\">\n\
<thead>\n\
  <tr><th>Код</th><th>Номенклатура</th><th>Количество</th><th>Производитель</th><th>Описание</th></tr>\n\
  </thead>\n\
<tbody>");
        for(var i =0;i<data.length;i++) {
          components_id = data[i].component_id;
          $("#params_t").append("<tr><td onclick=\"getRepairBySpare(this.innerHTML)\">" + data[i].component_id + "</td><td>" + data[i].spare.spare_name + "</td><td>" + data[i].amount + "</td><td>" + data[i].spare.spare_producer + "</td><td>" + data[i].spare.description + "</td></tr>");

        }
        jQuery("#result").append("<table class=\"table table-hover\" id=\"repair_t\"></table>");
      });
    }

        function getDocuments(equipment_id){
            $.ajax({
                type: "GET",
                contentType: 'application/json',
                url: "/documents/"+equipment_id,
                dataType: 'json',
                mimeType: 'application/json',
            }).done(function( data) {
                jQuery("#result").html("<center></br><h5>Оборудование: " + current_equipment_name + "</h5></br><button class=\"btn btn-default\"  type=\"button\" onclick=\"addDocuments();\">Добавить документ</button></br></center><table class=\"table table-hover\" id=\"params_t\">\n\
<thead>\n\
  <tr><th>&nbsp;</th><th>Наименование</th><th>Дата добавления</th><th>Описание</th></tr>\n\
  </thead>\n\
<tbody>");
                for(var i =0;i<data.length;i++) {
                    components_id = data[i].component_id;
                    var date = new Date(data[i].date_of_adding);
                    var mm = date.getMonth() + 1;
                    var dtade = date.getFullYear() + "-" + 0 + mm + "-" + date.getDate();
                    $("#params_t").append("<tr><td onclick=\"openDocuments(this.innerHTML)\">" + data[i].document_id + "</td><td>" + data[i].document_name + "</td><td>" + dtade + "</td><td>" + data[i].description + "</td></tr>");

                }
                jQuery("#result").append("<table class=\"table table-hover\" id=\"repair_t\"></table>");
            });
        }

        function addDocuments() {

            jQuery("#new_doc").dialog({
                        title: "Добавление нового документа",
                        width:550,
                        height: 470,
                        resizable: false,
                        cache: false,
                        modal: true
                    }
            );
        }

        function openDocuments(document_id){
            location.href = "/documents/download/"+document_id;
        }

        function getRepairBySpare(component_id){
      $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/components/"+component_id +"/equipment/" + current_equipment,
        dataType: 'json',
        mimeType: 'application/json',
      }).done(function( data ) {
        $("#repair_t").html("<table class=\"table table-hover\" id=\"repair_t\">\n\
        <thead>\n\
          <tr><th>Вид ремонта</th><th>Дата</th></tr>\n\
        </thead>\n\
        <tbody>");
          for(var i =0;i<data.length;i++) {
            var date = new Date(data[i].start_date);
            var month = date.getMonth() + 1;
            var day = date.getDate();
            if(day<10){
              day="0"+day;
            }
            if(month<10){
              month ="0" +month;
            }
            var dtade = day + "-" + month + "-" + date.getFullYear();
            $("#repair_t").append("<tr><td>" + data[i].type_of_maintenance.type_of_maintenance_name + "</td><td>" + dtade + "</td></tr>");
          }
    });
    }

    function getParams(type_id){
      $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/equipments/parameters/"+type_id,
        dataType: 'json',
        mimeType: 'application/json',
      }).done(function( data ) {
        jQuery("#result").html("<center><h5></br>Оборудование: " + current_equipment_name + "</h5></center><table class=\"table table-hover\" id=\"params_t\">\n\
<thead>\n\
  <tr><th>Характеристика</th><th>Значение</th><th>Единица измерения</th></tr>\n\
  </thead>\n\
<tbody>\n");
        for(var i =0;i<data.length;i++) {
          $("#params_t").append("<tr><td>" + data[i].parameter_name + "</td><td>" + data[i].parameter_value +"</td><td>" + data[i].measure.measure_value + "</td></tr>");
        }
      });
    }

    function workedHours(equipment_id){
      $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/working_hours/"+equipment_id,
        dataType: 'json',
        mimeType: 'application/json',
      }).done(function( data ) {
        work_hours = 0;
        if(data.length>=1){
          work_hours = data[data.length-1].value;
        }
        jQuery("#result").html("<center><h5></br>Оборудование: " + current_equipment_name + "</h5></center> <b>Текущая наработка: " + work_hours +  "</b></br></br>\
        <button class=\"btn btn-default\"  type=\"button\" onclick=\"addWorkedHours()\">Добавить наработку</button></br><center>История изменений наработки по текущему образцу оборудования</center><table class=\"table table-hover\" id=\"params_t\"></br></br>\n\
               <div id=\"myfirstchart\" style=\"height: 250px;\"></div> </br>\
<thead>\n\
  <tr><th>Изменил</th><th>Дата изменения</th><th>Значение</th></tr>\n\
  </thead>\n\
<tbody>");

        mas = [];
        for(var i =0;i<=data.length-1;i++) {
          var date = new Date(data[i].date_of_adding);
          var mm = date.getMonth() + 1;
          var dtade = date.getFullYear() + "-" + 0 + mm + "-" + date.getDate();
          var value = data[i].value;
          var t = {year: dtade, value: value};
          mas.push(t);
        }
        console.log(mas);
        new Morris.Line({
          // ID of the element in which to draw the chart.
          element: 'myfirstchart',
          // Chart data records -- each entry in this array corresponds to a point on
          // the chart.
          data: mas,
          // The name of the data record attribute that contains x-values.
          xkey: 'year',
          // A list of names of data record attributes that contain y-values.
          ykeys: ['value'],
          // Labels for the ykeys -- will be displayed when you hover over the
          // chart.
          labels: ['Наработка']
        });
      for(var i =0;i<=data.length-1;i++) {
        var date = new Date(data[i].date_of_adding);
        var mm = date.getMonth() + 1;
        var dtade = date.getFullYear() + "-" + 0 + mm + "-" + date.getDate();
        $("#params_t").append("<tr><td>" + data[i].responsible.last_name + "</td><td>" + dtade + "</td><td>" + data[i].value +"</td></tr>");
      }
      });
    }

    function addWorkedHours() {
      jQuery("#new_working_hours").dialog({
                title: "Добавление наработки",
                width:350,
                height: 270,
                resizable:false,
                cache: false,
                modal: true,
                buttons: [
                  {
                    text: 'Добавить',
                    click: function() {
                        if (parseFloat(document.getElementById("work_hour").value) <= 0 || parseFloat(document.getElementById("work_hour").value)>100) {
                            $("#errormsg").text("Неверное значение");
                        }
                        else {
                            var value = parseFloat(document.getElementById("work_hour").value);
                            var that = this;
                            var eq_this = current_equipment;
                            $(that).dialog("close");
                            $("#work_hour").val("");
                            $.ajax({
                                type: "POST",
                                url: "/working_hours/add",
                                content: "application/json",
                                dataType: "json",
                                context: $(this),
                                data: {value: value, equipment_id: current_equipment},
                                success: function (returnData) {
                                    $(that).dialog("close");
                                    SendGet(current_equipment);
                                    workedHours(current_equipment);
                                    //window.location.reload(true);//$('#container').html(returnData);
                                }
                            });
                        }
                        SendGet(current_equipment);
                        workedHours(current_equipment);
                    }},
                  {
                    text: 'Закрыть',
                    click: function() {
                      jQuery("#new_working_hours").dialog('close');
                      $("#work_hour").val("");
                    }}
                ]
                  }
      );
    }

    function downTime(equipment_id){
        $.ajax({
            type: "GET",
            contentType: 'application/json',
            url: "/downtime/"+equipment_id,
            dataType: 'json',
            mimeType: 'application/json',
        }).done(function( data ) {
            work_hours = 0;
            if(data.length>=1){
                work_hours = data[data.length-1].value;
            }
            jQuery("#result").html("<center><h5></br>Оборудование: " + current_equipment_name + "</h5></center> <b>Текущий простой оборудования: " + work_hours +  "</b></br></br>\
        <button class=\"btn btn-default\"  type=\"button\" onclick=\"addDownTime()\">Добавить простой</button></br><center>История изменений простоя по текущему образцу оборудования</center><table class=\"table table-hover\" id=\"params_t\"></br></br>\n\
               <div id=\"myfirstchart\" style=\"height: 250px;\"></div> </br>\
<thead>\n\
  <tr><th>Изменил</th><th>Дата изменения</th><th>Значение</th></tr>\n\
  </thead>\n\
<tbody>");

            mas = [];
            for(var i =0;i<=data.length-1;i++) {
                var date = new Date(data[i].date_of_adding);
                var mm = date.getMonth() + 1;
                var dtade = date.getFullYear() + "-" + 0 + mm + "-" + date.getDate();
                var value = data[i].value;
                var t = {year: dtade, value: value};
                mas.push(t);
            }
            console.log(mas);
            new Morris.Line({
                // ID of the element in which to draw the chart.
                element: 'myfirstchart',
                // Chart data records -- each entry in this array corresponds to a point on
                // the chart.
                data: mas,
                // The name of the data record attribute that contains x-values.
                xkey: 'year',
                // A list of names of data record attributes that contain y-values.
                ykeys: ['value'],
                // Labels for the ykeys -- will be displayed when you hover over the
                // chart.
                labels: ['Простой']
            });
            for(var i =0;i<=data.length-1;i++) {
                var date = new Date(data[i].date_of_adding);
                var mm = date.getMonth() + 1;
                var dtade = date.getFullYear() + "-" + 0 + mm + "-" + date.getDate();
                $("#params_t").append("<tr><td>" + data[i].responsible.last_name + "</td><td>" + dtade + "</td><td>" + data[i].value +"</td></tr>");
            }
        });
    }

    function addDownTime() {
        jQuery("#new_downtime").dialog({
                    title: "Добавление простоя",
                    width:350,
                    height: 270,
                    resizable:false,
                    cache: false,
                    modal: true,
                    buttons: [
                        {
                            text: 'Добавить',
                            click: function() {
                                if (parseFloat(document.getElementById("downtime").value) <= 0 || parseFloat(document.getElementById("downtime").value)>100) {
                                    $("#errormsg2").text("Неверное значение");
                                }
                                else {
                                    var value = parseFloat(document.getElementById("downtime").value);
                                    var that = this;
                                    $(that).dialog("close");
                                    $("#downtime").val("");
                                    $.ajax({
                                        type: "POST",
                                        url: "/downtime/add",
                                        content: "application/json",
                                        dataType: "json",
                                        context: $(this),
                                        data: {value: value, equipment_id: current_equipment},
                                        success: function (returnData) {
                                            $(that).dialog("close");
                                            //window.location.reload(true);//$('#container').html(returnData);
                                        }
                                    });
                                    SendGet(current_equipment);
                                    downTime(current_equipment);
                                }
                                SendGet(current_equipment);
                                downTime(current_equipment);
                            }},
                        {
                            text: 'Закрыть',
                            click: function() {
                                jQuery("#new_downtime").dialog('close');
                                $("#downtime").val("");
                            }}
                    ]
                }
        );
    }

    function addTechCard() {

      jQuery("#new_tech_card").dialog({
                title: "Добавление технологической карты оборудования",
                width:550,
                height: 470,
                resizable: false,
                cache: false,
                modal: true
              }
      );
        $.datepicker.setDefaults(
                $.extend(
                        {'dateFormat':'yy-mm-dd'},
                        $.datepicker.regional['ru']
                )
        );
        $("#datepicker1").datepicker({ dateFormat: 'yy-mm-dd' });
    }

    function refreshContent()
    {
        history.go(0);
    }

    function addTechnologicalCard() {
      var type_of_maintenance_id = $("#maintenanceType").val();
      var start_date= $("#datepicker1").val();
      var description = $("#description_tech").val();

      var that = this;
      $.ajax({
        type: "POST",
        url: "/cards/add",
        content: "application/json",
        dataType: "json",
        data: { equipment_id: current_equipment, type_of_maintenance_id : type_of_maintenance_id, start_date : start_date, description : description  },
        success: function(returnData){
          $(that).dialog("close");
        }});
    }

    function openTechCard(techCardId){
        console.log(techCardId);
      $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/repair/"+techCardId,
        dataType: 'json',
        mimeType: 'application/json',
      }).done(function( data ) {
        jQuery("#techCardDialog").html("<div class=\"col-md-6\" style=\"margin: 8px;\">\n\
            <p class=\"form-control-static\"> <b>Номер тех.карты: </b></p>\n\
            <p class=\"form-control-static\"> <b>Цех: </b></p>\n\
            <p class=\"form-control-static\"> <b>Оборудование: </b></p>\n\
            <p class=\"form-control-static\"> <b>Тип ТО: </b></p>\n\
            <p class=\"form-control-static\"> <b>Дата начала:</b></p>\n\
            <p class=\"form-control-static\"> <b>Дата окончания:</b></p>\n\
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
            <p class=\"form-control-static\" id=\"end_date\"></p>\n\
            <p class=\"form-control-static\" id=\"responsible_for_delivery\"></p>\n\
            \n\
            <p class=\"form-control-static\" id=\"repair_title\"></p>\n\
            \n\
            <p class=\"form-control-static\" id=\"status\"></p></div>");

          var start_date = new Date(data.start_date);
          var mm = start_date.getMonth() + 1;
          var dtade = start_date.getFullYear() + "-" + 0 + mm + "-" + start_date.getDate();
          var end_date = new Date(data.end_date);
          var end_mm = end_date.getMonth() + 1;
          var end_dtade = end_date.getFullYear() + "-" + 0 + end_mm + "-" + end_date.getDate();

        desc = data.description;
          jQuery("#repair_sheet_id").text(data.sheet_number);
          jQuery("#subdivision_id").text(data.subdivision.subdivision_name);
          jQuery("#equipment_id").text(data.equipment.equipment_name);
          jQuery("#type_of_maintenance_id").text(data.type_of_maintenance.type_of_maintenance_name);
          jQuery("#start_date").text(dtade);
          jQuery("#end_date").text(end_dtade);
          jQuery("#responsible_for_delivery").text(data.responsible_for_delivery.last_name);
          jQuery("#repair_title").text(desc);
          jQuery("#status").text(data.status.status);
        jQuery("#techCardDialog").dialog({
          title: "Технологическая карта №"+ data.sheet_number,
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
  </script>


</head>
<body onload="getList();">
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
  <div class="row" style="background-color:lavender;min-height:800px; min-width: 600px;">
    <div class="col-md-2" style="overflow-y: scroll;overflow-x: scroll;max-height:1000px;">

      <div class="tree well" align="left">
        <li>
          <span>Все оборудование</span>
        <ul id="all_equipments">
          <c:forEach items="${subdivisions}" var="subdivision" begin="0" end="${subdivisions.size()}" varStatus="status">
            <li>
              <span >${subdivisions.get(status.index).subdivision_name}</span>
              <c:if test="${subdivisions.get(status.index).subdivision_id==1}">
                <ul id="myeq1" class="myeq1">
                </ul>
              </c:if>
              <c:if test="${subdivisions.get(status.index).subdivision_id==2}">
                <ul id="myeq2" class="myeq2"></ul>
              </c:if>
              <c:if test="${subdivisions.get(status.index).subdivision_id==3}">
                <ul id="myeq3" class="myeq3"></ul>
              </c:if>
              <c:if test="${subdivisions.get(status.index).subdivision_id==4}">
                <ul id="myeq4" class="myeq4"></ul>
              </c:if>
              <c:if test="${subdivisions.get(status.index).subdivision_id==5}">
                <ul id="myeq5" class="myeq5"></ul>
              </c:if>
              <c:if test="${subdivisions.get(status.index).subdivision_id==6}">
                <ul id="myeq6" class="myeq6"></ul>
              </c:if>
              <c:if test="${subdivisions.get(status.index).subdivision_id==7}">
                <ul id="myeq7" class="myeq7"></ul>
              </c:if>
              <script>
                $.ajax({
                  type: "GET",
                  contentType: 'application/json',
                  url: "/equipments/subdivisions/"+${status.index+1},
                  dataType: 'json',
                }).done(function( data ) {
                  for(var i =0;i<data.length;i++) {
                      if(data[i].status.status_id==1) {
                          var html = "<li style='color: #228B22; list-style-type: square;'> <span style='color: green;' onclick=\"SendGet(" + data[i].equipment_id + ");\">" + data[i].equipment_name + "</span> </li>";
                      }
                      if(data[i].status.status_id==2) {
                          var html = "<li style='color: orange; list-style-type: square;'> <span  style='color: orange;' onclick=\"SendGet(" + data[i].equipment_id + ");\">" + data[i].equipment_name + "</span> </li>";
                      }
                      if(data[i].status.status_id==3) {
                          var html = "<li style='color: #FF4500; list-style-type: square;'> <span  style='color: red;' onclick=\"SendGet(" + data[i].equipment_id + ");\">" + data[i].equipment_name + "</span> </li>";
                      }
                      if(data[i].status.status_id==4) {
                          var html = "<li style='color: #B0C4DE; list-style-type: square;'> <span  style='color: grey;' onclick=\"SendGet(" + data[i].equipment_id + ");\">" + data[i].equipment_name + "</span> </li>";
                      }
                    if(data[i].subdivision.subdivision_id==1) {
                      jQuery("#myeq1").append(html);
                    }
                    if(data[i].subdivision.subdivision_id==2){
                      jQuery("#myeq2").append(html);
                    }
                    if(data[i].subdivision.subdivision_id==3) {
                      jQuery("#myeq3").append(html);
                    }
                    if(data[i].subdivision.subdivision_id==4){
                      jQuery("#myeq4").append(html);
                    }
                    if(data[i].subdivision.subdivision_id==5) {
                      jQuery("#myeq5").append(html);
                    }
                    if(data[i].subdivision.subdivision_id==6){
                      jQuery("#myeq6").append(html);
                    }
                    if(data[i].subdivision.subdivision_id==7) {
                      jQuery("#myeq7").append(html);
                    }
                  }
                });</script>
            </li>
          </c:forEach>
        </ul>
        </li>
      </div>
  </div>
    <div class="col-md-8" style="background:beige;min-height:800px; min-width: 600px; text-align:  center">
      </br>
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
          </div>
        </div>
      </div>
      <form class="form-horizontal">
        <div class="form-group" id="result" align="left" style="margin-left: 50px;margin-right: 50px;">
            <div style="display: table; margin: 0 auto; text-align: center;">
                <div id="chart_div" style="width: 600px; height: 450px;margin-top: 20px"></div>

                </div>
        </div>
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
                        <td><form:textarea path="description" /></td>
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
              <td><form:input path="description" id="description_tech"/></td>
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