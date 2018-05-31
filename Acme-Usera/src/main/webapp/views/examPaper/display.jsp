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




<h2> <jstl:out value="${examPaper.exam.title}"> </jstl:out> </h2>


<table class="displayStyle">

<tr>
<td> <strong> <spring:message code="examPaper.mark" /> : </strong> </td>
<td> <jstl:out value="${exam.mark}"></jstl:out></td>
</tr>


<spring:message code="question.moment.format" var="format" />
<tr>
<td> <strong> <spring:message code="examPaper.moment" /> : </strong> </td>
<td> <fmt:formatDate value="${examPaper.moment}" pattern="${format}"/>   </td>
</tr>

</table>

<display:table name="examQuestions" id="row" requestURI="examPaper/display.do" class="displaytag">

	<spring:message code="examQuestion.number" var="numberHeader" />
	<display:column property="number" title="${numberHeader}"  />
	<spring:message code="examQuestion.statement" var="statementHeader" />
	<display:column property="statement" title="${statementHeader}" />
	<spring:message code="examQuestion.maxScore" var="maxScoreHeader" />
	<display:column property="maxScore" title="${maxScoreHeader}" />
	<spring:message code="examQuestion.photoURL" var="photoURLHeader" />
	<spring:message code="examQuestion.pictureError" var="pictureError" />
	<display:column title="${photoURLHeader}"  > <img src="${row.photoURL}" alt="${pictureError}"  width="200" height="200"> </display:column>
	<security:authorize access="hasRole('TEACHER')">
		<spring:message code="examQuestion.answer" var="answerHeader" />
		<display:column property="answer" title="${answerHeader}" />
	</security:authorize>
	<security:authorize access="hasRole('STUDENT')">
		<spring:message code="examPaper.answer" var="examAnswerHeader" />
		<display:column>
				<a href="examAnswer/student/create.do?examPaperId=${examPaper.id}"> <spring:message code="examAnswer.create"/></a>
		</display:column>
	</security:authorize>
	

</display:table>


<spring:message code="datatables.locale.lang" var="tableLang"/>
<spring:message code="datatables.moment.format" var="tableFormatMoment"/>
<script>
$(document).ready( function () {	
	
	
    $('#row').dataTable( {
    	"language": {
        	"url": '${tableLang}'
    	},
		"lengthMenu": [ 5, 10, 25, 50, 100 ]
    } );
} );
</script>
