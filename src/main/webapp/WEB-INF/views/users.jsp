<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>

<html>
<head>

  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

  <!-- Optional theme -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>

  <!-- Latest compiled and minified JavaScript -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>

  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

  <title>ИС ПТОР Оборудование</title>
  <script type="text/javascript">

      window.onload = getUsers;

    function getUsers(){
      $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/users/all",
        dataType: 'json',
        mimeType: 'application/json',
      }).done(function( data ) {
        jQuery("#result").html("<center></br><button class=\"btn btn-info\"  type=\"button\" onclick=\"openUsers()\">Добавить пользователя</button></br><table class=\"table table-hover\" id=\"params_t\">\n\
               </br>\
<thead>\n\
  <tr><th>ИД</th><th>Имя</th><th>Фамилия</th><th>Почта</th><th></th></tr>\n\
  </thead>\n\
<tbody>\n");

        for(var i =0;i<=data.length-1;i++) {
          $("#params_t").append("<tr><td>" + data[i].ssoid + "</td><td>" + data[i].first_name + "</td><td>" + data[i].last_name + "</td><td>" + data[i].email +"</td></tr>");
        }
      });
    }

    function openUsers() {
      jQuery("#new_users").dialog({
              title: "Добавление пользователя",
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
      </br>
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
      <div id="new_users"  title="Добавление пользователя"  style="display: none">
        <div class="col-md-5" style="margin-top: 45px;margin-left: 50px">
        <table>
          <form:form method="POST" modelAttribute="userForm" >
            <tr>
              <td><label><spring:message text="ССОид "/></label></td>
              <td><form:input path="ssoid" id="work_hour1" /></td>
              <p id ="error1" style="color: red"></p>
            </tr>
            <tr><td><br/></td><td><br/></td></tr>
            <tr>
              <td><label><spring:message text="Имя "/></label></td>
              <td><form:input path="first_name" id="work_hour2"/></td>
              <p id ="error2" style="color: red"></p>
            </tr>
            <tr><td><br/></td><td><br/></td></tr>
            <tr>
              <td><label><spring:message text="Фамилия "/></label></td>
              <td><form:input path="last_name" id="work_hour3" /></td>
              <p id ="error3" style="color: red"></p>
            </tr>
            <tr><td><br/></td><td><br/></td></tr>
            <tr>
              <td><label><spring:message text="Пароль "/></label></td>
              <td><form:input path="password" id="work_hour4" /></td>
              <p id ="error4" style="color: red"></p>
            </tr>
            <tr><td><br/></td><td><br/></td></tr>
            <tr>
              <td><label><spring:message text="Почта "/></label></td>
              <td><form:input path="email" id="work_hour5"/></td>
              <p id ="error5" style="color: red"></p>
            </tr>
            <tr><td><br/></td><td><br/></td></tr>
            <tr>
              <td align="center"><input type="submit" value="<spring:message text="Добавить"/>"/></td>
              <td align="center"><input type="button"
                     value="<spring:message text="Закрыть"/>" onclick="jQuery('#new_users').dialog('close');"/>
              </td>
            </tr>
            <tr><td><br/></td><td><br/></td></tr>
          </form:form>
        </table>
          </div>
      </div>
</body>