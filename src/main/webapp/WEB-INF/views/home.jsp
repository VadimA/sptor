<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>

<html>
<head>
  <title>Equipment Page</title>
  <!-- Latest compiled and minified CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

  <!-- Optional theme -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>

  <!-- Latest compiled and minified JavaScript -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

  <script src="https://code.highcharts.com/highcharts.js"></script>
  <script src="http://code.highcharts.com/modules/exporting.js"></script>

  <script>

    $(function () {
      $('#charts').highcharts({
        chart: {
          type: 'column'
        },
        title: {
          text: 'График выполнения заявок'
        },
        subtitle: {
          text: '2016 год'
        },
        xAxis: {
          categories: [
            'Июл',
            'Авг',
            'Сен',
            'Окт',
            'Ноя',
            'Дек',
            'Янв',
            'Фев',
            'Мар',
            'Апр',
            'Май',
            'Июн'
          ]
        },
        yAxis: {
          min: 0,
          title: {
            text: 'Заявки'
          }
        },
        tooltip: {
          headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
          pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
          '<td style="padding:0"><b>{point.y}</b></td></tr>',
          footerFormat: '</table>',
          shared: true,
          useHTML: true
        },
        plotOptions: {
          column: {
            pointPadding: 0.2,
            borderWidth: 0
          }
        },
        series: [{
          name: 'Новые',
          data: [12,3,4,23,12,6,3,6,8,12,12,16]

        },{
          name: 'Выполненные',
          data: [11,2,4,19,12,4,3,5,4,11,10,15]

        }]
      });
    });

  </script>

  <link title="timeline-styles" rel="stylesheet" href="https://cdn.knightlab.com/libs/timeline3/latest/css/timeline.css">

  <!-- 2 -->
  <script src="https://cdn.knightlab.com/libs/timeline3/latest/js/timeline.js"></script>

  <div id='timeline-embed' style="width: 100%; height: 600px"></div>

  <!-- 3 -->s xsxx  xcs 
  <script type="text/javascript">
      // The TL.Timeline constructor takes at least two arguments:
      // the id of the Timeline container (no '#'), and
      // the URL to your JSON data file or Google spreadsheet.
      // the id must refer to an element "above" this code,
      // and the element must have CSS styling to give it width and height
      // optionally, a third argument with configuration options can be passed.
      // See below for more about options.
      timeline = new TL.Timeline('timeline-embed',
          'https://docs.google.com/spreadsheets/d/1cWqQBZCkX9GpzFtxCWHoqFXCHg-ylTVUWlnrdYMzKUI/pubhtml');
  </script>
</head>

      <div id="charts" ></div>
