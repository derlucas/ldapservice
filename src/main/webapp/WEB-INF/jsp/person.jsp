<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>LDAP Selfservice - Form</title>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
    </head>

    <body>

        <div class="container">
            <h1>
                Edit Account
            </h1>
            <div class="span-12 last">

                <form:form modelAttribute="person" action="person" method="post">

                    <form:errors path="*" cssStyle="color : red;"/>

                    <fieldset>
                        <legend>Person Fields</legend>
                        <p>
                            <form:label	for="firstName" path="firstName" cssErrorClass="error">Vorname</form:label><br/>
                            <form:input path="firstName" /> <form:errors path="firstName" />
                        </p>
                        <p>
                            <form:label for="lastName" path="lastName" cssErrorClass="error">Nachname</form:label><br/>
                            <form:input path="lastName" /> <form:errors path="lastName" />
                        </p>

                        <p>
                            <input type="submit" />
                        </p>
                    </fieldset>
                </form:form>
            </div>

            <hr/>
        </div>
    </body>
</html>