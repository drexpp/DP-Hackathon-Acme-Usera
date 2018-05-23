

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->



<display:table pagesize="5" class="displaytag" 
	name="courses" requestURI="course/list.do" id="row">
	
	
	<!-- title -->
	<spring:message code="course.title"
		var="title" />
	<display:column property="title" title="${title}"
		sortable="true" />
		
		
	<!-- description -->
	<spring:message code="course.description"
		var="description" />
	<display:column property="description" title="${description}"
		sortable="false" />

	<!-- image -->
	<spring:message code="course.pictureURL"
		var="pictureURL" />
	<spring:message code="course.pictureError" var="pictureError" />
	<display:column title="${pictureURL}"
		sortable="false" >
		<img src="${row.photoURL}" alt="${pictureError}"  width="200" height="200"> 
	</display:column>
	
	<!-- isClose -->
		<spring:message code="course.isClose"
		var="isCloseHeader" />
	<display:column title="${isCloseHeader}"> 
	<security:authorize access="hasRole('TEACHER')" var ="isTeacher"/>
	<jstl:choose>
	<jstl:when test="${isTeacher}">
	<jstl:if test="${principal.coursesCreated.contains(row)}">
		<jstl:choose>
		<jstl:when test="${row.isClosed == false}">
			<a href="course/user/close.do?courseId=${row.id}"> <spring:message
			code="course.makeClose" />
		</a>
		</jstl:when>
		<jstl:otherwise>
		<img class="alarmImg" src="images/cancel.png" width="30" height="auto"/>
		</jstl:otherwise>
		 </jstl:choose>
	</jstl:if>
	</jstl:when>
	
	<jstl:otherwise>
	<jstl:choose>
			<jstl:when test="${row.isClosed == true}">
			<img class="alarmImg" src="images/cancel.png" width="30" height="auto"/>
		</jstl:when>
		
		<jstl:otherwise>
			<spring:message code="course.isOpen"/>
		</jstl:otherwise>
		</jstl:choose>
	</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<!-- Category -->
	<spring:message code="course.category"
		var="category" />
	<display:column property="category.name" title="${category}"
		sortable="true" />
	
		
	<!-- Display -->
<security:authorize access="hasRole('TEACHER')" var="isTeacher"/>
<security:authorize access="hasRole('STUDENT')" var="isStudent"/>
<security:authorize access="isAuthenticated()">
	<display:column>
	<jstl:choose>
	<jstl:when test="${isTeacher }">
	<jstl:if test="${principal.coursesJoined.contains(row) }">
		<a href="course/display.do?courseId=${row.id}"> <spring:message
			code="course.display" />
		</a>
	</jstl:if>
	</jstl:when>
	<jstl:when test="${isStudent and subscribed.contains(row)}">
	<a href="course/display.do?courseId=${row.id}"> <spring:message
			code="course.display" />
		</a>
	</jstl:when>
	
	<jstl:when test="${isStudent and !subscribed.contains(row) and !row.isClosed}">
	<a href="subscription/student/create.do?courseId=${row.id}"> <spring:message
			code="course.subscribe" />
		</a>
	</jstl:when>
	
	
	</jstl:choose>
	</display:column>
</security:authorize>	


<!-- Edit -->
<security:authorize access="hasRole('TEACHER')">
	<display:column>
	<jstl:if test="${principal.coursesCreated.contains(row)}">
		<a href="course/teacher/edit.do?courseId=${row.id}"> <spring:message
			code="course.edit" />
		</a>
	</jstl:if>	
	</display:column>
</security:authorize>	
	
</display:table>

<!-- Create -->
<security:authorize access="hasRole('TEACHER')">
		<a href="course/teacher/create.do"> <spring:message
			code="course.create" />
		</a>
</security:authorize>	
