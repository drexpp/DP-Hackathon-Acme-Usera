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

import services.CourseService;
import services.ForumService;
import services.QuestionService;
import services.StudentService;
import domain.Course;
import domain.Forum;
import domain.Question;
import domain.Student;
import forms.QuestionForm;

@Controller
@RequestMapping("/question/student")
public class QuestionStudentController {
	
	
	// Services

		@Autowired
		private QuestionService	questionService;
		
		@Autowired
		private ForumService	forumService;
		
		@Autowired
		private CourseService	courseService;
		
		@Autowired
		private StudentService	studentService;
		
		// Constructors

		public QuestionStudentController() {
			super();
		}
		
		
		// Listing

		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list() {
			final ModelAndView result;
			Boolean permission = true;
			final Collection<Question> questions = new ArrayList<>();
			Student principal = this.studentService.findByPrincipal();
			
			for(Question question: principal.getQuestions()) {
				if(question.getForum().getCourse().getIsClosed() == false){
					questions.add(question); //Listar las respuestas del student.
				}
			}
			
			result = new ModelAndView("question/list");
			result.addObject("questions", questions);
			result.addObject("permission", permission);

			if(principal != null){
				result.addObject("principal",principal);
			}

			return result;

		}
		
		//Create
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create(@RequestParam final int forumId, final RedirectAttributes redir) {
			ModelAndView result;
			Student principal;
			Forum forum = null;
			Boolean permission = true;;
			QuestionForm question = new QuestionForm();
		try{
			forum = this.forumService.findOne(forumId);
			try {
				principal = this.studentService.findByPrincipal();
				Assert.notNull(principal);
				Collection<Course> subs = this.courseService.findCoursesStandardAndPremium(principal.getId());
				Assert.isTrue(subs.contains(forum.getCourse()));
				question.setForum(forum);
				result = this.createEditModelAndView(question);
				if(forum.getCourse().getIsClosed() == true){
					permission = false;
				}
				result.addObject("permission", permission);
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:../../forum/display.do?forumId="+forum.getId());
				redir.addFlashAttribute("message", "question.permision");

			}
			
		}catch (Throwable oops) {
			result = new ModelAndView("redirect:../../course/list.do");
			String successfulMessage = "question.commit.error";
			redir.addFlashAttribute("message", successfulMessage);
		}
			return result;
		}
		
		// Edition ----------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(final QuestionForm questionForm, final BindingResult binding) {
			ModelAndView result;
			Question question = this.questionService.reconstruct(questionForm,binding);
			if (binding.hasErrors())
				result = this.createEditModelAndView(questionForm);
			else
				try {
					this.questionService.save(question);
					result = new ModelAndView("redirect:../../forum/display.do?forumId="+question.getForum().getId());
				} catch (final Throwable oops) {
					String errorMessage = "question.commit.error";
					result = this.createEditModelAndView(questionForm, errorMessage);
				}

			return result;
			}
			
				
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam final int questionId, final RedirectAttributes redir) {
			ModelAndView result;
			Question question;
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
		
		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam final int questionId, RedirectAttributes redir) {
		ModelAndView result;
		Question question;
		Student principal = this.studentService.findByPrincipal();
		
		question = this.questionService.findOne(questionId);	

		try {
			Assert.isTrue(question.getStudent().getId()==principal.getId());
			this.questionService.delete(question);
			result = new ModelAndView("redirect:../../forum/display.do?forumId="+question.getForum().getId());
			String successfulMessage = "question.commit.ok";
			redir.addFlashAttribute("message", successfulMessage);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:../../forum/display.do?forumId="+question.getForum().getId());
			String errorMessage = "question.commit.error";
			redir.addFlashAttribute("message", errorMessage);
		}

		return result;
	}

		private ModelAndView createEditModelAndView(QuestionForm questionForm) {
			ModelAndView result;
			result = this.createEditModelAndView(questionForm, null);
			return result;
		}

		private ModelAndView createEditModelAndView(QuestionForm questionForm,String message) {
			
			ModelAndView result;

			result = new ModelAndView("question/edit");
			result.addObject("permission", true);
			result.addObject("questionForm", questionForm);
			result.addObject("message", message);

			return result;
			
			
		}
		

}
