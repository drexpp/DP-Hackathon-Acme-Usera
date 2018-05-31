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

<security:authorize access="hasRole('STUDENT')">
<jstl:choose>
<jstl:when test="${permission}"> 

<form:form action="examAnswer/student/edit.do" modelAttribute="examAnswerForm">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="examPaper"/>
	<form:hidden path="number"/>
	
<acme:textbox code="examAnswer.text" path="text"/>

	<spring:message code="examAnswer.save" var="saveAnswer"  />
	<spring:message code="examAnswer.cancel" var="cancelAnswer"  />
	
	<input type="submit" id="submit" name="save"
		value="${saveAnswer}" />&nbsp; 
	
	<input type="button" name="cancel"
		value="${cancelAnswer}"
		onclick="javascript: relativeRedir('examPaper/display.do?examPaperId=${examAnswerForm.examPaper.id}');" />
	<br />

</form:form>


</jstl:when>
<jstl:otherwise>

<spring:message code="examAnswer.permission" />

</jstl:otherwise>
</jstl:choose>
</security:authorize>


<security:authorize access="hasRole('TEACHER')">

<form:form action="examAnswer/teacher/edit.do" modelAttribute="examAnswer">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="examPaper"/>
	<form:hidden path="number"/>
	<form:hidden path="text"/>
	
<acme:textbox code="examAnswer.mark" path="mark"/>

	<spring:message code="examAnswer.save" var="saveAnswer"  />
	<spring:message code="examAnswer.cancel" var="cancelAnswer"  />
	
	<input type="submit" id="submit" name="save"
		value="${saveAnswer}" />&nbsp; 
	
	<input type="button" name="cancel"
		value="${cancelAnswer}"
		onclick="javascript: relativeRedir('examPaper/display.do?examPaperId=${examAnswer.examPaper.id}');" />
	<br />

</form:form>

</security:authorize>





