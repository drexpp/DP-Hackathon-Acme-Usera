package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Admin;
import domain.Exam;
import domain.ExamAnswer;
import domain.ExamPaper;
import domain.ExamQuestion;
import domain.Student;

import repositories.ExamPaperRepository;

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
				private ExamAnswerService				examAnswerService;
				
				@Autowired
				private CertificationService				certificationService;
				
				
				

	
	
				
	public ExamPaper create() {
		Student principal;
		ExamPaper exam = new ExamPaper();
		principal = this.studentService.findByPrincipal();
		Assert.notNull(principal);
		exam.setExamAnswer(new ArrayList<ExamAnswer>());
		return exam;
	}
	
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

	public void deleteByAdmin(final ExamPaper examPaper) {
		Admin principal;
		ExamPaper result;
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
				
}
