package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.CourseRepository;
import domain.Admin;
import domain.Advertisement;
import domain.Forum;
import domain.Lesson;
import domain.Sponsor;
import domain.Student;
import domain.Subscription;
import domain.Teacher;

import domain.Course;
import forms.CourseForm;


@Service
@Transactional
public class CourseService {

	// Managed Repository
	@Autowired
	private CourseRepository			courseRepository;

	// Supporting services
	@Autowired
	private TeacherService				teacherService;
	
	@Autowired
	private SponsorService				sponsorService;

	@Autowired
	private AdminService			adminService;
	
	@Autowired
	private ExamService			examService;
	
	@Autowired
	private LessonService			lessonService;
	
	@Autowired
	private ForumService			forumService;

	@Autowired
	private SubscriptionService			subscriptionService;
	
	@Autowired
	private StudentService	studentService;
	
	@Autowired
	private Validator	validator;

	// Constructors

	public CourseService() {
		super();
	}

	// Simple CRUD methods

	
	public Course create() {
		Teacher principal;
		Course course = new Course();

		principal = this.teacherService.findByPrincipal();
		Assert.notNull(principal);
		course.setCreator(principal);
		course.setCreationDate(new Date(System.currentTimeMillis()));
		course.setIsClosed(false);
		course.setAdvertisements(new ArrayList<Advertisement>());
		course.setLessons(new ArrayList<Lesson>());
		course.setSubscriptions(new ArrayList<Subscription>());

		return course;
	}


	public Collection<Course> findAll() {
		final Collection<Course> result = this.courseRepository.findAll();
		
		
		return result;
	}

	// Other business methods

	public void deleteByAdmin(final Course course) {
		Admin principal;

		Assert.notNull(course);
		Assert.isTrue(course.getId() != 0);
		principal = this.adminService.findByPrincipal();
		Assert.notNull(principal);

		final Collection<Advertisement> adversToRemove = course.getAdvertisements();

		final Collection<Subscription> subscriptionsToRemove = new ArrayList<Subscription>(course.getSubscriptions());
		Collection<Teacher> teachers = new ArrayList<Teacher>(course.getTeachers());
		Collection<Lesson> lessons = new ArrayList<Lesson>(course.getLessons());
		
		
		for(Lesson l : lessons){
			this.lessonService.delete(l);
		}

		for (final Advertisement ad : adversToRemove){
			Collection<Course> toUpdate = ad.getCourses();
			Collection<Course> updated = new ArrayList<Course>(toUpdate);

			updated.remove(course);
			ad.setCourses(updated);
			
		}			
		for (final Subscription s : subscriptionsToRemove)
			this.subscriptionService.delete(s);


		

		for (final Teacher t : teachers) {
			final Collection<Course> courses = t.getCoursesJoined();
			final Collection<Course> updated2 = new ArrayList<Course>(courses);
			updated2.remove(course);
			t.setCoursesJoined(updated2);
		}
		if (course.getExam() != null){
		this.examService.deleteByAdmin(course.getExam());
		
		}
		Teacher teacher = course.getCreator();
		final Collection<Course> courses = teacher.getCoursesCreated();
		final Collection<Course> updated2 = new ArrayList<Course>(courses);
		teacher.setCoursesCreated(updated2);
		this.forumService.deleteByAdmin(course.getForum());
		this.courseRepository.delete(course);

	}
	// Teachers must be able to create Courses
	public Course save(final Course courseToSave) {
		Teacher principal;
		Course result;
		Assert.notNull(courseToSave);

		principal = this.teacherService.findByPrincipal();

		Assert.notNull(principal);
		
		if (courseToSave.getId() != 0){
		Assert.isTrue(principal.getCoursesCreated().contains(courseToSave));
		}
	
		result = this.courseRepository.save(courseToSave);
	

		if (courseToSave.getId() == 0) {
			
			Forum forum = this.forumService.create();
			forum.setCourse(result);
			this.forumService.save(forum);
			
			final Collection<Teacher> teachers = new ArrayList<Teacher>();
			teachers.add(principal);
			result.setCreator(principal);
			result.setTeachers(teachers);

			final Collection<Course> joined = principal.getCoursesJoined();
			joined.add(result);
			principal.setCoursesJoined(joined);

		}

		
		
		return result;
	}
	public Collection<Course> findJoinedByTeacherId(final int id) {
		Teacher principal;
		Collection<Course> result;

		principal = this.teacherService.findByPrincipal();
		Assert.notNull(principal);
		result = this.courseRepository.selectJoinedByTeacherId(id);

		Assert.notNull(result);

		return result;

	}


