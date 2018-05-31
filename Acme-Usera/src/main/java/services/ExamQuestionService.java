package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ExamQuestionRepository;
import domain.Admin;
import domain.Exam;
import domain.ExamQuestion;
import domain.Teacher;

@Service
@Transactional
public class ExamQuestionService {

	// Managed Repository
			@Autowired
			private ExamQuestionRepository			examQuestionRepository;
			
			@Autowired
			private TeacherService				teacherService;
			
			@Autowired
			private AdminService				adminService;
	
			
	public ExamQuestion create() {
		Teacher principal;
		ExamQuestion examQuestion = new ExamQuestion();
		principal = this.teacherService.findByPrincipal();
		Assert.notNull(principal);
		examQuestion.setNumber(0);
		return examQuestion;
	}
	
	public Collection<ExamQuestion> findAll() {
		final Collection<ExamQuestion> result = this.examQuestionRepository.findAll();	
		return result;
	}
	
	
	public ExamQuestion save(final ExamQuestion examQuestion) {
		Teacher principal;
		ExamQuestion result;
		Integer number = 0;

		Assert.notNull(examQuestion);

		principal = this.teacherService.findByPrincipal();

		Assert.notNull(principal);
		
		if (examQuestion.getId() != 0){
		Assert.isTrue(principal.getCoursesJoined().contains(examQuestion.getExam().getCourse()));
		}
		
		Exam examen = examQuestion.getExam();
		
		number = examen.getExamQuestions().size()+1;
		examQuestion.setNumber(number);
		
		result = this.examQuestionRepository.save(examQuestion);
	
		if(examQuestion.getId() == 0){
			Exam exam = result.getExam();
			Integer puntuacion = result.getMaxScore();
			Integer cantidad = exam.getExamQuestions().size()+1;
			Integer total = 0;
			Collection<ExamQuestion> toUpdate = exam.getExamQuestions();
			Collection<ExamQuestion> updated = new ArrayList<ExamQuestion>(toUpdate);
			toUpdate.add(result);
			exam.setExamQuestions(updated);
			
			for(ExamQuestion examquestion: exam.getExamQuestions()){
				puntuacion = puntuacion + examquestion.getMaxScore();
			}
			if(cantidad != 0){
				total = puntuacion/cantidad;
			}
			exam.setMark(total);
			
			
		}	
		
		return result;
	}
	
	
	
	
	public void deleteByAdmin(final ExamQuestion examQuestion) {
		Admin principal;

		Assert.notNull(examQuestion);

		principal = this.adminService.findByPrincipal();

		Assert.notNull(principal);
		
		Exam examen = examQuestion.getExam();
		Collection<ExamQuestion> examQuestions = new ArrayList<ExamQuestion>(examen.getExamQuestions());
		examQuestions.remove(examQuestion);
		examen.setExamQuestions(examQuestions);
		
		this.examQuestionRepository.delete(examQuestion);
	
		
	}
	
	
	public ExamQuestion findOne(final int examId) {
		ExamQuestion result = this.examQuestionRepository.findOne(examId);
		Assert.notNull(result);

		return result;

	}
	
	
	public Collection<ExamQuestion> findAnsweredQuestions(int examPaperId){
		
		Collection<ExamQuestion> res = this.examQuestionRepository.findAnsweredQuestions(examPaperId);
		return res;
		
	}
	
}