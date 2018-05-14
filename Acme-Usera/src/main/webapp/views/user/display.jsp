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
<td class ="left-display"> <strong> <spring:message code="user.username" /> : </strong> </td>
<td class="right-display">  <jstl:out value = "${user.userAccount.username}"/> &nbsp;  </td>
</tr>

<tr>
<td class ="left-display"> <strong> <spring:message code="user.name" /> : </strong> </td>
<td class="right-display">  <jstl:out value = "${user.name}"/> &nbsp;  </td>
</tr>

<tr>
<td class ="left-display"> <strong> <spring:message code="user.surname" /> : </strong> </td>
<td class="right-display">  <jstl:out value = "${user.surname}"/> &nbsp;  </td>
</tr>

<tr>
<td class ="left-display"> <strong> <spring:message code="user.phone" /> : </strong> </td>
<td class="right-display">  <jstl:out value="${user.phone}" /> &nbsp; </td>
</tr>

<security:authorize access="hasRole('USER')">
<jstl:if test="${principal.id == user.id }">
<tr>
<td class ="left-display"> <strong> <spring:message code="user.address" /> : </strong> </td>
<td class="right-display">  <jstl:out value="${user.address}" /> &nbsp; </td>
</tr>
</jstl:if>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
<tr>
<td class ="left-display"> <strong> <spring:message code="user.address" /> : </strong> </td>
<td class="right-display">  <jstl:out value="${user.address}" /> &nbsp; </td>
</tr>

</security:authorize>

<tr>
<td class ="left-display"> <strong> <spring:message code="user.email" /> : </strong> </td>
<td class="right-display"> <jstl:out value ="${user.email}" /> &nbsp; </td>
</tr>

<tr>
<td class ="left-display"> <strong> <spring:message code="user.dateBirth" /> : </strong> </td>
<td class="right-display"> <jstl:out value ="${user.dateBirth}" /> &nbsp; </td>
</tr>




<spring:message code="user.showRende" var="showRende"/>

<tr>
<td class ="left-display"> <strong> <spring:message code="user.rendes" /> : </strong> </td>
<td class="right-display"> 

<jstl:choose>
<jstl:when test="${not empty user.rendes}"> 
<ul>
<jstl:forEach items="${user.rendes}" var="rende">
<li> <jstl:out value="${rende.name}"/> &nbsp; (<a href="rende/user/display.do?rendeId=${rende.id}"> ${showRende} </a>) </li>
</jstl:forEach>
</ul> 
</jstl:when>
<jstl:otherwise>

<spring:message code="user.rendes.empty" />

</jstl:otherwise>
</jstl:choose>

</td>
</tr>

<jstl:if test="${viewAttendants}">

		<spring:message code="user.questionAndReplies" var="QuestionReplies"/>
	<tr><td class ="left-display"><strong><jstl:out value="${QuestionReplies}"/> <spring:message code="user.to"/> ${rende.name} :</strong></td>
		<td class="right-display"><jstl:forEach items="${mapQuestionsView}" var="map" varStatus="loop">
				<br/>
					<strong> <spring:message code="user.question" /> ${loop.count} : </strong><jstl:out value="${map.key.question}"/> &nbsp; <br/>
					<strong> <spring:message code="user.reply" /> ${loop.count} : </strong><jstl:out value="${map.value.reply}"/><br/>
				<br/>
		</jstl:forEach></td>
	
	
	</tr>
	
</jstl:if>

</table>






