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
<td class="right-display">  <jstl:out value = "${teacher.userAccount.username}"/> &nbsp;  </td>
</tr>

<tr>
<td class ="left-display"> <strong> <spring:message code="actor.name" /> : </strong> </td>
<td class="right-display">  <jstl:out value = "${teacher.name}"/> &nbsp;  </td>
</tr>

<tr>
<td class ="left-display"> <strong> <spring:message code="actor.surname" /> : </strong> </td>
<td class="right-display">  <jstl:out value = "${teacher.surname}"/> &nbsp;  </td>
</tr>

<tr>
<td class ="left-display"> <strong> <spring:message code="actor.email" /> : </strong> </td>
<td class="right-display"> <jstl:out value ="${teacher.email}" /> &nbsp; </td>
</tr>

<tr>
<td class ="left-display"> <strong> <spring:message code="actor.phone" /> : </strong> </td>
<td class="right-display">  <jstl:out value="${teacher.phone}" /> &nbsp; </td>
</tr>

<tr>
<spring:message code = "actor.dateBirth.format" var="format"/>
<td class ="left-display"> <strong> <spring:message code="actor.dateBirth" /> : </strong> </td>
<td class="right-display"> <fmt:formatDate value="${teacher.dateBirth}" pattern="${format}"/> &nbsp; </td>
</tr>

<security:authorize access="hasRole('TEACHER')">
<tr>
<td class ="left-display"> <strong> <spring:message code="actor.address" /> : </strong> </td>
<td class="right-display">  <jstl:out value="${teacher.address}" /> &nbsp; </td>
</tr>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
<tr>
<td class ="left-display"> <strong> <spring:message code="actor.address" /> : </strong> </td>
<td class="right-display">  <jstl:out value="${teacher.address}" /> &nbsp; </td>
</tr>

</security:authorize>

</table>

<h3 class="titles"><spring:message code="actor.contactInfo" /></h3>

<table class="displayStyle" >
	<tr>
		<td class ="left-display"> <strong> <spring:message code="actor.skype" /> : </strong> </td>
		<td class="right-display">  <jstl:out value="${teacher.contactInfo.skype}" /> &nbsp; </td>
	</tr>
	
	<tr>
		<td class ="left-display"> <strong> <spring:message code="actor.contactPhone" /> : </strong> </td>
		<td class="right-display">  <jstl:out value="${teacher.contactInfo.contactPhone}" /> &nbsp; </td>
	</tr>
	
	<tr>
		<td class ="left-display"> <strong> <spring:message code="actor.comments" /> : </strong> </td>
		<td class="right-display">  
			<jstl:choose>
				<jstl:when test="${not empty teacher.contactInfo.comments}">
					<jstl:forEach items="${teacher.contactInfo.comments}" var="comment">
						<ul>
							<li> <jstl:out value="${comment}"/> </li>
						</ul>
					</jstl:forEach>	
				</jstl:when>
				<jstl:otherwise>
					<spring:message code="actor.comments.empty"/>
				</jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>
	
	<tr>
		<td class ="left-display"> <strong> <spring:message code="actor.links" /> : </strong> </td>
		<td class="right-display">
			<jstl:choose>
				<jstl:when test="${not empty teacher.contactInfo.links}">
					<jstl:forEach items="${teacher.contactInfo.links}" var="link">
						<ul>
							<li> <jstl:out value="${link}"/> </li>
						</ul>
					</jstl:forEach>	
				</jstl:when>
				<jstl:otherwise>
					<spring:message code="actor.links.empty"/>
				</jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>
</table>
