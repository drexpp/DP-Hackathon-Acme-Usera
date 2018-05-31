package controllers.student;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ExamPaperService;
import services.ExamService;
import services.StudentService;
import controllers.AbstractController;
import domain.Exam;
import domain.ExamPaper;
import domain.Student;
import forms.ExamPaperForm;

@Controller
@RequestMapping("/examPaper/student")
public class ExamPaperStudentController extends AbstractController{
	
	
	// Services

			@Autowired
			private ExamPaperService	examPaperService;
			
			@Autowired
			private ExamService			examService;
			
			@Autowired
			private StudentService		studentService;
			
		
			// Constructors

			public ExamPaperStudentController() {
				super();
			}
			
			
			//Create
			@RequestMapping(value = "/create", method = RequestMethod.GET)
			public ModelAndView create(@RequestParam final int examId, final RedirectAttributes redir) {
				ModelAndView result;
				Student principal;
				Exam exam = null;
				Boolean permission = true;;
				ExamPaper examPaper = new ExamPaper();
			try{
				exam = this.examService.findOne(examId);
				try {
					principal = this.studentService.findByPrincipal();
					Assert.notNull(principal);
					examPaper = this.examPaperService.create(examId);
					examPaper.setExam(exam);
					examPaper.setStudent(principal);
					examPaper = this.examPaperService.save(examPaper);
					int examPaperId = examPaper.getId();
					result = new ModelAndView("redirect:../../examPaper/display.do?examPaperId="+examPaperId);
					if(exam.getCourse().getIsClosed() == true){
						permission = false;
					}
					result.addObject("examPaperId", examPaperId);
					result.addObject("permission", permission);
				} catch (final Throwable oops) {
					result = new ModelAndView("redirect:../../exam/display.do?examId="+exam.getId());
					redir.addFlashAttribute("message", "examPaper.permission");

				}
				
			}catch (Throwable oops) {
				result = new ModelAndView("redirect:../../course/list.do");
				String successfulMessage = "examPaper.commit.error";
				redir.addFlashAttribute("message", successfulMessage);
			}
				this.examPaperService.flush();
				return result;
			}
			
			
			// Edition ----------------------------------------------------------------

			@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
			public ModelAndView save(final ExamPaperForm examPaperForm, final BindingResult binding) {
				ModelAndView result;
				ExamPaper examPaper = this.examPaperService.reconstruct(examPaperForm,binding);
				if (binding.hasErrors())
					result = this.createEditModelAndView(examPaperForm);
				else
					try {
						this.examPaperService.save(examPaper);
						result = new ModelAndView("redirect:../../exam/display.do?examId="+examPaper.getExam().getId());
					} catch (final Throwable oops) {
						String errorMessage = "examPaper.commit.error";
						result = this.createEditModelAndView(examPaperForm, errorMessage);
					}

				return result;
				}
				
		/*			
			@RequestMapping(value = "/edit", method = RequestMethod.GET)
			public ModelAndView edit(@RequestParam final int examPaperId, final RedirectAttributes redir) {
				ModelAndView result;
				ExamPaper examPaper;
				Boolean permission = true;
				QuestionForm questionForm;
				final Student principal = this.studentService.findByPrincipal();
				question = this.questionService.findOne(questionId);
				try {
					Assert.isTrue(question.getStudent().equals(principal));
					questionForm = this.questionService.reconstructForm(question);
					result = this.createEditModelAndView(questionForm);
					if(question.getForum().getCourse().getIsClosed() == true){
						permission = false;
					}
					result.addObject("permission", permission);
					} catch (final Throwable oops) {
					result = new ModelAndView("redirect:../../forum/display.do?forumId="+question.getForum().getId());
					redir.addFlashAttribute("message", "question.permision");
				}

					return result;

				}		
			*/
			
			
			private ModelAndView createEditModelAndView(ExamPaperForm examPaperForm) {
				ModelAndView result;
				result = this.createEditModelAndView(examPaperForm, null);
				return result;
			}

			private ModelAndView createEditModelAndView(ExamPaperForm examPaperForm,String message) {
				
				ModelAndView result;

				result = new ModelAndView("examPaper/edit");
				result.addObject("permission", true);
				result.addObject("examPaperForm", examPaperForm);
				result.addObject("message", message);

				return result;
				
				
			}

}
