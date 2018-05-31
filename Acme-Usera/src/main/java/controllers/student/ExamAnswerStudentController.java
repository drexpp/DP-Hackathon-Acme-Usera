package controllers.student;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.Actor;
import domain.Answer;
import domain.ExamAnswer;
import domain.ExamPaper;
import domain.Question;
import domain.Student;
import forms.AnswerForm;
import forms.ExamAnswerForm;
import services.ActorService;
import services.AnswerService;
import services.ExamAnswerService;
import services.ExamPaperService;
import services.QuestionService;
import services.StudentService;
import services.TeacherService;

@Controller
@RequestMapping("/examAnswer/student")
public class ExamAnswerStudentController {
	
	
	// Services

		@Autowired
		private ExamAnswerService	examAnswerService;
		
		@Autowired
		private ExamPaperService	examPaperService;
		
		@Autowired
		private StudentService	studentService;
		
		@Autowired
		private TeacherService	teacherService;
		
		@Autowired
		private ActorService	actorService;
		
		// Constructors

		public ExamAnswerStudentController() {
			super();
		}
		
		
		//Create
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create(@RequestParam final int examPaperId, final RedirectAttributes redir) {
			ModelAndView result;
			Boolean permission = true;
			ExamPaper examPaper = null;
			ExamAnswerForm examAnswer = new ExamAnswerForm();
			try{
				examPaper = this.examPaperService.findOne(examPaperId);
				try{
					Assert.isTrue(!((examPaper.getExamAnswer().size())==(examPaper.getExam().getExamQuestions().size()))); //Si quiero crear respuestas, que no se hayan respondido todas las preguntas.
					examAnswer.setExamPaper(examPaper);
					result = this.createEditModelAndView(examAnswer);
					if(examPaper.getExam().getCourse().getIsClosed() == true){
						permission = false;
					}
					result.addObject("permission", permission);
				} catch (final Throwable oops) {
					result = new ModelAndView("redirect:../../examPaper/display.do?examPaperId=" + examPaper.getId());
					redir.addFlashAttribute("message", "answer.permission");
				}
			}catch (Throwable oops) {
				result = new ModelAndView("redirect:../../course/list.do");
				String successfulMessage = "answer.commit.error";
				redir.addFlashAttribute("message", successfulMessage);
			}
			return result;
		}
		
		// Edition ----------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(final ExamAnswerForm examAnswerForm, final BindingResult binding) {
			ModelAndView result;
			ExamAnswer examAnswer = this.examAnswerService.reconstruct(examAnswerForm,binding);
			if (binding.hasErrors())
				result = this.createEditModelAndView(examAnswerForm);
			else
				try {
					this.examAnswerService.save(examAnswer);
					result = new ModelAndView("redirect:../../examPaper/display.do?examPaperId="+examAnswer.getExamPaper().getId());
				} catch (final Throwable oops) {
					String errorMessage = "examAnswer.commit.error";
					result = this.createEditModelAndView(examAnswerForm, errorMessage);
				}

			return result;
			}
			
				
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam final int examAnswerId, final RedirectAttributes redir) {
			ModelAndView result;
			ExamAnswer examAnswer;
			Boolean permission = true;
			ExamAnswerForm examAnswerForm;
			final Student principal = this.studentService.findByPrincipal();
			
		try{
			examAnswer = this.examAnswerService.findOne(examAnswerId);
		
			try {
				examAnswerForm = this.examAnswerService.reconstructForm(examAnswer);
				result = this.createEditModelAndView(examAnswerForm);
				if(examAnswer.getExamPaper().getExam().getCourse().getIsClosed() == true){
					permission = false;
				}
				result.addObject("permission", permission);
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:../../examPaper/display.do?examPaperId="+examAnswer.getExamPaper().getId());
				redir.addFlashAttribute("message", "examAnswer.permission");

			}
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:../../course/list.do");
			String errorMessage = "examAnswer.commit.error";
			redir.addFlashAttribute("message", errorMessage);
		}

			return result;

		}		
		
		

		private ModelAndView createEditModelAndView(ExamAnswerForm examAnswerForm) {
			ModelAndView result;
			result = this.createEditModelAndView(examAnswerForm, null);
			return result;
		}

		private ModelAndView createEditModelAndView(ExamAnswerForm examAnswerForm,String message) {
			
			ModelAndView result;

			result = new ModelAndView("examAnswer/edit");
			result.addObject("permission", true);
			result.addObject("examAnswerForm", examAnswerForm);
			result.addObject("message", message);

			return result;
			
			
		}
		

}
