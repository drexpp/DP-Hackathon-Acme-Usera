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
import domain.ProfessionalRecord;


@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest{
	
	@Autowired
	CurriculumService curriculumService;
	
	@Autowired
	ProfessionalRecordService professionalRecordService;
	
	@Autowired
	TeacherService teacherService;
	

	@Test
	public void CreateEditProfessionalTestDriver() {
	
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				{"teacher2","curriculum2", null },//Crear un record profesional
			 
				//TESTS NEGATIVOS:
				{"student1","curriculum1", IllegalArgumentException.class}, //Intentar crear un record profesional si soy un estudiante
			 
				{"admin","curriculum1", IllegalArgumentException.class} //Intentar crear un record profesional si soy un admin
			
		};
		
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.CreateEditProfessionalTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
			
	}
	
	
	@SuppressWarnings("deprecation")
	protected void CreateEditProfessionalTemplate(final String username,final String curriculumId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Curriculum curriculum = this.curriculumService.findOne(this.getEntityId(curriculumId));
			ProfessionalRecord professionalRecord = this.professionalRecordService.create();
			
			professionalRecord.setCompanyName("Mi compañia");
			professionalRecord.setStartDate(new Date(2014,01,02));
			professionalRecord.setEndDate(new Date(2017,01,02));
			professionalRecord.setRole("Técnico");
			professionalRecord.setLinkAttachment("https://diploma.com/professionalRecord1");

			List<ProfessionalRecord> professionalRecords = new ArrayList<ProfessionalRecord>();
			professionalRecords.add(professionalRecord);
			
			curriculum.setProfessionalRecords(professionalRecords);
			
			
			this.professionalRecordService.save(professionalRecord);
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	


	@Test
	public void DeleteProfessionalRecordTestDriver() {
		
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				
				{"teacher1", "professionalRecord1", null }, //Borrar mi record profesional
			 
				//TESTS NEGATIVOS:
				{"admin", "professionalRecord2", IllegalArgumentException.class}, //Intentar borrar un record profesional como administrador
			 
				{"student1", "professionalRecord1",IllegalArgumentException.class} //Intentar borrar un record profesional como student
			
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.DeleteProfessionalRedordTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		this.rollbackTransaction();
	}
	
	
	protected void DeleteProfessionalRedordTemplate(final String username, final String professionalRecordId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			ProfessionalRecord professionalRecord = this.professionalRecordService.findOne(this.getEntityId(professionalRecordId));
			this.professionalRecordService.delete(professionalRecord);
			this.professionalRecordService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}


	
}
