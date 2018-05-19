package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Course;

import services.ActorService;
import services.CourseService;

@Controller
@RequestMapping("/course")
public class CourseController {
	
	
	// Services

		@Autowired
		private CourseService	courseService;
		
		@Autowired
		private ActorService	actorService;
		
		
		// Constructors

		public CourseController() {
			super();
		}
		
		
		// Listing

		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list() {
			final ModelAndView result;
			final Collection<Course> courses;
			Actor principal = this.actorService.findByPrincipal();
			
			courses = this.courseService.findAll();
			
			result = new ModelAndView("course/list");
			result.addObject("courses", courses);
			if(principal != null){
				result.addObject("principal",principal);
			}

			return result;

		}
		

}
