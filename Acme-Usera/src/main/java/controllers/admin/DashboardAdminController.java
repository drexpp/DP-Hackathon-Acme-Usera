package controllers.admin;



import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdminService;

import controllers.AbstractController;
import domain.Course;

@Controller
@RequestMapping("dashboard/admin")
public class DashboardAdminController extends AbstractController {

	
	//Autowired
	@Autowired
	AdminService 		adminService;
	

	
	//Constructor
	public DashboardAdminController(){
		super();
	}
	
	//Save
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		
		Double AverageCoursesPerTeacher,StandardDesviationCoursesPerTeacher, AverageLessonsPerCourse, StandardDesviationLessonPerCourse, ratioOfCoursesPerCategory,
		AverageAnswersPerStudent,StandardDesviationAnswersPerStudent, AverageQuestionsPerCourse, StandardDesviationQuestionsPerCourse, RatioLessonsPerCourse,
		StandardDesviationCoursesClosed, MinScoreStudent, MaxScoreStudent, AvgScoreStudent, StandardDesviationScoreStudent, 
		MinTutorialsTeacher, MaxTutorialsTeacher, AvgTutorialsTeacher, StandardDesviationTutorialsTeacher,RatioStudentsWithScore0;
		
		Collection<Course> top3CoursesWithMoreSubscriptions, top3CoursesWithMoreQuestions, top3TeachersWithMoreAnswers, Top5StudentsWithMoreScore;
		

		//Stadistics
		AverageCoursesPerTeacher = this.adminService.AverageCoursesPerTeacher();
		StandardDesviationCoursesPerTeacher = this.adminService.StandardDesviationCoursesPerTeacher();
		
		AverageLessonsPerCourse = this.adminService.AverageLessonsPerCourse();
		StandardDesviationLessonPerCourse = this.adminService.StandardDesviationLessonPerCourse();

		ratioOfCoursesPerCategory = this.adminService.ratioOfCoursesPerCategory();
		AverageAnswersPerStudent = this.adminService.AverageAnswersPerStudent();

		StandardDesviationAnswersPerStudent = this.adminService.StandardDesviationAnswersPerStudent();
		AverageQuestionsPerCourse = this.adminService.AverageQuestionsPerCourse();

		StandardDesviationQuestionsPerCourse = this.adminService.StandardDesviationQuestionsPerCourse();
		RatioLessonsPerCourse = this.adminService.RatioLessonsPerCourse();

		StandardDesviationCoursesClosed = this.adminService.StandardDesviationCoursesClosed();
		MinScoreStudent = this.adminService.MinScoreStudent();

		MaxScoreStudent = this.adminService.MaxScoreStudent();
		AvgScoreStudent = this.adminService.AvgScoreStudent();
		StandardDesviationScoreStudent = this.adminService.StandardDesviationScoreStudent();
		MinTutorialsTeacher = this.adminService.MinTutorialsTeacher();
		MaxTutorialsTeacher= this.adminService.MaxTutorialsTeacher();
		AvgTutorialsTeacher= this.adminService.AvgTutorialsTeacher();
		
		StandardDesviationTutorialsTeacher = this.adminService.StandardDesviationTutorialsTeacher();
		RatioStudentsWithScore0 = this.adminService.RatioStudentsWithScore0();
		
		
		Top5StudentsWithMoreScore = this.adminService.Top5StudentsWithMoreScore();
		top3CoursesWithMoreSubscriptions = this.adminService.top3CoursesWithMoreSubscriptions();
		top3CoursesWithMoreQuestions = this.adminService.top3CoursesWithMoreQuestions();
		top3TeachersWithMoreAnswers = this.adminService.top3TeachersWithMoreAnswers();
		

		result = new ModelAndView("admin/dashboard");
		

		result.addObject("AverageCoursesPerTeacher", AverageCoursesPerTeacher);
		result.addObject("StandardDesviationCoursesPerTeacher", StandardDesviationCoursesPerTeacher);
		result.addObject("AverageLessonsPerCourse", AverageLessonsPerCourse);
		result.addObject("StandardDesviationLessonPerCourse", StandardDesviationLessonPerCourse);
		result.addObject("ratioOfCoursesPerCategory", ratioOfCoursesPerCategory);
		result.addObject("AverageAnswersPerStudent", AverageAnswersPerStudent);
		result.addObject("StandardDesviationAnswersPerStudent", StandardDesviationAnswersPerStudent);
		result.addObject("AverageQuestionsPerCourse", AverageQuestionsPerCourse);
		result.addObject("StandardDesviationQuestionsPerCourse", StandardDesviationQuestionsPerCourse);
		result.addObject("RatioLessonsPerCourse", RatioLessonsPerCourse);
		result.addObject("StandardDesviationCoursesClosed", StandardDesviationCoursesClosed);
		result.addObject("MinScoreStudent", MinScoreStudent);
		result.addObject("MaxScoreStudent", MaxScoreStudent);
		result.addObject("AvgScoreStudent", AvgScoreStudent);
		result.addObject("StandardDesviationScoreStudent", StandardDesviationScoreStudent);
		result.addObject("MinTutorialsTeacher", MinTutorialsTeacher);
		result.addObject("MaxTutorialsTeacher", MaxTutorialsTeacher);
		result.addObject("AvgTutorialsTeacher", AvgTutorialsTeacher);
		result.addObject("StandardDesviationTutorialsTeacher",StandardDesviationTutorialsTeacher);
		result.addObject("RatioStudentsWithScore0",RatioStudentsWithScore0);
		result.addObject("top3CoursesWithMoreSubscriptions",top3CoursesWithMoreSubscriptions);
		result.addObject("top3CoursesWithMoreQuestions",top3CoursesWithMoreQuestions);
		result.addObject("top3TeachersWithMoreAnswers", top3TeachersWithMoreAnswers);
		result.addObject("Top5StudentsWithMoreScore", Top5StudentsWithMoreScore);
	

		return result;
	}
}