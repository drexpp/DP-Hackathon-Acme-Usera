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


<form:form action="exam/teacher/edit.do" modelAttribute="examForm">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="course" />
	<form:hidden path="mark"/>
	
<acme:textbox code="exam.title" path="title"/>	
<br>	

	<spring:message code="exam.save" var="saveExam"  />
	<spring:message code="exam.delete" var="deleteExam"  />
	<spring:message code="exam.confirm.delete" var="confirmDeleteExam"  />
	<spring:message code="exam.cancel" var="cancelExam"  />
	
	<input type="submit" id="submit" name="save"
		value="${saveExam}" />&nbsp; 
				
	<input type="button" name="cancel"
		value="${cancelExam}"
		onclick="javascript: relativeRedir('course/list.do');" />
	<br />


</form:form>






