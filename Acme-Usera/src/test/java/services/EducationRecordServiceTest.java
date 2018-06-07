package services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Curriculum;
import domain.EducationRecord;


@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class EducationRecordServiceTest extends AbstractTest{
	
	@Autowired
	CurriculumService curriculumService;
	
	@Autowired
	EducationRecordService educationRecordService;
	
	@Autowired
	TeacherService teacherService;
	

	@Test
	public void CreateEditEducationTestDriver() {
	
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				{"teacher2","curriculum2", null },//Crear un record educacional
			 
				//TESTS NEGATIVOS:
				{"student1","curriculum1", IllegalArgumentException.class}, //Intentar crear un record educacional si soy un estudiante
			 
				{"admin","curriculum1", IllegalArgumentException.class} //Intentar crear un record educacional si soy un admin
			
		};
		
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.CreateEditEducationTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
			
	}
	
	
	@SuppressWarnings("deprecation")
	protected void CreateEditEducationTemplate(final String username,final String curriculumId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Curriculum curriculum = this.curriculumService.findOne(this.getEntityId(curriculumId));
			EducationRecord educationRecord = this.educationRecordService.create();
			
			educationRecord.setDiplomaTitle("diploma");
			educationRecord.setStartDate(new Date(2014,01,02));
			educationRecord.setEndDate(new Date(2017,01,02));
			educationRecord.setInstitutionName("Universidad Sevilla");
			educationRecord.setLinkAttachment("https://diploma.com/educationRecord1");
			
			List<EducationRecord> educationRecords = new ArrayList<EducationRecord>();
			educationRecords.add(educationRecord);
			
			curriculum.setEducationRecord(educationRecords);
			
			
			this.educationRecordService.save(educationRecord);
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	


	@Test
	public void DeleteEducationRecordTestDriver() {
		
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				
				{"teacher1", "educationRecord1", null }, //Borrar mi recor educacional
			 
				//TESTS NEGATIVOS:
				{"admin", "educationRecord2", IllegalArgumentException.class}, //Intentar borrar un educationRecord como administrador
			 
				{"student1", "educationRecord1",IllegalArgumentException.class} //Intentar borrar un educationRecord como student
			
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.DeleteEducationRedordTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		this.rollbackTransaction();
	}
	
	
	protected void DeleteEducationRedordTemplate(final String username, final String educationRecordId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			EducationRecord educationRecord = this.educationRecordService.findOne(this.getEntityId(educationRecordId));
			this.educationRecordService.delete(educationRecord);
			this.educationRecordService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}


	
}
