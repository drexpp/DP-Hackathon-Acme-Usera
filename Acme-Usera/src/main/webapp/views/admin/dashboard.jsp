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

<security:authorize access="hasRole('ADMIN')">
	
	<h1 id="h1Statistics"><spring:message code="administrator.statistics" /></h1>
	<br/>
	<!-- 1 -->
	<table class="displayStyle">

		<tr>
			<th><spring:message code="administrator.query" /></th>
			<th><spring:message code="administrator.value" /></th>
		
		</tr>
		
		<tr>
			<td><spring:message code="administrator.AverageCoursesPerTeacher" /></td>
			<td>${AverageCoursesPerTeacher }</td>
		</tr>
	</table>
	
	<!-- 2 -->
	<table class="displayStyle">
		
		<tr>
			<th><spring:message code="administrator.query" /></th>
			<th><spring:message code="administrator.value" /></th>
		
		</tr>
		
		<tr>
			<td><spring:message code="administrator.StandardDesviationCoursesPerTeacher" /></td>
			<td>${StandardDesviationCoursesPerTeacher }</td>
		</tr>
	</table>
	
	<!-- 3 -->
	<table class="displayStyle">
		
		<tr>
			<th><spring:message code="administrator.query" /></th>
			<th><spring:message code="administrator.value" /></th>
		
		</tr>
		
		<tr>
			<td><spring:message code="administrator.AverageLessonsPerCourse" /></td>
			<td>${AverageLessonsPerCourse}</td>
		</tr>
	</table>
	
	<!-- 4 -->
	
	<table class="displayStyle">
		
		<tr>
			<th><spring:message code="administrator.query" /></th>
			<th><spring:message code="administrator.value" /></th>
		
		</tr>
		
		<tr>
			<td><spring:message code="administrator.StandardDesviationLessonPerCourse" /></td>
			<td>${StandardDesviationLessonPerCourse }</td>
		</tr>
	</table>
	
	
		<!-- 5 -->
	
	<table class="displayStyle">
		
		<tr>
			<th><spring:message code="administrator.query" /></th>
			<th><spring:message code="administrator.value" /></th>
		
		</tr>
		
		<tr>
			<td><spring:message code="administrator.ratioOfCoursesPerCategory" /></td>
			<td>${ratioOfCoursesPerCategory }</td>
		</tr>
	</table>
	
	
	
	
		<!-- 6 -->
	
	<table class="displayStyle">
		
		<tr>
			<th><spring:message code="administrator.query" /></th>
			<th><spring:message code="administrator.value" /></th>
		
		</tr>
		
		<tr>
			<td><spring:message code="administrator.AverageAnswersPerStudent" /></td>
			<td>${AverageAnswersPerStudent }</td>
		</tr>
	</table>
	
	
	
	
		<!-- 7 -->
	
	<table class="displayStyle">
		
		<tr>
			<th><spring:message code="administrator.query" /></th>
			<th><spring:message code="administrator.value" /></th>
		
		</tr>
		
		<tr>
			<td><spring:message code="administrator.StandardDesviationAnswersPerStudent" /></td>
			<td>${StandardDesviationAnswersPerStudent }</td>
		</tr>
	</table>
	
	
	
	
		<!-- 8 -->
	
	<table class="displayStyle">
		
		<tr>
			<th><spring:message code="administrator.query" /></th>
			<th><spring:message code="administrator.value" /></th>
		
		</tr>
		
		<tr>
			<td><spring:message code="administrator.AverageQuestionsPerCourse" /></td>
			<td>${AverageQuestionsPerCourse }</td>
		</tr>
	</table>
	
	
	
		<!-- 9 -->
	
	<table class="displayStyle">
		
		<tr>
			<th><spring:message code="administrator.query" /></th>
			<th><spring:message code="administrator.value" /></th>
		
		</tr>
		
		<tr>
			<td><spring:message code="administrator.StandardDesviationQuestionsPerCourse" /></td>
			<td>${StandardDesviationQuestionsPerCourse }</td>
		</tr>
	</table>
	
	
	
	
	
		<!-- 10 -->
	
	<table class="displayStyle">
		
		<tr>
			<th><spring:message code="administrator.query" /></th>
			<th><spring:message code="administrator.value" /></th>
		
		</tr>
		
		<tr>
			<td><spring:message code="administrator.RatioLessonsPerCourse" /></td>
			<td>${RatioLessonsPerCourse }</td>
		</tr>
	</table>
	
	
	
	
	
		<!-- 11 -->
	
	<table class="displayStyle">
		
		<tr>
			<th><spring:message code="administrator.query" /></th>
			<th><spring:message code="administrator.value" /></th>
		
		</tr>
		
		<tr>
			<td><spring:message code="administrator.StandardDesviationCoursesClosed" /></td>
			<td>${StandardDesviationCoursesClosed }</td>
		</tr>
	</table>
	
	
	
	
		<!-- 12 -->
	
	<table class="displayStyle">
		
		<tr>
			<th><spring:message code="administrator.query" /></th>
			<th><spring:message code="administrator.value" /></th>
		
		</tr>
		
		<tr>
			<td><spring:message code="administrator.MinScoreStudent" /></td>
			<td>${MinScoreStudent }</td>
		</tr>
	</table>
	
	
	
	
		<!-- 13 -->
	
	<table class="displayStyle">
		
		<tr>
			<th><spring:message code="administrator.query" /></th>
			<th><spring:message code="administrator.value" /></th>
		
		</tr>
		
		<tr>
			<td><spring:message code="administrator.MaxScoreStudent" /></td>
			<td>${MaxScoreStudent }</td>
		</tr>
	</table>
	
	
	
		<!-- 14 -->
	
	<table class="displayStyle">
		
		<tr>
			<th><spring:message code="administrator.query" /></th>
			<th><spring:message code="administrator.value" /></th>
		
		</tr>
		
		<tr>
			<td><spring:message code="administrator.AvgScoreStudent" /></td>
			<td>${AvgScoreStudent }</td>
		</tr>
	</table>
	
	
	
	
		<!-- 15 -->
	
	<table class="displayStyle">
		
		<tr>
			<th><spring:message code="administrator.query" /></th>
			<th><spring:message code="administrator.value" /></th>
		
		</tr>
		
		<tr>
			<td><spring:message code="administrator.StandardDesviationScoreStudent" /></td>
			<td>${StandardDesviationScoreStudent }</td>
		</tr>
	</table>
	
			<!-- 16 -->
	
	<table class="displayStyle">
		
		<tr>
			<th><spring:message code="administrator.query" /></th>
			<th><spring:message code="administrator.value" /></th>
		
		</tr>
		
		<tr>
			<td><spring:message code="administrator.MinTutorialsTeacher" /></td>
			<td>${MinTutorialsTeacher }</td>
		</tr>
	</table>
			<!-- 17 -->
	
	<table class="displayStyle">
		
		<tr>
			<th><spring:message code="administrator.query" /></th>
			<th><spring:message code="administrator.value" /></th>
		
		</tr>
		
		<tr>
			<td><spring:message code="administrator.MaxTutorialsTeacher" /></td>
			<td>${MaxTutorialsTeacher }</td>
		</tr>
	</table>
			<!-- 18 -->
	
	<table class="displayStyle">
		
		<tr>
			<th><spring:message code="administrator.query" /></th>
			<th><spring:message code="administrator.value" /></th>
		
		</tr>
		
		<tr>
			<td><spring:message code="administrator.AvgTutorialsTeacher" /></td>
			<td>${AvgTutorialsTeacher }</td>
		</tr>
	</table>
			<!-- 19 -->
	
	<table class="displayStyle">
		
		<tr>
			<th><spring:message code="administrator.query" /></th>
			<th><spring:message code="administrator.value" /></th>
		
		</tr>
		
		<tr>
			<td><spring:message code="administrator.StandardDesviationTutorialsTeacher" /></td>
			<td>${StandardDesviationTutorialsTeacher }</td>
		</tr>
	</table>
			<!-- 20 -->
	
	<table class="displayStyle">
		
		<tr>
			<th><spring:message code="administrator.query" /></th>
			<th><spring:message code="administrator.value" /></th>
		
		</tr>
		
		<tr>
			<td><spring:message code="administrator.RatioStudentsWithScore0" /></td>
			<td>${RatioStudentsWithScore0 }</td>
		</tr>
	</table>
	
	
	
	
	
			<!-- 21 -->
	
	<table class="displayStyle">
