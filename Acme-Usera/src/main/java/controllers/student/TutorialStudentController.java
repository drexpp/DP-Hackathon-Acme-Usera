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

import services.TeacherService;
import services.TutorialService;

import controllers.AbstractController;
import domain.Teacher;
import domain.Tutorial;


@Controller
@RequestMapping("/tutorial/student")
public class TutorialStudentController extends AbstractController{

	// Services
	@Autowired
	private TeacherService	teacherService;
	
	@Autowired
	private TutorialService	tutorialService;
	
	
	// Constructors

	public TutorialStudentController() {
		super();
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam Integer teacherId, RedirectAttributes redir) {
		ModelAndView result;
		Tutorial tutorial = this.tutorialService.create();
		try{
			Teacher teacher = this.teacherService.findOne(teacherId);
			tutorial.setTeacher(teacher);
			result = this.createEditModelAndView(tutorial);
		} catch(Throwable oops){
			result = new ModelAndView("redirect:/course/list.do");	
			redir.addFlashAttribute("message", "course.permision"); 
		}
		return result;
	}

	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Tutorial tutorial, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(tutorial);
		} else{
			try {
				this.tutorialService.save(tutorial);
				result = new ModelAndView("redirect:/course/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tutorial,"tutorial.commit.error");
			}
		}
		return result;
	}
	
	
	private ModelAndView createEditModelAndView(Tutorial tutorial) {
		ModelAndView result;
		result = this.createEditModelAndView(tutorial,null);
		return result;
	}

	private ModelAndView createEditModelAndView(Tutorial tutorial, String message) {
		ModelAndView result;

		result = new ModelAndView("tutorial/edit");
		result.addObject("tutorial", tutorial);
		result.addObject("message", message);
		return result;
	}
	
}
