
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:choose>
<jstl:when test="${permiso}">

<spring:message code="teacher.terms" var="terms"/>

<script type="text/javascript">
function Terms(){
	alert("${terms}");
}
</script>
<form:form action="teacher/register.do" modelAttribute="actorForm" >


	<form:hidden path="id"/>
	<form:hidden path="version"/>

	<acme:textbox code="teacher.name" path="name"/>
	<br />
	
	<acme:textbox code="teacher.surname" path="surname"/>
	<br />
	
	<acme:textbox code="teacher.email" path="email"/>
	<br />
	
	<!-- Input del form con el formato antiguo para permitir el pattern -->
	<form:label path="phone">
		<spring:message code="teacher.phone" />
	</form:label>	
	<form:input path="phone" pattern="[0-9]{9}"/>	
	<form:errors path="phone" cssClass="error" />
	<br />
	<br />
	<acme:textbox code="teacher.address" path="address"/>
	<br />
	
	<acme:textbox code="teacher.dateBirth" path="dateBirth"/>
	<br />
	
	
	<acme:textbox code="teacher.username" path="userAccount.username"/>
	<br />
	
	<acme:password code="teacher.password" path="userAccount.password"/>
	<br />
	
	<acme:password code="teacher.confirmPassword" path="confirmPassword"/>
	<br />
	
	<spring:message code ="teacher.check"/>
	<form:checkbox path="check"/>
	<form:errors path="check" cssClass="error" />
	<br />
	

	<input type="submit" name="save" id="save"
		value="<spring:message code="teacher.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="teacher.cancel" />"
		onclick="javascript: relativeRedir('');" />
	<br />
<jstl:out value="${message}"/>
</form:form>

</jstl:when>
<jstl:otherwise>
<spring:message code="teacher.permision" />
</jstl:otherwise>
</jstl:choose>