<jstl:forEach varStatus="i" var="course" items="${top3CoursesWithMoreSubscriptions}">	
		<jstl:if test="${i.index == 0 }">
		<tr>

			<th><spring:message code="administrator.query" /></th>
			<th>Top ${i.index + 1}</th>
		
		</tr>
		<tr>
			<td><spring:message code="administrator.top3CoursesWithMoreSubscriptions" /></td>
			<td><a href="course/display.do?courseId=${course.id}"> ${course.title} </a></td>
		</tr>
		
		</jstl:if>
		
		<jstl:if test="${i.index != 0 }">
		<tr>

			<th></th>
			<th>Top ${i.index + 1}</th>
		
		</tr>
		
		<tr>
			<td></td>
			<td><a href="course/display.do?courseId=${course.id}"> ${course.title} </a></td>
		</tr>
		
		</jstl:if>
		
		
</jstl:forEach>	
	</table>
	
		
					<!-- 22 -->
	
	<table class="displayStyle">
<jstl:forEach varStatus="i" var="course" items="${top3CoursesWithMoreQuestions}">	
		<jstl:if test="${i.index == 0 }">
		<tr>

			<th><spring:message code="administrator.query" /></th>
			<th>Top ${i.index + 1}</th>
		
		</tr>
		<tr>
			<td><spring:message code="administrator.top3CoursesWithMoreQuestions" /></td>
			<td><a href="course/display.do?courseId=${course.id}"> ${course.title} </a></td>
		</tr>
		
		</jstl:if>
		
		<jstl:if test="${i.index != 0 }">
		<tr>

			<th></th>
			<th>Top ${i.index + 1}</th>
		
		</tr>
		
		<tr>
			<td></td>
			<td><a href="course/display.do?courseId=${course.id}"> ${course.title} </a></td>
		</tr>
		
		</jstl:if>
		
		
