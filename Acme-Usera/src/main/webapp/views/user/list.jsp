

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
	name="users" requestURI="user${uri}/list.do" id="row">
	
	
	<!-- userAccount -->
	<spring:message code="user.username"
		var="usernameHeader" />
	<display:column property="userAccount.username" title="${usernameHeader}"
		sortable="true" />
		
		
	<!-- name -->
	<spring:message code="user.name"
		var="nameHeader" />
	<display:column property="name" title="${nameHeader}"
		sortable="true" />

	<!-- surname -->
	<spring:message code="user.surname"
		var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}"
		sortable="true" />
		
	<!-- phone -->
	<spring:message code="user.phone"
		var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}"
		sortable="true" />
		
	<!-- address -->
	<spring:message code="user.address"
			var="addressHeader" />
	<security:authorize access="hasRole('USER')">
			<display:column title="${addressHeader}"
			sortable="true">
		<jstl:if test="${principal==row}">
			<jstl:out value ="${row.address}"/>
		</jstl:if>
			</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
			<display:column title="${addressHeader}"
			sortable="true">
			<jstl:out value ="${row.address}"/>
			</display:column>
	</security:authorize>

	<!-- email -->
	<spring:message code="user.email"
		var="emailHeader" />
	<display:column property="email" title="${emailHeader}"
		sortable="true" />
		
	<!-- dateBirth -->
	<spring:message code="user.dateBirth"
		var="dateBirthHeader" />
	<display:column property="dateBirth" title="${dateBirthHeader}"
		sortable="true" />	
		
	
	<display:column>
		<a href="user${uri}/display.do?userId=${row.id}"> <spring:message
			code="user.display" />
		</a>
	</display:column>
		
</display:table>
