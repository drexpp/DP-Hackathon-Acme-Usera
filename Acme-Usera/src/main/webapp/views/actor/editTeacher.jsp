<%--
 * 
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jstl:choose>
<jstl:when test="${permiso}">

<form:form action="${formURL}" modelAttribute="editActorTeacherForm" >


	<form:hidden path="id"/>
	<form:hidden path="version"/>

	<acme:textbox code="actor.name" id="name" path="name"/>
	<br />
	
	<acme:textbox code="actor.surname" path="surname"/>
	<br />
	
	<acme:textbox code="actor.email" path="email"/>
	<br />
	
	<acme:textbox code="actor.dateBirth" path="dateBirth"/>
	<br />
	
	<acme:textbox oninput="setCustomValidity('')" id="phone" path="phone" pattern="\\+?([0-9]{9})?" code="actor.phone"/>
	<br />
	<acme:textbox code="actor.address" path="address"/>
	<br />
	<fieldset>
		
	<h3 class="titles"><spring:message code="actor.contactInfo"/></h3>
		
	<acme:textbox code="actor.skype" path="skype"/>
	<br>
	<acme:textbox oninput="setCustomValidity('')" id="contactPhone" code="actor.contactPhone" path="contactPhone" pattern="\\+?([0-9]{9})?"/>
	
	<fieldset>
	<legend> <form:label path="comments"> <spring:message code="actor.comments" />: </form:label> </legend>
		<div id="list1">
		<table class="displayStyle">
			<jstl:forEach begin="0" end="${elementsLengthComments}" var="comments" varStatus="i" step="1">
	  			 <tr class="list-item">
			<td> <form:input path="comments[${i.index}]" /></td>
	    		<td>	<a href="#" onclick="event.preventDefault();"
					class="list-remove"> <spring:message code="actor.comment.remove" /> </a> </td>
		    </tr>
	            <br />
	        </jstl:forEach>
		        
		</table>
		
		<a href="#" onclick="event.preventDefault();" class="list-add"><spring:message code="actor.comment.add" /></a>
		</div>
		<br />
		<form:errors cssClass="error" path="comments" />

	</fieldset>
	
	<fieldset>

	
	<form:label path="links"> <spring:message code="actor.links" />: </form:label>



		<div id="list2">
		<table class="displayStyle">

			
			<jstl:forEach begin="0" end="${elementsLengthLinks}" var="links" varStatus="i" step="1">
	  			 <tr class="list-item">
			<td> <form:input path="links[${i.index}]" /></td>
	    		<td>	<a href="#" onclick="event.preventDefault();"
					class="list-remove"> <spring:message code="actor.links.remove" /> </a> </td>
		    </tr>
	            <br />
	        </jstl:forEach>
		        
		</table>
		
		<a href="#" onclick="event.preventDefault();" class="list-add"><spring:message code="actor.links.add" /></a>
		</div>
		<br />
		<form:errors cssClass="error" path="links" />

	</fieldset>
	</fieldset>
	<br>
	

	<input type="submit" name="save" id="save"
		value="<spring:message code="actor.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="actor.cancel" />"
		onclick="javascript: relativeRedir('');" />
	<br />
</form:form>

</jstl:when>
<jstl:otherwise>
<spring:message code="actor.permision" />
</jstl:otherwise>
</jstl:choose>
<script>
var message = '<spring:message code="actor.invalidPhone"/>';
</script>
<script>
$(document).ready(function() {
    $("#list1").dynamiclist();
});
$(document).ready(function() {
    $("#list2").dynamiclist();
});
</script>