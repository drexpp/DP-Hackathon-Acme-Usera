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


<form:form action="customisation/admin/edit.do" modelAttribute="customisation">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox code="customisation.welcomeMessageEn" path="bannerEn"/>
	
	<acme:textbox code="customisation.welcomeMessageEs" path="bannerEs"/>
	
	<acme:textbox code="customisation.standardPrice" path="standardPrice"/>

	<acme:textbox code="customisation.premiumPrice" path="premiumPrice"/>

	<spring:message code="customisation.save" var="save"/>	
	
	<input type="submit" id="submit" name="save"
		value="${save}" />&nbsp; 	
		
		
		
	<spring:message code="customisation.cancel" var="cancelCustomisation"  />
		
	<input type="button" name="cancel"
		value="${cancelCustomisation}"
		onclick="javascript: relativeRedir('customisation/admin/display.do');" />
	<br />


</form:form>
