<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<!-- Optional theme -->
</head>

<div class="tree well" align="left">
    <li>
        <span role="button">Все оборудование</span>
        <ul id="all_equipments">
            <c:forEach items="${subdivisions}" var="subdivision" begin="0" end="${subdivisions.size()}" varStatus="status">
                <li>
                    <span role="button">${subdivisions.get(status.index).subdivision_name}</span>
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
                                    var html = "<li style='color: #228B22; list-style-type: square;'> <span role='button' style='color: green;' onclick=\"SendGet(" + data[i].equipmentId + ");\">" + data[i].equipmentName + "</span> </li>";
                                }
                                if(data[i].status.status_id==2) {
                                    var html = "<li style='color: orange; list-style-type: square;'> <span role='button' style='color: orange;' onclick=\"SendGet(" + data[i].equipmentId + ");\">" + data[i].equipmentName + "</span> </li>";
                                }
                                if(data[i].status.status_id==3) {
                                    var html = "<li style='color: #FF4500; list-style-type: square;'> <span role='button' style='color: red;' onclick=\"SendGet(" + data[i].equipmentId + ");\">" + data[i].equipmentName + "</span> </li>";
                                }
                                if(data[i].status.status_id==4) {
                                    var html = "<li style='color: #B0C4DE; list-style-type: square;'> <span  role='button' style='color: grey;' onclick=\"SendGet(" + data[i].equipmentId + ");\">" + data[i].equipmentName + "</span> </li>";
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
</div>