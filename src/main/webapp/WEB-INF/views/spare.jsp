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

  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>


  <script type="text/javascript">
    window.onload = getSpare;
    function getSpare(){
      $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/spare/all",
        dataType: 'json',
        mimeType: 'application/json',
      }).done(function( data ) {
        jQuery("#result").html("</br><button class=\"btn btn-info\"  type=\"button\" onclick=\"openSpare()\">Добавить запчасть</button></br><table class=\"table table-hover\" id=\"params_t\">\n\
               </br>\
<thead>\n\
  <tr><th>Наименование</th><th>Изготовитель</th><th>Цена</th><th>Описание</th><th>На складе</th></tr>\n\
  </thead>\n\
<tbody>");


        for(var i =0;i<=data.length-1;i++) {
          $("#params_t").append("<tr><td>" + data[i].spare_name + "</td><td>" + data[i].spare_producer + "</td><td>" + data[i].price + "</td><td>" + data[i].description +"</td><td>" + data[i].amount_in_stock +"</td></tr>");
        }
      });
    }

    function openSpare() {
      jQuery("#new_spare").dialog({
                title: "Добавление запчасти",
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
      $("#datepicker1").datepicker({ dateFormat: 'yy-mm-dd' });
      $("#datepicker2").datepicker({ dateFormat: 'yy-mm-dd' });

    });
  </script>

</head>
<body>
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
      <div id="new_spare"  title="Добавление запчасти"  style="display: none">
        <div class="col-md-5" style="margin-top: 45px;margin-left: 50px">
          <table>
            <form:form method="POST" modelAttribute="spareForm" >
              <tr>
                <td><label><spring:message text="Наименование "/></label></td>
                <td><form:input path="spare_name" id="work_hour1" />
                  <p id ="error1" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Изготовитель "/></label></td>
                <td><form:input path="spare_producer" id="work_hour2"/>
                  <p id ="error2" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Цена "/></label></td>
                <td><form:input path="price" id="work_hour3" type="number"/>
                  <p id ="error3" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="На складе"/></label></td>
                <td><form:input path="amount_in_stock" id="work_hour5" type="number"/>
                  <p id ="error5" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Описание "/></label></td>
                <td><form:textarea rows="4" cols="30" path="description" id="work_hour4"/>
                  <p id ="error4" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td align="center"><input type="submit" value="<spring:message text="Добавить"/>"/></td>
                <td align="center"><input type="button"
                                          value="<spring:message text="Закрыть"/>" onclick="jQuery('#new_spare').dialog('close');"/></td>

              </tr>
              <tr><td><br/></td><td><br/></td></tr>
            </form:form>
          </table>
        </div>
      </div>
</body>
