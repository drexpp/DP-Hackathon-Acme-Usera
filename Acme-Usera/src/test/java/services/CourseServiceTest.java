package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Category;
import domain.Course;
import domain.Teacher;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class CourseServiceTest extends AbstractTest{
	
	@Autowired
	CourseService courseService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	TeacherService teacherService;
	

	
	@Test
	public void ListCoursesAndCreateTestDriver() {
	//UC01 - Crear y listar curso
		final Object testingData[][] = {
			{//principal/categoryOfTheCourseToCreate/
				//TEST POSITIVO: LISTAR Y CREAR UN CURSO CON LA CATEGORÍA 1 ASOCIADA
				"teacher1", "category1", null 
			}, {
				//TESTS NEGATIVOS:
				"teacher1", null, ConstraintViolationException.class //Intentar crear un curso sin categoría
			}, {
				"student1", "category1",ConstraintViolationException.class //Intentar crear un curso como student
			}
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.ListCoursesAndCreateTestDriver((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		this.rollbackTransaction();
	}
	
	
	protected void ListCoursesAndCreateTestDriver(final String username, final String categoryId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			this.courseService.findAll();
			Course courseToCreate = this.courseService.create();
			courseToCreate.setTitle("Test");
			courseToCreate.setDescription("Test");
			Category category = null;
			if(categoryId != null){
			category = this.categoryService.findOne(super.getEntityId(categoryId));	
			} 
			courseToCreate.setCategory(category);
			this.courseService.save(courseToCreate);
			this.courseService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	
	@Test
	public void EditCourseTestDriver() {
	
		
		//UC02 - Editar cursos
		
		final Object testingData[][] = {
			{
				//TEST POSITIVO: EDITAR EL CURSO UNO COMO TEACHER 1
				
				"teacher1", "course1", null 
			}, {
				//TESTS NEGATIVOS:
				"student3", "course2", IllegalArgumentException.class //Intentar editar un curso como estudiante
			}, {
				"teacher3", "course1",IllegalArgumentException.class //Intentar editar un curso el cual no es dueño
			}
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.EditCourseTestDriver((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		this.rollbackTransaction();
	}
	
	
	protected void EditCourseTestDriver(final String username, final String courseId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Course course = this.courseService.findOne(this.getEntityId(courseId));
			course.setTitle("Changing test");
			this.courseService.save(course);
			this.courseService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	
	@Test
	public void JoinRemoveAndCloseCourseTestDriver() {
	
		//UC02 Unirte a un curso como profesor y mostrar sus lecciones y UC02 (2) Cerrar un curso
		
		final Object testingData[][] = {
			{
				//TEST POSITIVO: UNIRME A UN CURSO EN EL CUAL NO ESTOY UNIDO COMO TEACHER 3, LUEGO ME LOGUEO COMO TEACHER 1 EL CUAL ES EL PROPIETARIO DEL CURSO 2 Y ELIMINARÉ
				//AL TEACHER 3 DE ESE CURSO, UNA VEZ REALIZADO ESTO CERRARÉ EL CURSO COMO TEACHER 1 (SOLO LOS CREADORES PUEDEN CERRAR EL CURSO)
				
				"teacher3", "course2", "teacher1", null 
			}, {
				//TESTS NEGATIVOS:
				"teacher3", "course3","teacher1", IllegalArgumentException.class //Intentar unirme a un curso que ya está cerrado (El course3 está cerrado
				//Por lo que no debe dejar unirse
				
			}, {
				"teacher1", "course1","teacher1",IllegalArgumentException.class //Intentar unirme a un curso al que ya estoy unido (De hecho el teacher1 es el creador
				//Del course1 por lo que está unido implicitamente
			},
			
			{
				"teacher3", "course2","teacher2",IllegalArgumentException.class //Igual que el caso positivo pero esta vez el usuario que intentará eliminar al teacher3
				//Y posteriormente cerrar el curso será un profesor que NO es el creador del curso por lo que daría error
			}
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.JoinCourseTestDriver((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2],(Class<?>) testingData[i][3]);
		this.rollbackTransaction();
	}
	
	
	protected void JoinCourseTestDriver(final String username, final String courseId, String creatorUsername, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Course course = this.courseService.findOne(this.getEntityId(courseId));
			Teacher userToJoin = this.teacherService.findByPrincipal();
			this.courseService.join(course, userToJoin);
			this.courseService.flush();
			unauthenticate();
			super.authenticate(creatorUsername);
			this.courseService.remove(course, userToJoin);
			this.courseService.CloseCourse(course);
			this.courseService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	
	
	@Test
	public void DeleteCourseTestDriver() {
	
		
		//UC06-Borrar cursos como administrador
		
		final Object testingData[][] = {
			{
				//TEST POSITIVO: BORRAR EL CURSO COMO ADMIN, EL ÚNICO ROL QUE PUEDE BORRARLOS
				
				"admin", "course1", null 
			}, {
				//TESTS NEGATIVOS:
				"student3", "course2", IllegalArgumentException.class //Intentar borrar un curso como estudiante
			}, {
				"teacher3", "course1",IllegalArgumentException.class //Intentar borrar un curso como profesor
			}
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.DeleteCourseTestDriver((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		this.rollbackTransaction();
	}
	
	
	protected void DeleteCourseTestDriver(final String username, final String courseId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Course course = this.courseService.findOne(this.getEntityId(courseId));
			this.courseService.deleteByAdmin(course);
			this.courseService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	
	
}
