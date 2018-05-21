package controllers.student;

import javax.validation.Valid;

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
import services.CustomisationService;
import services.StudentService;
import services.SubscriptionService;
import services.TeacherService;

import controllers.AbstractController;
import domain.Course;
import domain.Customisation;
import domain.Student;
import domain.Subscription;
import forms.CourseForm;

@Controller
@RequestMapping("/subscription/student")
public class SubscriptionStudentController extends AbstractController{

	// Services

				@Autowired
				private CourseService	courseService;
				
				@Autowired
				private StudentService	studentService;
				
				@Autowired
				private SubscriptionService	subscriptionService;
				
				@Autowired
				private CustomisationService	customisationService;
				
				
				// Constructors

				public SubscriptionStudentController() {
					super();
				}
				
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam Integer courseId, RedirectAttributes redir) {
		ModelAndView result;
		Subscription subscription = this.subscriptionService.create();
		try{
			Course course = this.courseService.findOne(courseId);
			subscription.setCourse(course);
		} catch(Throwable oops){
			result = new ModelAndView("redirect:/course/list.do");	
			redir.addFlashAttribute("message", "course.permision"); 
		}
		

		result = this.createEditModelAndView(subscription);

		return result;
	}
	
	
	//Edit
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Subscription subscription, final BindingResult binding) {
		ModelAndView result;
		subscription = this.subscriptionService.checkSubscriptionType(subscription, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(subscription);
		} else
			try {
				this.subscriptionService.save(subscription);
				result = new ModelAndView("redirect:/course/display.do?courseId=" + subscription.getCourse().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(subscription, "subscription.commit.error");
			}

		return result;
	}
	

	private ModelAndView createEditModelAndView(Subscription subscription) {
		ModelAndView result;
		result = this.createEditModelAndView(subscription, null);
		return result;
	}

	private ModelAndView createEditModelAndView(Subscription subscription,
			String message) {
		ModelAndView result;
		Student principal = this.studentService.findByPrincipal();
		Customisation customisation = this.customisationService.find();
		
		result = new ModelAndView("subscription/edit");
		result.addObject("subscription", subscription);
		result.addObject("customisation", customisation);
		result.addObject("principal", principal);
		result.addObject("message", message);
		
		return result;
	}
	
}
