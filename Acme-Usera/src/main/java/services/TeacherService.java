
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TeacherRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Exam;
import domain.Lesson;
import domain.MailMessage;
import domain.Course;
import domain.Teacher;
import domain.Tutorial;
import forms.ActorForm;

@Service
@Transactional
public class TeacherService {

	// Managed Repository
	@Autowired
	private TeacherRepository	teacherRepository;
	
	@Autowired
	private FolderService folderService;

	@Autowired
	private Validator		validator;


	// Supporting services

	// Constructors

	public TeacherService() {
		super();
	}

	// Simple CRUD methods
	public Teacher create() {
		Teacher result;

		result = new Teacher();
		result.setCoursesCreated(new ArrayList<Course>());
		result.setCoursesJoined(new ArrayList<Course>());
		result.setTutorials(new ArrayList<Tutorial>());
		result.setContactInfo(null);
		result.setCurriculum(null);
		result.setExams(new ArrayList<Exam>());
		result.setLessons(new ArrayList<Lesson>());
		result.setReceivedMessages(new ArrayList<MailMessage>());
		result.setSentMessages(new ArrayList<MailMessage>());
		result.setFolders(this.folderService.createSystemFolders());
		
		return result;
	}

	public Teacher save(final Teacher teacher) {
		Teacher saved;
		Assert.notNull(teacher);

		if (teacher.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			teacher.getUserAccount().setPassword(passwordEncoder.encodePassword(teacher.getUserAccount().getPassword(), null));
		}

		saved = this.teacherRepository.save(teacher);

		//TEST ASSERT - Testing if the user is in the system after saving him/her
		Assert.isTrue(this.teacherRepository.findAll().contains(saved));
		//TEST ASSERT =========================================
		return saved;
	}

	public Teacher findOne(final int teacherId) {
		Teacher result;
		result = this.teacherRepository.findOne(teacherId);
		return result;
	}

	public Collection<Teacher> findAll() {
		Collection<Teacher> result;
		result = this.teacherRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	//Other business methods
	public Teacher findByPrincipal() {
		Teacher result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;

	}

	public Teacher findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Teacher result;
		result = this.teacherRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public Teacher reconstruct(final ActorForm actorForm, final BindingResult binding) {
		final Teacher teacher = this.create();
		teacher.setName(actorForm.getName());
		teacher.setSurname(actorForm.getSurname());
		teacher.setEmail(actorForm.getEmail());
		teacher.setId(actorForm.getId());
		teacher.setAddress(actorForm.getAddress());
		teacher.setVersion(actorForm.getVersion());
		teacher.setPhone(actorForm.getPhone());
		teacher.setUserAccount(actorForm.getUserAccount());
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		auth.setAuthority("TEACHER");
		authorities.add(auth);
		teacher.getUserAccount().setAuthorities(authorities);

		this.validator.validate(actorForm, binding);
		if (!(actorForm.getConfirmPassword().equals((actorForm.getUserAccount().getPassword()))) || actorForm.getConfirmPassword() == null)
			binding.rejectValue("confirmPassword", "user.passwordMiss");
		if ((actorForm.getCheck() == false))
			binding.rejectValue("check", "user.uncheck");
		return teacher;
	}

	public void flush() {
		this.teacherRepository.flush();
	}
	
	public Collection<Teacher> findTutorsByStudent (Integer studentId){
		Collection<Teacher> res = this.teacherRepository.findTutorsByStudent(studentId);
		return res;
		
	}

}
