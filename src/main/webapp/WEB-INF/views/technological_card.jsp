<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<html>
<head>
  <title>Technological Card</title>
  <style type="text/css">
    .tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
    .tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
    .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
    .tg .tg-4eph{background-color:#f9f9f9}
  </style>
</head>
<body>

<c:url var="addAction" value="/cards/add" ></c:url>

<form:form action="${addAction}" commandName="technological_card">
  <table>
    <c:if test="${!empty technological_card.technological_card_id}">
      <tr>
        <td>
          <form:label path="technological_card_id">
            <spring:message text="ID"/>
          </form:label>
        </td>
        <td>
          <form:input path="technological_card_id" readonly="true" size="8"  disabled="true" />
          <form:hidden path="technological_card_id" />
        </td>
      </tr>
    </c:if>
    <tr>
      <td>
        <form:label path="type_of_maintenance_id">
          <spring:message text="type_of_maintenance_id"/>
        </form:label>
      </td>
      <td>
        <form:input path="type_of_maintenance_id" />
      </td>
    </tr>
    <tr>
      <td>
        <form:label path="equipment_id">
          <spring:message text="equipment_id"/>
        </form:label>
      </td>
      <td>
        <form:input path="equipment_id" />
      </td>
    </tr>
    <tr>
      <td>
        <form:label path="technological_card_number">
          <spring:message text="technological_card_number"/>
        </form:label>
      </td>
      <td>
        <form:input path="technological_card_number" />
      </td>
    </tr>
    <tr>
      <td>
        <form:label path="start_date">
          <spring:message text="start_date"/>
        </form:label>
      </td>
      <td>
        <form:input path="start_date" />
      </td>
    </tr>
    <tr>
      <td>
        <form:label path="end_date">
          <spring:message text="end_date"/>
        </form:label>
      </td>
      <td>
        <form:input path="end_date" />
      </td>
    </tr>
    <tr>
      <td>
        <form:label path="responsible_for_delivery">
          <spring:message text="responsible_for_delivery"/>
        </form:label>
      </td>
      <td>
        <form:input path="responsible_for_delivery" />
      </td>
    </tr>
    <tr>
      <td>
        <form:label path="responsible_for_reception">
          <spring:message text="responsible_for_reception"/>
        </form:label>
      </td>
      <td>
        <form:input path="responsible_for_reception" />
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
        <c:if test="${!empty technological_card.technological_card_id}">
          <input type="submit"
                 value="<spring:message text="Edit Card"/>" />
        </c:if>
        <c:if test="${empty technological_card.technological_card_id}">
          <input type="submit"
                 value="<spring:message text="Add Card"/>" />
        </c:if>
      </td>
    </tr>
  </table>
</form:form>
<br>


<h1>Card List</h1>
<c:if test="${!empty listCards}">
  <table class="tg">
    <tr>
      <th width="80">Card ID</th>
      <th width="100">type_of_maintenance_id</th>
      <th width="100">equipment_id</th>
      <th width="100">technological_card_number</th>
      <th width="100">start_date</th>
      <th width="100">end_date</th>
      <th width="100">responsible_for_delivery</th>
      <th width="100">responsible_for_reception</th>
      <th width="100">description</th>
      <th width="80">Edit</th>
      <th width="80">Delete</th>
    </tr>
    <c:forEach items="${listCards}" var="technological_card">
      <tr>
        <td>${technological_card.technological_card_id}</td>
        <td>${technological_card.type_of_maintenance_id}</td>
        <td>${technological_card.equipment_id}</td>
        <td>${technological_card.technological_card_number}</td>
        <td>${technological_card.start_date}</td>
        <td>${technological_card.end_date}</td>
        <td>${technological_card.responsible_for_delivery}</td>
        <td>${technological_card.responsible_for_reception}</td>
        <td>${technological_card.description}</td>
        <td><a href="<c:url value='cards/edit/${technological_card.technological_card_id}' />" >Edit</a></td>
        <td><a href="<c:url value='cards/remove/${technological_card.technological_card_id}' />" >Delete</a></td>
      </tr>
    </c:forEach>
  </table>
</c:if>
</body>