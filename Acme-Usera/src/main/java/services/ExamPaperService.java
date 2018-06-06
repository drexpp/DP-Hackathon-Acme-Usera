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

import repositories.ExamPaperRepository;
import domain.Admin;
import domain.Exam;
import domain.ExamAnswer;
import domain.ExamPaper;
import domain.Student;
import forms.ExamPaperForm;

@Service
@Transactional
public class ExamPaperService {

	// Managed Repository
				@Autowired
				private ExamPaperRepository			examPaperRepository;
				
				@Autowired
				private StudentService				studentService;
				
				@Autowired
				private AdminService				adminService;
				
				@Autowired
				private ExamAnswerService			examAnswerService;
				
				@Autowired
				private ExamService					examService;
				
				@Autowired
				private CertificationService		certificationService;
				
				@Autowired
				private Validator	validator;
				

	
	
				
	public ExamPaper create(final int examId) {
		Student principal;
		Exam exam;
		ExamPaper examPaper = new ExamPaper();
		
		principal = this.studentService.findByPrincipal();
		exam = this.examService.findOne(examId);
		Assert.notNull(principal);
		Assert.isTrue(this.findExamPaperFromCourseAndStudent(principal.getId(), exam.getCourse().getId()) == null);
		examPaper.setMoment(new Date(System.currentTimeMillis()-1));
		examPaper.setCertification(null);
		examPaper.setExamAnswer(new ArrayList<ExamAnswer>());
		examPaper.setExam(exam);
		examPaper.setIsFinished(false);
		examPaper.setMark(0);
		return examPaper;
	}
/*	
	public ExamPaperForm createForm() {
		Student principal;
		ExamPaperForm examPaperForm;

		principal = this.studentService.findByPrincipal();
		Assert.notNull(principal);
		examPaperForm = new ExamPaperForm();

		return examPaperForm;
	}
*/	
	public Collection<ExamPaper> findAll() {
		final Collection<ExamPaper> result = this.examPaperRepository.findAll();	
		return result;
	}
	
	
	public ExamPaper save(final ExamPaper examPaper) {
		Student principal;
		ExamPaper result;
		Assert.notNull(examPaper);
		
	//	Assert.isTrue(principal.getLessons().containAll(examPaper.getExam().getCourse().getLessons()));
		
		principal = this.studentService.findByPrincipal();

		Assert.notNull(principal);
		Assert.isTrue(examPaper.getExam().getCourse().getIsClosed() == false);
		
		result = this.examPaperRepository.save(examPaper);
	
		if(examPaper.getId() == 0){
			Exam exam = result.getExam();
			Collection<ExamPaper> toUpdate = exam.getExamPaper();
			Collection<ExamPaper> updated = new ArrayList<ExamPaper>(toUpdate);
			toUpdate.add(result);
			exam.setExamPaper(updated);
			
			result.setStudent(principal);
			
			result.setCertification(null);
		
		}	
		
		return result;
	}
	public ExamPaper findOne(final int examId) {
		ExamPaper result = this.examPaperRepository.findOne(examId);
		Assert.notNull(result);

		return result;

	}

	public void deleteByAdmin(final ExamPaper examPaper) {
		Admin principal;
		Assert.notNull(examPaper);
		
	//	Assert.isTrue(principal.getLessons().containAll(examPaper.getExam().getCourse().getLessons()));
		
		principal = this.adminService.findByPrincipal();

		Assert.notNull(principal);
		
		Exam examen = examPaper.getExam();
		Collection<ExamPaper> examPapers = new ArrayList<ExamPaper>(examen.getExamPaper());
		examPapers.remove(examPaper);
		examen.setExamPaper(examPapers);
		
		Collection<ExamAnswer> examAnswers = new ArrayList<ExamAnswer>(examPaper.getExamAnswer());
		for(ExamAnswer eA : examAnswers){
			this.examAnswerService.deleteByAdmin(eA);
		}
		if (examPaper.getCertification() != null) {

			this.certificationService.deleteByAdmin(examPaper
					.getCertification());

		}
		this.examPaperRepository.delete(examPaper);

	}	
	
	public ExamPaper findExamPaperFromCourseAndStudent(Integer studentId, Integer courseId){
		ExamPaper result;
		result=this.examPaperRepository.findExamPaperFromCourseAndStudent(studentId, courseId);
		return result;
	}
	
	public void finish(ExamPaper examPaper){
		Student principal;
		principal = this.studentService.findByPrincipal();
		Assert.notNull(principal);
		
		if(examPaper.getExamAnswer().size() == examPaper.getExam().getExamQuestions().size()){
			examPaper.setIsFinished(true);
			this.save(examPaper);
		}
	}
	
	public void flush(){
		this.examPaperRepository.flush();
	}
	
	public ExamPaper reconstruct(ExamPaperForm examPaperForm, BindingResult binding) {
		final Exam exam = this.examService.findOne(examPaperForm.getExam().getId());
		final ExamPaper examPaper = this.create(exam.getId());
		Student principal;
		Date moment;
		
		principal = this.studentService.findByPrincipal();
		moment = new Date(System.currentTimeMillis() - 1);	
		
		examPaper.setId(examPaperForm.getId());
		examPaper.setVersion(examPaperForm.getVersion());
		examPaper.setMoment(moment);
		examPaper.setMark(examPaperForm.getMark());
		examPaper.setIsFinished(false);
		examPaper.setExam(examPaperForm.getExam());
		examPaper.setStudent(principal);
		examPaper.setExamAnswer(new ArrayList<ExamAnswer>());
		this.validator.validate(examPaper, binding);
		
		return examPaper;
	}
/*
	public ExamPaperForm reconstructForm(final ExamPaper examPaper) {
		ExamPaperForm result;
		
		result = this.createForm();
		result.setId(examPaper.getId());
		result.setVersion(examPaper.getVersion());
		result.setMoment(examPaper.getMoment());
		result.setMark(examPaper.getMark());
		result.setIsFinished(examPaper.getIsFinished());
		result.setExam(examPaper.getExam());
		return result;
	}
*/				
}
