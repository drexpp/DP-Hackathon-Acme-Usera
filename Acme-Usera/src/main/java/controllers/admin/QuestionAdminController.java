package controllers.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.Admin;
import domain.Question;
import services.AdminService;
import services.QuestionService;

@Controller
@RequestMapping("/question/admin")
public class QuestionAdminController {
	
	
	// Services

		@Autowired
		private QuestionService	questionService;
	
		@Autowired
		private AdminService	adminService;
		
		// Constructors

		public QuestionAdminController() {
			super();
		}
		
		
		// Delete ----------------------------------------------------------------

	
		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam final int questionId, RedirectAttributes redir) {
		ModelAndView result;
		Question question;
		Admin principal;
		principal = this.adminService.findByPrincipal();
		Assert.notNull(principal);
		question = this.questionService.findOne(questionId);
		Assert.notNull(question);

		try {
			this.questionService.deleteByAdmin(question);
			result = new ModelAndView("redirect:../../forum/display.do?forumId="+question.getForum().getId());
			String successfulMessage = "question.commit.ok";
			redir.addFlashAttribute("message", successfulMessage);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:../../forum/display.do?forumId="+question.getForum().getId());
			String errorMessage = "question.commit.error";
			redir.addFlashAttribute("message", errorMessage);
		}

		return result;
	}

}
