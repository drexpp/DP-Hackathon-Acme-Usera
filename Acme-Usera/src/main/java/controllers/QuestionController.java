package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ActorService;
import services.QuestionService;
import services.StudentService;
import domain.Actor;
import domain.Answer;
import domain.Question;
import domain.Student;
import domain.Teacher;

@Controller
@RequestMapping("/question")
public class QuestionController {
	
	
	// Services

		@Autowired
		private QuestionService	questionService;
		
		@Autowired
		private ActorService	actorService;
		
		@Autowired
		private StudentService	studentService;
		
		// Constructors

		public QuestionController() {
			super();
		}
		
		
		// Listing

		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list() {
			final ModelAndView result;
			final Collection<Question> questions;
			Actor principal = this.actorService.findByPrincipal();
			
			questions = this.questionService.findAll();
			
			result = new ModelAndView("question/list");
			result.addObject("questions", questions);
			if(principal != null){
				result.addObject("principal",principal);
			}

			return result;

		}
		
		//Display
		@RequestMapping(value = "/display", method = RequestMethod.GET)
		public ModelAndView display(@RequestParam final int questionId, RedirectAttributes redir) {
			ModelAndView result;
			Question question = null;
			Collection<Answer>answers;
			Actor principal;


		try{
			question = this.questionService.findOne(questionId);
			try{
				answers = question.getAnswers();
				principal = this.actorService.findByPrincipal();
				if (principal instanceof Student) {
					String subscription = this.studentService
							.checkSubscription(question.getForum().getCourse());
					Assert.isTrue(subscription.equals("STANDARD")
							|| subscription.equals("PREMIUM"));

				} else if (principal instanceof Teacher) {
					Teacher teacher = (Teacher) principal;
					Assert.isTrue(teacher.getCoursesJoined().contains(
							question.getForum().getCourse()));
				}
				result = new ModelAndView("question/display");
				result.addObject("question", question);
				result.addObject("answers", answers);
				result.addObject("principal", principal);
			}catch (Throwable oops){
				result = new ModelAndView("redirect:../../forum/display.do?forumId="+question.getForum().getId());	
				redir.addFlashAttribute("message", "question.permission"); 
			}
			
		}catch (Throwable oops) {
				result = new ModelAndView("redirect:../course/list.do");
				String successfulMessage = "answer.commit.error";
				redir.addFlashAttribute("message", successfulMessage);
		}
					
		return result;

		}
		

}
