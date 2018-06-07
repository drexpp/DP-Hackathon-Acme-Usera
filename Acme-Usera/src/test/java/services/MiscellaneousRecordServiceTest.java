package services;

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
import domain.MiscellaneousRecord;


@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest{
	
	@Autowired
	CurriculumService curriculumService;
	
	@Autowired
	MiscellaneousRecordService miscellaneousRecordService;
	
	@Autowired
	TeacherService teacherService;
	

	@Test
	public void CreateEditMiscellaneousTestDriver() {
	
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				{"teacher2","curriculum2", null },//Crear un record variado
			 
				//TESTS NEGATIVOS:
				{"student1","curriculum1", IllegalArgumentException.class}, //Intentar crear un record variado si soy un estudiante
			 
				{"admin","curriculum1", IllegalArgumentException.class} //Intentar crear un record variado si soy un admin
			
		};
		
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.CreateEditMiscellaneousTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
			
	}
	
	
	protected void CreateEditMiscellaneousTemplate(final String username,final String curriculumId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Curriculum curriculum = this.curriculumService.findOne(this.getEntityId(curriculumId));
			MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.create();
			
			miscellaneousRecord.setTitle("title");
			miscellaneousRecord.setLinkAttachment("https://diploma.com/miscellaneousRecord1");

			List<MiscellaneousRecord> miscellaneousRecords = new ArrayList<MiscellaneousRecord>();
			miscellaneousRecords.add(miscellaneousRecord);
			
			curriculum.setMiscellaneousRecord(miscellaneousRecords);
			
			
			this.miscellaneousRecordService.save(miscellaneousRecord);
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	


	@Test
	public void DeleteMiscellaneousRecordTestDriver() {
		
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				
				{"teacher1", "miscellaneousRecord1", null }, //Borrar mi record profesional
			 
				//TESTS NEGATIVOS:
				{"admin", "miscellaneousRecord2", IllegalArgumentException.class}, //Intentar borrar un record profesional como administrador
			 
				{"student1", "miscellaneousRecord1",IllegalArgumentException.class} //Intentar borrar un record profesional como student
			
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.DeleteMiscellaneousRedordTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		this.rollbackTransaction();
	}
	
	
	protected void DeleteMiscellaneousRedordTemplate(final String username, final String miscellaneousRecordId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.findOne(this.getEntityId(miscellaneousRecordId));
			this.miscellaneousRecordService.delete(miscellaneousRecord);
			this.miscellaneousRecordService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}


	
}
