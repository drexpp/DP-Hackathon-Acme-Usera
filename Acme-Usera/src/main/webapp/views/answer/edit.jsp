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

<form:form action="answer/student/edit.do" modelAttribute="answerForm">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="question" />
	
<acme:textbox code="answer.text" path="text"/>

<acme:textbox code="answer.photoURL" path="photoURL"/>	

	<spring:message code="answer.save" var="saveAnswer"  />
	<spring:message code="answer.delete" var="deleteAnswer"  />
	<spring:message code="answer.confirm.delete" var="confirmDeleteAnswer"  />
	<spring:message code="answer.cancel" var="cancelAnswer"  />
	
	<input type="submit" id="submit" name="save"
		value="${saveAnswer}" />&nbsp; 
	
	<input type="button" name="cancel"
		value="${cancelAnswer}"
		onclick="javascript: relativeRedir('course/list.do');" />
	<br />


</form:form>


</jstl:when>
<jstl:otherwise>

<spring:message code="answer.permission" />

</jstl:otherwise>
</jstl:choose>



