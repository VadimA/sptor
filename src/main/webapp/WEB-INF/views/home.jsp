<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>

<html>
<head>
    <title>Equipment Page</title>
    <script>
        $(function () {
            $('#charts').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: 'График выполнения заявок на обслуживание'
                },
                subtitle: {
                    text: '2017/2018 год'
                },
                xAxis: {
                    categories: [
                        'Май',
                        'Июн',
                        'Июл',
                        'Авг',
                        'Сен',
                        'Окт',
                        'Ноя',
                        'Дек',
                        'Янв',
                        'Фев',
                        'Мар',
                        'Апр'
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
                    name: 'Плановые',
                    data: [12,3,4,23,12,6,3,6,8,12,12,16]
                },{
                    name: 'Внеплановые',
                    data: [9,2,4,15,6,4,3,4,4,7,8,11]
                }]
            });
        });
    </script>
</head>

<div id="charts" ></div>