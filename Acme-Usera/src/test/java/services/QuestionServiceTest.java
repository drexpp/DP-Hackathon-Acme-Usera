package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Question;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/junit.xml"})
@Transactional
public class QuestionServiceTest extends AbstractTest{
	
	@Autowired
	private QuestionService questionService;
	@Test
	public void ListQuestionsAndCreateTestDriver() {
	
		//UC24 - Escribir una pregunta en el foro de un curso
		final Object testingData[][] = {
			{//principal/categoryOfTheQuestionToCreate/
				//TEST POSITIVO: LISTAR Y CREAR UNA PREGUNTA EN EL FORO1 EN EL QUE EL ESTUDIANTE 1 TIENE ACCESO
				"student1", "forum1", null 
			}, {
				//TESTS NEGATIVOS:
				"student1", "forum3", IllegalArgumentException.class //Intentar crear una lección en un curso cerrado
			}, {
				"teacher3", "course2",IllegalArgumentException.class //Intentar crear una pregunta como profesor
			}
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.ListQuestionsAndCreateTestDriver((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		this.rollbackTransaction();
	}
	
	
	protected void ListQuestionsAndCreateTestDriver(final String username, final String forumId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Question questionToCreate = this.questionService.create(this.getEntityId(forumId));
			questionToCreate.setTitle("Test");
			questionToCreate.setQuestion("Test");
			this.questionService.save(questionToCreate);
			this.questionService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	@Test
	public void DeleteQuestionTestDriver() {
	
		
		//UC027-Eliminar una pregunta
		
		final Object testingData[][] = {
			{//principal/categoryOfTheQuestionToCreate/
				//TEST POSITIVO: Borrar una pregunta de la que soy dueño y cuyo curso no está cerrado
				"student1", "question1", null 
			}, {
				//TESTS NEGATIVOS:
				"student2", "question1", IllegalArgumentException.class //Intentar borrar una question de la que no soy dueño
			}, {
				"student2", "question2",IllegalArgumentException.class //Intentar borrar una pregunta de la que sí soy dueño pero cuyo curso está cerrado
			}
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.DeleteQuestionTestDriver((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		this.rollbackTransaction();
	}
	
	
	protected void DeleteQuestionTestDriver(final String username, final String questionId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Question questionToCreate = this.questionService.findOne(this.getEntityId(questionId));
			this.questionService.delete(questionToCreate);
			this.questionService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
