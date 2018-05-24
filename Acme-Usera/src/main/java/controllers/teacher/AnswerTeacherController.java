package controllers.teacher;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import domain.Answer;
import domain.Teacher;
import services.AnswerService;
import services.TeacherService;

@Controller
@RequestMapping("/answer/teacher")
public class AnswerTeacherController {
	
	
	// Services

		@Autowired
		private AnswerService	answerService;
		
		@Autowired
		private TeacherService	teacherService;
		
		// Constructors

		public AnswerTeacherController() {
			super();
		}
		
		
		//Change privacity
		@RequestMapping(value = "/solution", method = RequestMethod.GET)
		public ModelAndView changePrivacity(@RequestParam final int answerId, RedirectAttributes redir) {
			ModelAndView result;
			Teacher teacher;
			Answer answer = null;
			teacher = this.teacherService.findByPrincipal();
			Assert.notNull(teacher);
			
			try{
				answer = this.answerService.findOne(answerId);

			try {
				this.answerService.changeIsSolution(answerId);
				result = new ModelAndView("redirect:../../question/display.do?questionId="+answer.getQuestion().getId());
				String successfulMessage = "answer.commit.ok";
				redir.addFlashAttribute("message", successfulMessage);
			} catch (Throwable oops) {
				result = new ModelAndView("redirect:../../question/display.do?questionId="+answer.getQuestion().getId());
				String successfulMessage = "answer.commit.error";
				redir.addFlashAttribute("message", successfulMessage);
			}
			
			} catch (Throwable oops) {
				result = new ModelAndView("redirect:../course/list.do");
				String successfulMessage = "answer.commit.error";
				redir.addFlashAttribute("message", successfulMessage);
			}
			return result;
		}
		

}
