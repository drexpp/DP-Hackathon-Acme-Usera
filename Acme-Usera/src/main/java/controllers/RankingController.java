package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Student;

import services.StudentService;

@Controller
@RequestMapping("/ranking")
public class RankingController extends AbstractController {

	@Autowired
	StudentService			studentService;
	
	
	@RequestMapping(value="/list", method = RequestMethod.GET)		
	public ModelAndView list(){
		ModelAndView result;
		Collection<Student> students;
		
		students = this.studentService.findStudentsRankedByPoints();
		
		result = new ModelAndView("ranking/list");
		result.addObject("students", students);
		
		return result;
	}
	
}
