package controllers.teacher;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Category;
import domain.Course;

import forms.CourseForm;

import services.ActorService;
import services.CategoryService;
import services.CourseService;
import services.TeacherService;

@Controller
@RequestMapping("/course/teacher")
public class CourseTeacherController {

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
						result = new ModelAndView("redirect:/list.do");
					} catch (final Throwable oops) {
						String errorMessage = "course.commit.error";
						result = this.createEditModelAndView(courseForm, errorMessage);
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
