package controllers.teacher;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.CourseService;
import services.ExamService;
import services.TeacherService;
import controllers.AbstractController;
import domain.Course;
import domain.Exam;
import domain.Teacher;
import forms.ExamForm;

@Controller
@RequestMapping("/exam/teacher")
public class ExamTeacherController extends AbstractController{
	
	// Services

	@Autowired
	private ExamService	examService;
	
	@Autowired
	private CourseService	courseService;
	
	@Autowired
	private TeacherService	teacherService;
	
	



	// Constructors

	public ExamTeacherController() {
		super();
	}
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam Integer courseId, RedirectAttributes redir) {
		ModelAndView result;
		ExamForm exam = new ExamForm();
		Teacher principal = this.teacherService.findByPrincipal();
		try{
			Course course = this.courseService.findOne(courseId);
			Assert.isTrue(!course.getIsClosed());
			Assert.isTrue(principal.getCoursesJoined().contains(course));
			exam.setCourse(course);
			result = this.createEditModelAndView(exam);
		} catch(Throwable oops){
			result = new ModelAndView("redirect:/course/list.do");	
			redir.addFlashAttribute("message", "course.permision"); 
		}
		

	

		return result;
	}
	
	
	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ExamForm examForm, final BindingResult binding) {
		ModelAndView result;
		Exam exam = this.examService.reconstruct(examForm,binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(examForm);
		else
			try {
				this.examService.save(exam);
				result = new ModelAndView("redirect:/course/list.do");
			} catch (final Throwable oops) {
				String errorMessage = "exam.commit.error";
				result = this.createEditModelAndView(examForm, errorMessage);
			}

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int examId, final RedirectAttributes redir) {
		ModelAndView result;
		Exam exam;
		ExamForm examForm;
		Teacher principal = this.teacherService.findByPrincipal();
		try {
			exam = this.examService.findOne(examId);
			Assert.isTrue(!exam.getCourse().getIsClosed());
			Assert.isTrue(principal.getCoursesJoined().contains(exam.getCourse()));			
			examForm = this.examService.reconstructForm(exam);
			result = this.createEditModelAndView(examForm);
			
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/course/list.do");
			redir.addFlashAttribute("message", "exam.permision");

		}

		return result;

	}		
/*	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int questionId) {
		ModelAndView result;
		Rende r = new Rende();
		Collection<Question> res = new ArrayList<Question>();
		Boolean permisos = false;
		try {
			final Question question = this.questionService.findOne(questionId);
			r = question.getRende();
			res = r.getQuestions();
			res.remove(question);
			this.questionService.delete(question);
			permisos = true;
			final String message = "question.deleted";
			result = this.CreateListModelAndView(res, message, permisos);
		} catch (final Throwable oops) {
			result = this.CreateListModelAndView(res, "question.commit.error", false);
		}

		return result;
	}
*/	
	

	private ModelAndView createEditModelAndView(ExamForm examForm) {
		ModelAndView result;
		result = this.createEditModelAndView(examForm, null);
		return result;
	}

	private ModelAndView createEditModelAndView(ExamForm examForm,String message) {
		
		ModelAndView result;

		result = new ModelAndView("exam/edit");
		result.addObject("examForm", examForm);
		result.addObject("message", message);

		return result;
		
		
	}
	

}
