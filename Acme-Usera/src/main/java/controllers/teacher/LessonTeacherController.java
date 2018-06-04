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
import services.LessonService;
import services.TeacherService;
import controllers.AbstractController;
import domain.Course;
import domain.Lesson;
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
		Teacher principal = this.teacherService.findByPrincipal();
		try{
			Course course = this.courseService.findOne(courseId);
			Assert.isTrue(!course.getIsClosed());
			Assert.isTrue(principal.getCoursesJoined().contains(course));
			lesson.setCourse(course);
			result = this.createEditModelAndView(lesson);
		} catch(Throwable oops){
			result = new ModelAndView("redirect:/course/list.do");	
			redir.addFlashAttribute("message", "course.permision"); 
		}
		

	

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
		Teacher principal = this.teacherService.findByPrincipal();
		try {
			lesson = this.lessonService.findOne(lessonId);
			Assert.isTrue(!lesson.getCourse().getIsClosed());
			Assert.isTrue(principal.getCoursesJoined().contains(lesson.getCourse()));			
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
