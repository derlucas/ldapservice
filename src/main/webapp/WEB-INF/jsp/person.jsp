<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>LDAP Selfservice - Form</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>

    <body>

        <div class="container">
            <h1>
                Edit Account
            </h1>

            <div class="span-12 last">

                <form:form modelAttribute="person" action="person" method="post">

                    <fieldset>
                        <legend>Person Fields</legend>
                        <p>
                            <form:label	for="firstName" path="firstName" cssErrorClass="error">Vorname</form:label><br/>
                            <form:input path="firstName" /> <form:errors path="firstName" cssStyle="color : red;" />
                        </p>
                        <p>
                            <form:label for="lastName" path="lastName" cssErrorClass="error">Nachname</form:label><br/>
                            <form:input path="lastName" /> <form:errors path="lastName" cssStyle="color : red;" />
                        </p>
                        <p>
                            <form:label for="emailAddress" path="emailAddress" cssErrorClass="error">E-Mail</form:label><br/>
                            <form:input path="emailAddress" /> <form:errors path="emailAddress" cssStyle="color : red;" />
                        </p>
                        <p>
                            <form:label for="uid" path="uid" cssErrorClass="error">Nickname</form:label><br/>
                            <form:input path="uid" /> <form:errors path="uid" cssStyle="color : red;" />
                        </p>

                        <p>
                            <form:label for="password" path="password" cssErrorClass="error">Passwort</form:label><br/>
                            <form:password path="password" /> <form:errors path="password" cssStyle="color : red;" />
                        </p>
                        <p>
                            <form:label for="passwordConfirmation" path="uid" cssErrorClass="error">Passwort (nochmal)</form:label><br/>
                            <form:password path="passwordConfirmation" /> <form:errors path="passwordConfirmation" cssStyle="color : red;" />
                        </p>
                        <p>
                            <form:label for="gender" path="gender" cssErrorClass="error">Geschlecht</form:label><br/>
                            <form:select path="gender">
                                <form:option value="" label="-" />
                                <form:option value="M" label="männlich" />
                                <form:option value="W" label="weiblich" />
                            </form:select> <form:errors path="gender" cssStyle="color : red;" />
                        </p>

                        <p>
                            <input type="submit" />
                        </p>
                    </fieldset>
                </form:form>
            </div>

            <hr/>

            <span style="float: right">
                <a href="?lang=en">en</a> | <a href="?lang=de">de</a>
            </span>
        </div>
    </body>
</html>