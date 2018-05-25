package controllers.teacher;

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

import controllers.AbstractController;

import domain.Category;
import domain.Course;
import domain.Teacher;

import forms.CourseForm;

import services.CategoryService;
import services.CourseService;
import services.TeacherService;

@Controller
@RequestMapping("/course/teacher")
public class CourseTeacherController extends AbstractController{

	// Services

			@Autowired
			private CourseService	courseService;
			
			@Autowired
			private TeacherService	teacherService;
			
			@Autowired
			private CategoryService	categoryService;
	
	
			// Constructors

			public CourseTeacherController() {
				super();
			}
			
			// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CourseForm course = new CourseForm();

		result = this.createEditModelAndView(course);

		return result;
	}
	
	//Join
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public ModelAndView join(@RequestParam final int courseId, final RedirectAttributes redir) {
		ModelAndView result;
	
			try {
				Course course = this.courseService.findOne(courseId);
				Teacher teacher = this.teacherService.findByPrincipal();
				Assert.isTrue(!teacher.getCoursesJoined().contains(course));
				Assert.isTrue(course.getIsClosed() == false);
				this.courseService.join(course, teacher);
				result = new ModelAndView("redirect:/course/list.do");
			} catch (final Throwable oops) {
				String errorMessage = "course.commit.error";
				result = new ModelAndView("redirect:/course/list.do");
				redir.addFlashAttribute("message",errorMessage);
			}

		return result;
	}
	
	
	//remove
	
	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public ModelAndView remove(@RequestParam final int courseId,@RequestParam final int teacherId, final RedirectAttributes redir) {
		ModelAndView result;
	
			try {
				Course course = this.courseService.findOne(courseId);
				Teacher teacher = this.teacherService.findOne(teacherId);
				this.courseService.remove(course, teacher);
				result = new ModelAndView("redirect:/course/display.do?courseId="+ course.getId());
			} catch (final Throwable oops) {
				String errorMessage = "course.commit.error";
				result = new ModelAndView("redirect:/course/list.do");
				redir.addFlashAttribute("message",errorMessage);
			}

		return result;
	}
	
	
	// Edition ----------------------------------------------------------------

			@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
			public ModelAndView save(final CourseForm courseForm, final BindingResult binding) {
				ModelAndView result;
				Course course = this.courseService.reconstruct(courseForm,binding);
				if (binding.hasErrors())
					result = this.createEditModelAndView(courseForm);
				else
					try {
						this.courseService.save(course);
						result = new ModelAndView("redirect:/course/list.do");
					} catch (final Throwable oops) {
						String errorMessage = "course.commit.error";
						result = this.createEditModelAndView(courseForm, errorMessage);
					}

				return result;
			}
			
			
			@RequestMapping(value = "/edit", method = RequestMethod.GET)
			public ModelAndView edit(@RequestParam final int courseId, final RedirectAttributes redir) {
				ModelAndView result;
				Course course;
				CourseForm courseForm;
				final Teacher principal = this.teacherService.findByPrincipal();
				try {
					course = this.courseService.findOne(courseId);
					Assert.isTrue(course.getCreator().equals(principal));
					Assert.isTrue(!course.getIsClosed());
					courseForm = this.courseService.reconstructForm(course);
					result = this.createEditModelAndView(courseForm);
					
				} catch (final Throwable oops) {
					result = new ModelAndView("redirect:/course/list.do");
					redir.addFlashAttribute("message", "course.permision");

				}

				return result;

			}		

	private ModelAndView createEditModelAndView(CourseForm courseForm) {
		ModelAndView result;
		result = this.createEditModelAndView(courseForm, null);
		return result;
	}

	private ModelAndView createEditModelAndView(CourseForm courseForm,String message) {
		
		ModelAndView result;
		Collection<Category> categories = this.categoryService.findAll();

		result = new ModelAndView("course/edit");
		result.addObject("courseForm", courseForm);
		result.addObject("message", message);
		result.addObject("categories", categories);

		return result;
		
		
	}
			
}
