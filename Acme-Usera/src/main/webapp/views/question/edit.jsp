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
<jstl:when test="${permission}"> 

<form:form action="question/student/edit.do" modelAttribute="questionForm">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="forum" />
	
<acme:textbox code="question.title" path="title"/>

<acme:textarea code="question.question" path="question"/>

<acme:textbox code="question.photoURL" path="photoURL"/>	
	
	<spring:message code="question.save" var="saveQuestion"  />
	<spring:message code="question.delete" var="deleteQuestion"  />
	<spring:message code="question.confirm.delete" var="confirmDeleteQuestion"  />
	<spring:message code="question.cancel" var="cancelQuestion"  />
	
	<input type="submit" id="submit" name="save"
		value="${saveQuestion}" />&nbsp; 
				
	<input type="button" name="cancel"
		value="${cancelQuestion}"
		onclick="javascript: relativeRedir('course/list.do');" />
	<br />


</form:form>


</jstl:when>
<jstl:otherwise>

<spring:message code="question.permission" />

</jstl:otherwise>
</jstl:choose>



