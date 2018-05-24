
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

<spring:message code="actor.terms" var="terms"/>

<script type="text/javascript">
function Terms(){
	alert("${terms}");
}
</script>
<form:form action="${formURL}" modelAttribute="actorForm" >


	<form:hidden path="id"/>
	<form:hidden path="version"/>

	<acme:textbox code="actor.name" path="name"/>
	<br />
	
	<acme:textbox code="actor.surname" path="surname"/>
	<br />
	
	<acme:textbox code="actor.email" path="email"/>
	<br />
	
	<!-- Input del form con el formato antiguo para permitir el pattern -->
	<form:label path="phone">
		<spring:message code="actor.phone" />
	</form:label>	
	<form:input path="phone" pattern="\\+?([0-9]{9})?"/>	
	<form:errors path="phone" cssClass="error" />
	<br />
	<br />
	<acme:textbox code="actor.address" path="address"/>
	<br />
	
	<spring:message code="actor.dateBirth.placeholder" var="datePlaceHolder"/>
	<acme:textbox code="actor.dateBirth" path="dateBirth" placeholder="${datePlaceHolder}"/>
	<br />
	
	
	<acme:textbox code="actor.username" path="userAccount.username"/>
	<br />
	
	<acme:password code="actor.password" path="userAccount.password"/>
	<br />
	
	<acme:password code="actor.confirmPassword" path="confirmPassword"/>
	<br/>
	<br/>
	
	<fieldset>
		
	<h3 class="titles"><spring:message code="actor.contactInfo"/></h3>
	
	
	<acme:textbox code="actor.skype" path="skype"/>
	<br />
	
	<acme:textbox code="actor.contactPhone" path="contactPhone"/>
	<br />
	
	<fieldset>
	<legend> <form:label path="comments"> <spring:message code="actor.comments" />: </form:label> </legend>

		<div id="list1">
		<table class="displayStyle">
			
			<jstl:forEach begin="0" end="0" var="comments" varStatus="i" step="1">
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

			
			<jstl:forEach begin="0" end="0" var="links" varStatus="i" step="1">
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
	<br/>
	<br/>
	
	<spring:message code ="actor.check"/>
	<form:checkbox path="check"/>
	<form:errors path="check" cssClass="error" />
	<br />
	
	<br />

	<input type="submit" name="save" id="save"
		value="<spring:message code="actor.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="actor.cancel" />"
		onclick="javascript: relativeRedir('');" />
	<br />
	<script>
    $(document).ready(function() {
        $("#list1").dynamiclist();
    });
    $(document).ready(function() {
        $("#list2").dynamiclist();
    });
</script>
</form:form>

</jstl:when>
<jstl:otherwise>
<spring:message code="actor.permision" />
</jstl:otherwise>
</jstl:choose>


