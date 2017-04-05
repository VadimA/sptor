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
  <script src='https://fullcalendar.io/js/fullcalendar-2.6.1/lib/moment.min.js'></script>
  <script src='https://fullcalendar.io/js/fullcalendar-2.6.1/lib/jquery.min.js'></script>
  <script src='https://fullcalendar.io/js/fullcalendar-2.6.1/fullcalendar.min.js'></script>
  <script src='https://fullcalendar.io/js/fullcalendar-2.6.1/lang-all.js'></script>


  <style>

    body {
      margin: 0;
      padding: 0;
      font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
      font-size: 14px;
    }

    #top {
      background: #eee;
      border-bottom: 1px solid #ddd;
      padding: 0 10px;
      line-height: 40px;
      font-size: 12px;
    }

    #calendar {
      max-width: 900px;
      margin: 40px auto;
      padding: 0 10px;
    }

  </style>

</head>

    </br>
    В системе управления планированием технического обслуживания и ремонта «СПТОР» реализован следующий функционал:</br>
    –	учет состава оборудования, ведение эксплуатационной и ремонтной документации по нему (паспортов оборудования, ремонтных карт);</br>
    –	ведение структуры и состава ремонтных работ, нормативной базы  ресурсов по их проведению;</br>
    –	формирование и составление графиков ППР (месячных, годовых);</br>
    –	ввод, хранение и корректировка данных об оборудовании, комплектующих, запчастях, поставщиках;</br>
    –	 формирование потребности в ресурсах на проведение ремонтных работ;</br>
    –	учет и контроль процесса формирования и выполнения заявок на техническое обслуживания и ремонт оборудования;</br>
    –	учет и анализ работы оборудования (учет наработки и простоя, параметров работы оборудования);</br>
    –	учет состава и истории замен деталей и узлов оборудования и его элементов;</br>
    –	формирование отчетности (акты ввода/вывода оборудования в/из ре-монта, отчеты о проведенных ТОиР);</br>
    –	регистрация и авторизация пользователей. В веб-приложении суще-ствует возможность добавления, удаления, редактирования пользователей (сотрудников предприятия);</br>