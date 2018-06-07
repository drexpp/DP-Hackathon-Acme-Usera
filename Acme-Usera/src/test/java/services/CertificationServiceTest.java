package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Certification;
import domain.ExamPaper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/junit.xml"})
@Transactional
public class CertificationServiceTest extends AbstractTest {

	@Autowired
	private CertificationService certificationService;
	@Autowired
	private ExamPaperService examPaperService;
	
	
	@Test
	public void driverCertification(){
		Object testingData[][] = {

								//==========================================================================//

								//Tests POSITIVOS 
								//
								//un teacher crea un certification (automaticamente) si el examPaper está aprobado
								{"teacher1","examPaper3", null},
								//Tests NEGATIVOS
								//Un teacher intenta crea un certification de un exam paper suspenso
								{"teacher1","examPaper2", IllegalArgumentException.class},
								//Solo puede crear una certificación un profesor y ningún otro rol
								{"admin","examPaper1", IllegalArgumentException.class},
								
		};
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			templateSubscribe(((String) testingData[i][0]), (String) testingData[i][1],  (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void templateSubscribe(String username, final String examPaperId, Class<?> expected) {
		Class<?> caught;
		caught = null;
		try{
			super.authenticate(username);
			ExamPaper examPaper = this.examPaperService.findOne(super.getEntityId(examPaperId));
			Certification certification = this.certificationService.create();
			certification.setExamPaper(examPaper);		
			certification.setStudent(examPaper.getStudent());	
			this.certificationService.save(certification);
			super.unauthenticate();
		}catch(Throwable oops){
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
		
	}
	
	}