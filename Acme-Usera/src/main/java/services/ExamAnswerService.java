package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ExamAnswerRepository;
import domain.Actor;
import domain.Admin;
import domain.Certification;
import domain.ExamAnswer;
import domain.ExamPaper;
import domain.Student;
import domain.Teacher;
import forms.ExamAnswerForm;

@Service
@Transactional
public class ExamAnswerService {

	// Managed Repository
			@Autowired
			private ExamAnswerRepository			examAnswerRepository;

			@Autowired
			private StudentService				studentService;
			
			@Autowired
			private TeacherService				teacherService;
			
			@Autowired
			private ActorService				actorService;
			
			@Autowired
			private ExamPaperService			examPaperService;
			
			@Autowired
			private AdminService				adminService;
			
			@Autowired
			private CertificationService		certificationService;
			
			@Autowired
			private Validator					validator;
	
			
	public ExamAnswer create(final int examPaperId) {
		Student principal;
		ExamPaper examPaper;
		ExamAnswer examAnswer = new ExamAnswer();
		
		examPaper = this.examPaperService.findOne(examPaperId);
		principal = this.studentService.findByPrincipal();
		examAnswer.setExamPaper(examPaper);
		examAnswer.setMark(0);
		examAnswer.setNumber(0);
		Assert.notNull(principal);
		return examAnswer;
	}
	
	public ExamAnswerForm createForm() {
		Student principal;
		ExamAnswerForm examAnswerForm;

		principal = this.studentService.findByPrincipal();
		Assert.notNull(principal);
		examAnswerForm = new ExamAnswerForm();

		return examAnswerForm;
	}
	
	public Collection<ExamAnswer> findAll() {
		final Collection<ExamAnswer> result = this.examAnswerRepository.findAll();	
		return result;
	}
	
	
	public ExamAnswer save(final ExamAnswer examAnswer) {
		Actor principal;
		ExamAnswer result;
		Integer puntuacion = 0;
		Integer cantidad;
		Integer total = 0;
		
		Assert.notNull(examAnswer);

		principal = this.actorService.findByPrincipal();

		Assert.notNull(principal);
		Assert.isTrue(principal instanceof Student || principal instanceof Teacher);
		
		ExamPaper examen = examAnswer.getExamPaper();
		
		if(examAnswer.getId()==0){
			Integer number = 0;
			number = examen.getExamAnswer().size()+1;
			examAnswer.setNumber(number);
		}
	
		result = this.examAnswerRepository.save(examAnswer);
	
		ExamPaper examPaper = result.getExamPaper();

		cantidad = examPaper.getExamAnswer().size();
		
		if(examAnswer.getId() == 0){
			puntuacion = 0;
			examPaper.setMark(puntuacion);
			
			Collection<ExamAnswer> toUpdate = examPaper.getExamAnswer();
			Collection<ExamAnswer> updated = new ArrayList<ExamAnswer>(toUpdate);
			toUpdate.add(result);
			examPaper.setExamAnswer(updated);
			
			
		}	
	
		
	
		for(ExamAnswer examanswer: examPaper.getExamAnswer()){
			puntuacion = puntuacion + examanswer.getMark();
		}
		
		
		if(cantidad != 0){
			total = puntuacion/cantidad;
		}
		if(principal instanceof Teacher){
				examPaper.setMark(total);
		}
		
		if(total >= 50){
			if(examPaper.getCertification() == null){
				Certification newCertification;
				
				newCertification = this.certificationService.create();
				newCertification.setExamPaper(examPaper);
				newCertification.setStudent(examPaper.getStudent());

				this.certificationService.save(newCertification);
			}
		}
		
		return result;
	}
	
	
	public ExamAnswer findOne(final int examId) {
		ExamAnswer result = this.examAnswerRepository.findOne(examId);
		Assert.notNull(result);

		return result;

	}

	public void deleteByAdmin(final ExamAnswer examAnswer) {
		Admin principal;

		Assert.notNull(examAnswer);

		principal = this.adminService.findByPrincipal();

		Assert.notNull(principal);
		
		ExamPaper examen = examAnswer.getExamPaper();
		Collection<ExamAnswer> examAnswers = new ArrayList<ExamAnswer>(examen.getExamAnswer());
		examAnswers.remove(examAnswer);
		examen.setExamAnswer(examAnswers);
		
		this.examAnswerRepository.delete(examAnswer);
		
	}
	
	public void delete(final ExamAnswer examAnswer) {
		Teacher principal;

		Assert.notNull(examAnswer);

		principal = this.teacherService.findByPrincipal();

		Assert.notNull(principal);
		
		ExamPaper examen = examAnswer.getExamPaper();
		Collection<ExamAnswer> examAnswers = new ArrayList<ExamAnswer>(examen.getExamAnswer());
		examAnswers.remove(examAnswer);
		examen.setExamAnswer(examAnswers);
		
		this.examAnswerRepository.delete(examAnswer);
		
	}
	
	public ExamAnswer findExamAnswerByNumbers(int number, int examPaperId){
		ExamAnswer result;
		result = this.examAnswerRepository.findExamAnswerByNumbers(number, examPaperId);
		return result;
	}
	
	public ExamAnswer reconstruct(ExamAnswerForm examAnswerForm, BindingResult binding) {
		final ExamPaper examPaper = this.examPaperService.findOne(examAnswerForm.getExamPaper().getId());
		final ExamAnswer examAnswer = this.create(examPaper.getId());
		
		examAnswer.setId(examAnswerForm.getId());
		examAnswer.setVersion(examAnswerForm.getVersion());
		examAnswer.setMark(0);
		examAnswer.setText(examAnswerForm.getText());
		examAnswer.setNumber(examAnswerForm.getNumber());
		examAnswer.setExamPaper(examPaper);
		this.validator.validate(examPaper, binding);
		
		return examAnswer;
	}

	public ExamAnswerForm reconstructForm(final ExamAnswer examAnswer) {
		ExamAnswerForm result;
		
		result = this.createForm();
		result.setId(examAnswer.getId());
		result.setVersion(examAnswer.getVersion());
		result.setText(examAnswer.getText());
		result.setNumber(examAnswer.getNumber());
		result.setExamPaper(examAnswer.getExamPaper());

		return result;
	}

	public void flush() {
		this.examAnswerRepository.flush();
	}
	

}