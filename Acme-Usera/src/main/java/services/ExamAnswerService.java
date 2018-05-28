package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Admin;
import domain.Exam;
import domain.ExamPaper;
import domain.ExamAnswer;
import domain.ExamQuestion;
import domain.Student;

import repositories.ExamAnswerRepository;

@Service
@Transactional
public class ExamAnswerService {

	// Managed Repository
			@Autowired
			private ExamAnswerRepository			examAnswerRepository;

			@Autowired
			private StudentService				studentService;
			
			@Autowired
			private AdminService				adminService;
	
			
	public ExamAnswer create() {
		Student principal;
		ExamAnswer exam = new ExamAnswer();
		principal = this.studentService.findByPrincipal();
		Assert.notNull(principal);
		return exam;
	}
	
	public Collection<ExamAnswer> findAll() {
		final Collection<ExamAnswer> result = this.examAnswerRepository.findAll();	
		return result;
	}
	
	
	public ExamAnswer save(final ExamAnswer examAnswer) {
		Student principal;
		ExamAnswer result;
		Assert.notNull(examAnswer);

		principal = this.studentService.findByPrincipal();

		Assert.notNull(principal);
		
//		Assert.isTrue(principal.getLessons().containAll(examPaper.getExam().getCourse().getLessons()));
		
	
		result = this.examAnswerRepository.save(examAnswer);
	
		if(examAnswer.getId() == 0){
			ExamPaper exam = result.getExamPaper();
			Collection<ExamAnswer> toUpdate = exam.getExamAnswer();
			Collection<ExamAnswer> updated = new ArrayList<ExamAnswer>(toUpdate);
			toUpdate.add(result);
			exam.setExamAnswer(updated);
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
	
}
