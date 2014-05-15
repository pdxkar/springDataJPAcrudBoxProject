<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="spring.data.jpa.example.title"/></title>
    <link rel="stylesheet" href="/static/css/styles.css" type="text/css"/>
</head>
<body>
<div class="messages">
    <c:if test="${feedbackMessage != null}">
        <div class="messageblock"><c:out value="${feedbackMessage}"/></div>
    </c:if>
    <c:if test="${errorMessage != null}">
        <div class="errorblock"><c:out value="${errorMessage}"/></div>
    </c:if>
</div>
<h1><spring:message code="box.list.page.title"/></h1>
<a href="/box/create"><spring:message code="box.create.link.label"/></a>
<table>
    <thead>
    <tr>
        <td><spring:message code="box.label.boxType"/></td>
        <td><spring:message code="box.label.attribute"/></td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${boxes}" var="box">
        <tr>
            <td><c:out value="${box.boxType}"/></td>
            <td><c:out value="${box.attribute}"/></td>
            <td><a href="/box/edit/<c:out value="${box.id}"/>"><spring:message code="box.edit.link.label"/></a></td>
            <td><a href="/box/delete/<c:out value="${box.id}"/>"><spring:message code="box.delete.link.label"/></a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>