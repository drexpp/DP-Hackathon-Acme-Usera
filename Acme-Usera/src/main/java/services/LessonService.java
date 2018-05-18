package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Course;
import domain.Lesson;
import domain.Teacher;

import repositories.LessonRepository;

@Service
@Transactional
public class LessonService {

	// Managed Repository
		@Autowired
		private LessonRepository			lessonRepository;
		
		@Autowired
		private TeacherService				teacherService;


		
		
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
		
	/*	public void updateWatched(){
			Student student = this.studentService.findByPrincipal();
			
			Collection<Lesson> toUpdate2 = student.get();
			Collection<Lesson> updated2 = new ArrayList<Lesson> (toUpdate2);
			updated2.add(result);
			teacher.setLessons(updated2);
			
			
		}*/
	
}
