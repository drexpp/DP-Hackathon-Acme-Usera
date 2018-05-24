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


<jstl:if test="${course.isClosed == true }">
<img src="images/cancel.png" width="60" height="auto"/>
</jstl:if>

<h2> <jstl:out value="${course.title}"> </jstl:out> </h2>
<spring:message code="subscription.subscriptionType" var ="subscriptionTypeHeader"/> 
<strong> <jstl:out value="${subscriptionTypeHeader}"></jstl:out>: ${subscriptionType} </strong>

<br/>
<br/>

<table class="displayStyle">


<tr>
<td> <strong> <spring:message code="course.description" /> : </strong> </td>
<td> <jstl:out value="${ course.description}"></jstl:out></td>
</tr>


<spring:message code="master.page.date.pattern" var ="pattern"/>
<tr>
<td> <strong> <spring:message code="course.creation.date" /> : </strong> </td>
<td> <fmt:formatDate value="${course.creationDate}" pattern="${pattern}"/> </td>
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



<display:table name="lessons" id="row" requestURI="course/display.do?courseId=${course.id}" class="displaytag">
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
	<a href="lesson/student/read.do?lessonId=${row.id}"> <img class="eyeImg" src="images/eye.png" width="30" height="auto"/>
		</a>	
	</jstl:when>
<jstl:otherwise>
<img class="eyeImg" src="images/notEye.png" width="30" height="auto"/>
</jstl:otherwise>	
	</jstl:choose>
	</display:column>
</security:authorize>
<security:authorize access="hasRole('TEACHER')">
<display:column>
<a href="lesson/teacher/edit.do?lessonId=${row.id}"><spring:message code="lesson.edit"/> </a>	
</display:column>
</security:authorize>
</display:table>

<security:authorize access="hasRole('TEACHER')">
<jstl:if test="${principal.coursesJoined.contains(course) }">
<spring:message code="lesson.create" var="createLesson"/> 
<a href="lesson/teacher/create.do?courseId=${course.id}"> 
	<jstl:out value="${createLesson}"></jstl:out>	</a>
</jstl:if>
</security:authorize>
<br/>
<br/>


<!-- Tabla de profesores para solicitar una tutoría -->
<security:authorize access="hasRole('STUDENT')">
<jstl:if test="${subscriptionType.equals('PREMIUM') }">

<spring:message code="course.tutors" var="teachers"/> 
<h3> <jstl:out value="${teachers}"> </jstl:out> </h3>
<display:table name="course.teachers" id="tutor" requestURI="course/display.do?courseId=${course.id}" class="displaytag">
<!-- Name -->
<spring:message code ="teacher.name" var="teacherName"/>
<display:column property="name" title="${teacherName}" sortable="true" />
<!-- Surname -->
<spring:message code ="teacher.surname" var="teacherSurname"/>
<display:column property="surname" title="${teacherSurname}" sortable="true" />
<!-- Create Tutorial -->
<display:column>    
<spring:message code="tutorial.create" var="createTutorial" />
<a href="tutorial/student/create.do?teacherId=${tutor.id}"> 
	<jstl:out value="${createTutorial}"></jstl:out>	</a>
</display:column>
</display:table>


</jstl:if>
</security:authorize>

<jstl:if test="${advert != null}">
	<spring:message code ="course.imageBannerNotFound" var = "imageBannerNotFound"></spring:message>
	<a href="${advert.targetURL}">
		<img src="${advert.bannerURL}" alt="${imageBanner}">
	</a>
</jstl:if>

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


<script>
$(document).ready( function () {	
	
	
    $('#tutor').dataTable( {
    	"language": {
        	"url": '${tableLang}'
    	},
		"lengthMenu": [ 5, 10, 25, 50, 100 ]
    } );
} );
</script>