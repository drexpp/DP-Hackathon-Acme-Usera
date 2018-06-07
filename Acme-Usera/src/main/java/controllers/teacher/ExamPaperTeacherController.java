package controllers.teacher;


import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.TeacherService;
import controllers.AbstractController;
import domain.Exam;
import domain.ExamPaper;
import domain.Teacher;

@Controller
@RequestMapping("/examPaper/teacher")
public class ExamPaperTeacherController extends AbstractController{
	
	
	// Services
			
			@Autowired
			private TeacherService		teacherService;
			
		
			// Constructors

			public ExamPaperTeacherController() {
				super();
			}
			
			// Listing

			@RequestMapping(value = "/list", method = RequestMethod.GET)
			public ModelAndView list() {
				final ModelAndView result;
				Boolean permission = true;
				final Collection<ExamPaper> examPapers = new ArrayList<>();
				Teacher principal = this.teacherService.findByPrincipal();
						
				for(Exam exam: principal.getExams()) {
					if(exam.getCourse().getIsClosed() == false){
						for(ExamPaper examPaper : exam.getExamPaper())
							if (examPaper.getIsFinished() == true){
						examPapers.add(examPaper); //Listar los examPapers guardados en el sistema que no pertenecen a un curso cerrado.
							}
					}
				}
				
				
						
				result = new ModelAndView("examPaper/list");
				result.addObject("examPapers", examPapers);
				result.addObject("permission", permission);

				if(principal != null){
					result.addObject("principal",principal);
				}

				return result;

			}

}
