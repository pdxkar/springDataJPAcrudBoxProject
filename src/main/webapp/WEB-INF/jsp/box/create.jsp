<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title><spring:message code="spring.data.jpa.example.title"/></title>
    <link rel="stylesheet" href="/static/css/styles.css" type="text/css"/>
</head>
<body>
    <h1><spring:message code="box.create.page.title"/></h1>
    <a href="/"><spring:message code="box.list.link.label"/></a>
    <div>
        <form:form action="/box/create" commandName="box" method="POST">
            <div>
                <form:label path="boxType"><spring:message code="box.label.boxType"/>:</form:label>
                <form:input path="boxType" size="20"/>
                <form:errors path="boxType" cssClass="error" element="div"/>
            </div>
            <div>
                <form:label path="attribute"><spring:message code="box.label.attribute"/>:</form:label>
                <form:input path="attribute" size="20"/>
                <form:errors path="attribute" cssClass="error" element="div"/>
            </div>
            <div>
                <input type="submit" value="<spring:message code="box.create.page.submit.label"/>"/>
            </div>
        </form:form>
    </div>
</body>
</html>