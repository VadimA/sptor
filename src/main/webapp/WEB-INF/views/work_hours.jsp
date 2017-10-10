<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>


<html>
      <div class="text-center">
        <div class="btn-toolbar" \>
          <div class="btn-group">
            <button class="btn btn-default" onclick="SendGet(current_equipment);">Общее</button>
            <button class="btn btn-default" onclick="getParams(current_equipment_type);">Характеристики</button>
            <button class="btn btn-default" onclick="getSpare(current_equipment);">Комплектующие</button>
            <button class="btn btn-default" onclick="workedHours(current_equipment);">Наработка</button>
            <button class="btn btn-default" onclick="getTechCard(current_equipment);">Простой</button>
            <button class="btn btn-default" onclick="getTechCard(current_equipment);">Технологические карты</button>
            <button class="btn btn-default" onclick="getTechCard(current_equipment);">Документы</button>
          </div>
        </div>
      </div>
        <div class="form-group" id="result" align="left"></div>

        <div id="new_working_hours"  title="Добавление наработки">

          <form:form action="working_hours" commandName="workHoursForm" >
            <table></br>
              <tr>
                <td><label><spring:message text="Наработка"/></label></td>
                <td><form:input path="value" id="work_hour" type="number"/></td>
                <td><form:errors path="ssoid" cssClass="error"/></td>
              </tr>
            </table>
          </form:form>
        </div>
</html>