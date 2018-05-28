

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


<display:table name="questions" id="row" requestURI="question/student/list.do" class="displaytag">
	
	<spring:message code="question.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}"  >
	<jstl:choose>
				<jstl:when test="${not empty row.answers}">
					<a href="question/display.do?articleId=${row.id}">
						<jstl:out value="${row.title}"/>
					</a>
				</jstl:when>
			<jstl:otherwise>
			<jstl:out value="${row.title}"/>
			</jstl:otherwise>
			</jstl:choose>
	</display:column>
	
	<spring:message code="question.pictureError" var="pictureError" />
	
	<spring:message code="question.question" var="questionHeader" />
	<display:column property="question" title="${questionHeader}" />
	<spring:message code="question.format" var="format" />
	<spring:message code="question.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" format="${format}" />
	<spring:message code="question.photoURL" var="photoURLHeader" />
	<display:column title="${photoURLHeader}"> <img src="${row.photoURL}" alt="${pictureError}"  width="200" height="200"> </display:column>
	<spring:message code="question.isAnswered" var="isAnsweredHeader" />
	<display:column property="isAnswered" title="${isAnsweredHeader}"  />
	<spring:message code="question.student" var="studentHeader" />
	<display:column title="${studentHeader}"  > 
		<a href="student/display.do?studentId=${row.student.id}">
			<jstl:out value="${row.student.name} ${row.student.surname}"/>
		</a>
	</display:column>
	<display:column>
		<a href="question/display.do?questionId=${row.id}"> <spring:message
			code="question.display" />
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
