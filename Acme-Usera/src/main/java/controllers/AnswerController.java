package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AnswerService;
import domain.Actor;
import domain.Answer;

@Controller
@RequestMapping("/answer")
public class AnswerController {
	
	
	// Services

		@Autowired
		private AnswerService	answerService;
		
		@Autowired
		private ActorService	actorService;
		
		// Constructors

		public AnswerController() {
			super();
		}
		
		
		// Listing

		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list() {
			final ModelAndView result;
			final Collection<Answer> answers;
			Actor principal = this.actorService.findByPrincipal();
			
			answers = this.answerService.findAll();
			
			result = new ModelAndView("answer/list");
			result.addObject("answers", answers);
			if(principal != null){
				result.addObject("principal",principal);
			}

			return result;

		}

}
