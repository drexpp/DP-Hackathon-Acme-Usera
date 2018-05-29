<%--
 * 
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:choose>
<jstl:when test="${permiso}">

<form:form action="${formURL}" modelAttribute="editActorForm" >


	<form:hidden path="id"/>
	<form:hidden path="version"/>

	<acme:textbox code="actor.name" path="name"/>
	<br />
	
	<acme:textbox code="actor.surname" path="surname"/>
	<br />
	
	<acme:textbox code="actor.email" path="email"/>
	<br />
	
	<!-- Input del form con el formato antiguo para permitir el pattern -->
	<form:label path="phone">
		<spring:message code="actor.phone" />
	</form:label>	
	<form:input oninput="setCustomValidity('')" id="phone" path="phone" pattern="\\+?([0-9]{9})?"/>	
	<form:errors path="phone" cssClass="error" />
	<br />
	<br />
	<acme:textbox code="actor.address" path="address"/>
	<br />

	<br />
	<br />
	

	<input type="submit" name="save" id="save"
		value="<spring:message code="actor.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="actor.cancel" />"
		onclick="javascript: relativeRedir('');" />
	<br />
</form:form>

</jstl:when>
<jstl:otherwise>
<spring:message code="actor.permision" />
</jstl:otherwise>
</jstl:choose>
<spring:message code="actor.invalidPhone" var="errorMessage"/>
<input id="messageInternationalized" type ="hidden" value="${errorMessage}"/>
<script>
var input = document.getElementById('phone');
var messageInternationalized = $("#messageInternationalized").val();
input.oninvalid = function(event) {
    event.target.setCustomValidity(messageInternationalized);
};
</script>