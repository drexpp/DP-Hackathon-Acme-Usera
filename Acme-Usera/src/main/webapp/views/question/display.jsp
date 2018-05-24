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
<jstl:when test="${question.forum.course.isClosed == false}"> 

<h2> <jstl:out value="${question.title}"> </jstl:out> </h2>

<table class="displayStyle">


<tr>
<td> <strong> <spring:message code="question.question" /> : </strong> </td>
<td> <jstl:out value="${ question.question}"></jstl:out></td>
</tr>


<tr>
<spring:message code="question.moment.format" var = "format"/>
<td> <strong> <spring:message code="question.moment" /> : </strong> </td>
<td> <fmt:formatDate value = "${question.moment}" pattern="${format}"/> &nbsp;   </td>
</tr>

<tr>
<spring:message code="question.pictureError" var="pictureError" />
<td> <strong> <spring:message code="question.photoURL" /> : </strong> </td>
<td> <img src="${question.photoURL}" alt="${pictureError}"  width="200" height="200"/>   </td>
</tr>

<tr>
<td> <strong> <spring:message code="question.isAnswered" /> : </strong> </td>
<td> <jstl:out value="${question.isAnswered}"></jstl:out> </td>
</tr>


<tr>
<td> <strong> <spring:message code="question.student" /> : </strong> </td>
<td> <a href="student/display.do?teacherstudentId=${question.student.id}"> <jstl:out value="${question.student.name}"></jstl:out></a> </td>
</tr>

</table>



<spring:message code="question.answers" var="answers"/> 
<h3> <jstl:out value="${answers}"> </jstl:out> </h3>



<display:table name="answers" id="row" requestURI="question/display.do" pagesize="5" class="displaytag">

	<security:authorize access="hasRole('ADMIN')">
		<spring:message code="question.confirm" var="confirmQuestion"  />
		<display:column>
				<a href="answer/admin/delete.do?answerId=${row.id}" onclick="return confirm('${confirmQuestion}')"><spring:message code ="question.delete" /></a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('STUDENT')">
		<spring:message code="question.confirm" var="confirmQuestion"  />
		<display:column>
				<a href="answer/student/delete.do?answerId=${row.id}" onclick="return confirm('${confirmQuestion}')"><spring:message code ="question.delete" /></a>
		</display:column>
	</security:authorize>
	<spring:message code="answer.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" sortable="true" />
	<spring:message code="answer.format" var="format" />
	<spring:message code="answer.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" format="${format}"  sortable="true" />
	<spring:message code="answer.photoURL" var="photoURLHeader" />
	<display:column title="${photoURLHeader}" sortable="false" > <img src="${row.photoURL}" alt="${pictureError}"  width="200" height="200"> </display:column>
	<spring:message code="answer.isSolution" var="isSolutionHeader" />
	<display:column title="${isSolutionHeader}" >
	
	<security:authorize access="hasRole('TEACHER')">

	<jstl:if test="${row.question.isAnswered == false}">
	<jstl:choose>
		<jstl:when test="${row.isSolution == true}">
			<a href="answer/teacher/solution.do?answerId=${row.id}"> <spring:message
			code="answer.setSolutionFalse" />
		</a>
		</jstl:when>
		 
		<jstl:otherwise>
			<a href="answer/teacher/solution.do?answerId=${row.id}"> <spring:message
			code="answer.setSolutionTrue" />
		</a>
		</jstl:otherwise>
		</jstl:choose>
	</jstl:if>
	</security:authorize>

	<jstl:choose>
		<jstl:when test="${row.isSolution == true}">
			<spring:message code="answer.solutionTrue" var="solutionTrue" />
			<jstl:out value="${solutionTrue}"/>
		</jstl:when>
		
		<jstl:otherwise>
				<spring:message code="answer.solutionFalse" var="solutionFalse" />
			<jstl:out value="${solutionFalse}"/>
		</jstl:otherwise>
	</jstl:choose>
	</display:column>
	<spring:message code="answer.actor" var="actorHeader" />
	<display:column title="${actorHeader}"  > 
		<a href="student/display.do?studentId=${row.actor.id}">
			<jstl:out value="${row.actor.name} ${row.actor.surname}"/>
		</a>
	</display:column>

</display:table>

<security:authorize access="hasRole('STUDENT')">
<jstl:if test="${question.isAnswered == false}">
<a href="answer/student/create.do?questionId=${question.id}"> <spring:message code="question.answer.create"/></a>
</jstl:if>
</security:authorize>

</jstl:when>
<jstl:otherwise>
<spring:message code="question.permission" />
</jstl:otherwise>
</jstl:choose>

