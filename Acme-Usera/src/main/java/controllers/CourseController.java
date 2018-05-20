package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.Actor;
import domain.Advertisement;
import domain.Course;
import domain.Lesson;

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
		
		//Display
				@RequestMapping(value = "/display", method = RequestMethod.GET)
				public ModelAndView display(@RequestParam final int courseId, RedirectAttributes redir) {
					ModelAndView result;
					Course course;
					Collection<Lesson> lessons;
				//	Advertisement advertChoosen;
					Actor principal;

					try{
					course = this.courseService.findOne(courseId);
					lessons = course.getLessons();
					principal = this.actorService.findByPrincipal();

					result = new ModelAndView("course/display");
					result.addObject("lessons", lessons);
					result.addObject("course", course);
					result.addObject("principal", principal);
					//result.addObject("advert", advertChoosen);
					}catch (Throwable oops){
						result = new ModelAndView("redirect:/course/list.do");	
						redir.addFlashAttribute("message", "course.permission"); 
					}//da igual que sea de artículo, el mensaje es el mismo
					
					return result;

			}
		

}
