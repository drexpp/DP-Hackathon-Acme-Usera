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


<form:form action="course/teacher/edit.do" modelAttribute="courseForm">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
<acme:textbox code="course.title" path="title"/>

<acme:textbox code="course.description" path="description"/>

<acme:textbox code="course.pictureURL" path="photoURL"/>

<acme:select items="${categories}" itemLabel="name" code="course.category" path="category"/>
	
	

	<spring:message code="course.save" var="saveCourse"  />
	<spring:message code="course.delete" var="deleteCourse"  />
	<spring:message code="course.confirm.delete" var="confirmDeleteCourse"  />
	<spring:message code="course.cancel" var="cancelCourse"  />
	
	<input type="submit" id="submit" name="save"
		value="${saveCourse}" />&nbsp; 
				
	<jstl:if test="${course.id != 0}">
		<input type="submit" name="delete"
			value="${deleteCourse}"
			onclick="return confirm('${confirmDeleteCourse}')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="${cancelCourse}"
		onclick="javascript: relativeRedir('course/list.do');" />
	<br />


</form:form>






