
package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Admin;
import domain.Course;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	@Query("select a from Admin a where a.userAccount.id = ?1")
	Admin findByUserAccountId(int userAccountId);
	
	//11.3 The average and the standard deviation of courses created per teacher.
		@Query("select avg(t.coursesCreated.size) from Teacher t")
		Double AverageCoursesPerTeacher();
		@Query("select stddev(t.coursesCreated.size) from Teacher t")
		Double StandardDesviationCoursesPerTeacher();
		
	//11.3 The average and the standard deviation of lessons per teacher.
				@Query("select avg(c.lessons.size) from Course c")
				Double AverageLessonsPerCourse();
				@Query("select stddev(c.lessons.size) from Course c")
				Double StandardDesviationLessonPerCourse();
		
		
	// 11. Top 3 courses with more students subscripted
	@Query("select c from Course c order by c.subscriptions.size desc")
	Collection<Course> top3CoursesWithMoreSubscriptions();
	
	// 11. Ratio courses per category
	@Query("select 1.0*count(c.courses.size)/(select count(c) from Course c) from Category c")
	Double ratioOfCoursesPerCategory();
	
	
	//5. The average and the standard deviation of answers created per student.
		@Query("select avg(s.answers.size) from Student s")
		Double AverageAnswersPerStudent();
		@Query("select stddev(s.answers.size) from Student s")
		Double StandardDesviationAnswersPerStudent();
		
	// 5. The average and the standard deviation of questions created per Course.
	@Query("select avg(c.forum.questions.size) from Course c")
	Double AverageQuestionsPerCourse();
	@Query("select stddev(c.forum.questions.size) from Course c")
	Double StandardDesviationQuestionsPerCourse();
	
	// 11. Top 3 courses with more students subscripted
	@Query("select distinct c from Course c join c.forum.questions q order by q.size desc")
	Collection<Course> top3CoursesWithMoreQuestions();
	
	@Query("select distinct t from Teacher t order by t.answers.size desc")
	Collection<Course> top3TeachersWithMoreAnswers();
	
	//11.3 The average and the standard deviation of courses no-closed.
	@Query("select 1.0*count(c) / (select count(c1) from Course c1) from Course c where c.isClosed = true")
	Double RatioLessonsPerCourse();
	@Query("select stddev(c) from Course c where c.isClosed = true")
	Double StandardDesviationCoursesClosed();
	
	//5. Min score from student
			@Query("select min(s.score) from Student s")
			Double MinScoreStudent();
			
	//5. Max score from student
			@Query("select max(s.score) from Student s")
			Double MaxScoreStudent();
			
	//5. avg score from student
			@Query("select avg(s.score) from Student s")
			Double AvgScoreStudent();
			
	//5. StandardDesviation score from student
			@Query("select stddev(s.score) from Student s")
			Double StandardDesviationScoreStudent();
			

//5. Min tutorials from student
@Query("select min(s.tutorials.size) from Teacher s")
Double MinTutorialsTeacher();

//5. Max tutorials from student
@Query("select max(s.tutorials.size) from Teacher s")
Double MaxTutorialsTeacher();

//5. avg tutorials from student
@Query("select avg(s.tutorials.size) from Teacher s")
Double AvgTutorialsTeacher();

//5. StandardDesviation tutorials from student
@Query("select stddev(s.tutorials.size) from Teacher s")
Double StandardDesviationTutorialsTeacher();

//5.El ratio de alumnos con puntuación seguimiento en rango 1, rango 2, rango 3 y rango 4.
//TODO

//Ratio of Students with score = 0

@Query("select 1.0*count(s) / (select count(s1) from Student s1) from Student s where s.score = 0")
Double RatioStudentsWithScore0();

@Query("select s from Student s order by s.score desc")
Collection<Course> Top5StudentsWithMoreScore();

}
