<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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


  <script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.min.js"></script>

  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

  <script type="text/javascript" src="charts/sources/jscharts.js"></script>

  <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.css">
  <script src="//cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
  <script src="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.min.js"></script>


  <script>
    $(document).ready(function() {
      $("#datepicker1").datepicker({ dateFormat: 'yy-mm-dd' });
      $("#datepicker2").datepicker({ dateFormat: 'yy-mm-dd' });

    });
  </script>

  <style>
    .error {
      color: red; font-weight: bold;
    }
  </style>

</head>

      <div id="result">
    <h3>Список документов</h3>
    <c:if  test="${!empty documentList}">
      <table class="data">
        <tr>
          <th>Имя</th>
          <th>Описание</th>
          <th>&nbsp;</th>
        </tr>
        <c:forEach items="${documentList}" var="document">
          <tr>
            <td width="100px">${document.document_name}</td>
            <td width="250px">${document.description}</td>
            <td width="20px">
              <span class="glyphicon glyphicon-plus" id="openDoc"></span>

              <a href="/documents/remove/${document.document_id}"
                 onclick="return confirm('Вы действительно хотите удалить текущий документ?')">
                <span class="glyphicon glyphicon-minus"></span></a>
            </td>
          </tr>
        </c:forEach>
      </table>
    </c:if>

    </br>
      <h3>Добавить новый документ</h3>
      <form:form method="post" action="/documents/save" commandName="document" enctype="multipart/form-data">
        <form:errors path="*" cssClass="error"/>
        <table>
          <tr>
            <td><form:label path="document_name">Наименование</form:label></td>
            <td><form:input path="document_name" /></td>
          </tr>
          <tr>
            <td><form:label path="description">Описание</form:label></td>
            <td><form:textarea rows="4" cols="30" path="description" id="description"/></td>
          </tr>
          <tr>
            <td><form:label path="content">Document</form:label></td>
            <td><input type="file" name="file" id="file"></input></td>
          </tr>
          <tr>
            <td><input type="hidden" name="equipment" path="equipment" value="${cu}">
          </tr>
          <tr>
            <td colspan="2">
              <input type="submit" value="Добавить документ"/>
            </td>
          </tr>
        </table>
      </form:form>

      <br/>
    </div>