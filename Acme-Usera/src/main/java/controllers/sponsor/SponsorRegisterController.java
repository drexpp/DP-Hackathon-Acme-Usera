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
import forms.EditActorForm;


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
				binding.rejectValue("userAccount.username", "actor.username.error");
				result = this.createEditModelAndView(actorForm);
				result.addObject("permiso", true);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(actorForm, "actor.commit.error");
			}
		return result;
	}
	
	
	@RequestMapping(value="/display", method = RequestMethod.GET)
	public ModelAndView display(){
		ModelAndView result;
		Sponsor principal;
		
		principal = this.sponsorService.findByPrincipal();
		
		result = new ModelAndView("actor/display");
		result.addObject("actor", principal);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView result;
		EditActorForm editActorForm;
		Sponsor principal;
		
		editActorForm = new EditActorForm();
		principal = this.sponsorService.findByPrincipal();
			
		editActorForm = this.sponsorService.construct(editActorForm, principal);
		
		result = this.createEditModelAndView(editActorForm);
		
		return result;
		
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView edit(final EditActorForm editActorForm, BindingResult binding){
		ModelAndView result;
		Sponsor sponsor;
		
		
		sponsor = this.sponsorService.reconstruct(editActorForm, binding);
		
		if(binding.hasErrors()){
			result = this.createEditModelAndView(editActorForm);
		}else{
			try{
				this.sponsorService.save(sponsor);
				result = new ModelAndView("redirect:/sponsor/display.do");
			}catch (Throwable oops){
				result = this.createEditModelAndView(editActorForm);
			}
		}
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(EditActorForm editActorForm) {
		ModelAndView result;
		
		result = this.createEditModelAndView(editActorForm, null);
		
		return result;
	}

	protected ModelAndView createEditModelAndView(EditActorForm editActorForm,
			String message) {
		ModelAndView result;
		String requestURI;
		Sponsor principal;
		Boolean permiso;
		
		permiso = false;
		
		principal = this.sponsorService.findByPrincipal();
		
		if(principal.getId() == editActorForm.getId()){
			permiso = true;
		}
		
		requestURI = "student/student/editProfile.do";
		
		result = new ModelAndView("actor/edit");
		result.addObject("editActorForm", editActorForm);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);
		result.addObject("permiso", permiso);
		
		
		return result;
	}

	protected ModelAndView createEditModelAndView(final ActorForm actorForm) {
		ModelAndView result;

		result = this.createEditModelAndView(actorForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ActorForm actorForm, final String message) {
		final ModelAndView result;
		final String formURL = "sponsor/register.do";

		result = new ModelAndView("actor/register");
		result.addObject("actorForm", actorForm);
		result.addObject("message", message);
		result.addObject("formURL", formURL);
		return result;
	}
}