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


<form:form action="lesson/teacher/edit.do" modelAttribute="lessonForm">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="course" />
	
<acme:textbox code="lesson.title" path="title"/>

<acme:textarea code="lesson.description" path="description"/>

<acme:textarea code="lesson.body" path="body"/>

<acme:textbox code="lesson.pictureURL" path="photoURL"/>

<acme:textbox code="lesson.videoURL" path="videoURL"/>

	
	

	<spring:message code="lesson.save" var="saveCourse"  />
	<spring:message code="lesson.delete" var="deleteCourse"  />
	<spring:message code="lesson.confirm.delete" var="confirmDeleteCourse"  />
	<spring:message code="lesson.cancel" var="cancelCourse"  />
	
	<input type="submit" id="submit" name="save"
		value="${saveCourse}" />&nbsp; 
				
	<jstl:if test="${lessonForm.id != 0}">
		<input type="submit" name="delete"
			value="${deleteCourse}"
			onclick="return confirm('${confirmDeleteCourse}')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="${cancelCourse}"
		onclick="javascript: relativeRedir('course/display.do?courseId=${lessonForm.course.id}');" />
	<br />


</form:form>






