package controllers.teacher;


import java.util.Collection;

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

import services.ExamAnswerService;
import services.ExamPaperService;
import services.ExamQuestionService;
import services.TeacherService;
import controllers.AbstractController;
import domain.ExamAnswer;
import domain.ExamPaper;
import domain.ExamQuestion;
import domain.Teacher;

@Controller
@RequestMapping("/examAnswer/teacher")
public class ExamAnswerTeacherController extends AbstractController{
	
	
	// Services

			@Autowired
			private ExamPaperService	examPaperService;
			
			@Autowired
			private ExamQuestionService			examQuestionService;
			
			@Autowired
			private ExamAnswerService	examAnswerService;
			
			@Autowired
			private TeacherService		teacherService;
			
		
			// Constructors

			public ExamAnswerTeacherController() {
				super();
			}
			
			//Display
			@RequestMapping(value = "/evaluate", method = RequestMethod.GET)
			public ModelAndView evaluate(@RequestParam final int examQuestionId, final int examPaperId, RedirectAttributes redir) {
				ModelAndView result = new ModelAndView();
				ExamPaper examPaper;
				ExamAnswer examAnswer;
				ExamQuestion examQuestion;
				Collection<ExamQuestion> examQuestions;
				Teacher principal;
				
				try{
					examPaper = this.examPaperService.findOne(examPaperId);
					Assert.isTrue(examPaper.getIsFinished() == true);
					examQuestion = this.examQuestionService.findOne(examQuestionId);
					examAnswer = this.examAnswerService.findExamAnswerByNumbers(examQuestion.getNumber(), examPaperId);
					principal = this.teacherService.findByPrincipal();
					examQuestions = examPaper.getExam().getExamQuestions();		
					Assert.isTrue(principal.getCoursesJoined().contains(examAnswer.getExamPaper().getExam().getCourse()));
					
					result = new ModelAndView("examAnswer/display");
					
					result.addObject("examAnswer", examAnswer);
					result.addObject("examPaper", examPaper);
					result.addObject("principal", principal);
					result.addObject("examQuestions", examQuestions);
					
				}catch (Throwable oops){
					result = new ModelAndView("redirect:/examPaper/teacher/list.do");	
					redir.addFlashAttribute("message", "examPaper.permission"); 
				}
					
				return result;

			}

			// Edition
			@RequestMapping(value = "/edit", method = RequestMethod.GET)
			public ModelAndView edit(@RequestParam final int examAnswerId, RedirectAttributes redir) {
				ModelAndView result;
				ExamAnswer examAnswer;
				try{
				examAnswer = this.examAnswerService.findOne(examAnswerId);
				Assert.isTrue(examAnswer.getExamPaper().getIsFinished() == true);
				Assert.notNull(examAnswer);
				result = this.createEditModelAndView(examAnswer);
				} catch (Throwable oops){
					result = new ModelAndView("redirect:/examPaper/teacher/list.do");	
					redir.addFlashAttribute("message", "examPaper.permission"); 
				}

				return result;
			}

			@RequestMapping(value = "/edit", method = RequestMethod.POST)
			public ModelAndView save(@Valid final ExamAnswer examAnswer, final BindingResult binding) {
				ModelAndView result;

				if (binding.hasErrors())
					result = this.createEditModelAndView(examAnswer);
				else
					try {
						this.examAnswerService.save(examAnswer);
						result = new ModelAndView("redirect:../../examAnswer/display.do?examAnswerId="+ examAnswer.getId());
					} catch (final Throwable oops) {
						result = this.createEditModelAndView(examAnswer, "examAnswer.commit.error");
					}
				return result;
			}

			//Ancillary methods
			protected ModelAndView createEditModelAndView(final ExamAnswer examAnswer) {
				ModelAndView result;

				result = this.createEditModelAndView(examAnswer, null);

				return result;
			}

			protected ModelAndView createEditModelAndView(final ExamAnswer examAnswer, final String messageCode) {
				final ModelAndView result;
				Teacher principal;

				principal = this.teacherService.findByPrincipal();
				Assert.isTrue(principal.getCoursesJoined().contains(examAnswer.getExamPaper().getExam().getCourse()));

				result = new ModelAndView("examAnswer/edit");
				result.addObject("examAnswer", examAnswer);
				result.addObject("principal", principal);

				result.addObject("message", messageCode);

				return result;

			}

}
