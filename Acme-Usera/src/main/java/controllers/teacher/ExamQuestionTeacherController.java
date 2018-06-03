
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ExamQuestionService;
import services.ExamService;
import services.TeacherService;
import controllers.AbstractController;
import domain.Exam;
import domain.ExamQuestion;
import domain.Teacher;

@Controller
@RequestMapping("/examQuestion/teacher")
public class ExamQuestionTeacherController extends AbstractController {

	// Services

	@Autowired
	private ExamQuestionService		examQuestionService;
	
	@Autowired
	private ExamService		examService;

	@Autowired
	private TeacherService			teacherService;

	// Constructors

	public ExamQuestionTeacherController() {
		super();
	}

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam Integer examId, RedirectAttributes redir) {
		ModelAndView result;
		ExamQuestion examQuestion;
		Teacher principal = this.teacherService.findByPrincipal();
		examQuestion = this.examQuestionService.create();

		try{
			Exam exam = this.examService.findOne(examId);
			Assert.isTrue(!exam.getCourse().getIsClosed());
			Assert.isTrue(principal.getCoursesJoined().contains(exam.getCourse()));
			examQuestion.setExam(exam);
			result = this.createEditModelAndView(examQuestion);

		}catch(Throwable oops){
			result = new ModelAndView("redirect:/course/list.do");	
			redir.addFlashAttribute("message", "examQuestion.permision"); 
		}

		return result;

	}
	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int examQuestionId, RedirectAttributes redir) {
		ModelAndView result;
		ExamQuestion examQuestion;
		try{
			examQuestion = this.examQuestionService.findOne(examQuestionId);
			Assert.notNull(examQuestion);
			result = this.createEditModelAndView(examQuestion);
		} catch (Throwable oops){
			result = new ModelAndView("redirect:/course/list.do");	
			redir.addFlashAttribute("message", "examQuestion.permision");
		}


		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@Valid final ExamQuestion examQuestion, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(examQuestion);
		else
			try {
				this.examQuestionService.save(examQuestion);
				result = new ModelAndView("redirect:../../exam/display.do?examId="+ examQuestion.getExam().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(examQuestion, "examQuestion.commit.error");
			}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final ExamQuestion examQuestion , RedirectAttributes redir){
		ModelAndView result;
		ExamQuestion examquestion;
		Teacher principal;
		
		principal = this.teacherService.findByPrincipal();
		examquestion = this.examQuestionService.findOne(examQuestion.getId());
		
		if(examquestion.getExam().getTeacher() != principal){
			result = new ModelAndView("redirect:../../exam/display.do?examId="+ examQuestion.getExam().getId());
			redir.addFlashAttribute("message", "examQuestion.permision"); 
		}else{
			try{
				this.examQuestionService.delete(examquestion);
				result = new ModelAndView("redirect:../../exam/display.do?examId="+ examQuestion.getExam().getId());
			}catch(Throwable oops){
				String message = "service.commit.error";
				result = this.createEditModelAndView(examQuestion, message);
			}
		}
		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final ExamQuestion examQuestion) {
		ModelAndView result;

		result = this.createEditModelAndView(examQuestion, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ExamQuestion examQuestion, final String messageCode) {
		final ModelAndView result;
		Teacher principal;

		principal = this.teacherService.findByPrincipal();
		Assert.isTrue(principal.getCoursesJoined().contains(examQuestion.getExam().getCourse()));

		result = new ModelAndView("examQuestion/edit");
		result.addObject("examQuestion", examQuestion);
		result.addObject("principal", principal);

		result.addObject("message", messageCode);

		return result;

	}

}
