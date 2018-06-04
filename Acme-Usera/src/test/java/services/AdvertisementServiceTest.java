package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Advertisement;
import domain.Course;
import domain.CreditCard;


@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class AdvertisementServiceTest extends AbstractTest {
	
	
	@Autowired
	AdvertisementService 		advertisementService;
	
	//Supporting Services for listing principal advertisements and the course to add the advertisement
	@Autowired
	SponsorService				sponsorService;
	
	@Autowired
	CourseService				courseService;
	
	@Test
	public void driverRegisterAdvertisementAndListSponsorAdvertisements(){
		Object testingData[][] = {
				//Caso de test 1: POSITIVO. Comportamiento: Como se describe anteriormente.
				{"sponsor1", "sponsor1","title1", "https://i.imgur.com/mz4KOzY.png", "https://i.imgur.com/mz4KOzY.png", "course1",null},
				//Caso de test 2: NEGATIVO. Comportamiento: Como se describe anteriormente.
				{"teacher1", "sponsor1","title1", "https://i.imgur.com/mz4KOzY.png", "https://i.imgur.com/mz4KOzY.png", "course1",IllegalArgumentException.class},
				//Caso de test 3: NEGATIVO. Comportamiento: Como se describe anteriormente.
				{"sponsor1", "sponsor1","", "https://i.imgur.com/mz4KOzY.png", "https://i.imgur.com/mz4KOzY.png", "course1",IllegalArgumentException.class}
				
				
		};
		for(int i = 0; i < testingData.length ;i++){
			this.startTransaction();
			templateRegisterAdvertisementAndListSponsorAdvertisements(((String) testingData[i][0]),super.getEntityId((String) testingData[i][1]),((String) testingData[i][2]),((String) testingData[i][3]),((String) testingData[i][4]),super.getEntityId((String) testingData[i][5]),((Class<?>) testingData[i][6]));
			this.rollbackTransaction();
		}
	}


	private void templateRegisterAdvertisementAndListSponsorAdvertisements(String username, int sponsorId, String title, String bannerURL, String targetPageURL, int courseId, Class<?> expected) {
		Class<?> caught;
		Collection<Course> courses;
		Advertisement advertisement;
		Course course;
		courses = new ArrayList<Course>();
		
		caught = null;
		
		course = this.courseService.findOne(courseId);
		courses.add(course);
		
		try{
			super.authenticate(username);
			
			advertisement = this.advertisementService.create();
			this.advertisementService.findBySponsorId(sponsorId);
			
			advertisement.setTitle(title);
			advertisement.setBannerURL(bannerURL);
			advertisement.setTargetURL(targetPageURL);
			advertisement.setCourses(courses);
			advertisement.setCreditCard(generateCreditCard());
			
			this.advertisementService.save(advertisement);
			
			unauthenticate();
		}catch(Throwable oops){
			caught = oops.getClass();
		}
		
		checkExceptions(expected, caught);
	}
	
	protected CreditCard generateCreditCard(){
		CreditCard creditCard;
		
		creditCard = new CreditCard();
		creditCard.setHolderName("HolderTest");
		creditCard.setBrandName("Visa");
		creditCard.setNumber("5220277771031876");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(21);
		creditCard.setCVV(424);
		
		return creditCard;
	}
}
