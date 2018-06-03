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
import repositories.AnswerRepository;
import domain.Actor;
import domain.Admin;
import domain.Answer;
import domain.Course;
import domain.Question;
import domain.Student;
import domain.Teacher;
import forms.AnswerForm;


@Service
@Transactional
public class AnswerService {

	// Managed Repository
	@Autowired
	private AnswerRepository		answerRepository;

	// Supporting services
	@Autowired
	private ActorService			actorService;
	
	@Autowired
	private AdminService			adminService;
	
	@Autowired
	private StudentService			studentService;
	
	@Autowired
	private TeacherService			teacherService;
	
	@Autowired
	private CourseService			courseService;
	
	@Autowired
	private QuestionService			questionService;
	
	@Autowired
	private Validator				validator;

	// Constructors

	public AnswerService() {
		super();
	}

	// Simple CRUD methods

	
	public Answer create(final int questionId) {
		Actor principal;
		Question question;
		Answer answer = new Answer();

		principal = this.actorService.findByPrincipal();
		question = this.questionService.findOne(questionId);
		Assert.notNull(principal);
		Assert.isTrue(question.getIsAnswered()==false);
		Collection<Course> subs = this.courseService.findCoursesStandardAndPremium(principal.getId());
		Assert.isTrue(subs.contains(question.getForum().getCourse()));
		answer.setMoment(new Date(System.currentTimeMillis() - 1));
		answer.setQuestion(question);
		answer.setActor(principal);
		
		return answer;
	}
	
	public AnswerForm createForm() {
		Actor principal;
		AnswerForm answerForm;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		answerForm = new AnswerForm();

		return answerForm;
	}

	public Collection<Answer> findAll() {
		final Collection<Answer> result = this.answerRepository.findAll();
		
		
		return result;
	}

	// Other business methods

	public void delete(final Answer answer) { 
		Student principal;
		Collection<Answer> updated;

		Assert.notNull(answer);
		Assert.isTrue(answer.getId() != 0);
		Assert.isTrue(answer.getQuestion().getForum().getCourse().getIsClosed() == false);

		
		principal = this.studentService.findByPrincipal();
		Assert.notNull(principal);
		
		Assert.isTrue(principal.getAnswers().contains(answer) || principal.getQuestions().contains(answer.getQuestion()));
		if(principal.getAnswers().contains(answer) || principal.getQuestions().contains(answer.getQuestion())){ //si soy el creador de esa respuesta
			final Question question = answer.getQuestion();
			final Collection<Answer> answer1 = question.getAnswers();
			updated = new ArrayList<Answer>(answer1);
			updated.remove(answer);
			question.setAnswers(updated);
			
			final Actor actor = answer.getActor();
			final Collection<Answer> answer2 = actor.getAnswers();
			updated = new ArrayList<Answer>(answer2);
			updated.remove(answer);
			actor.setAnswers(updated);
			
			this.answerRepository.delete(answer);
		}
	}
	
	public void deleteByAdmin(final Answer answer){
		Admin principal;
		Collection<Answer> updated;
		
		Assert.notNull(answer);
		Assert.isTrue(answer.getId() != 0);
		
		principal = this.adminService.findByPrincipal();
		Assert.notNull(principal);
		
		final Question question = answer.getQuestion();
		final Collection<Answer> answer1 = question.getAnswers();
		updated = new ArrayList<Answer>(answer1);
		updated.remove(answer);
		question.setAnswers(updated);
		
		final Actor actor = answer.getActor();
		final Collection<Answer> answer2 = actor.getAnswers();
		updated = new ArrayList<Answer>(answer2);
		updated.remove(answer);
		actor.setAnswers(updated);
		
		this.answerRepository.delete(answer);
	}
	
	public Answer save(final Answer answerToSave) {
		Actor principal;
		Answer result;
		Assert.notNull(answerToSave);

		principal = this.actorService.findByPrincipal();

		Assert.notNull(principal);
		
		if (answerToSave.getId() != 0){
		Assert.isTrue(principal.getAnswers().contains(answerToSave));
		}
	
		result = this.answerRepository.save(answerToSave);
		
		final Collection<Answer> update = new HashSet<>(principal.getAnswers());
		update.add(result);
		principal.setAnswers(update);
		
		final Collection<Answer> update2 = new HashSet<>(result.getQuestion().getAnswers());
		update2.add(result);
		result.getQuestion().setAnswers(update2);
		
		return result;
	}
	
	public Answer findOne(final int answerId) {

		Answer result = this.answerRepository.findOne(answerId);
		Assert.notNull(result);

		return result;
	}
		
	public void flush(){
		this.answerRepository.flush();
	}
	
	public void changeIsSolution (int answerId){
		Teacher teacher = this.teacherService.findByPrincipal();
		Student student;
		Integer score = 100;
		Assert.notNull(teacher);
		Answer answer = this.findOne(answerId);
		Assert.isTrue(teacher.getCoursesJoined().contains(answer.getQuestion().getForum().getCourse())); //Puede cambiar si está unido al curso (el creador también está unido)
		if(answer.getQuestion().getIsAnswered() == false){//Si no ha sido respondida se puede cambiar, si no no.
			if(answer.getIsSolution() == true){ //Si se marca como solución una respuesta se cambia como contestada la pregunta.
				answer.setIsSolution(false);
				answer.getQuestion().setIsAnswered(false);
			} else{
				answer.setIsSolution(true);
				answer.getQuestion().setIsAnswered(true); 
				if(answer.getActor() instanceof Student){
					student = studentService.findOne(answer.getActor().getId());
					score = score + student.getScore(); //se le suman 100 puntos al estudiante que respondió correctamente.
					student.setScore(score);
				}
			}
		}
	}

	public Answer reconstruct(AnswerForm answerForm, BindingResult binding) {
		final Question question = this.questionService.findOne(answerForm.getQuestion().getId());
		Actor principal;
		Date moment;
		final Answer answer = this.create(question.getId());
		principal = this.actorService.findByPrincipal();
		moment = new Date(System.currentTimeMillis() - 1);	

		answer.setId(answerForm.getId());
		answer.setVersion(answer.getVersion());
		answer.setText(answerForm.getText());
		answer.setPhotoURL(answerForm.getPhotoURL());
		answer.setActor(principal);
		answer.setIsSolution(false);
		answer.setMoment(moment);
		answer.setQuestion(answerForm.getQuestion());
		
		this.validator.validate(answer, binding);
		
		return answer;
	}

	public AnswerForm reconstructForm(final Answer answer) {
		AnswerForm answerForm;
		
		answerForm = this.createForm();
		answerForm.setText(answer.getText());
		answerForm.setPhotoURL(answer.getPhotoURL());
		answerForm.setId(answer.getId());
		answerForm.setVersion(answer.getVersion());
		answerForm.setQuestion(answer.getQuestion());
		return answerForm;
	}
}