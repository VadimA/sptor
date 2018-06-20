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
    window.onload = getSubdivision;
    function getSubdivision(){
      $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/subdivision/all",
        dataType: 'json',
        mimeType: 'application/json',
      }).done(function( data ) {
        jQuery("#result").html("</br><button class=\"btn btn-info\"  type=\"button\" onclick=\"openSubdivision()\">Добавить цех</button></br><table class=\"table table-hover\" id=\"params_t\">\n\
               </br>\
<thead>\n\
  <tr><th>Цех</th><th>Аббревиатура</th><th>Ответственный</th><th>Описание</th></tr>\n\
  </thead>\n\
<tbody>");


        for(var i =0;i<=data.length-1;i++) {
          $("#params_t").append("<tr><td>" + data[i].subdivision_name + "</td><td>" + data[i].abbreviation + "</td><td>" + data[i].responsible + "</td><td>" + data[i].description + "</td></tr>");
        }
      });
    }

    function openSubdivision() {
      jQuery("#new_subdivision").dialog({
                title: "Добавление цеха",
                width:500,
                height: 520,
                resizable:false,
                cache: false,
                modal: true
              }
      );
    }
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
      <div id="new_subdivision"  title="Добавление цеха"  style="display: none">
        <div class="col-md-5" style="margin-top: 45px;margin-left: 50px">
          <table>
            <form:form method="POST" modelAttribute="subForm" >
              <tr>
                <td><label><spring:message text="Цех "/></label></td>
                <td><form:input  path="subdivision_name" id="work_hour1" />
                  <p id ="error1" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Аббревиатура "/></label></td>
                <td><form:input path="abbreviation" id="work_hour2" />
                  <p id ="error2" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Ответственный "/></label></td>
                <td><form:input path="responsible" id="work_hour3" />
                  <p id ="error3" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td><label><spring:message text="Описание "/></label></td>
                <td><form:input path="description" id="work_hour4" />
                  <p id ="error4" style="color: red"></p>
                </td>
              </tr>
              <tr><td><br/></td><td><br/></td></tr>
              <tr>
                <td align="center"><input type="submit" value="<spring:message text="Добавить"/>"/></td>
                <td align="center"><input type="button"
                                          value="<spring:message text="Закрыть"/>" onclick="jQuery('#new_subdivision').dialog('close');"/></td>

              </tr>
              <tr><td><br/></td><td><br/></td></tr>
            </form:form>
          </table>
        </div>
      </div>
</body>