package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.ExamAnswer;
import domain.ExamPaper;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class ExamAnswerServiceTest  extends AbstractTest{
	
	@Autowired
	ExamAnswerService examAnswerService;
	
	@Autowired
	ExamPaperService examPaperService;
	
	@Test
	public void CreateExamAnswerTestDriver() {
	
		
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				{"student1", "examPaper1", null}, //Comprobar que deja crear una examAnswer a un examPaper si no ha sido finalizado
			
				//TESTS NEGATIVOS:
				{"student1", "examPaper2", IllegalArgumentException.class}, //Intentar crear una examAnswer a un examAnswer que sí haya sido finalizado
			
				{"teacher1", "examPaper1", IllegalArgumentException.class}, //Intentar crear una examAnswer siendo profesor
			
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.CreateExamAnswerTestDriver((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	
	protected void CreateExamAnswerTestDriver(final String username, final String examPaperId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			ExamPaper examPaper = this.examPaperService.findOne(this.getEntityId(examPaperId));
			ExamAnswer examAnswerToCreate = this.examAnswerService.create(this.getEntityId(examPaperId));
			examAnswerToCreate.setText("answer");
			examAnswerToCreate.setNumber(1);
			examAnswerToCreate.setExamPaper(examPaper);
			this.examAnswerService.save(examAnswerToCreate);
			this.examAnswerService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	
	
}
