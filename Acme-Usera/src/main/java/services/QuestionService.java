package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.QuestionRepository;
import domain.Actor;
import domain.Admin;
import domain.Answer;
import domain.Forum;
import domain.Question;
import domain.Student;
import forms.QuestionForm;


@Service
@Transactional
public class QuestionService {

	// Managed Repository
	@Autowired
	private QuestionRepository			questionRepository;

	// Supporting services
	@Autowired
	private StudentService				studentService;
	
	@Autowired
	private ActorService				actorService;
	
	@Autowired
	private ForumService				forumService;
	
	@Autowired
	private AnswerService				answerService;

	@Autowired
	private AdminService				adminService;
	
	@Autowired
	private Validator					validator;

	// Constructors

	public QuestionService() {
		super();
	}

	// Simple CRUD methods

	
	public Question create(final int forumId) {
		Student principal;
		Forum forum;
		Question question = new Question();

		principal = this.studentService.findByPrincipal();
		forum = this.forumService.findOne(forumId);
		Assert.notNull(principal);
		question.setStudent(principal);
		question.setMoment(new Date(System.currentTimeMillis()-1));
		question.setAnswers(new ArrayList<Answer>());
		question.setForum(forum);
		
		return question;
	}

	public QuestionForm createForm() {
		Student principal;
		QuestionForm questionForm;

		principal = this.studentService.findByPrincipal();
		Assert.notNull(principal);
		questionForm = new QuestionForm();

		return questionForm;
	}

	public Collection<Question> findAll() {
		final Collection<Question> result = this.questionRepository.findAll();
		return result;
	}

	// Other business methods
	public void delete(final Question question) { 
		Student principal;
		Collection<Question> updated, updated2;

		Assert.notNull(question);
		Assert.isTrue(question.getId() != 0);
		
		principal = this.studentService.findByPrincipal();
		Assert.notNull(principal);
		
		if(principal.getQuestions().contains(question)){ //si soy el creador de esa respuesta
			
			final Forum forum = question.getForum();
			final Collection<Question> question1 = forum.getQuestions();
			updated = new ArrayList<Question>(question1);
			updated.remove(question);
			forum.setQuestions(updated);
			
			final Student student = question.getStudent();
			final Collection<Question> question2 = student.getQuestions();
			updated2 = new ArrayList<Question>(question2);
			updated2.remove(question);
			student.setQuestions(updated2);
			
			
			for (final Answer a : question.getAnswers())
				this.answerService.delete(a);
			
			this.questionRepository.delete(question);
		}
	}

	public void deleteByAdmin(final Question question) {
		Admin principal;
		Collection<Question> updated;

		Assert.notNull(question);
		Assert.isTrue(question.getId() != 0);
		principal = this.adminService.findByPrincipal();
		Assert.notNull(principal);

		final Forum forum = question.getForum();
		final Collection<Question> question1 = forum.getQuestions();
		updated = new ArrayList<Question>(question1);
		updated.remove(question);
		forum.setQuestions(updated);
		
		final Student student = question.getStudent();
		final Collection<Question> question2 = student.getQuestions();
		updated = new ArrayList<Question>(question2);
		updated.remove(question);
		student.setQuestions(updated);
		
		for (final Answer a : question.getAnswers())
			this.answerService.delete(a);
		
		this.questionRepository.delete(question);

	}
	
	public Question save(final Question questionToSave) {
		Student principal;
		Question result;
		Assert.notNull(questionToSave);

		principal = this.studentService.findByPrincipal();

		Assert.notNull(principal);
		
		if (questionToSave.getId() != 0){
		Assert.isTrue(principal.getQuestions().contains(questionToSave));
		}
	
		result = this.questionRepository.save(questionToSave);

		final Collection<Question> update = new HashSet<>(principal.getQuestions());
		update.add(result);
		principal.setQuestions(update);
				
		return result;
	}

	public Question findOne(final int questionId) {
		Actor principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Question result = this.questionRepository.findOne(questionId);
		Assert.notNull(result);

		return result;
	}
	
	public void flush(){
		this.questionRepository.flush();
	}

	public Question reconstruct(QuestionForm questionForm, BindingResult binding) {
		final Forum forum = this.forumService.findOne(questionForm.getForum().getId());
		final Question question = this.create(forum.getId());
		Student principal;
		Date moment;
		
		moment = new Date(System.currentTimeMillis() - 1);	
		principal = this.studentService.findByPrincipal();
		
		question.setId(questionForm.getId());
		question.setVersion(questionForm.getVersion());
		question.setTitle(questionForm.getTitle());
		question.setMoment(moment);
		question.setQuestion(questionForm.getQuestion());
		question.setPhotoURL(questionForm.getPhotoURL());
		question.setStudent(principal);
		question.setIsAnswered(false);
		question.setForum(questionForm.getForum());
		question.setAnswers(new ArrayList<Answer>());
		this.validator.validate(question, binding);
		
		return question;
	}

	public QuestionForm reconstructForm(final Question question) {
		QuestionForm result;
		
		result = this.createForm();
		result.setId(question.getId());
		result.setVersion(question.getVersion());
		result.setTitle(question.getTitle());
		result.setQuestion(question.getQuestion());
		result.setPhotoURL(question.getPhotoURL());
		result.setForum(question.getForum());
		return result;
	}
}