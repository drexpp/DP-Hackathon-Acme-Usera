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
import domain.Sponsor;
import domain.Advertisement;
import domain.Course;
import domain.Lesson;
import domain.Student;
import domain.Teacher;

import services.ActorService;
import services.AdvertisementService;
import services.CourseService;
import services.StudentService;

@Controller
@RequestMapping("/course")
public class CourseController extends AbstractController{
	
	
	// Services

		@Autowired
		private CourseService	courseService;
		
		@Autowired
		private ActorService	actorService;
		
		@Autowired
		private StudentService	studentService;
	
		@Autowired
		private AdvertisementService advertisementService;
		
		
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
			if(principal instanceof Student){
				Collection<Course> subscribed = this.courseService.selectCoursesSubscriptedByUser(principal.getId());
				Collection<Course> accessForum = this.courseService.findCoursesStandardAndPremium(principal.getId());
				result.addObject("accessForum", accessForum);
				result.addObject("subscribed",subscribed);
			}
			
			if(principal instanceof Sponsor){
				Collection<Course> coursesWithAds = this.courseService.findCoursesWithAdsPlacedBySponsor();
				result.addObject("coursesWithAds", coursesWithAds);
			}

			return result;

		}
		
		@RequestMapping(value = "/myCourses", method = RequestMethod.GET)
		public ModelAndView MyCourses() {
			ModelAndView result = new ModelAndView();
			Collection<Course> courses = new ArrayList<Course>();
			Actor principal = this.actorService.findByPrincipal();
			result = new ModelAndView("course/list");
			if (principal instanceof Student){
				Student student = (Student) principal;
				courses = this.courseService.selectCoursesSubscriptedByUser(student.getId());
				Collection<Course> accessForum = this.courseService.findCoursesStandardAndPremium(principal.getId());
				result.addObject("accessForum", accessForum);
				Collection<Course> subscribed = this.courseService.selectCoursesSubscriptedByUser(principal.getId());
				result.addObject("subscribed",subscribed);
			} else if (principal instanceof Teacher){
				Teacher teacher = (Teacher) principal;
				courses = teacher.getCoursesJoined();
			} else if (principal instanceof Sponsor){
				Collection<Course> coursesWithAds = this.courseService.findCoursesWithAdsPlacedBySponsor();
				courses = coursesWithAds;
				result.addObject("coursesWithAds", coursesWithAds);
				
			}
			result.addObject("courses", courses);
			result.addObject("principal",principal);

			return result;

		}
		
		//Display
				@RequestMapping(value = "/display", method = RequestMethod.GET)
				public ModelAndView display(@RequestParam final int courseId, RedirectAttributes redir) {
					ModelAndView result = new ModelAndView();
					Course course;
					Collection<Lesson> lessons;
					Collection<Course> coursesWithExamPaperFromStudent;
					Advertisement advertChoosen;
					Actor principal;

					try{
					course = this.courseService.findOne(courseId);
					lessons = course.getLessons();
					principal = this.actorService.findByPrincipal();
					advertChoosen = this.advertisementService.findRandomAdvertisement(course);
					result = new ModelAndView("course/display");
					
					if (principal instanceof Student) {
						String subscriptionType = this.studentService.checkSubscription(course);
						result.addObject("subscriptionType", subscriptionType);
						if(subscriptionType.equals("FREE")){
						} 
					} else {
						result.addObject("advert", advertChoosen);
					}
					
					if(principal instanceof Teacher){
						Teacher principalT = (Teacher) principal;
						Assert.isTrue(principalT.getCoursesJoined().contains(course));
						result.addObject("advert", advertChoosen);
			}
			if (principal instanceof Student) {
				String subscriptionType = this.studentService.checkSubscription(course);
				coursesWithExamPaperFromStudent = this.courseService.findCoursesWithExamPaperFromStudent(principal.getId());
				result.addObject("subscriptionType", subscriptionType);
				result.addObject("coursesWithExamPaperFromStudent", coursesWithExamPaperFromStudent);
				if(subscriptionType.equals("FREE")){
					result.addObject("advert", advertChoosen);
				}
			
		
				
			} else if (principal instanceof Sponsor){
				Collection<Course> coursesWithAds = this.courseService.findCoursesWithAdsPlacedBySponsor();
				Assert.isTrue(coursesWithAds.contains(course));
			}
					result.addObject("lessons", lessons);
					result.addObject("course", course);
					result.addObject("principal", principal);
				
					}catch (Throwable oops){
						result = new ModelAndView("redirect:/course/list.do");	
						redir.addFlashAttribute("message", "course.permision"); 
					}//da igual que sea de artículo, el mensaje es el mismo
					
					return result;

			}
		

}
