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



<form:form action="examPaper/student/edit.do" modelAttribute="examPaperForm">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="exam" />
	<form:hidden path="moment"/>
	
	<spring:message code="examPaper.save" var="saveExamPaper"  />
	<spring:message code="examPaper.cancel" var="cancelExamPaper"  />
	
	<input type="submit" id="submit" name="save"
		value="${saveExamPaper}" />&nbsp; 
				
	<input type="button" name="cancel"
		value="${cancelExamPaper}"
		onclick="javascript: relativeRedir('exam/display.do?examId=${examPaperForm.exam.id}');" />
	<br />


</form:form>





