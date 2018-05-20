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
		final String formURL = "student/register.do";

		result = new ModelAndView("actor/register");
		result.addObject("actorForm", actorForm);
		result.addObject("message", message);
		result.addObject("formURL", formURL);
		
		return result;
	}
}