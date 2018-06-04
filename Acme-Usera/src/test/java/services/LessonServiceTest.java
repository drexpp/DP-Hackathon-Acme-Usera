package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Course;
import domain.Lesson;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class LessonServiceTest  extends AbstractTest{
	
	@Autowired
	LessonService lessonService;
	
	@Autowired
	CourseService courseService;
	
	
	@Test
	public void ListLessonsAndCreateTestDriver() {
	
		
		
		
		final Object testingData[][] = {
			{//principal/categoryOfTheLessonToCreate/
				//TEST POSITIVO: LISTAR Y CREAR UNA LECCIÓN CON EL CURSO 2 ASOCIADO
				"teacher1", "course2", null 
			}, {
				//TESTS NEGATIVOS:
				"teacher1", "course3", IllegalArgumentException.class //Intentar crear una lección en un curso cerrado
			}, {
				"teacher3", "course2",IllegalArgumentException.class //Intentar crear una lección en un curso en el que no estás unido
			}
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.ListLessonsAndCreateTestDriver((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		this.rollbackTransaction();
	}
	
	
	protected void ListLessonsAndCreateTestDriver(final String username, final String courseId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Course course = this.courseService.findOne(this.getEntityId(courseId));
			Lesson lessonToCreate = this.lessonService.create();
			lessonToCreate.setTitle("Test");
			lessonToCreate.setDescription("Test");
			lessonToCreate.setCourse(course);
			lessonToCreate.setBody("Test");
			this.lessonService.save(lessonToCreate);
			this.lessonService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	
	@Test
	public void EditLessonsTestDriver() {
			
		final Object testingData[][] = {
			{//principal/categoryOfTheLessonToCreate/
				//TEST POSITIVO: LISTAR Y CREAR UN CURSO CON LA CATEGORÍA 1 ASOCIADA
				"teacher1", "lesson1", null 
			}, {
				//TESTS NEGATIVOS:
				"teacher2", "lesson2", IllegalArgumentException.class //Intentar editar una lección en un curso cerrado
			}, {
				"teacher3", "lesson3",IllegalArgumentException.class //Intentar editar una lección en un curso en el que no estás unido
			}
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.EditLessonsTestDriver((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		this.rollbackTransaction();
	}
	
	
	protected void EditLessonsTestDriver(final String username, final String lessonId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Lesson lessonToEdit = this.lessonService.findOne(this.getEntityId(lessonId));
			lessonToEdit.setBody("changing");
			this.lessonService.save(lessonToEdit);
			this.lessonService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void ReadLessonTestDriver() {
			
		final Object testingData[][] = {
			{//principal/categoryOfTheLessonToCreate/
				//TEST POSITIVO: Leer una leccion no leída y a cuyo curso estás subscrito
				"student1", "lesson2", null 
			}, {
				//TESTS NEGATIVOS:
				"student1", "lesson1", IllegalArgumentException.class //Leer una lección ya leída
			}, {
				"student3", "lesson3",IllegalArgumentException.class //Leer una lección a cuyo curso no estás subscrito
			}
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.ReadLessonTestDriver((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		this.rollbackTransaction();
	}
	
	
	protected void ReadLessonTestDriver(final String username, final String lessonId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Lesson lessonToEdit = this.lessonService.findOne(this.getEntityId(lessonId));
			this.lessonService.readLesson(lessonToEdit);
			this.lessonService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	
}
