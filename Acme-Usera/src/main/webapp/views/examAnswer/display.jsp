<%--
 * 
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>


<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>




<table class="displayStyle">

<tr>
<td> <strong> <spring:message code="examAnswer.mark" /> : </strong> </td>
<td> <jstl:out value="${examAnswer.mark}"></jstl:out></td>
</tr>

<tr>
<td> <strong> <spring:message code="examAnswer.text" /> : </strong> </td>
<td> <jstl:out value="${examAnswer.text}"></jstl:out>   </td>
</tr>

</table>

<security:authorize access="hasRole('TEACHER')">
<a href="examAnswer/teacher/edit.do?examAnswerId=${examAnswer.id}"> <spring:message code="examAnswer.evaluation"/></a>
<br>
<a href="examPaper/display.do?examPaperId=${examAnswer.examPaper.id}"> <spring:message code="examAnswer.goBack"/></a>
</security:authorize>