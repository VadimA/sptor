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

    <script type="text/javascript">

        function openUsers() {
            jQuery("#formcontainer").dialog({
                        title: "Добавление пользователя",
                        width: 'auto',
                        height:'auto',
                        resizable:false,
                        cache: false,
                        modal: true
                    }
            );
        }
    </script>
</head>
<body>

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


<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<script src="<c:url value='/resources/angular/users/user_service.js' />"></script>
<script src="<c:url value='/resources/angular/users/user_controller.js' />"></script>
</body>

