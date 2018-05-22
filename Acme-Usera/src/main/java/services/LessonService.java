package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Course;
import domain.Lesson;
import domain.Student;
import domain.Teacher;
import forms.LessonForm;

import repositories.LessonRepository;

@Service
@Transactional
public class LessonService {

	// Managed Repository
		@Autowired
		private LessonRepository			lessonRepository;
		
		@Autowired
		private StudentService				studentService;
		
		@Autowired
		private TeacherService				teacherService;
		
		@Autowired
		private CourseService				courseService;
		
		@Autowired
		private Validator	validator;


		
		
		public Lesson create() {
			Teacher principal;
			Lesson lesson = new Lesson();;
			principal = this.teacherService.findByPrincipal();
			lesson.setTeacher(principal);
			Assert.notNull(principal);
			lesson.setCreationDate(new Date(System.currentTimeMillis()));

			return lesson;
		}


		public Collection<Lesson> findAll() {
			final Collection<Lesson> result = this.lessonRepository.findAll();
			
			
			return result;
		}

		// Other business methods
		public void delete(final Lesson lesson) {
			Teacher principal;
			Assert.notNull(lesson);
			Assert.isTrue(lesson.getId() != 0);

			principal = this.teacherService.findByPrincipal();
			Assert.notNull(principal);

			Assert.isTrue(principal.getCoursesCreated().contains(lesson.getCourse()));
			Assert.isTrue(lesson.getCourse().getIsClosed() == false);
			
			Course course = lesson.getCourse();
			Collection<Lesson> toUpdate1 = course.getLessons();
			Collection<Lesson> updated1 = new ArrayList<Lesson> (toUpdate1);
			updated1.remove(lesson);
			course.setLessons(updated1);
			
			Teacher teacher = lesson.getTeacher();
			Collection<Lesson> toUpdate2 = teacher.getLessons();
			Collection<Lesson> updated2 = new ArrayList<Lesson> (toUpdate2);
			updated2.remove(lesson);
			teacher.setLessons(updated2);
			
			
			
			

			this.lessonRepository.delete(lesson);
		}

		public Lesson save(final Lesson lessonToSave) {
			Teacher principal;
			Lesson result;
			Assert.notNull(lessonToSave);

			principal = this.teacherService.findByPrincipal();

			Assert.notNull(principal);
			if(lessonToSave.getId()==0){
				Assert.isTrue(lessonToSave.getTeacher().equals(principal));
			}
			
			
			if (lessonToSave.getId() != 0){
			Assert.isTrue(principal.getCoursesJoined().contains(lessonToSave.getCourse()));
			}
			
		
			result = this.lessonRepository.save(lessonToSave);
		
			Course course = result.getCourse();
			Collection<Lesson> toUpdate1 = course.getLessons();
			Collection<Lesson> updated1 = new ArrayList<Lesson> (toUpdate1);
			updated1.add(result);
			course.setLessons(updated1);
			
			Teacher teacher = result.getTeacher();
			Collection<Lesson> toUpdate2 = teacher.getLessons();
			Collection<Lesson> updated2 = new ArrayList<Lesson> (toUpdate2);
			updated2.add(result);
			teacher.setLessons(updated2);

			
			
			return result;
		}
	
		public Lesson findOne(final int lessonId) {
			Lesson result = this.lessonRepository.findOne(lessonId);
			Assert.notNull(result);

			return result;

		}


		public Lesson reconstruct(LessonForm lessonForm, BindingResult binding) {
			Lesson lesson = this.create();
			if (lessonForm.getId() == 0){
				lesson = this.create();

			} else {
				lesson = this.findOne(lessonForm.getId());
			}
			lesson.setTitle(lessonForm.getTitle());
			lesson.setDescription(lessonForm.getDescription());
			lesson.setBody(lessonForm.getBody());
			lesson.setPhotoURL(lessonForm.getPhotoURL());
			lesson.setCourse(lessonForm.getCourse());
			lesson.setVideoURL(lessonForm.getVideoURL());
			 
			validator.validate(lessonForm, binding);
			
			return lesson;
		}


		public LessonForm reconstructForm(Lesson lesson) {
			LessonForm lessonForm = new LessonForm();
			lessonForm.setTitle(lesson.getTitle());
			lessonForm.setDescription(lesson.getDescription());
			lessonForm.setBody(lesson.getBody());
			lessonForm.setId(lesson.getId());
			lessonForm.setPhotoURL(lesson.getPhotoURL());
			lessonForm.setVideoURL(lesson.getVideoURL());
			lessonForm.setCourse(lesson.getCourse());
			return lessonForm;
		}
		
		public void readLesson(Lesson lesson){
			Student student = this.studentService.findByPrincipal();
			Assert.isTrue(!student.getLessons().contains(lesson));
			Collection<Course> subscribed = this.courseService.selectCoursesSubscriptedByUser(student.getId()); 
			Assert.isTrue(subscribed.contains(lesson.getCourse()));
			
			Collection<Lesson> toUpdate2 = student.getLessons();
			Collection<Lesson> updated2 = new ArrayList<Lesson> (toUpdate2);
			updated2.add(lesson);
			student.setLessons(updated2);
		}
			
			
		
	
}
