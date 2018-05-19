package controllers.sponsor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import services.SponsorService;

import controllers.AbstractController;
import domain.Sponsor;
import forms.ActorForm;


@Controller
@RequestMapping("/sponsor")
public class SponsorRegisterController extends AbstractController {

	@Autowired
	private SponsorService	sponsorService;


	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final ActorForm actorForm = new ActorForm();
		result = this.createEditModelAndView(actorForm);
		result.addObject("permiso", true);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ActorForm actorForm, final BindingResult binding) {
		ModelAndView result;
		Sponsor sponsor;
		
		sponsor = this.sponsorService.create();
		sponsor = this.sponsorService.reconstruct(actorForm, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(actorForm);
			result.addObject("permiso", true);
		} else
			try {
				this.sponsorService.save(sponsor);
				result = new ModelAndView("redirect:../");
			} catch (final DataIntegrityViolationException oops) {
				binding.rejectValue("userAccount.username", "user.username.error");
				result = this.createEditModelAndView(actorForm);
				result.addObject("permiso", true);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(actorForm, "user.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final ActorForm actorForm) {
		ModelAndView result;

		result = this.createEditModelAndView(actorForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ActorForm actorForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("sponsor/register");
		result.addObject("actorForm", actorForm);
		result.addObject("message", message);
		return result;
	}
}