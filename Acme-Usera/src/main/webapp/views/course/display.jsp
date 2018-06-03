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
		<spring:message code="course.isClose"
		var="isCloseHeader" />
<img src="images/cancel.png" width="60" height="auto" title="${isCloseHeader}"/>
</jstl:if>

<h2> <jstl:out value="${course.title}"> </jstl:out> </h2>
<spring:message code="subscription.subscriptionType" var ="subscriptionTypeHeader"/> 
<strong> <jstl:out value="${subscriptionTypeHeader}"></jstl:out>:
<jstl:if test="${subscriptionType == 'FREE'}">
<spring:message code="subscription.subscriptionType.free"/>
</jstl:if>

<jstl:if test="${subscriptionType == 'STANDARD'}">
<spring:message code="subscription.subscriptionType.standard"/>
</jstl:if>

<jstl:if test="${subscriptionType == 'PREMIUM'}">
<spring:message code="subscription.subscriptionType.premium"/>
</jstl:if>

</strong>
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
<td> <a href="teacher/display.do?teacherId=${course.creator.id}"> <jstl:out value="${ course.creator.name}"></jstl:out>
		</a>  </td>
</tr>

</table>



<spring:message code="course.lessons" var="lessons"/> 
<h3 class="titles"> <jstl:out value="${lessons}"> </jstl:out> </h3>



<display:table name="lessons" id="row" requestURI="course/display.do?courseId=${course.id}" class="displaytag">
<spring:message code="course.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}"   />
	<spring:message code="course.description" var="description" />
	<display:column property="description" title="${description}"/>
	<spring:message code="lesson.display" var="display" />
	<display:column>
	<a href="lesson/display.do?lessonId=${row.id}"> <jstl:out value="${display}"></jstl:out>
		</a>
	</display:column>
	
<security:authorize access="hasRole('STUDENT')">
	<display:column>
	<jstl:choose>
	<jstl:when test="${not principal.lessons.contains(row)}">
			<spring:message code="lesson.read"
		var="readHeader" />
	<a href="lesson/student/read.do?lessonId=${row.id}"> <img class="eyeImg" src="images/eye.png" width="30" height="auto" title="${readHeader}"/>
		</a>	
	</jstl:when>
<jstl:otherwise>
		<spring:message code="lesson.readed"
		var="readedHeader" />
<img class="eyeImg" src="images/notEye.png" width="30" height="auto" title="${readedHeader}"/>
</jstl:otherwise>	
	</jstl:choose>
	</display:column>
</security:authorize>
<!-- Edit Lesson -->
<security:authorize access="hasRole('TEACHER')">
<display:column>
<jstl:if test="${principal.coursesJoined.contains(course) and !course.isClosed }">
<a href="lesson/teacher/edit.do?lessonId=${row.id}"><spring:message code="lesson.edit"/> </a>	
</jstl:if>
</display:column>
</security:authorize>
</display:table>
<!-- Create Lesson -->
<security:authorize access="hasRole('TEACHER')">
<jstl:if test="${principal.coursesJoined.contains(course) and !course.isClosed }">
<spring:message code="lesson.create" var="createLesson"/> 
<a href="lesson/teacher/create.do?courseId=${course.id}"> 
	<jstl:out value="${createLesson}"></jstl:out>	</a>
</jstl:if>
</security:authorize>
<br/>
<br/>


<!-- Tabla de profesores para solicitar una tutoría (STUDENT) -->
<security:authorize access="hasRole('STUDENT')">
<jstl:if test="${subscriptionType.equals('PREMIUM') }">

<spring:message code="course.tutors" var="teachers"/> 
<h3 class="titles"> <jstl:out value="${teachers}"> </jstl:out> </h3>
<display:table name="course.teachers" id="tutor" requestURI="course/display.do?courseId=${course.id}" class="displaytag">
<!-- Name -->
<spring:message code ="teacher.name" var="teacherName"/>
<display:column property="name" title="${teacherName}"   />
<!-- Surname -->
<spring:message code ="teacher.surname" var="teacherSurname"/>
<display:column property="surname" title="${teacherSurname}"   />
<!-- Create Tutorial -->
<display:column>    
<jstl:if test="${course.isClosed == false }">
<spring:message code="tutorial.create" var="createTutorial" />
<a href="tutorial/student/create.do?teacherId=${tutor.id}"> 
	<jstl:out value="${createTutorial}"></jstl:out>	</a>
</jstl:if>
</display:column>
</display:table>


</jstl:if>
</security:authorize>


<!-- Tabla de profesores (TEACHER) -->
<security:authorize access="hasRole('TEACHER')">

<spring:message code="course.tutors" var="teachers"/> 
<h3 class="titles"> <jstl:out value="${teachers}"> </jstl:out> </h3>
<display:table name="course.teachers" id="tutor" requestURI="course/display.do?courseId=${course.id}" class="displaytag">
<!-- Name -->
<spring:message code ="teacher.name" var="teacherName"/>
<display:column property="name" title="${teacherName}"   />
<!-- Surname -->
<spring:message code ="teacher.surname" var="teacherSurname"/>
<display:column property="surname" title="${teacherSurname}"   />
<!-- Create Tutorial -->
<jstl:if test="${principal.equals(course.creator)}">
<display:column>    
<jstl:if test="${!principal.equals(tutor)}">
<spring:message code="course.delete" var="deleteTutor" />
<a href="course/teacher/remove.do?courseId=${course.id}&teacherId=${tutor.id}"> 
	<jstl:out value="${deleteTutor}"></jstl:out>	</a>
</jstl:if>
</display:column>
</jstl:if>

</display:table>

</security:authorize>

<jstl:if test="${advert != null}">
	<spring:message code ="course.imageBannerNotFound" var = "imageBannerNotFound"></spring:message>
	<a href="${advert.targetURL}">
		<img src="${advert.bannerURL}" alt="${imageBanner}">
	</a>
</jstl:if>

<!-- Exam -->
<spring:message code="course.exam" var="exam"/> 
<h3 class="titles"> <jstl:out value="${exam}"> </jstl:out> </h3>
<security:authorize access="hasRole('STUDENT')">
	<jstl:if test="${not coursesWithExamPaperFromStudent.contains(course) && course.exam != null && principal.lessons.containsAll(course.lessons) }">
		<a href="examPaper/student/create.do?examId=${course.exam.id}"> <spring:message code="exam.examPaper.evaluate"/></a>
	</jstl:if>	
	<jstl:if test="${exams.contains(course.exam)}">
		<a href="exam/display.do?examId=${course.exam.id}"> <spring:message code="exam.examPaper.showMyExamPaper"/></a>
	</jstl:if>	
</security:authorize>

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