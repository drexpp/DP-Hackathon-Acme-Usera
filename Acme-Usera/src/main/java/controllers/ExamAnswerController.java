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
import services.CourseService;
import services.ExamAnswerService;
import services.ExamQuestionService;
import domain.Actor;
import domain.Course;
import domain.ExamAnswer;
import domain.ExamPaper;
import domain.ExamQuestion;
import domain.Student;
import domain.Teacher;

@Controller
@RequestMapping("/examAnswer")
public class ExamAnswerController extends AbstractController{
	
	
	// Services

			
			@Autowired
			private ExamAnswerService	examAnswerService;
			
			@Autowired
			private ExamQuestionService	examQuestionService;
			
			@Autowired
			private CourseService	courseService;
			
			@Autowired
			private ActorService	actorService;
			
		
			// Constructors

			public ExamAnswerController() {
				super();
			}
			
			
			
			//Display
			@RequestMapping(value = "/display", method = RequestMethod.GET)
			public ModelAndView display(@RequestParam final int examAnswerId, RedirectAttributes redir) {
				ModelAndView result = new ModelAndView();
				ExamAnswer examAnswer;
				ExamPaper examPaper;
				Collection<ExamQuestion> examQuestions;
				Actor principal;

				try{
					examAnswer = this.examAnswerService.findOne(examAnswerId);
					examPaper = examAnswer.getExamPaper();
					principal = this.actorService.findByPrincipal();
					examQuestions = examPaper.getExam().getExamQuestions();
					result = new ModelAndView("examAnswer/display");
				
					if(principal instanceof Teacher){
						Teacher principalT = (Teacher) principal;
						Assert.isTrue(principalT.getCoursesJoined().contains(examPaper.getExam().getCourse()));
					}
					
					if (principal instanceof Student) {
						Collection<Course> subscribed = this.courseService.selectCoursesSubscriptedByUser(principal.getId()); 
						Assert.isTrue(subscribed.contains(examPaper.getExam().getCourse()));						
						Collection<ExamQuestion> answered	 = this.examQuestionService.findAnsweredQuestions(examPaper.getId());
						result.addObject("answered", answered);
					} 
					result.addObject("examAnswer", examAnswer);
					result.addObject("examPaper", examPaper);
					result.addObject("principal", principal);
					result.addObject("examQuestions", examQuestions);
			
				}catch (Throwable oops){
					result = new ModelAndView("redirect:/course/list.do");	
					redir.addFlashAttribute("message", "examPaper.permission"); 
				}
				
				return result;

		}

}
