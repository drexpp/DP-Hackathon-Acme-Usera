package controllers;

import java.util.ArrayList;
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
import domain.Advertisement;
import domain.Course;
import domain.Tutorial;
import domain.Student;
import domain.Teacher;

import services.ActorService;
import services.AdvertisementService;
import services.CourseService;
import services.TutorialService;

@Controller
@RequestMapping("/tutorial")
public class TutorialController extends AbstractController{
	
	
	// Services

			@Autowired
			private TutorialService	tutorialService;
			
			@Autowired
			private ActorService	actorService;
			
			@Autowired
			private CourseService	courseService;
			
			@Autowired
			private AdvertisementService advertisementService;
		
			// Constructors

			public TutorialController() {
				super();
			}
				
			// Listing

			@RequestMapping(value = "/list", method = RequestMethod.GET)
			public ModelAndView list() {
				final ModelAndView result;
				Collection<Tutorial> tutorials = new ArrayList<Tutorial>();
				Actor principal = this.actorService.findByPrincipal();
				
				if (principal instanceof Student){
					principal = (Student) principal;
					tutorials = ((Student) principal).getTutorials();
				}
				if (principal instanceof Teacher){
					principal = (Teacher) principal;
					tutorials = ((Teacher) principal).getTutorials();
				}
				
				result = new ModelAndView("tutorial/list");
				result.addObject("tutorials", tutorials);
				result.addObject("principal", principal);

				return result;

			}
}
