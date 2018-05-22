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




<h2> <jstl:out value="${lesson.title}"> </jstl:out> </h2>


<table class="displayStyle">
<spring:message code="master.page.date.pattern" var ="pattern"/>
<tr>
<td> <strong> <spring:message code="course.creation.date" /> : </strong> </td>
<td><fmt:formatDate value="${lesson.creationDate}" pattern="${pattern}" /></td>
</tr>

<tr>
<td> <strong> <spring:message code="lesson.description" /> : </strong> </td>
<td> <jstl:out value="${ lesson.description}"></jstl:out></td>
</tr>

<tr>
<td> <strong> <spring:message code="lesson.body" /> : </strong> </td>
<td> <jstl:out value="${lesson.body}"></jstl:out>   </td>
</tr>


<tr>
<td> <strong> <spring:message code="lesson.pictureURL" /> : </strong> </td>
<td> <img src="${lesson.photoURL}" alt="${pictureError}"  width="200" height="200"/>   </td>
</tr>

<tr>
<td> <strong> <spring:message code="lesson.videoURL" /> : </strong> </td>
<td>
<jstl:if test="${lesson.videoURL != '' }">
<iframe width="420" height="315" src="${lesson.videoURL}"></iframe>
</jstl:if>
</td>
</tr>

</table>

<jstl:if test="${advert != null}">
	<spring:message code ="course.imageBannerNotFound" var = "imageBannerNotFound"></spring:message>
	<a href="${advert.targetURL}">
		<img src="${advert.bannerURL}" alt="${imageBanner}">
	</a>
</jstl:if>
