

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
	name="tutorials" requestURI="tutorial/list.do" id="row">
	
	
	<!-- startTime -->
	<spring:message code="tutorial.pattern" var ="pattern"/>
	<spring:message code="tutorial.start.time"
		var="startTime" />
	<display:column title="${startTime}"
		sortable="true">
	<fmt:formatDate value="${row.startTime}" pattern="${pattern}"/>	
		</display:column>
		
<security:authorize access="hasRole('TEACHER')">		
	<!-- student -->
	<spring:message code="tutorial.student"
		var="student" />
	<display:column property="student.name" title="${student}"
		sortable="true" />
</security:authorize>


<security:authorize access="hasRole('STUDENT')">		
	<!-- teacher -->
	<spring:message code="tutorial.teacher"
		var="teacher" />
	<display:column property="teacher.name" title="${teacher}"
		sortable="true" />
</security:authorize>
	


<!-- Refuse -->
<security:authorize access="hasRole('TEACHER')">
<jsp:useBean id="now" class="java.util.Date"/>
	<display:column>
	<jstl:if test="${principal.tutorials.contains(row) and row.startTime gt now}">
		<a href="tutorial/teacher/refuse.do?tutorialId=${row.id}"> <spring:message
			code="tutorial.refuse" />
		</a>
	</jstl:if>	
	</display:column>
</security:authorize>	
	
</display:table>
