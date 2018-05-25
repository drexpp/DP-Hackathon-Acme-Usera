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


<form:form action="tutorial/student/edit.do" modelAttribute="tutorial">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="student"/>
	<form:hidden path="teacher"/>
	
	
<acme:textbox code="tutorial.start.time" path="startTime"/>

	<spring:message code="tutorial.save" var="savetutorial"  />
	<spring:message code="tutorial.confirm.delete" var="confirmDeletetutorial"  />
	<spring:message code="tutorial.cancel" var="canceltutorial"  />
	
	<input type="submit" id="submit" name="save"
		value="${savetutorial}" />&nbsp; 
				
	<input type="button" name="cancel"
		value="${canceltutorial}"
		onclick="javascript: relativeRedir('course/list.do');" />
	<br />


</form:form>






