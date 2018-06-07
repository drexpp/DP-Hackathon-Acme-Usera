package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Course;
import domain.Forum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/junit.xml"})
@Transactional
public class ForumServiceTest extends AbstractTest{
	
	@Autowired
	private ForumService forumService;
	
	@Autowired
	private CourseService courseService;
	
	
	@Test
	public void ListQuestionsAndCreateTestDriver() {
	
		
		
		
		final Object testingData[][] = {
			
				//TEST POSITIVO: 
				{"teacher1", "course1", null },
			 
				//TESTS NEGATIVOS:
				{"teacher2", "course1", IllegalArgumentException.class}, //Intentar crear un foro si no eres el creador (se crea automáticamente cuando se crea el curso)
			 
				{"admin", "course1", IllegalArgumentException.class } //Intentar crear un foro si no eres profesor
			
		};
		
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.ListForumAndCreateTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}
	
	
	protected void ListForumAndCreateTemplate(final String username, final String courseId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Forum forum = this.forumService.create();
			Course course = this.courseService.findOne(this.getEntityId(courseId));
			forum.setCourse(course);
			this.forumService.save(forum);
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}


}
