<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<html>
<head>
  <title>ActionTest</title>
</head>
<body>

<div id="container">
  <div id="header" class="col-md-12">
    <t:insertAttribute name="header" />
  </div>
  <div class="row" style="background-color:lavender;min-height:800px;">
    <div id="menu_left" class="col-md-2" style="overflow-y: scroll;">
      <t:insertAttribute name="menu_left" />
    </div>
    <div id="content" class="col-md-8" style="background:beige;min-height:600px; text-align:  center">
          <t:insertAttribute name="body" />
     </div>

    <div id="menu_right"class="col-md-2">
      <t:insertAttribute name="menu_right" />
    </div>

    <script src="js/vendor/jquery-1.9.1.min.js"></script>
    <script src="js/vendor/jquery-1.9.1.min.js"></script>
    <script src="js/vendor/bootstrap.min.js"></script>
    <script src="js/main.js"></script>

    <script src="js/jquery.ba-cond.min.js"></script>
    <script src="js/jquery.slitslider.js"></script>
  </div>
</body>
</html>