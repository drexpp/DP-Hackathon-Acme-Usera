package controllers.teacher;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.TutorialService;
import services.TeacherService;

import controllers.AbstractController;
import domain.Tutorial;
import domain.Teacher;

@Controller
@RequestMapping("/tutorial/teacher")
public class TutorialTeacherController extends AbstractController{
	
	// Services

	@Autowired
	private TutorialService	tutorialService;
	
	@Autowired
	private TeacherService	teacherService;
	
	



	// Constructors

	public TutorialTeacherController() {
		super();
	}
	
	
	@RequestMapping(value = "/refuse", method = RequestMethod.GET)
	public ModelAndView refuse(@RequestParam Integer tutorialId, RedirectAttributes redir) {
		ModelAndView result;
		try{
			Teacher principal = this.teacherService.findByPrincipal();
			Tutorial tutorial = this.tutorialService.findOne(tutorialId);
			Assert.isTrue(principal.getTutorials().contains(tutorial));
			Date now = new Date();
			Assert.isTrue(tutorial.getStartTime().after(now));
			this.tutorialService.delete(tutorial);
			result = new ModelAndView("redirect:/tutorial/list.do");	
		} catch(Throwable oops){
			result = new ModelAndView("redirect:/tutorial/list.do");	
			redir.addFlashAttribute("message", "course.permision"); 
		}

		return result;
	}
	
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam Integer tutorialId, RedirectAttributes redir) {
		ModelAndView result;
		try{
			Tutorial tutorial = this.tutorialService.findOne(tutorialId);
			Date now = new Date();
			Assert.isTrue(tutorial.getStartTime().after(now));
			
			this.tutorialService.saveTutorialForStudent(tutorial);
			result = new ModelAndView("redirect:/tutorial/list.do");	
		} catch(Throwable oops){
			result = new ModelAndView("redirect:/tutorial/list.do");	
			redir.addFlashAttribute("message", "course.permision"); 
		}

		return result;
	}
}