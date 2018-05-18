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
import domain.Lesson;
import domain.Teacher;

import repositories.ExamQuestionRepository;
import repositories.ExamRepository;

@Service
@Transactional
public class ExamQuestionService {

	// Managed Repository
			@Autowired
			private ExamQuestionRepository			examQuestionRepository;
			
			@Autowired
			private TeacherService				teacherService;

			@Autowired
			private StudentService				studentService;
	
			
	public ExamQuestion create() {
		Teacher principal;
		ExamQuestion exam;
		principal = this.teacherService.findByPrincipal();
		Assert.notNull(principal);
		return exam;
	}
	
	public Collection<ExamQuestion> findAll() {
		final Collection<ExamQuestion> result = this.examQuestionRepository.findAll();	
		return result;
	}
	
	
	public ExamQuestion save(final ExamQuestion examQuestion) {
		Teacher principal;
		ExamQuestion result;
		Assert.notNull(examQuestion);

		principal = this.teacherService.findByPrincipal();

		Assert.notNull(principal);
		
		if (examQuestion.getId() != 0){
		Assert.isTrue(principal.getCoursesJoined().contains(examQuestion.getExam().getCourse()));
		}
		
	
		result = this.examQuestionRepository.save(examQuestion);
	
		if(examQuestion.getId() == 0){
			Exam exam = result.getExam();
			Collection<ExamQuestion> toUpdate = exam.getExamQuestions();
			Collection<ExamQuestion> updated = new ArrayList<ExamQuestion>(toUpdate);
			toUpdate.add(result);
			exam.setExamQuestions(updated);
		}	
		
		return result;
	}
	
	
	public ExamQuestion findOne(final int examId) {
		ExamQuestion result = this.examQuestionRepository.findOne(examId);
		Assert.notNull(result);

		return result;

	}
	
}
