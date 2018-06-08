package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Curriculum;
import domain.PersonalRecord;


@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class CurriculumServiceTest extends AbstractTest{
	
	@Autowired
	CurriculumService curriculumService;
	
	@Autowired
	PersonalRecordService personalRecordService;
	
	@Autowired
	TeacherService teacherService;
	

	@Test
	public void CreateEditCurriculumTestDriver() {
		//UC19-Crear y editar curriculum
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				{"teacher3", null },//Crear un currículum
			 
				//TESTS NEGATIVOS:
				{"teacher1", IllegalArgumentException.class}, //Intentar crear un curriculum si ya tiene 1 creado
			 
				{"student1", IllegalArgumentException.class} //Intentar crear un curriculum como un rol distinto a teacher
			
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.CreateCurriculumTemplate((String) testingData[i][0],  (Class<?>) testingData[i][1]);
		this.rollbackTransaction();
	}
	
	
	protected void CreateCurriculumTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Curriculum curriculum = this.curriculumService.create();
			PersonalRecord personalRecord = this.personalRecordService.create();
			
			personalRecord.setName("Alfredo");
			personalRecord.setSurname("Palomar");
			personalRecord.setEmail("alfredoPalomar@gmail.com");
			personalRecord.setPhone("666777888");
			personalRecord.setLinkPhoto("https://www.imagen.es");
			personalRecord.setLinkedInProfile("https://linkedin.com/personalRecordNuevo");
			
			curriculum.setPersonalRecord(personalRecord);
			
			this.curriculumService.save(curriculum);
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	

	
	@Test
	public void DeleteCurriculumTestDriver() {
		
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				
				{"teacher1", "curriculum1", null }, //Borrar mi curriculum
			 
				//TESTS NEGATIVOS:
				{"teacher1", "curriculum2", IllegalArgumentException.class}, //Intentar borrar un curriculum que no es mio
			 
				{"student1", "curriculum1",IllegalArgumentException.class} //Intentar borrar un curriculum como student
			
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.DeleteCurriculumTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		this.rollbackTransaction();
	}
	
	
	protected void DeleteCurriculumTemplate(final String username, final String curriculumId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Curriculum curriculum = this.curriculumService.findOne(this.getEntityId(curriculumId));
			this.curriculumService.delete(curriculum);
			this.curriculumService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	
	
}
