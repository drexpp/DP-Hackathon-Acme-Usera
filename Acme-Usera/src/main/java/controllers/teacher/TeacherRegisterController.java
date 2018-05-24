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
				binding.rejectValue("userAccount.username", "user.username.error");
				result = this.createEditModelAndView(actorFormTeacher);
				result.addObject("permiso", true);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(actorFormTeacher, "user.commit.error");
			}
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