	public Course findOne(final int CourseId) {

		Course result = this.courseRepository.findOne(CourseId);
		Assert.notNull(result);

		return result;

	}



	public Teacher join(final Course course, final Teacher teacher) {
		Teacher result;
		Teacher principal = this.teacherService.findByPrincipal();
		Assert.isTrue(!principal.getCoursesJoined().contains(course)&& !principal.getCoursesCreated().contains(course));
		Assert.isTrue(course.getIsClosed() == false);
		if (!course.getTeachers().contains(teacher)) {
			course.getTeachers().add(teacher);
			teacher.getCoursesJoined().add(course);
		}
		result = teacher;
		return result;
	}

	public Teacher remove(final Course course, final Teacher teacher) {
		Teacher result;
		Teacher principal = this.teacherService.findByPrincipal();
		Assert.isTrue(course.getCreator().equals(principal));
		Assert.isTrue(course.getIsClosed() == false);
		Assert.isTrue(teacher.getCoursesJoined().contains(course));
		if (course.getTeachers().contains(teacher)) {
			course.getTeachers().remove(teacher);
			teacher.getCoursesJoined().remove(course);
		}
		result = teacher;
		return result;
	}
	
	public void CloseCourse (Course course){
		Teacher principal = this.teacherService.findByPrincipal();
		Assert.isTrue(course.getCreator().equals(principal));
		Assert.isTrue(course.getIsClosed() == false);
		course.setIsClosed(true);
		this.flush();
	}
	
	
	public Collection<Course> findCourseByCategory(Integer categoryId){
		Collection<Course> result;
		
		result = this.courseRepository.findCourseByCategory(categoryId);
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Course> selectCoursesSubscriptedByUser(int id){
		Student principal = this.studentService.findByPrincipal();
		Assert.notNull(principal);
		Collection<Course> res = this.courseRepository.selectCoursesSubscriptedByUser(id);
		return res;
	}
	public void flush(){
		this.courseRepository.flush();
	}

	public Course reconstruct(CourseForm courseForm, BindingResult binding) {
		this.validator.validate(courseForm, binding);
		Course course = new Course();
		if (!binding.hasErrors()){
		if(courseForm.getId()== 0){
		course = this.create();
		}else{
		course = this.findOne(courseForm.getId());	
		}
		course.setTitle(courseForm.getTitle());
		course.setDescription(courseForm.getDescription());
		course.setPhotoURL(courseForm.getPhotoURL());
		course.setCategory(courseForm.getCategory());
		} 
		return course;
	}

	public CourseForm reconstructForm(Course course) {
		CourseForm courseForm = new CourseForm();
		courseForm.setCategory(course.getCategory());
		courseForm.setDescription(course.getDescription());
		courseForm.setPhotoURL(course.getPhotoURL());
		courseForm.setTitle(course.getTitle());
		courseForm.setId(course.getId());
		return courseForm;
	}
	
	  public Collection<Course> findCoursesSubscribedFreeByUser(Integer idUser){
		  Student principal = this.studentService.findByPrincipal();
		  Assert.notNull(principal);
		  Collection<Course> res = this.courseRepository.findCoursesSubscribedFreeByUser(idUser);
		  return res;
	  }
	  
	  public Collection<Course> findCoursesSubscribedStandardByUser(Integer idUser){
		  Student principal = this.studentService.findByPrincipal();
		  Assert.notNull(principal);
		  Collection<Course> res = this.courseRepository.findCoursesSubscribedStandardByUser(idUser);
		  return res;
	  }
	  
	  public Collection<Course> findCoursesSubscribedPremiumByUser(Integer idUser){
		  Student principal = this.studentService.findByPrincipal();
		  Assert.notNull(principal);
		  Collection<Course> res = this.courseRepository.findCoursesSubscribedPremiumByUser(idUser);
		  return res;
	  }
	
	public Collection<Course> findCoursesStandardAndPremium (Integer idUser){
		 Student principal = this.studentService.findByPrincipal();
		  Assert.notNull(principal);
		  Collection<Course> all = new HashSet<Course>();
			Collection<Course> subscribedStandard = this.findCoursesSubscribedStandardByUser(principal.getId());
			Collection<Course> subscribedPremium = this.findCoursesSubscribedPremiumByUser(principal.getId());
			all.addAll(subscribedPremium);
			all.addAll(subscribedStandard);
			return all;
	}
	
	public Collection<Course> findCoursesWithAdsPlacedBySponsor(){
		Sponsor principal = this.sponsorService.findByPrincipal();
		Assert.notNull(principal);
		Collection<Course> res = new ArrayList<Course>(this.courseRepository.findCoursesWithAdsPlacedBySponsor(principal.getId()));
		return res;
		
		
	}
}