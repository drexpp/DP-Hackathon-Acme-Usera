package controllers.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.StudentService;
import controllers.AbstractController;
import domain.Student;
import forms.ActorForm;
import forms.EditActorForm;


@Controller
@RequestMapping("/student")
public class StudentRegisterController extends AbstractController {

	@Autowired
	private StudentService	studentService;


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
		Student student = this.studentService.create();
		student = this.studentService.reconstruct(actorForm, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(actorForm);
			result.addObject("permiso", true);
		} else
			try {
				this.studentService.save(student);
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
		Student principal;
		
		principal = this.studentService.findByPrincipal();
		
		result = new ModelAndView("actor/display");
		result.addObject("actor", principal);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView result;
		EditActorForm editActorForm;
		Student principal;
		
		editActorForm = new EditActorForm();
		principal = this.studentService.findByPrincipal();
			
		editActorForm = this.studentService.construct(editActorForm, principal);
		
		result = this.createEditModelAndView(editActorForm);
		
		return result;
		
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView edit(final EditActorForm editActorForm, BindingResult binding){
		ModelAndView result;
		Student student;
					
		student = this.studentService.reconstruct(editActorForm, binding);
		
		if(binding.hasErrors()){
			result = this.createEditModelAndView(editActorForm);
		}else{
			try{
				this.studentService.save(student);
				result = new ModelAndView("redirect:/student/display.do");
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
		Student principal;
		Boolean permiso;
		
		permiso = false;
		
		principal = this.studentService.findByPrincipal();
		
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
		final String formURL = "student/register.do";

		result = new ModelAndView("actor/register");
		result.addObject("actorForm", actorForm);
		result.addObject("message", message);
		result.addObject("formURL", formURL);
		
		return result;
	}
}