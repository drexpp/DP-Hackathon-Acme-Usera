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

import domain.Actor;
import domain.Course;
import domain.Exam;
import domain.ExamQuestion;
import domain.Student;
import domain.Teacher;

import services.ActorService;
import services.CourseService;
import services.ExamService;

@Controller
@RequestMapping("/exam")
public class ExamController extends AbstractController{
	
	
	// Services

			@Autowired
			private ExamService	examService;
			
			@Autowired
			private ActorService	actorService;
			
			@Autowired
			private CourseService	courseService;
			
			// Constructors

			public ExamController() {
				super();
			}
			
			
			
			//Display
			@RequestMapping(value = "/display", method = RequestMethod.GET)
			public ModelAndView display(@RequestParam final int examId, RedirectAttributes redir) {
				ModelAndView result = new ModelAndView();
				Exam exam;
				Collection<ExamQuestion>examQuestions;
				Actor principal;

				try{
				exam = this.examService.findOne(examId);
				examQuestions = exam.getExamQuestions();
				principal = this.actorService.findByPrincipal();
				result = new ModelAndView("exam/display");
				if(principal instanceof Teacher){
					Teacher principalT = (Teacher) principal;
					Assert.isTrue(principalT.getCoursesJoined().contains(exam.getCourse()));
				}
				if (principal instanceof Student) {
					Collection<Course> subscribed = this.courseService.selectCoursesSubscriptedByUser(principal.getId()); 
					Assert.isTrue(subscribed.contains(exam.getCourse()));
				} 
				result.addObject("exam", exam);
				result.addObject("examQuestions", examQuestions);
				result.addObject("principal", principal);
			
				}catch (Throwable oops){
					result = new ModelAndView("redirect:/course/list.do");	
					redir.addFlashAttribute("message", "exam.permission"); 
				}
				
				return result;

		}

}
