<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page session="true" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">


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
          <security:authorize access="hasRole('ROLE_ADMIN')">
            <li><a href="<c:url value="/graphics"/>">План-график</a></li>
            <li><a href="<c:url value="/users"/>">Справочники</a></li>
            <li><a href="<c:url value="/planning"/>">Планирование</a></li>
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
            <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"> ${current_user} <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="<c:url value="/logout"/>">Выйти</a></li>
            </ul>
          </li>
        </ul>
      </div><!--/.nav-collapse -->
    </div><!--/.container-fluid -->
  </nav>