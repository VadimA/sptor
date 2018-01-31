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
  <link rel='stylesheet' href='https://fullcalendar.io/js/fullcalendar-3.8.1/fullcalendar.min.css' />
  <script src='https://fullcalendar.io/js/fullcalendar-3.8.1/lib/jquery.min.js'></script>
  <script src="https://fullcalendar.io/js/fullcalendar-3.8.1/lib/jquery-ui.custom.min.js"></script>
  <script src='https://fullcalendar.io/js/fullcalendar-3.8.1/lib/moment.min.js'></script>
  <script src='https://fullcalendar.io/js/fullcalendar-3.8.1/fullcalendar.min.js'></script><script>
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
                  var desc = data[i].type_of_maintenance.type_of_maintenance_name + " " + data[i].equipment.typeOfEquipment.type_of_equipment_name + " " + data[i].equipment.equipmentName;
                  var t = {id : id,start: dtade, end: end_date, title: desc, allDay: true, url: "/repair"};
                  mas.push(t);
              }
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
                  monthNames: ['Январь','Февраль','Март','Апрель','Май','Июнь','Июль','Август','Сентябрь','Октябрь','Ноябрь','Декабрь'],
                  monthNamesShort: ['Янв.','Фев.','Март','Апр.','Май','Июн.','Июл.','Авг.','Сент.','Окт.','Ноя.','Дек.'],
                  dayNames: ["Воскресенье","Понедельник","Вторник","Среда","Четверг","Пятница","Суббота"],
                  dayNamesShort: ["ВС","ПН","ВТ","СР","ЧТ","ПТ","СБ"],
                  buttonText: {
                      prev: "Пред",
                      next: "След",
                      prevYear: "Пред. год",
                      nextYear: "След. год",
                      today: "Сегодня",
                      month: "Месяц",
                      week: "Неделя",
                      day: "День"
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