</jstl:forEach>	
	</table>


					<!-- 23 -->
	
	<table class="displayStyle">
<jstl:forEach varStatus="i" var="course" items="${top3TeachersWithMoreAnswers}">	
		<jstl:if test="${i.index == 0 }">
		<tr>

			<th><spring:message code="administrator.query" /></th>
			<th>Top ${i.index + 1}</th>
		
		</tr>
		<tr>
			<td><spring:message code="administrator.top3TeachersWithMoreAnswers" /></td>
			<td><a href="teacher/display.do?teacherId=${course.id}"> ${course.name} </a></td>
		</tr>
		
		</jstl:if>
		
		<jstl:if test="${i.index != 0 }">
		<tr>

			<th></th>
			<th>Top ${i.index + 1}</th>
		
		</tr>
		
		<tr>
			<td></td>
			<td><a href="teacher/display.do?teacherId=${course.id}"> ${course.name} </a></td>
		</tr>
		
		</jstl:if>
		
		
</jstl:forEach>	
	</table>
	
	
						<!-- 23 -->
	
	<table class="displayStyle">
<jstl:forEach varStatus="i" var="course" items="${Top5StudentsWithMoreScore}">	
		<jstl:if test="${i.index == 0 }">
		<tr>

			<th><spring:message code="administrator.query" /></th>
			<th>Top ${i.index + 1}</th>
		
		</tr>
		<tr>
			<td><spring:message code="administrator.Top5StudentsWithMoreScore" /></td>
			<td>${course.name} ${course.surname} </td>
		</tr>
		
		</jstl:if>
		
		<jstl:if test="${i.index != 0 }">
		<tr>

			<th></th>
			<th>Top ${i.index + 1}</th>
		
		</tr>
		
		<tr>
			<td></td>
			<td>${course.name} ${course.surname}</td>
		</tr>
		
		</jstl:if>
		
		
</jstl:forEach>	
	</table>
	
	
	
</security:authorize>