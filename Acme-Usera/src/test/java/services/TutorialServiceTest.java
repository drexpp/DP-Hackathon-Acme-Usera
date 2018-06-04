package services;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Teacher;
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/junit.xml"})
@Transactional
public class TutorialServiceTest extends AbstractTest {
	
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private TutorialService tutorialService;
	
	@Test
	public void createTutorial(){
		Object testingData[][] = {
								//Tests POSITIVOS 
								//
								//Un student se subscribe normalmente
								{"student1","teacher1", null},
								//Tests NEGATIVOS
								//Un student intenta hacer una tutoría sin estar subscrito al curso en el que imparte
								{"student3", "teacher1", IllegalArgumentException.class},
								//Intentar hacer una tutoría como profesor
								{"teacher1", "course2",  IllegalArgumentException.class},
								
		};
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			createTutorial(((String) testingData[i][0]), this.getEntityId((String) testingData[i][1]),  (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void createTutorial(String username, int teacherId, Class<?> expected) {
		Class<?> caught;
		caught = null;
		try{
			super.authenticate(username);
			
			Teacher teacher = this.teacherService.findOne(teacherId);
			Tutorial tutorial = this.tutorialService.create();
			tutorial.setTeacher(teacher);
			@SuppressWarnings("deprecation")
			Date startTime = new Date(2022, 12, 3);
			tutorial.setStartTime(startTime);
			this.tutorialService.save(tutorial);
			super.unauthenticate();
		}catch(Throwable oops){
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
		
	}
	
	
	
	@Test
	public void denyTutorial(){
		Object testingData[][] = {

								//Tests POSITIVOS 
								//
								//Un teacher elimina una tutoría
								{"teacher1","tutorial1", null},
								//Tests NEGATIVOS
								//Un teacher elimina una tutoría que no es suya
								{"teacher2", "tutorial1", IllegalArgumentException.class},
								//Un student elimina una tutoría
								{"student1", "tutorial2",  IllegalArgumentException.class},
								
		};
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			denyTutorial(((String) testingData[i][0]), this.getEntityId((String) testingData[i][1]),  (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void denyTutorial(String username, int tutorialId, Class<?> expected) {
		Class<?> caught;
		caught = null;
		try{
			super.authenticate(username);
			
			Tutorial tutorial = this.tutorialService.findOne(tutorialId);
			this.tutorialService.delete(tutorial);
			super.unauthenticate();
		}catch(Throwable oops){
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
		
	}
	
	
	@Test
	public void aceptTutorial(){
		Object testingData[][] = {
								//Tests POSITIVOS 
								//
								//Un teacher elimina una tutoría
								{"teacher1","tutorial1", null},
								//Tests NEGATIVOS
								//Un teacher elimina una tutoría que no es suya
								{"teacher2", "tutorial1", IllegalArgumentException.class},
								//Un student elimina una tutoría
								{"student1", "tutorial2",  IllegalArgumentException.class},
								
		};
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			aceptTutorial(((String) testingData[i][0]), this.getEntityId((String) testingData[i][1]),  (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void aceptTutorial(String username, int tutorialId, Class<?> expected) {
		Class<?> caught;
		caught = null;
		try{
			super.authenticate(username);
			
			Tutorial tutorial = this.tutorialService.findOne(tutorialId);
			this.tutorialService.saveTutorialForStudent(tutorial);
			super.unauthenticate();
		}catch(Throwable oops){
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
		
	}
	

}
