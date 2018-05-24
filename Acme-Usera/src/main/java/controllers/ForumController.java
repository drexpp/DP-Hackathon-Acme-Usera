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
import domain.Forum;
import domain.Question;
import services.ActorService;
import services.ForumService;

@Controller
@RequestMapping("/forum")
public class ForumController {
	
	
	// Services

		@Autowired
		private ForumService	forumService;
		
		@Autowired
		private ActorService	actorService;
		
		// Constructors

		public ForumController() {
			super();
		}

		
		//Display
		@RequestMapping(value = "/display", method = RequestMethod.GET)
		public ModelAndView display(@RequestParam final int forumId, RedirectAttributes redir) {
			ModelAndView result;
			Forum forum;
			Collection<Question> questions;
			Actor principal;

			try{
				forum = this.forumService.findOne(forumId);
				questions = forum.getQuestions();
				principal = this.actorService.findByPrincipal();

				result = new ModelAndView("forum/display");
				result.addObject("questions", questions);
				result.addObject("forum", forum);
				result.addObject("principal", principal);
				}catch (Throwable oops){
					result = new ModelAndView("redirect:/course/list.do");	
					redir.addFlashAttribute("message", "forum.permission"); 
			}
			return result;
		}
		

}
