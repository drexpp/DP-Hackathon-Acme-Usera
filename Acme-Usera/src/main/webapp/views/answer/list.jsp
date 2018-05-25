

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

<display:table name="answers" id="row" requestURI="answer${uri}/list.do" class="displaytag">
	<spring:message code="answer.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" sortable="true" />
	<spring:message code="answer.format" var="format" />
	<spring:message code="answer.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" format="${format}" sortable="true" />
	<spring:message code="answer.photoURL" var="photoURLHeader" />
	<display:column property="photoURL" title="${photoURLHeader}" sortable="false" />
	<spring:message code="answer.isSolution" var="isSolutionHeader" />
	<display:column property="isSolution" title="${isSolutionHeader}" sortable="true" />
	<spring:message code="answer.question" var="questionHeader" />
	<display:column property="question.title" title="${questionHeader}" sortable="true" />
	<spring:message code="answer.actor" var="actorHeader" />
	<display:column property="actor.name" title="${actorHeader}" sortable="true" />

</display:table>

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