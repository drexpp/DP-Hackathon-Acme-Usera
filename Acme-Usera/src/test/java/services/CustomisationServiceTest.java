package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Customisation;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class CustomisationServiceTest extends AbstractTest{
	
	@Autowired
	CustomisationService customisationService;

	

	
	@Test
	public void editCustomisationTestDriver() {
	//UC18 - Mostrar y editar personalización
		final Object testingData[][] = {
			{
				//TEST POSITIVO: Cambiar la personalización del sistema
				"admin", "customisation1","new banner", "nuevo banner", null 
			}, {
				//TESTS NEGATIVOS:
				"teacher1", "customisation1", "new banner", "nuevo banner" ,IllegalArgumentException.class //Intentar cambiar la personalización sin ser administrador
			}, {
				"student1", "customisation1","new banner", "nuevo banner" ,IllegalArgumentException.class //Intentar cambiar la personalización sin ser administrador
			}
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			templateEditCustomisation((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2],(String) testingData[i][3],(Class<?>) testingData[i][4]);
		this.rollbackTransaction();
	}
	
	
	protected void templateEditCustomisation(final String username, final int customisationId, final String bannerEn, final String bannerEs,final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			Customisation customisation;
			super.authenticate(username);
			customisation = this.customisationService.find();
			
			customisation.setBannerEn(bannerEn);
			customisation.setBannerEs(bannerEs);
			
			this.customisationService.save(customisation);
			
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}	
}
