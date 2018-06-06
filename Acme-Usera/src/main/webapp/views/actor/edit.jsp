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

	<acme:textbox code="actor.name" id="name" path="name"/>
	<br />
	
	<acme:textbox code="actor.surname" path="surname"/>
	<br />
	
	<acme:textbox code="actor.email" path="email"/>
	<br />
	
	<acme:textbox code="actor.address" path="address"/>
	<br />
	
	<acme:textbox code="actor.dateBirth" path="dateBirth"/>
	<br />
	
	<!-- Input del form con el formato antiguo para permitir el pattern -->
	<acme:textbox oninput="setCustomValidity('')" id="phone" path="phone" pattern="\\+?([0-9]{9})?" code="actor.phone"/>
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
<script>
var message = '<spring:message code="actor.invalidPhone"/>';
</script>