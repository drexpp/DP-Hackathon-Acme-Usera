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
import domain.Answer;
import services.AdminService;
import services.AnswerService;

@Controller
@RequestMapping("/answer/admin")
public class AnswerAdminController {
	
	
	// Services

		@Autowired
		private AnswerService	answerService;
	
		@Autowired
		private AdminService	adminService;
		
		// Constructors

		public AnswerAdminController() {
			super();
		}
		
		
		// Delete ----------------------------------------------------------------

	
		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam final int questionId, RedirectAttributes redir) {
		ModelAndView result;
		Answer answer;
		Admin principal;
		principal = this.adminService.findByPrincipal();
		Assert.notNull(principal);
		answer = this.answerService.findOne(questionId);
		Assert.notNull(answer);

		try {
			this.answerService.deleteByAdmin(answer);
			result = new ModelAndView("redirect:../../question/display.do?questionId="+answer.getQuestion().getId());
			String successfulMessage = "question.commit.ok";
			redir.addFlashAttribute("message", successfulMessage);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:../../question/display.do?questionId="+answer.getQuestion().getId());
			String errorMessage = "question.commit.error";
			redir.addFlashAttribute("message", errorMessage);
		}

		return result;
	}

}
