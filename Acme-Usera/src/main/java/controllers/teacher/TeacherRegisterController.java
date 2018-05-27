package controllers.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import services.ContactInfoService;
import services.TeacherService;

import controllers.AbstractController;
import domain.ContactInfo;
import domain.Teacher;
import forms.ActorFormTeacher;
import forms.EditActorForm;


@Controller
@RequestMapping("/teacher")
public class TeacherRegisterController extends AbstractController {

	@Autowired
	private TeacherService	teacherService;
	
	@Autowired
	private ContactInfoService		contactInfoService;


	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final ActorFormTeacher actorFormTeacher = new ActorFormTeacher();
		result = this.createEditModelAndView(actorFormTeacher);
		result.addObject("permiso", true);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ActorFormTeacher actorFormTeacher, final BindingResult binding) {
		ModelAndView result;
		ContactInfo contactInfo;
		Teacher teacher;
		teacher = this.teacherService.create();
		teacher = this.teacherService.reconstruct(actorFormTeacher, binding);
		contactInfo = this.contactInfoService.create();
		contactInfo = this.contactInfoService.reconstruct(actorFormTeacher, binding);
		
		
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(actorFormTeacher);
			result.addObject("permiso", true);
		} else
			try {
				this.teacherService.save(teacher);
				this.contactInfoService.save(contactInfo);
				result = new ModelAndView("redirect:../");
			} catch (final DataIntegrityViolationException oops) {
				binding.rejectValue("userAccount.username", "actor.username.error");
				result = this.createEditModelAndView(actorFormTeacher);
				result.addObject("permiso", true);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(actorFormTeacher, "actor.commit.error");
			}
		return result;
	}
	
	
	@RequestMapping(value="/display", method = RequestMethod.GET)
	public ModelAndView display(){
		ModelAndView result;
		Teacher principal;
		
		principal = this.teacherService.findByPrincipal();
		
		result = new ModelAndView("actor/display");
		result.addObject("actor", principal);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView result;
		EditActorForm editActorForm;
		Teacher principal;
		
		editActorForm = new EditActorForm();
		principal = this.teacherService.findByPrincipal();
			
		editActorForm = this.teacherService.construct(editActorForm, principal);
		
		result = this.createEditModelAndView(editActorForm);
		
		return result;
		
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView edit(final EditActorForm editActorForm, BindingResult binding){
		ModelAndView result;
		Teacher teacher;
		
		if(!editActorForm.getName().isEmpty() && !editActorForm.getSurname().isEmpty() && !editActorForm.getEmail().isEmpty())
			teacher = this.teacherService.reconstruct(editActorForm, binding);
		else{
			result = this.createEditModelAndView(editActorForm, "actor.commit.error");
			return result;
		}
			
		
		teacher = this.teacherService.reconstruct(editActorForm, binding);
		
		if(binding.hasErrors()){
			result = this.createEditModelAndView(editActorForm);
		}else{
			try{
				this.teacherService.save(teacher);
				result = new ModelAndView("redirect:/teacher/display.do");
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
		Teacher principal;
		Boolean permiso;
		
		permiso = false;
		
		principal = this.teacherService.findByPrincipal();
		
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


	protected ModelAndView createEditModelAndView(final ActorFormTeacher actorFormTeacher) {
		ModelAndView result;

		result = this.createEditModelAndView(actorFormTeacher, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ActorFormTeacher actorFormTeacher, final String message) {
		final ModelAndView result;
		final String formURL = "teacher/register.do";

		result = new ModelAndView("actor/registerTeacher");
		result.addObject("actorForm", actorFormTeacher);
		result.addObject("message", message);
		result.addObject("formURL", formURL);
		return result;
	}
}