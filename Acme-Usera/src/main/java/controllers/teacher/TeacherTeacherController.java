
package controllers.teacher;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TeacherService;
import controllers.AbstractController;
import domain.Actor;
import domain.Teacher;

@Controller
@RequestMapping("/teacher/teacher")
public class TeacherTeacherController extends AbstractController {

	// Services

	@Autowired
	private TeacherService			teacherService;

	// Constructors

	public TeacherTeacherController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int teacherId) {
		ModelAndView result;
		Teacher teacher;

		teacher = this.teacherService.findOne(teacherId);
		Assert.notNull(teacher);

		result = this.createEditModelAndView(teacher);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Teacher teacher, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(teacher);
		else
			try {
				Teacher saved;
				Actor actor;
				saved = this.teacherService.save(teacher);

				result = new ModelAndView("actor/display");
				result.addObject("teacher", saved);
				actor = saved;
				result.addObject("actor", actor);

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(teacher, "teacher.commit.error");

			}
		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Teacher teacher) {
		ModelAndView result;

		result = this.createEditModelAndView(teacher, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Teacher teacher, final String message) {
		ModelAndView result;
		boolean permission = false;
		Teacher principal;

		principal = this.teacherService.findByPrincipal();

		if (principal.getId() == teacher.getId())
			permission = true;

		result = new ModelAndView("teacher/edit");

		result.addObject("teacher", teacher);
		result.addObject("actionURI", "teacher/teacher/edit.do");
		result.addObject("redirectURI", "actor/display.do");
		result.addObject("permission", permission);
		result.addObject("message", message);

		return result;
	}

}
