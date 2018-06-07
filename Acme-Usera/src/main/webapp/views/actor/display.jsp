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


<table class="displayStyle" >


<tr>
<td class ="left-display"> <strong> <spring:message code="actor.username" /> : </strong> </td>
<td class="right-display">  <jstl:out value = "${actor.userAccount.username}"/> &nbsp;  </td>
</tr>

<tr>
<td class ="left-display"> <strong> <spring:message code="actor.name" /> : </strong> </td>
<td class="right-display">  <jstl:out value = "${actor.name}"/> &nbsp;  </td>
</tr>

<tr>
<td class ="left-display"> <strong> <spring:message code="actor.surname" /> : </strong> </td>
<td class="right-display">  <jstl:out value = "${actor.surname}"/> &nbsp;  </td>
</tr>

<tr>
<td class ="left-display"> <strong> <spring:message code="actor.email" /> : </strong> </td>
<td class="right-display"> <jstl:out value ="${actor.email}" /> &nbsp; </td>
</tr>

<tr>
<td class ="left-display"> <strong> <spring:message code="actor.phone" /> : </strong> </td>
<td class="right-display">  <jstl:out value="${actor.phone}" /> &nbsp; </td>
</tr>

<tr>
<spring:message code = "actor.dateBirth.format" var="format"/>
<td class ="left-display"> <strong> <spring:message code="actor.dateBirth" /> : </strong> </td>
<td class="right-display"> <fmt:formatDate value="${actor.dateBirth}" pattern="${format}"/> &nbsp; </td>
</tr>

<security:authorize access="hasRole('STUDENT')">
<jstl:if test="${principal.id == student.id }">
<tr>
<td class ="left-display"> <strong> <spring:message code="actor.address" /> : </strong> </td>
<td class="right-display">  <jstl:out value="${actor.address}" /> &nbsp; </td>
</tr>
</jstl:if>
</security:authorize>

<security:authorize access="hasRole('SPONSOR')">
<jstl:if test="${principal.id == sponsor.id }">
<tr>
<td class ="left-display"> <strong> <spring:message code="actor.address" /> : </strong> </td>
<td class="right-display">  <jstl:out value="${actor.address}" /> &nbsp; </td>
</tr>
</jstl:if>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
<tr>
<td class ="left-display"> <strong> <spring:message code="actor.address" /> : </strong> </td>
<td class="right-display">  <jstl:out value="${actor.address}" /> &nbsp; </td>
</tr>

</security:authorize>  
</table>
<br>
<security:authorize access="hasRole('STUDENT')">
<jstl:if test="${principal.id == student.id }">
<tr>
<td class ="left-display"> <strong> <spring:message code="actor.certifications" /> : </strong> </td>
	<td class="right-display">  
	<jstl:choose>
		<jstl:when test="${not empty actor.certifications}">
			<jstl:forEach items="${actor.certifications}" var="certification">
				<ul>
					<li><a href="examPaper/student/certification.do?certificationId=${certification.id}"><jstl:out value="${certification.examPaper.exam.course.title}"/></a></li>
				</ul>
			</jstl:forEach>
		</jstl:when>
		<jstl:otherwise>
			<br>
			<br>
			<spring:message code="actor.certifications.empty"/>
		</jstl:otherwise>
	</jstl:choose>
	</td>
</tr>
</jstl:if>
</security:authorize>
