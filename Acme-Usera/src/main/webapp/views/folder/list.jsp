<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div class="messages-table">
	<div class="menu-folders">
	<h3 class="titles"> <spring:message code="folder.listFolders" /></h3>
	<table class="displayStyle" >
		<jstl:forEach items="${folders}" var="folder" varStatus="loop">
		<tr>
			<td>
				<a href="folder/actor/list.do?folderId=${folder.id}"><spring:message code="folder${loop.index}"/></a>
			</td>
		</tr>
		</jstl:forEach>
	</table>
	</div>
	
	<%-- La lista de mensajes de la carpeta seleccionada: --%>
	
	<div class="messages-list">
	<h3 class="titles"> <spring:message code="folder.messages" /> <spring:message code="folder${currentFolderId}"></spring:message></h3>
	

	<display:table name="messages" id="row"
		requestURI="messages/actor/list.do"
		class="displaytag">
				<spring:message code="folder.message.subject" var="subject"/>
				<display:column title="${subject}">
					<jstl:out value = "${row.subject}"/>
				</display:column>
				<spring:message code="folder.message.body" var="body"/>
				<display:column title="${body}">
					<jstl:out value = "${row.body}"/>
				</display:column>
				
				<spring:message code="folder.message.moment" var="moment"/>
				<spring:message code="folder.moment.format" var="formatMoment" />
 				<display:column property="moment" title="${moment}" format = "${formatMoment}"/>
 				
				<spring:message code="folder.message.sender" var="sender"/>
				<display:column title="${sender}">
					<jstl:out value = "${row.sender.userAccount.username}"/>
				</display:column>
				<spring:message code="folder.editFolder" var="edit"/>
				<display:column>
				
					<a href="message/actor/edit.do?messageId=${row.id}"><jstl:out value="${edit}"/></a>
				</display:column>
				
				<spring:message code="folder.reply" var="reply"/>
				<display:column>
					<a href="message/actor/create.do?userToReply=${row.sender.userAccount.username}"><jstl:out value="${reply}"/></a>
				</display:column>
	</display:table>
	<br/>
	<br/>
	
	<a href="message/actor/create.do"><spring:message code="folder.writeMessage" /></a>
	<br/>
	
	<security:authorize access="hasRole('ADMIN')">
	<a href="message/admin/create.do"><spring:message code="folder.broadCastMessage" /></a>
	<br>
	</security:authorize>
	</div>
</div>

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