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

<h1>
  Add a Person
</h1>

<c:url var="addAction" value="/subdivisions/add" ></c:url>

<form:form action="${addAction}" commandName="subdivision">
  <table>
    <c:if test="${!empty subdivision.subdivision_id}">
      <tr>
        <td>
          <form:label path="subdivision_id">
            <spring:message text="ID"/>
          </form:label>
        </td>
        <td>
          <form:input path="subdivision_id" readonly="true" size="8"  disabled="true" />
          <form:hidden path="subdivision_id" />
        </td>
      </tr>
    </c:if>
    <tr>
      <td>
        <form:label path="subdivision_name">
          <spring:message text="Name"/>
        </form:label>
      </td>
      <td>
        <form:input path="subdivision_name" />
      </td>
    </tr>
    <tr>
    <td>
      <form:label path="abbreviation">
        <spring:message text="Abbreviation"/>
      </form:label>
    </td>
    <td>
      <form:input path="abbreviation" />
    </td>
  </tr>
    <tr>
      <td>
        <form:label path="description">
          <spring:message text="Description"/>
        </form:label>
      </td>
      <td>
        <form:input path="description" />
      </td>
    </tr>
    <tr>
      <td>
        <form:label path="responsible">
          <spring:message text="Responsible"/>
        </form:label>
      </td>
      <td>
        <form:input path="responsible" />
      </td>
    </tr>

    <tr>
      <td colspan="2">
        <c:if test="${!empty subdivision.subdivision_id}">
          <input type="submit"
                 value="<spring:message text="Edit Person"/>" />
        </c:if>
        <c:if test="${empty subdivision.subdivision_id}">
          <input type="submit"
                 value="<spring:message text="Add Person"/>" />
        </c:if>
      </td>
    </tr>
  </table>
</form:form>
<br>

<h1>Subdivision List</h1>

<c:if test="${!empty listSubdivisions}">
  <table class="tg">
    <tr>
      <th width="80">Subdivision ID</th>
      <th width="120">Subdivision Name</th>
      <th width="120">Abbreviation</th>
      <th width="120">Responsible </th>
      <th width="120">Description</th>
      <th width="80">Edit</th>
      <th width="80">Delete</th>
    </tr>
    <c:forEach items="${listSubdivisions}" var="subdivision">
      <tr>
        <td>${subdivision.subdivision_id}</td>
        <td>${subdivision.subdivision_name}</td>
        <td>${subdivision.abbreviation}</td>
        <td>${subdivision.responsible}</td>
        <td>${subdivision.description}</td>
        <td><a href="<c:url value='/edit/${subdivision.subdivision_id}' />" >Edit</a></td>
        <td><a href="<c:url value='/remove/${subdivision.subdivision_id}' />" >Delete</a></td>
      </tr>
    </c:forEach>
  </table>
</c:if>
</body>