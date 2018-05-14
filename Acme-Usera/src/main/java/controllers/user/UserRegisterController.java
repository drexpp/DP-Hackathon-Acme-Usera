
package controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.UserService;
import domain.Actor;
import domain.User;
import forms.ActorForm;

@Controller
@RequestMapping("/user")
public class UserRegisterController {

	@Autowired
	private UserService	userService;


	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Actor principal = null;
		Boolean permiso;
		final ActorForm actorForm = new ActorForm();
		result = this.createEditModelAndView(actorForm);
		try {
			principal = this.userService.findByPrincipal();
		} catch (final RuntimeException oops) {
		}
		if (principal == null) {
			permiso = true;
			result.addObject("permiso", permiso);
		} else {
			permiso = false;
			result.addObject("permiso", null);
		}
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ActorForm actorForm, final BindingResult binding) {
		ModelAndView result;
		User user = new User();
		user = this.userService.reconstruct(actorForm, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(actorForm);
			result.addObject("permiso", true);
		} else
			try {
				this.userService.save(user);
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

		result = new ModelAndView("user/register");
		result.addObject("actorForm", actorForm);
		result.addObject("message", message);
		return result;
	}
}
