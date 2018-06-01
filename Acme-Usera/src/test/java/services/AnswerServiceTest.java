package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import domain.Answer;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/junit.xml"})
@Transactional
public class AnswerServiceTest extends AbstractTest{
	
	@Autowired
	private AnswerService answerService;

	
	@Test
	public void ListAnswersAndCreateTestDriver() {
	
		
		
		
		final Object testingData[][] = {
			{//principal/categoryOfTheAnswerToCreate/
				//TEST POSITIVO: un estudiante responde a la question1 (Se puede responder tanto como student como teacher)
				"student1", "question2", null 
			}, {
				//TESTS NEGATIVOS:
				"student3", "question2", IllegalArgumentException.class //Intentar responder a una pregunta cuyo curso no estás subscrito
			}, {
				"student1", "question1",IllegalArgumentException.class //Intentar crear una respuesta a una pregunta ya respondida
			}
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.ListAnswersAndCreateTestDriver((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		this.rollbackTransaction();
	}
	
	
	protected void ListAnswersAndCreateTestDriver(final String username, final String questionId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Answer answerToCreate = this.answerService.create(this.getEntityId(questionId));
			answerToCreate.setText("Test");
			this.answerService.save(answerToCreate);
			this.answerService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	
	@Test
	public void ChangeIsSolutionTestDriver() {
	
		
		
		
		final Object testingData[][] = {
			{//principal/categoryOfTheAnswerToCreate/
				//TEST POSITIVO: Intentar elegir como solución una respuesta en cuyo curso participas
				"teacher2", "answer4", null 
			}, {
				//TESTS NEGATIVOS:
				"teacher1", "answer4", IllegalArgumentException.class //Intentar elegir como solución una respuesta en cuyo curso no participas
			}, {
				"student1", "answer1",IllegalArgumentException.class //Intentar elegir como solución una respuesta como student
			}
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.ChangeIsSolutionTestDriver((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		this.rollbackTransaction();
	}
	
	
	protected void ChangeIsSolutionTestDriver(final String username, final String answerId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			this.answerService.changeIsSolution(this.getEntityId(answerId));
			this.answerService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
}
