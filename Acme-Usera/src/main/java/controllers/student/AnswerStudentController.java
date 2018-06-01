package controllers.student;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.Actor;
import domain.Answer;
import domain.Course;
import domain.Question;
import domain.Student;
import forms.AnswerForm;
import services.ActorService;
import services.AnswerService;
import services.CourseService;
import services.QuestionService;
import services.StudentService;

@Controller
@RequestMapping("/answer/student")
public class AnswerStudentController {
	
	
	// Services

		@Autowired
		private AnswerService	answerService;
		
		@Autowired
		private QuestionService	questionService;
		
		@Autowired
		private StudentService	studentService;
		
		@Autowired
		private ActorService	actorService;
		
		@Autowired
		private CourseService	courseService;
		
		// Constructors

		public AnswerStudentController() {
			super();
		}
		
		
		// Listing
		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list() {
			final ModelAndView result;
			final Collection<Answer> answers = new ArrayList<>();
			Student principal = this.studentService.findByPrincipal();
			
			for(Answer answer: principal.getAnswers()) {
				if(answer.getQuestion().getForum().getCourse().getIsClosed() == false){
					answers.add(answer); //Listar las respuestas del student.
				}
			}
			
			result = new ModelAndView("answer/list");
			result.addObject("answers", answers);
			if(principal != null){
				result.addObject("principal",principal);
			}

			return result;

		}
		
		//Create
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create(@RequestParam final int questionId, final RedirectAttributes redir) {
			ModelAndView result;
			Boolean permission = true;
			Question question = null;
			AnswerForm answer = new AnswerForm();
			try{
				question = this.questionService.findOne(questionId);
				try{
					Assert.isTrue(question.getIsAnswered()==false);
					answer.setQuestion(question);
					
					Student principal = this.studentService.findByPrincipal();
					Collection<Course> subs = this.courseService.findCoursesStandardAndPremium(principal.getId());
					Assert.isTrue(subs.contains(question.getForum().getCourse()));
					
					result = this.createEditModelAndView(answer);
					if(question.getForum().getCourse().getIsClosed() == true){
						permission = false;
					}
					result.addObject("permission", permission);
				} catch (final Throwable oops) {
					result = new ModelAndView("redirect:../../question/display.do?questionId="+question.getId());
					redir.addFlashAttribute("message", "answer.permission");
				}
			}catch (Throwable oops) {
				result = new ModelAndView("redirect:../../course/list.do");
				String successfulMessage = "answer.commit.error";
				redir.addFlashAttribute("message", successfulMessage);
			}
			return result;
		}
		
		// Edition ----------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(final AnswerForm answerForm, final BindingResult binding) {
			ModelAndView result;
			Answer answer = this.answerService.reconstruct(answerForm,binding);
			if (binding.hasErrors())
				result = this.createEditModelAndView(answerForm);
			else
				try {
					this.answerService.save(answer);
					result = new ModelAndView("redirect:../../question/display.do?questionId="+answer.getQuestion().getId());
				} catch (final Throwable oops) {
					String errorMessage = "answer.commit.error";
					result = this.createEditModelAndView(answerForm, errorMessage);
				}

			return result;
			}
			
				
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam final int answerId, final RedirectAttributes redir) {
			ModelAndView result;
			Answer answer;
			Boolean permission = true;
			AnswerForm answerForm;
			final Student principal = this.studentService.findByPrincipal();
			
		try{
			answer = this.answerService.findOne(answerId);
		
			try {
				Assert.isTrue(answer.getActor().equals(principal));
				answerForm = this.answerService.reconstructForm(answer);
				result = this.createEditModelAndView(answerForm);
				if(answer.getQuestion().getForum().getCourse().getIsClosed() == true){
					permission = false;
				}
				result.addObject("permission", permission);
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:../../question/display.do?questionId="+answer.getQuestion().getId());
				redir.addFlashAttribute("message", "answer.permission");

			}
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:../../course/list.do");
			String errorMessage = "answer.commit.error";
			redir.addFlashAttribute("message", errorMessage);
		}

			return result;

		}		
		
		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam final int answerId, RedirectAttributes redir) {
		ModelAndView result;
		Answer answer;
		Actor principal = this.actorService.findByPrincipal();
		
		answer = this.answerService.findOne(answerId);

		try {
			Assert.isTrue(answer.getActor().getId()==principal.getId());
			this.answerService.delete(answer);
			result = new ModelAndView("redirect:../../question/display.do?questionId="+answer.getQuestion().getId());
			String successfulMessage = "answer.commit.ok";
			redir.addFlashAttribute("message", successfulMessage);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:../../question/display.do?questionId="+answer.getQuestion().getId());
			String errorMessage = "answer.commit.error";
			redir.addFlashAttribute("message", errorMessage);
		}

		return result;
	}

		private ModelAndView createEditModelAndView(AnswerForm answerForm) {
			ModelAndView result;
			result = this.createEditModelAndView(answerForm, null);
			return result;
		}

		private ModelAndView createEditModelAndView(AnswerForm answerForm,String message) {
			
			ModelAndView result;

			result = new ModelAndView("answer/edit");
			result.addObject("permission", true);
			result.addObject("answerForm", answerForm);
			result.addObject("message", message);

			return result;
			
			
		}
		

}
