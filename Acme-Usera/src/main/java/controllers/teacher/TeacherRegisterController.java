package controllers.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ContactInfoService;
import services.TeacherService;
import controllers.AbstractController;
import domain.ContactInfo;
import domain.Teacher;
import forms.ActorFormTeacher;
import forms.EditActorTeacherForm;


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
				Teacher resultTeacher;
				resultTeacher = this.teacherService.save(teacher);
				this.contactInfoService.save(contactInfo, resultTeacher);
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
	public ModelAndView display(final Integer teacherId,RedirectAttributes redir){
		ModelAndView result;
		Teacher teacher;
		try{
		teacher = this.teacherService.findOne(teacherId);
		Assert.notNull(teacher);
		result = new ModelAndView("actor/displayTeacher");
		result.addObject("teacher", teacher);
		} catch(Throwable oops){
		result = new ModelAndView("redirect:/");
		redir.addFlashAttribute("message", "lesson.permision");
		}
		
		return result;
	}
	
	
	
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView result;
		EditActorTeacherForm editActorTeacherForm;
		Teacher principal;
		
		
		editActorTeacherForm = new EditActorTeacherForm();
		principal = this.teacherService.findByPrincipal();
			
		editActorTeacherForm = this.teacherService.construct(editActorTeacherForm, principal);
		
		result = this.createEditModelAndView(editActorTeacherForm);
		
		
		return result;
		
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView edit(final EditActorTeacherForm editActorTeacherForm, BindingResult binding){
		ModelAndView result;
		Teacher teacher;
				
		teacher = this.teacherService.reconstruct(editActorTeacherForm, binding);
		
		if(binding.hasErrors()){
			result = this.createEditModelAndView(editActorTeacherForm);
		}else{
			try{
				this.teacherService.save(teacher);
				result = new ModelAndView("redirect:/teacher/teacher/display.do");
			}catch (Throwable oops){
				result = this.createEditModelAndView(editActorTeacherForm, "actor.commit.error");
			}
		}
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(EditActorTeacherForm editActorTeacherForm) {
		ModelAndView result;
		
		result = this.createEditModelAndView(editActorTeacherForm, null);
		
		return result;
	}

	protected ModelAndView createEditModelAndView(EditActorTeacherForm editActorTeacherForm,
			String message) {
		ModelAndView result;
		String requestURI;
		Teacher principal;
		Boolean permiso;
		Integer elementsLengthComments;
		Integer elementsLengthLinks;
		
		permiso = false;
		if(editActorTeacherForm.getComments().size() == 0)
			elementsLengthComments = 0;
		else
			elementsLengthComments = editActorTeacherForm.getComments().size()-1;
		
		if(editActorTeacherForm.getLinks().size() == 0)
			elementsLengthLinks = 0;
		else
			elementsLengthLinks = editActorTeacherForm.getLinks().size()-1;
		
		
		principal = this.teacherService.findByPrincipal();
		
		if(principal.getId() == editActorTeacherForm.getId()){
			permiso = true;
		}
		
		requestURI = "student/student/editProfile.do";
		
		result = new ModelAndView("actor/editTeacher");
		result.addObject("editActorTeacherForm", editActorTeacherForm);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);
		result.addObject("permiso", permiso);
		result.addObject("elementsLengthComments", elementsLengthComments);
		result.addObject("elementsLengthLinks", elementsLengthLinks);
		
		
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
		result.addObject("actorFormTeacher", actorFormTeacher);
		result.addObject("message", message);
		result.addObject("formURL", formURL);
		return result;
	}
}