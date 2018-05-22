package controllers.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.CurriculumService;
import services.LessonService;

import controllers.AbstractController;
import domain.Curriculum;
import domain.Lesson;

@Controller
@RequestMapping("/lesson/student")
public class LessonStudentController extends AbstractController{
	
	// Services

		@Autowired
		private LessonService	lessonService;


		// Constructors

		public LessonStudentController() {
			super();
		}
		
		
		//Displaying
		@RequestMapping(value = "/read", method = RequestMethod.GET)
		public ModelAndView display(@RequestParam final Integer lessonId, RedirectAttributes redir) {
			ModelAndView result;
			Lesson lesson = new Lesson();
			try{
			lesson = this.lessonService.findOne(lessonId);
			this.lessonService.readLesson(lesson);
			} catch (Throwable oops){
			result = new ModelAndView("redirect:/course/list.do");	
			redir.addFlashAttribute("message", "course.permision"); 
			}
			result = new ModelAndView("redirect:/course/display.do?courseId="+ lesson.getCourse().getId());
			return result;

		}

}
