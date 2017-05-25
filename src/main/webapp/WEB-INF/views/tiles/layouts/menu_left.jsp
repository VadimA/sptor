<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

<link rel="stylesheet" href="https:////code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>


<script type="text/javascript" charset="utf8" src="https://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.min.js"></script>

<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
</head>

<div class="tree well" align="left">
    <li>
        <span>Все оборудование</span>
        <ul id="all_equipments">
            <c:forEach items="${subdivisions}" var="subdivision" begin="0" end="${subdivisions.size()}" varStatus="status">
                <li>
                    <span >${subdivisions.get(status.index).subdivision_name}</span>
                    <c:if test="${subdivisions.get(status.index).subdivision_id==1}">
                        <ul id="myeq1" class="myeq1">
                        </ul>
                    </c:if>
                    <c:if test="${subdivisions.get(status.index).subdivision_id==2}">
                        <ul id="myeq2" class="myeq2"></ul>
                    </c:if>
                    <c:if test="${subdivisions.get(status.index).subdivision_id==3}">
                        <ul id="myeq3" class="myeq3"></ul>
                    </c:if>
                    <c:if test="${subdivisions.get(status.index).subdivision_id==4}">
                        <ul id="myeq4" class="myeq4"></ul>
                    </c:if>
                    <c:if test="${subdivisions.get(status.index).subdivision_id==5}">
                        <ul id="myeq5" class="myeq5"></ul>
                    </c:if>
                    <c:if test="${subdivisions.get(status.index).subdivision_id==6}">
                        <ul id="myeq6" class="myeq6"></ul>
                    </c:if>
                    <c:if test="${subdivisions.get(status.index).subdivision_id==7}">
                        <ul id="myeq7" class="myeq7"></ul>
                    </c:if>
                    <script>
                        $.ajax({
                            type: "GET",
                            contentType: 'application/json',
                            url: "/equipments/subdivisions/"+${status.index+1},
                            dataType: 'json',
                        }).done(function( data ) {
                            for(var i =0;i<data.length;i++) {
                                if(data[i].status.status_id==1) {
                                    var html = "<li style='color: #228B22; list-style-type: square;'> <span style='color: green;' onclick=\"SendGet(" + data[i].equipmentId + ");\">" + data[i].equipmentName + "</span> </li>";
                                }
                                if(data[i].status.status_id==2) {
                                    var html = "<li style='color: orange; list-style-type: square;'> <span  style='color: orange;' onclick=\"SendGet(" + data[i].equipmentId + ");\">" + data[i].equipmentName + "</span> </li>";
                                }
                                if(data[i].status.status_id==3) {
                                    var html = "<li style='color: #FF4500; list-style-type: square;'> <span  style='color: red;' onclick=\"SendGet(" + data[i].equipmentId + ");\">" + data[i].equipmentName + "</span> </li>";
                                }
                                if(data[i].status.status_id==4) {
                                    var html = "<li style='color: #B0C4DE; list-style-type: square;'> <span  style='color: grey;' onclick=\"SendGet(" + data[i].equipmentId + ");\">" + data[i].equipmentName + "</span> </li>";
                                }
                                if(data[i].subdivision.subdivision_id==1) {
                                    jQuery("#myeq1").append(html);
                                }
                                if(data[i].subdivision.subdivision_id==2){
                                    jQuery("#myeq2").append(html);
                                }
                                if(data[i].subdivision.subdivision_id==3) {
                                    jQuery("#myeq3").append(html);
                                }
                                if(data[i].subdivision.subdivision_id==4){
                                    jQuery("#myeq4").append(html);
                                }
                                if(data[i].subdivision.subdivision_id==5) {
                                    jQuery("#myeq5").append(html);
                                }
                                if(data[i].subdivision.subdivision_id==6){
                                    jQuery("#myeq6").append(html);
                                }
                                if(data[i].subdivision.subdivision_id==7) {
                                    jQuery("#myeq7").append(html);
                                }
                            }
                        });</script>
                </li>
            </c:forEach>
        </ul>
    </li>
    <div class="panel panel-default">
        <div class="panel-heading">
            <h5 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion" href="#collapseFour"><span class="glyphicon glyphicon-file">
                    </span>Оборудование</a>
            </h5>
        </div>
        <div id="collapseFour" class="panel-collapse collapse">
            <div class="list-group">
                <c:forEach items="${subdivisions}" var="subdivision" begin="0" end="${subdivisions.size()}" varStatus="status">

                <a href="javascript:SendGet(getSubdivision())" class="list-group-item">Dapibus ac facilisis in</a>
                <a href="#" class="list-group-item">Morbi leo risus</a>
                <a href="#" class="list-group-item">Porta ac consectetur ac</a>
                <a href="#" class="list-group-item">Vestibulum at eros</a>
                </c:forEach>
            </div>
        </div>
    </div>
</div>