
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

import repositories.TeacherRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.ContactInfo;
import domain.Course;
import domain.Exam;
import domain.Lesson;
import domain.MailMessage;
import domain.Teacher;
import domain.Tutorial;
import forms.ActorFormTeacher;
import forms.EditActorTeacherForm;

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

	public Teacher reconstruct(final ActorFormTeacher actorFormTeacher, final BindingResult binding) {
		final Teacher teacher = this.create();
		teacher.setName(actorFormTeacher.getName());
		teacher.setSurname(actorFormTeacher.getSurname());
		teacher.setEmail(actorFormTeacher.getEmail());
		teacher.setId(actorFormTeacher.getId());
		teacher.setAddress(actorFormTeacher.getAddress());
		teacher.setVersion(actorFormTeacher.getVersion());
		teacher.setDateBirth(actorFormTeacher.getDateBirth());
		teacher.setPhone(actorFormTeacher.getPhone());
		teacher.setUserAccount(actorFormTeacher.getUserAccount());
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		auth.setAuthority("TEACHER");
		authorities.add(auth);
		teacher.getUserAccount().setAuthorities(authorities);
		
		this.validator.validate(actorFormTeacher, binding);
		if (!(actorFormTeacher.getConfirmPassword().equals((actorFormTeacher.getUserAccount().getPassword()))) || actorFormTeacher.getConfirmPassword() == null)
			binding.rejectValue("confirmPassword", "actor.passwordMiss");
		if ((actorFormTeacher.getCheck() == false))
			binding.rejectValue("check", "actor.uncheck");
		return teacher;
	}

	public void flush() {
		this.teacherRepository.flush();
	}
	
	public Collection<Teacher> findTutorsByStudent (Integer studentId){
		Collection<Teacher> res = this.teacherRepository.findTutorsByStudent(studentId);
		return res;
		
	}

	public EditActorTeacherForm construct(EditActorTeacherForm editActorTeacherForm,
			Teacher principal) {
		
		editActorTeacherForm.setId(principal.getId());
		editActorTeacherForm.setVersion(principal.getVersion());
		editActorTeacherForm.setName(principal.getName());
		editActorTeacherForm.setSurname(principal.getSurname());
		editActorTeacherForm.setEmail(principal.getEmail());
		editActorTeacherForm.setPhone(principal.getPhone());
		editActorTeacherForm.setAddress(principal.getAddress());
		editActorTeacherForm.setDateBirth(principal.getDateBirth());
		
		editActorTeacherForm.setSkype(principal.getContactInfo().getSkype());
		editActorTeacherForm.setContactPhone(principal.getContactInfo().getContactPhone());
		editActorTeacherForm.setComments(principal.getContactInfo().getComments());
		editActorTeacherForm.setLinks(principal.getContactInfo().getLinks());
		
		
		return editActorTeacherForm;
	}

	public Teacher reconstruct(EditActorTeacherForm editActorTeacherForm,
			BindingResult binding) {
		Teacher result;
		ContactInfo contactInfo;
		
		Teacher principal = this.findByPrincipal();
		
		result = this.create();
		contactInfo = principal.getContactInfo();
		contactInfo.setSkype(editActorTeacherForm.getSkype());
		contactInfo.setContactPhone(editActorTeacherForm.getContactPhone());
		if(editActorTeacherForm.getComments().get(0).equals("")){
			List<String> commentsEmpty;
			commentsEmpty = new ArrayList<String>();
			contactInfo.setComments(commentsEmpty);
		}else
			contactInfo.setComments(editActorTeacherForm.getComments());
		
		if(editActorTeacherForm.getLinks().get(0).equals("")){
			List<String> linksEmpty;
			linksEmpty = new ArrayList<String>();
			contactInfo.setLinks(linksEmpty);
		}else
			contactInfo.setLinks(editActorTeacherForm.getLinks());
		result.setId(principal.getId());
		result.setName(editActorTeacherForm.getName());
		result.setSurname(editActorTeacherForm.getSurname());
		result.setEmail(editActorTeacherForm.getEmail());
		result.setDateBirth(editActorTeacherForm.getDateBirth());
		result.setId(editActorTeacherForm.getId());
		result.setAddress(editActorTeacherForm.getAddress());
		result.setVersion(editActorTeacherForm.getVersion());
		result.setPhone(editActorTeacherForm.getPhone());
		result.setTutorials(principal.getTutorials());
		result.setCoursesCreated(principal.getCoursesCreated());
		result.setCoursesJoined(principal.getCoursesJoined());
		result.setCurriculum(principal.getCurriculum());
		result.setContactInfo(principal.getContactInfo());
		result.setLessons(principal.getLessons());
		result.setSentMessages(principal.getReceivedMessages());
		result.setAnswers(principal.getAnswers());
		result.setReceivedMessages(principal.getReceivedMessages());
		result.setFolders(principal.getFolders());
		result.setUserAccount(principal.getUserAccount());
		
		result.setContactInfo(contactInfo);
		
		this.validator.validate(editActorTeacherForm, binding);

		
		return result;
	}
	
}
