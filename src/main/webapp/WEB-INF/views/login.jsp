<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>

  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

  <!-- Optional theme -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>

  <!-- Latest compiled and minified JavaScript -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>

  <title></title>
</head>
<body>

  <div class="container" align="center">

    <c:if test="${not empty error}">
      <div class="error">${error}</div>
    </c:if>

    <c:if test="${param.logout != null}">
      <div class="alert alert-success">
        <p>Вы успешно вышли из системы.</p>
      </div>
    </c:if>

    <form name="loginForm" class="form-signin" style="width:30%;" action="<c:url value='j_spring_security_check' />"
          method='POST'>
      <h2 class="form-signin-heading">Пожалуйста, войдите в систему</h2>
      <label for="inputEmail" class="sr-only">Ваш логин</label>
      <input type="text" id="inputEmail" class="form-control" name='ssoid' placeholder="Email-адрес" required autofocus>
      <label for="inputPassword" class="sr-only">Ваш пароль</label>
      <input type="password" id="inputPassword" class="form-control" name='password' placeholder="Пароль" required>

      <button class="submitLogin" name="submit" type="submit">Войти</button>

    </form>
  </div>
</body>
</html>
