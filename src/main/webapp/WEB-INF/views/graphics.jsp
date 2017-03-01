<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
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
          var end_date = date.setDate(date.getDate() + data[i].type_of_maintenance.duration);
          alert(end_date);
          var desc = data[i].type_of_maintenance.type_of_maintenance_name + " " + data[i].equipment.typeOfEquipment.type_of_equipment_name + " " + data[i].equipment.equipmentName;
          var t = {id : id,start: dtade, end: end_date, title: desc, allDay: true, url: "/repair"};
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

    function generateGraphics(){
      $.ajax({
        type: "GET",
        url: "/report/ppr",
        content: "application/json",
        dataType: "json",
        success: function (returnData) {
          $(that).dialog("close");
        }
      });
    }

  </script>

</head>

    <div id="calendar"></div>