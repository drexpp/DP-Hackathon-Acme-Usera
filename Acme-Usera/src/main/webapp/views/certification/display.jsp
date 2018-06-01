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


<jstl:choose>
	<jstl:when test="${permiso}">
		<h3 class="center-text titles"><spring:message code="certification.congratulations"/></h3>
		
		<table class="displayRanking">
			<tr>
				<td>
					<spring:message code="certification.ticker"/> : 
				</td>
				<td>
					<jstl:out value="${certification.ticker}"/>
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="certification.courseName"/> : 
				</td>
				<td>
					<jstl:out value="${certification.examPaper.exam.course.title}"/>
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="certification.courseCreator"/> : 
				</td>
				<td>
					<jstl:out value="${certification.examPaper.exam.course.creator.name}"/> <jstl:out value="${certification.examPaper.exam.course.creator.surname}"/>
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="certification.moment"/> : 
				</td>
				<td>
					<jstl:out value="${certification.moment}"/>
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="certification.FullName"/> : 
				</td>
				<td>
					<jstl:out value="${certification.student.name}"/> <jstl:out value="${certification.student.surname}"/>
				</td>
			</tr>
		</table>
		<h3 class="center-text"><spring:message code="certification.finalMark"/></h3>
		<h2 class="center-text"><span class="greenText"><jstl:out value="${certification.examPaper.mark}"/></span><span>/100</span></h2>
	</jstl:when>
	<jstl:otherwise>
		<jstl:out value="certification.permiso"/>
	</jstl:otherwise>
</jstl:choose>
<script>
$(document).ready(function(){
	$(".center-text, h1").css("text-align", "center");
	$(".center-text").css("color", "rgba(100, 124, 156, 1)");
});
</script>