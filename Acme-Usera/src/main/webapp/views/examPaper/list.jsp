

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


<display:table name="examPapers" id="row" requestURI="examPaper/teacher/list.do" class="displaytag">
	
	<spring:message code="examPaper.mark" var="markHeader" />
	<display:column property="mark" title="${markHeader}" />
	
	<spring:message code="question.format" var="format" />
	<spring:message code="examPaper.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" format="${format}" />
	
	<display:column>
		<a href="examPaper/display.do?examPaperId=${row.id}"> <spring:message
			code="examPaper.display" />
		</a>
	</display:column>

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
