package controllers.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.CategoryService;
import services.CourseService;
import services.LessonService;
import services.TeacherService;

import controllers.AbstractController;
import domain.Course;
import domain.Lesson;
import domain.Subscription;
import domain.Teacher;
import forms.LessonForm;

@Controller
@RequestMapping("/lesson/teacher")
public class LessonTeacherController extends AbstractController{
	
	// Services

	@Autowired
	private LessonService	lessonService;
	
	@Autowired
	private CourseService	courseService;
	
	@Autowired
	private TeacherService	teacherService;
	
	



	// Constructors

	public LessonTeacherController() {
		super();
	}
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam Integer courseId, RedirectAttributes redir) {
		ModelAndView result;
		LessonForm lesson = new LessonForm();
		try{
			Course course = this.courseService.findOne(courseId);
			lesson.setCourse(course);
		} catch(Throwable oops){
			result = new ModelAndView("redirect:/course/list.do");	
			redir.addFlashAttribute("message", "course.permision"); 
		}
		

		result = this.createEditModelAndView(lesson);

		return result;
	}
	
	
	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final LessonForm lessonForm, final BindingResult binding) {
		ModelAndView result;
		Lesson lesson = this.lessonService.reconstruct(lessonForm,binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(lessonForm);
		else
			try {
				this.lessonService.save(lesson);
				result = new ModelAndView("redirect:/course/display.do?courseId="+ lesson.getCourse().getId());
			} catch (final Throwable oops) {
				String errorMessage = "lesson.commit.error";
				result = this.createEditModelAndView(lessonForm, errorMessage);
			}

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int lessonId, final RedirectAttributes redir) {
		ModelAndView result;
		Lesson lesson;
		LessonForm lessonForm;
		this.teacherService.findByPrincipal();
		try {
			lesson = this.lessonService.findOne(lessonId);
			lessonForm = this.lessonService.reconstructForm(lesson);
			result = this.createEditModelAndView(lessonForm);
			
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/course/list.do");
			redir.addFlashAttribute("message", "lesson.permision");

		}

		return result;

	}		
	
	
	

	private ModelAndView createEditModelAndView(LessonForm lessonForm) {
		ModelAndView result;
		result = this.createEditModelAndView(lessonForm, null);
		return result;
	}

	private ModelAndView createEditModelAndView(LessonForm lessonForm,String message) {
		
		ModelAndView result;

		result = new ModelAndView("lesson/edit");
		result.addObject("lessonForm", lessonForm);
		result.addObject("message", message);

		return result;
		
		
	}
	

}
