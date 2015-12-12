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

<c:url var="addAction" value="//equipment/add" ></c:url>

<form:form action="${addAction}" commandName="equipment">
  <table>
    <c:if test="${!empty equipment.equipment_id}">
      <tr>
        <td>
          <form:label path="equipment_id">
            <spring:message text="ID"/>
          </form:label>
        </td>
        <td>
          <form:input path="equipment_id" readonly="true" size="8"  disabled="true" />
          <form:hidden path="equipment_id" />
        </td>
      </tr>
    </c:if>
    <tr>
      <td>
        <form:label path="equipment_name">
          <spring:message text="Name"/>
        </form:label>
      </td>
      <td>
        <form:input path="equipment_name" />
      </td>
    </tr>
    <tr>
      <td>
        <form:label path="type_of_equipment_id">
          <spring:message text="type_of_equipment_id"/>
        </form:label>
      </td>
      <td>
        <form:input path="type_of_equipment_id" />
      </td>
    </tr>
    <tr>
      <td>
        <form:label path="subdivision_id">
          <spring:message text="subdivision_id"/>
        </form:label>
      </td>
      <td>
        <form:input path="subdivision_id" />
      </td>
    </tr>
    <tr>
      <td>
        <form:label path="inventory_number">
          <spring:message text="inventory_number"/>
        </form:label>
      </td>
      <td>
        <form:input path="inventory_number" />
      </td>
    </tr>
    <tr>
      <td>
        <form:label path="graduation_year">
          <spring:message text="graduation_year"/>
        </form:label>
      </td>
      <td>
        <form:input path="graduation_year" />
      </td>
    </tr>
    <tr>
      <td>
        <form:label path="producer_of_equipment">
          <spring:message text="producer_of_equipment"/>
        </form:label>
      </td>
      <td>
        <form:input path="producer_of_equipment" />
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
        <c:if test="${!empty equipment.equipment_id}">
          <input type="submit"
                 value="<spring:message text="Edit Person"/>" />
        </c:if>
        <c:if test="${empty equipment.equipment_id}">
          <input type="submit"
                 value="<spring:message text="Add Person"/>" />
        </c:if>
      </td>
    </tr>
  </table>
</form:form>
<br>


<h1>Equipment List</h1>
<c:if test="${!empty listEquipments}">
  <table class="tg">
    <tr>
      <th width="80">Equipment ID</th>
      <th width="120">Equipment Name</th>
      <th width="120">Inventory Number</th>
      <th width="120">Producer </th>
      <th width="120">Description</th>
    </tr>
    <c:forEach items="${listEquipments}" var="equipment">
      <tr>
        <td>${equipment.equipment_id}</td>
        <td>${equipment.equipment_name}</td>
        <td>${equipment.inventory_number}</td>
        <td>${equipment.producer_of_equipment}</td>
        <td>${equipment.description}</td>
      </tr>
    </c:forEach>
  </table>
</c:if>
</body>