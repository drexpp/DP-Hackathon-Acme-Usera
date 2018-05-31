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

<form:form action="examAnswer/student/edit.do" modelAttribute="examAnswerForm">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="examPaper"/>
	<form:hidden path="number"/>
	
<jstl:out value="number"/>	
<acme:textbox code="examAnswer.text" path="text"/>

	<spring:message code="examAnswer.save" var="saveAnswer"  />
	<spring:message code="examAnswer.cancel" var="cancelAnswer"  />
	
	<input type="submit" id="submit" name="save"
		value="${saveAnswer}" />&nbsp; 
	
	<input type="button" name="cancel"
		value="${cancelAnswer}"
		onclick="javascript: relativeRedir('ExamPaper/display.do?examPaperId=${examAnswerForm.examPaper.id}');" />
	<br />

</form:form>


</jstl:when>
<jstl:otherwise>

<spring:message code="examAnswer.permission" />

</jstl:otherwise>
</jstl:choose>





