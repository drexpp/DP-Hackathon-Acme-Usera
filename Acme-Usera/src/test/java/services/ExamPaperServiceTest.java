package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.ExamPaper;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class ExamPaperServiceTest  extends AbstractTest{
	
	
	@Autowired
	ExamPaperService examPaperService;
	
	@Autowired
	ExamService examService;
	
	@Autowired
	CourseService courseService;
	
	@Test
	public void CreateExamPaperTestDriver() {
		
		
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				{"student2", "exam1", null}, //Comprobar que deja crear un examPaper del examen del curso 1 
			
				//TESTS NEGATIVOS:
				{"student3", "exam2", IllegalArgumentException.class}, //Intentar crear un examPaper sobre un curso cerrado
							
				{"teacher1", "exam1", IllegalArgumentException.class}, //Comprobar que no deja crear un examenPaper con otro rol distinto a student.

			
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.CreateExamPaperTestDriver((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}
	
	
	protected void CreateExamPaperTestDriver(final String username, final String examId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			ExamPaper examPaperToCreate = this.examPaperService.create(this.getEntityId(examId));
			this.examPaperService.save(examPaperToCreate);
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	@Test
	public void FinishExamPaperTestDriver() {
		
		
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				{"student1", "examPaper1", null}, //Comprobar que deja finalizar un examPaper del examen del curso 1 
			
				//TESTS NEGATIVOS:
				{"student3", "exam2", IllegalArgumentException.class}, //Intentar finalizar un examPaper sobre un curso cerrado
							
				{"teacher1", "exam1", IllegalArgumentException.class}, //Comprobar que no deja finalizar un examenPaper con otro rol distinto a student.

			
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.FinishExamPaperTestDriver((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}
	
	
	protected void FinishExamPaperTestDriver(final String username, final String examPaperId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			ExamPaper examPaperToFinish = this.examPaperService.findOne(this.getEntityId(examPaperId));
			this.examPaperService.finish(examPaperToFinish);
			this.examPaperService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	
}
