
package services;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdminRepository;
import security.LoginService;
import security.UserAccount;
import domain.Admin;
import domain.Course;
import forms.EditActorForm;

@Service
@Transactional
public class AdminService {

	// Managed Repository
	@Autowired
	private AdminRepository	adminRepository;


	// Supporting services
	@Autowired
	private Validator		validator;

	// Constructors

	public AdminService() {
		super();
	}

	// Simple CRUD methods
	public Admin create() {
		Admin principal;
		Admin result;
		principal = this.findByPrincipal();
		Assert.notNull(principal);
		result = new Admin();
		return result;
	}

	public Admin save(final Admin Admin) {
		Admin saved;
		Assert.notNull(Admin);

		if (Admin.getId() == 0) {
			Admin principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			Admin.getUserAccount().setPassword(passwordEncoder.encodePassword(Admin.getUserAccount().getPassword(), null));
		} else {
			Admin principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);

		}

		saved = this.adminRepository.save(Admin);

		return saved;
	}

	public Admin findOne(final int AdminId) {
		Admin result;
		result = this.adminRepository.findOne(AdminId);
		return result;
	}

	public Admin findByPrincipal() {
		Admin result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;

	}

	public Admin findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Admin result;
		result = this.adminRepository.findByUserAccountId(userAccount.getId());
		return result;
	}


	public EditActorForm construct(EditActorForm editActorForm,
			Admin principal) {
		
		editActorForm.setId(principal.getId());
		editActorForm.setVersion(principal.getVersion());
		editActorForm.setName(principal.getName());
		editActorForm.setSurname(principal.getSurname());
		editActorForm.setEmail(principal.getEmail());
		editActorForm.setPhone(principal.getPhone());
		editActorForm.setAddress(principal.getAddress());
		
		
		return editActorForm;
	}

	public Admin reconstruct(EditActorForm editActorForm,
			BindingResult binding) {
		Admin result;
		
		result = new Admin();
		
		Admin principal = this.findByPrincipal();
		
		result.setId(principal.getId());
		result.setName(editActorForm.getName());
		result.setSurname(editActorForm.getSurname());
		result.setEmail(editActorForm.getEmail());
		result.setId(editActorForm.getId());
		result.setAddress(editActorForm.getAddress());
		result.setVersion(editActorForm.getVersion());
		result.setPhone(editActorForm.getPhone());
		result.setSentMessages(principal.getReceivedMessages());
		result.setAnswers(principal.getAnswers());
		result.setReceivedMessages(principal.getReceivedMessages());
		result.setFolders(principal.getFolders());
		result.setUserAccount(principal.getUserAccount());
	
		
		this.validator.validate(editActorForm, binding);

		return result;
	}
	
	public Double AverageCoursesPerTeacher(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);
		Double res = this.adminRepository.AverageCoursesPerTeacher();
		return res;
	}
	
	public Double StandardDesviationCoursesPerTeacher(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);	
		Double res = this.adminRepository.StandardDesviationCoursesPerTeacher();
		return res;
	}
	
	public Double AverageLessonsPerCourse(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);	
		Double res = this.adminRepository.AverageLessonsPerCourse();
		return res;
	}
	
	public Double StandardDesviationLessonPerCourse(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);	
		Double res = this.adminRepository.StandardDesviationLessonPerCourse();
		return res;
	}
	
	public Collection<Course> top3CoursesWithMoreSubscriptions(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);	
		List<Course> top3CoursesWithMoreSubscriptions = new ArrayList<Course>(this.adminRepository.top3CoursesWithMoreSubscriptions());
		List<Course> res = top3CoursesWithMoreSubscriptions.subList(0, 2);
		return res;
	}
	
	public Double ratioOfCoursesPerCategory(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);	
		Double res = this.adminRepository.ratioOfCoursesPerCategory();
		return res;
	}
	
	public Double AverageAnswersPerStudent(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);	
		Double res = this.adminRepository.AverageAnswersPerStudent();
		return res;
	}
	
	public Double StandardDesviationAnswersPerStudent(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);	
		Double res = this.adminRepository.StandardDesviationAnswersPerStudent();
		return res;
	}
	
	public Double AverageQuestionsPerCourse(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);	
		Double res = this.adminRepository.AverageQuestionsPerCourse();
		return res;
	}
	
	public Double StandardDesviationQuestionsPerCourse(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);	
		Double res = this.adminRepository.StandardDesviationQuestionsPerCourse();
		return res;
	}

	public Collection<Course> top3CoursesWithMoreQuestions(){
		Admin principal = this.findByPrincipal();
		List<Course> res = new ArrayList<Course>();
		Assert.notNull(principal);	
		List<Course> top3CoursesWithMoreQuestions = new ArrayList<Course>(this.adminRepository.top3CoursesWithMoreQuestions());
		if (top3CoursesWithMoreQuestions.size() > 3){
		res = top3CoursesWithMoreQuestions.subList(0, 2);
		} else {
			res = top3CoursesWithMoreQuestions;
		}
		
		return res;
	}
	
	public Collection<Course> top3TeachersWithMoreAnswers(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);
		List<Course> res = new ArrayList<Course>();
		List<Course> top3TeachersWithMoreAnswers = new ArrayList<Course>(this.adminRepository.top3TeachersWithMoreAnswers());
		if (top3TeachersWithMoreAnswers.size() > 3){
		res = top3TeachersWithMoreAnswers.subList(0, 2);	
		} else {
			res = top3TeachersWithMoreAnswers;
		}

		return res;
	}
	
	public Double RatioLessonsPerCourse(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);	
		Double res = this.adminRepository.RatioLessonsPerCourse();
		return res;
	}
	
	public Double StandardDesviationCoursesClosed(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);	
		Double res = this.adminRepository.StandardDesviationCoursesClosed();
		return res;
	}
	
	public Double MinScoreStudent(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);	
		Double res = this.adminRepository.MinScoreStudent();
		return res;
	}
	
	public Double MaxScoreStudent(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);	
		Double res = this.adminRepository.MaxScoreStudent();
		return res;
	}
	
	public Double AvgScoreStudent(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);	
		Double res = this.adminRepository.AvgScoreStudent();
		return res;
	}
	
	public Double StandardDesviationScoreStudent(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);	
		Double res = this.adminRepository.StandardDesviationScoreStudent();
		return res;
	}
	
	public Double MinTutorialsTeacher(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);	
		Double res = this.adminRepository.MinTutorialsTeacher();
		return res;
	}
	
	public Double MaxTutorialsTeacher(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);	
		Double res = this.adminRepository.MaxTutorialsTeacher();
		return res;
	}
	
	public Double AvgTutorialsTeacher(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);	
		Double res = this.adminRepository.AvgTutorialsTeacher();
		return res;
	}
	
	public Double StandardDesviationTutorialsTeacher(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);	
		Double res = this.adminRepository.StandardDesviationTutorialsTeacher();
		return res;
	}
	
	public Double RatioStudentsWithScore0(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);	
		Double res = this.adminRepository.RatioStudentsWithScore0();
		return res;
	}
	
	public Collection<Course> Top5StudentsWithMoreScore(){
		Admin principal = this.findByPrincipal();
		Assert.notNull(principal);	
		List<Course> res = new ArrayList<Course>();
		List<Course> Top5StudentsWithMoreScore = new ArrayList<Course>(this.adminRepository.Top5StudentsWithMoreScore());
		if(Top5StudentsWithMoreScore.size() > 5){
			res = Top5StudentsWithMoreScore.subList(0, 4);
		} else {
			res = Top5StudentsWithMoreScore;
		}
		return res;
	}
	
	

}
