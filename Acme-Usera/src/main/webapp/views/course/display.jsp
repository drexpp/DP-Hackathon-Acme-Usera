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




<h2> <jstl:out value="${course.title}"> </jstl:out> </h2>

<jstl:if test="${course.isClosed == true }">
<jstl:out value="${ course.isClosed}"></jstl:out>
</jstl:if>

<table class="displayStyle">


<tr>
<td> <strong> <spring:message code="course.description" /> : </strong> </td>
<td> <jstl:out value="${ course.description}"></jstl:out></td>
</tr>



<tr>
<td> <strong> <spring:message code="course.creation.date" /> : </strong> </td>
<td> <jstl:out value="${ course.creationDate}"></jstl:out> </td>
</tr>


<tr>
<td> <strong> <spring:message code="course.pictureURL" /> : </strong> </td>
<td> <img src="${course.photoURL}" alt="${pictureError}"  width="200" height="200"/>   </td>
</tr>

<tr>
<td> <strong> <spring:message code="course.creator" /> : </strong> </td>
<td> <a href="teacher/display.do?teacherId=${row.creator.id}"> <jstl:out value="${ course.creator.name}"></jstl:out>
		</a>  </td>
</tr>

</table>



<spring:message code="course.lessons" var="lessons"/> 
<h3> <jstl:out value="${lessons}"> </jstl:out> </h3>



<display:table name="lessons" id="row" requestURI="course/display.do" pagesize="5" class="displaytag">
<spring:message code="course.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />
	<spring:message code="course.description" var="description" />
	<display:column property="description" title="${description}" sortable="false" />
	<spring:message code="lesson.display" var="display" />
	<display:column>
	<a href="lesson/display.do?lessonId=${row.id}"> <jstl:out value="${display}"></jstl:out>
		</a>
	</display:column>
	
<security:authorize access="hasRole('STUDENT')">
	<display:column>
	<jstl:choose>
	<jstl:when test="${not principal.lessons.contains(row)}">
	<a href="lesson/readed.do?lessonId=${row.id}"> <img class="eyeImg" src="images/eye.png" width="30" height="auto"/>
		</a>	
	</jstl:when>
<jstl:otherwise>
<img class="eyeImg" src="images/notEye.png" width="30" height="auto"/>
</jstl:otherwise>	
	</jstl:choose>
	</display:column>
</security:authorize>
</display:table>
<security:authorize access="hasRole('TEACHER')">
<jstl:if test="${principal.coursesJoined.contains(course) }">
<spring:message code="lesson.create" var="createLesson" />
<a href="lesson/teacher/create.do?courseId=${course.id}"> 
	<jstl:out value="${createLesson}"></jstl:out>	</a>
</jstl:if>
</security:authorize>


<jstl:if test="${advert != null}">
	<spring:message code ="course.imageBannerNotFound" var = "imageBannerNotFound"></spring:message>
	<a href="${advert.targetURL}">
		<img src="${advert.bannerURL}" alt="${imageBanner}">
	</a>
</jstl:if>
