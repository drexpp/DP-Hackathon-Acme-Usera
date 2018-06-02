package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Exam;
import domain.ExamQuestion;

import utilities.AbstractTest;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class ExamQuestionServiceTest  extends AbstractTest{
	
	@Autowired
	ExamQuestionService examQuestionService;
	
	@Autowired
	ExamService examService;
	
	@Test
	public void CreateExamQuestionTestDriver() {
	
		
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				{"teacher1", "exam1", null}, //Comprobar que deja crear una pregunta a un examen de curso 2 (abierto)
			
				//TESTS NEGATIVOS:
				{"teacher1", "exam2", IllegalArgumentException.class}, //Intentar crear una pregunta a un examen de un curso en el que no estás unido.
			
				{"student1", "course2",IllegalArgumentException.class}, //Intentar crear una pregunta sin ser un profesor.
			
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.CreateExamQuestionTestDriver((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}
	
	
	protected void CreateExamQuestionTestDriver(final String username, final String examId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Exam exam = this.examService.findOne(this.getEntityId(examId));
			ExamQuestion examQuestionToCreate = this.examQuestionService.create();
			examQuestionToCreate.setStatement("Test");
			examQuestionToCreate.setMaxScore(100);
			examQuestionToCreate.setAnswer("answer");
			examQuestionToCreate.setNumber(1);
			examQuestionToCreate.setExam(exam);
			this.examQuestionService.save(examQuestionToCreate);
			this.examQuestionService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	

	@Test
	public void EditExamQuestionTestDriver() {
			
		final Object testingData[][] = {
			
				{"teacher1", "examQuestion1", null}, //editar un pregunta de un examen del curso 1 (abierto)
			 
				//TESTS NEGATIVOS:
				{"teacher2", "examQuestion2", IllegalArgumentException.class}, //Intentar editar una pregunta de un examen de un curso cerrado
			 
				{"teacher3", "examQuestion2",IllegalArgumentException.class}, //Intentar editar una pregunta de un examen de un curso en el que no estás unido
			
				{"student1", "examQuestion2",IllegalArgumentException.class} //Intentar editar una pregunta con otro rol distinto de teacher

		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.EditExamQuestionTestDriver((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}
	
	
	protected void EditExamQuestionTestDriver(final String username, final String examQuestionId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			ExamQuestion examQuestionToEdit = this.examQuestionService.findOne(this.getEntityId(examQuestionId));
			examQuestionToEdit.setStatement("new statement");
			this.examQuestionService.save(examQuestionToEdit);
			this.examQuestionService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	
	@Test
	public void DeleteExamQuestionTestDriver() {
		
		final Object testingData[][] = {
			
				//TEST POSITIVO: Eliminar una pregunta de un examen como teacher si pertenezco al curso del examen al que pertenece
				{"teacher1", "examQuestion1", null},
			 
				//TESTS NEGATIVOS:
				{"teacher3", "examQuestion1", IllegalArgumentException.class},  //Intentar borrar una examQuestion de un curso al que no pertenezco
			
				{"student1", "examQuestion1",IllegalArgumentException.class} //Intentar borrar una examQuestion como otro rol (estudiante) que no sea profesor
			
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.DeleteExamQuestionTestDriver((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}
	
	
	protected void DeleteExamQuestionTestDriver(final String username, final String courseId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			ExamQuestion examQuestion = this.examQuestionService.findOne(this.getEntityId(courseId));
			this.examQuestionService.delete(examQuestion);
			this.examQuestionService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	

}
