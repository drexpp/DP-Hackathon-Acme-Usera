<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>


<div>
	<a href=""><img id="img-logo" src="images/logo.png" alt="Sample Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">	
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		
		<security:authorize access="permitAll">
			<li class="fNiv"><a class="fNiv"><spring:message code="master.page.courses" /></a>
			
			<ul>
					<li class="arrow"></li>
					<li><a href="course/list.do"><spring:message code="master.page.all.courses" /></a></li>
					
					<security:authorize access="hasAnyRole('TEACHER','STUDENT','SPONSOR')"> 
					<li><a href="course/myCourses.do"><spring:message code="master.page.my.courses" /></a></li>
					</security:authorize>
					

						
				</ul>
			</security:authorize>
		

		
		<security:authorize access="isAuthenticated()">
		
				<li><a class="fNiv" href="https://safe-atoll-72115.herokuapp.com/"><spring:message code="master.page.chat" /></a></li>
	
		</security:authorize>
		
		<security:authorize access="hasAnyRole('STUDENT', 'SPONSOR', 'TEACHER', 'ADMIN')">
			<li><a href="folder/actor/list.do"><spring:message code="master.page.mail" /></a>
			</li>
		</security:authorize>
		
		<security:authorize access="hasAnyRole('STUDENT', 'SPONSOR', 'TEACHER', 'ADMIN')">
			<li><a href="ranking/list.do"><spring:message code="master.page.ranking" /></a>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="dashboard/admin/display.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
					<li><a href="customisation/admin/display.do"><spring:message
								code="master.page.administrator.customisation" /></a></li>
					<li><a href="category/admin/list.do"><spring:message
								code="master.page.administrator.categories" /></a></li>
						
				</ul>
			</li>
		</security:authorize>
		
		
		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"><spring:message	code="master.page.user" /></a>
				<ul>
					<li class="arrow"></li>
						
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('TEACHER')">
			<li><a class="fNiv"><spring:message
						code="master.page.teacher" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="curriculum/teacher/search.do"><spring:message
								code="master.page.teacher.curriculum" /></a></li>
					<li><a href="tutorial/list.do"><spring:message
									code="master.page.my.tutorials" /></a></li>
					<li><a href="examPaper/teacher/list.do"><spring:message
									code="master.page.examPapers" /></a></li>

				</ul></li>
		</security:authorize>
		
		
		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.sponsor" /></a>
				<ul>
					<li class="arrow"></li>
						<li><a href="advertisement/sponsor/create.do"><spring:message
									code="master.page.sponsor.edit" /></a></li>
						<li><a href="advertisement/sponsor/list.do"><spring:message
									code="master.page.sponsor.list" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		
		<security:authorize access="hasRole('STUDENT')">
			<li><a class="fNiv"><spring:message
						code="master.page.student" /></a>
				<ul>
					<li class="arrow"></li>
						<li><a href="question/student/list.do"><spring:message
									code="master.page.question.list" /></a></li>
						<li><a href="answer/student/list.do"><spring:message
									code="master.page.answer.list" /></a></li>
						<li><a href="tutorial/list.do"><spring:message
									code="master.page.my.tutorials" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
		
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('ADMIN')">
					<li><a href="admin/display.do"><spring:message code="master.page.personalProfile" /> </a></li>
						<li><a href="admin/edit.do"><spring:message code="master.page.personalProfile.edit" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('SPONSOR')">
					<li><a href="sponsor/display.do"><spring:message code="master.page.personalProfile" /> </a></li>
						<li><a href="sponsor/edit.do"><spring:message code="master.page.personalProfile.edit" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('TEACHER')">
					<li><a href="teacher/teacher/display.do"><spring:message code="master.page.personalProfile" /> </a></li>
						<li><a href="teacher/edit.do"><spring:message code="master.page.personalProfile.edit" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('STUDENT')">
					<li><a href="student/display.do"><spring:message code="master.page.personalProfile" /> </a></li>
						<li><a href="student/edit.do"><spring:message code="master.page.personalProfile.edit" /></a></li>
					</security:authorize>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

