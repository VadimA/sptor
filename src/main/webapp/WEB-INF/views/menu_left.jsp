<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <div class="tree well" align="left">
        <ul id="all_equipments">
            <c:forEach items="${subdivisions}" var="subdivision" begin="0" end="${subdivisions.size()}" varStatus="status">
                <li>
                    <span >${subdivisions.get(status.index).subdivision_name}</span>
                </li>
            </c:forEach>
        </ul>
    </div>