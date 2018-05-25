package controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import controllers.AbstractController;

import domain.Course;
import domain.Admin;

import services.CategoryService;
import services.CourseService;
import services.AdminService;

@Controller
@RequestMapping("/course/admin")
public class CourseAdminController extends AbstractController{

	// Services

			@Autowired
			private CourseService	courseService;
			
			@Autowired
			private AdminService	adminService;
			
			@Autowired
			private CategoryService	categoryService;
	
	
			// Constructors

			public CourseAdminController() {
				super();
			}
			
			// Creation ---------------------------------------------------------------


	
	//Delete
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView join(@RequestParam final int courseId, final RedirectAttributes redir) {
		ModelAndView result;
	
			try {
				Course course = this.courseService.findOne(courseId);
				Admin admin = this.adminService.findByPrincipal();
				this.courseService.deleteByAdmin(course);
				result = new ModelAndView("redirect:/course/list.do");
			} catch (final Throwable oops) {
				String errorMessage = "course.commit.error";
				result = new ModelAndView("redirect:/course/list.do");
				redir.addFlashAttribute("message",errorMessage);
			}

		return result;
	}
	
	
	}