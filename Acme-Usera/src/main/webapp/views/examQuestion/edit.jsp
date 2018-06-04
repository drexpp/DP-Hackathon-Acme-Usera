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


<form:form action="examQuestion/teacher/edit.do" modelAttribute="examQuestion">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="exam" />
	<form:hidden path="number"/>
	<form:hidden path="maxScore"/>
	
<acme:textarea code="examQuestion.statement" path="statement"/>

<acme:textbox code="examQuestion.photoURL" path="photoURL"/>

<acme:textarea code="examQuestion.answer" path="answer"/>

<br>

	<spring:message code="examQuestion.save" var="saveExamQuestion"  />
	<spring:message code="examQuestion.cancel" var="cancelExamQuestion"  />
	
	<input type="submit" id="submit" name="save"
		value="${saveExamQuestion}" />&nbsp; 
		<jstl:if test="${examQuestion.id != 0}">
			<input type="submit" name="delete" value="<spring:message code="examQuestion.delete" />"
				onclick="return confirm('<spring:message code="examQuestion.confirm.delete" />')" />&nbsp;
		</jstl:if>
				
	<input type="button" name="cancel"
		value="${cancelExamQuestion}"
		onclick="javascript: relativeRedir('exam/display.do?examId=${examQuestion.exam.id}');" />
	<br />


</form:form>






