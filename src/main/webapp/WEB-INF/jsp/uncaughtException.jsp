<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Self Service</title>

</head>
<body>

<h1>Ausnahmefehler</h1>

<c:if test="${not empty exception}">

    <h4>
        Details:
    </h4>

    <c:out value="${exception.localizedMessage}"/>

    <h5>Stacktrace:</h5>

    <p>
        <c:forEach items="${exception.stackTrace}" var="trace">
            <c:out value="${trace}"/>
            <br/>
        </c:forEach>

    </p>
</c:if>


</body>
</html>