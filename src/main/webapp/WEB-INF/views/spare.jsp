<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<html>
<head>
  <title>Equipment Page</title>
  <style type="text/css">
    .tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
    .tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
    .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
    .tg .tg-4eph{background-color:#f9f9f9}
  </style>
</head>
<body>

<c:url var="addAction" value="/spares/add" ></c:url>

<form:form action="${addAction}" commandName="spare">
  <table>
    <c:if test="${!empty spare.spare_id}">
      <tr>
        <td>
          <form:label path="spare_id">
            <spring:message text="ID"/>
          </form:label>
        </td>
        <td>
          <form:input path="spare_id" readonly="true" size="8"  disabled="true" />
          <form:hidden path="spare_id" />
        </td>
      </tr>
    </c:if>
    <tr>
      <td>
        <form:label path="spare_name">
          <spring:message text="Name"/>
        </form:label>
      </td>
      <td>
        <form:input path="spare_name" />
      </td>
    </tr>
    <tr>
      <td>
        <form:label path="spare_producer">
          <spring:message text="spare_producer"/>
        </form:label>
      </td>
      <td>
        <form:input path="spare_producer" />
      </td>
    </tr>
    <tr>
      <td>
        <form:label path="price">
          <spring:message text="price"/>
        </form:label>
      </td>
      <td>
        <form:input path="price" />
      </td>
    </tr>
    <tr>
      <td>
        <form:label path="description">
          <spring:message text="description"/>
        </form:label>
      </td>
      <td>
        <form:input path="description" />
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <c:if test="${!empty spare.spare_id}">
          <input type="submit"
                 value="<spring:message text="Edit Person"/>" />
        </c:if>
        <c:if test="${empty spare.spare_id}">
          <input type="submit"
                 value="<spring:message text="Add Person"/>" />
        </c:if>
      </td>
    </tr>
  </table>
</form:form>
<br>


<h1>Equipment List</h1>
<c:if test="${!empty listSpares}">
  <table class="tg">
    <tr>
      <th width="80">Spare ID</th>
      <th width="120">Spare Name</th>
      <th width="120">Spare Producer</th>
      <th width="120">Price</th>
      <th width="120">Description</th>
      <th width="80">Edit</th>
      <th width="80">Delete</th>
    </tr>
    <c:forEach items="${listSpares}" var="spare">
      <tr>
        <td>${spare.spare_id}</td>
        <td>${spare.spare_name}</td>
        <td>${spare.spare_producer}</td>
        <td>${spare.price}</td>
        <td>${spare.description}</td>
        <td><a href="<c:url value='type/edit/${spare.spare_id}' />" >Edit</a></td>
        <td><a href="<c:url value='type/remove/${spare.spare_id}' />" >Delete</a></td>
      </tr>
    </c:forEach>
  </table>
</c:if>
</body>