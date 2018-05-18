package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Exam;
import domain.ExamAnswer;
import domain.ExamPaper;
import domain.ExamQuestion;
import domain.Lesson;
import domain.Student;
import domain.Teacher;

import repositories.ExamPaperRepository;
import repositories.ExamQuestionRepository;

@Service
@Transactional
public class ExamPaperService {

	// Managed Repository
				@Autowired
				private ExamPaperRepository			examPaperRepository;
				
				@Autowired
				private TeacherService				teacherService;

				@Autowired
				private StudentService				studentService;
	
	
				
	public ExamPaper create() {
		Student principal;
		ExamPaper exam;
		principal = this.studentService.findByPrincipal();
		Assert.notNull(principal);
		exam.setExamAnswer(new ArrayList<ExamAnswer>());
		return exam;
	}
	
	public Collection<ExamQuestion> findAll() {
		final Collection<ExamQuestion> result = this.examPaperRepository.findAll();	
		return result;
	}
	
	
	public ExamPaper save(final ExamPaper examPaper) {
		Student principal;
		ExamPaper result;
		Assert.notNull(examPaper);
		
	//	Assert.isTrue(principal.getLessons().containAll(examPaper.getExam().getCourse().getLessons()));
		
		principal = this.studentService.findByPrincipal();

		Assert.notNull(principal);
		
		result = this.examPaperRepository.save(examPaper);
	
		if(examPaper.getId() == 0){
			Exam exam = result.getExam();
			Collection<ExamPaper> toUpdate = exam.getExamPaper();
			Collection<ExamPaper> updated = new ArrayList<ExamPaper>(toUpdate);
			toUpdate.add(result);
			exam.setExamPaper(updated);
			
			
		}	
		
		return result;
	}
	public ExamPaper findOne(final int examId) {
		ExamPaper result = this.examPaperRepository.findOne(examId);
		Assert.notNull(result);

		return result;

	}			
				
}
