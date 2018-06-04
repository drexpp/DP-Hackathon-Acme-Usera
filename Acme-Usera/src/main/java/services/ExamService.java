package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ExamRepository;
import domain.Admin;
import domain.Course;
import domain.Exam;
import domain.ExamPaper;
import domain.ExamQuestion;
import domain.Teacher;
import forms.ExamForm;

@Service
@Transactional
public class ExamService {


	// Managed Repository
		@Autowired
		private ExamRepository			examRepository;
		
		@Autowired
		private TeacherService				teacherService;
		
		@Autowired
		private ExamQuestionService				examQuestionService;
		
		@Autowired
		private ExamPaperService				examPaperService;
		
		@Autowired
		private AdminService				adminService;
		
		@Autowired
		private Validator	validator;
		
		
		
		public Exam create() {
			Teacher principal;
			Exam exam = new Exam();;
			principal = this.teacherService.findByPrincipal();
			exam.setTeacher(principal);
			Assert.notNull(principal);
			exam.setMark(0);
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
			
			Assert.isTrue(principal.getCoursesJoined().contains(exam.getCourse()));
			Assert.isTrue(exam.getCourse().getIsClosed() == false);
			Assert.isTrue(exam.getCourse().getExam() == null);
			
			
			result = this.examRepository.save(exam);
			
			if(exam.getId() == 0){
				/*
				Course course = result.getCourse();
				course.setExam(result);
				*/
				exam.getCourse().setExam(result);
			
				
				Teacher teacher = result.getTeacher();
				Collection<Exam> toUpdate2 = teacher.getExams();
				Collection<Exam> updated2 = new ArrayList<Exam> (toUpdate2);
				updated2.add(result);
				teacher.setExams(updated2);
			}
			
			
			return result;
		}
	
		
		
		public void deleteByAdmin(final Exam exam) {
			Admin principal;
			Assert.notNull(exam);

			principal = this.adminService.findByPrincipal();

			Assert.notNull(principal);
			
			Course course = exam.getCourse();
			course.setExam(null);
			
			Collection<ExamQuestion> examQuestions = new ArrayList<ExamQuestion>(exam.getExamQuestions());
			Collection<ExamPaper> examPaper = new ArrayList<ExamPaper>(exam.getExamPaper());
			
			for (ExamQuestion eq : examQuestions){
				this.examQuestionService.deleteByAdmin(eq);
			}
			
			for(ExamPaper eP :examPaper){
				this.examPaperService.deleteByAdmin(eP);
			}
			
			this.examRepository.delete(exam);

			
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
		
		public Collection<Exam> selectExamsFromStudent(int studentId){
			Collection<Exam>res = this.examRepository.selectExamsFromStudent(studentId);
			return res;
		}
		
		
		public Exam reconstruct(ExamForm examForm, BindingResult binding) {
			Exam exam = this.create();
			if (examForm.getId() == 0){
				exam = this.create();

			} else {
				exam = this.findOne(examForm.getId());
			}
			exam.setTitle(examForm.getTitle());
			exam.setMark(0);
			exam.setCourse(examForm.getCourse());
			
			 
			validator.validate(examForm, binding);
			
			return exam;
		}


		public ExamForm reconstructForm(Exam exam) {
			ExamForm examForm = new ExamForm();
			examForm.setTitle(exam.getTitle());
			examForm.setId(exam.getId());
			examForm.setVersion(exam.getVersion());
			examForm.setCourse(exam.getCourse());
			return examForm;
		}


		public void flush() {
			this.examRepository.flush();
		}
		
		
}
