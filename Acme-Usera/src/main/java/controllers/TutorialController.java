package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Tutorial;
import domain.Student;
import domain.Teacher;

import services.ActorService;

@Controller
@RequestMapping("/tutorial")
public class TutorialController extends AbstractController{
	
	
	// Services
			
			@Autowired
			private ActorService	actorService;
		
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
