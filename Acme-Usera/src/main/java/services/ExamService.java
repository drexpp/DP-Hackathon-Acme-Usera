package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Course;
import domain.Exam;
import domain.ExamPaper;
import domain.ExamQuestion;
import domain.Teacher;

import repositories.ExamRepository;

@Service
@Transactional
public class ExamService {


	// Managed Repository
		@Autowired
		private ExamRepository			examRepository;
		
		@Autowired
		private TeacherService				teacherService;
		
		public Exam create() {
			Teacher principal;
			Exam exam = new Exam();;
			principal = this.teacherService.findByPrincipal();
			exam.setTeacher(principal);
			Assert.notNull(principal);
			exam.setExamPaper(new ArrayList<ExamPaper>());
			exam.setExamQuestions(new ArrayList<ExamQuestion>());

			return exam;
		}
		
		
		public Collection<Exam> findAll() {
			final Collection<Exam> result = this.examRepository.findAll();	
			return result;
		}
		
		
		public Exam save(final Exam exam) {
			Teacher principal;
			Exam result;
			Assert.notNull(exam);

			principal = this.teacherService.findByPrincipal();

			Assert.notNull(principal);
			
			if (exam.getId() != 0){
			Assert.isTrue(principal.getCoursesJoined().contains(exam.getCourse()));
			}
			
		
			result = this.examRepository.save(exam);
			if(exam.getId() == 0){
			Course course = result.getCourse();
			course.setExam(result);
			
		}
			
			
			return result;
		}
	
		public Exam findOne(final int examId) {
			Exam result = this.examRepository.findOne(examId);
			Assert.notNull(result);

			return result;

		}
		
		public Collection<Exam> selectAllExamOfCoursedJoinedByTeacher(int teacherId){
			Teacher principal = this.teacherService.findByPrincipal();
			Assert.notNull(principal);
			Collection<Exam> res = this.examRepository.selectAllExamOfCoursedJoinedByTeacher(teacherId);
			return res;
		}
		
		
}
