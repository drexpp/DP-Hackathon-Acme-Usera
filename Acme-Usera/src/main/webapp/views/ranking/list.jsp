

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<img id="ranking-img" src="images/ranking.png"/>

<jstl:forEach items="${students}" var="student" varStatus="i">
	<table class="displayRanking">
		<tr class="ranking${i.index}">
			<td>
				<jstl:out value="${student.userAccount.username}"/>
			</td>
			<td>
				<jstl:out value="${student.score}"/>
			</td>
		</tr>
	</table>
</jstl:forEach>


<script>
$(document).ready(function(){
	$("h1").css("text-align", "center");
});
</script>