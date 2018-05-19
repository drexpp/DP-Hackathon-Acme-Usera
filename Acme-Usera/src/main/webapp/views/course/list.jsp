

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
	
	<!-- Category -->
	<spring:message code="course.category"
		var="category" />
	<display:column property="category.name" title="${category}"
		sortable="true" />
	
		
	<!-- Display -->
<security:authorize access="isAuthenticated()">
	<display:column>
		<a href="course/display.do?courseId=${row.id}"> <spring:message
			code="course.display" />
		</a>
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
