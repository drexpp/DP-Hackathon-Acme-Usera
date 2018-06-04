package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Course;
import domain.Exam;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class ExamServiceTest  extends AbstractTest{
	
	@Autowired
	ExamService examService;
	
	@Autowired
	CourseService courseService;
	
	@Test
	public void CreateExamTestDriver() {
	
		
		
		
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				{"teacher1", "course2", null}, //Comprobar que deja crear el examen del curso 2 (ya que no tiene ninguno creado)
			
				//TESTS NEGATIVOS:
				{"teacher2", "course2", IllegalArgumentException.class}, //Intentar crear un examen en un curso en el que no estás unido.
			
				{"student1", "course2",IllegalArgumentException.class}, //Intentar crear un examen si no eres profesor
				
				{"teacher1", "course1", IllegalArgumentException.class}, //Comprobar que no deja crear un examen sobre un curso que ya tiene un examen creado.

			
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.CreateExamTestDriver((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}
	
	
	protected void CreateExamTestDriver(final String username, final String courseId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Course course = this.courseService.findOne(this.getEntityId(courseId));
			Exam examToCreate = this.examService.create();
			examToCreate.setTitle("Test");
			examToCreate.setCourse(course);
			this.examService.save(examToCreate);
			this.examService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
}
