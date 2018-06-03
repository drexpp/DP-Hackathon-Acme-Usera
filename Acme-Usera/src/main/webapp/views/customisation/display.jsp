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
		<th><spring:message code="customisation.parameters"/>  </th>
		<th><spring:message code="customisation.values"/>  </th>
	</tr>
	
	<tr>
		<td> <strong> <spring:message code="customisation.welcomeMessageEn" /> : </strong> </td>
		<td> <jstl:out value="${customisation.bannerEn}" /> </td>
	</tr>
	
	<tr>
		<td> <strong> <spring:message code="customisation.welcomeMessageEs" /> : </strong> </td>
		<td> <jstl:out value="${customisation.bannerEs}" /> </td>
	</tr>
	
	<tr>
		<td> <strong> <spring:message code="customisation.standardPrice" /> : </strong> </td>
		<td> <jstl:out value="${customisation.standardPrice}" /> </td>
	</tr>
	
	<tr>
		<td> <strong> <spring:message code="customisation.premiumPrice" /> : </strong> </td>
		<td> <jstl:out value="${customisation.premiumPrice}" /> </td>
	</tr>
	
	<tr>
		<td> <strong> <spring:message code="customisation.conversionRate" /> : </strong> </td>
		<td> <jstl:out value="${customisation.conversionRate}" /> </td>
	</tr>

</table>

<div>

<a href="customisation/admin/edit.do"> <spring:message code="customisation.edit"/> </a>

</div>


