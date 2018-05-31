package services;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SubscriptionRepository;
import domain.Admin;
import domain.CreditCard;
import domain.Student;
import domain.Course;
import domain.Subscription;

@Service
@Transactional
public class SubscriptionService {

	@Autowired
	private SubscriptionRepository		subscriptionRepository;
	
	@Autowired
	private StudentService				studentService;
	
	@Autowired
	private CourseService				courseService;

	@Autowired
	private AdminService					adminService;
	
	@Autowired
	private Validator		validator;

	// Constructors
	public SubscriptionService() {
		super();
	}

	// Simple CRUD methods
	public Subscription create() {
		Student principal;
		Subscription subscription = new Subscription();
		principal = this.studentService.findByPrincipal();
		Assert.notNull(principal);
		subscription.setStudent(principal);
		
		return subscription;
	}

	public Subscription save(final Subscription subscription) {
		Student principal;
		Subscription result;
		List<Subscription> updated,updated2;

		Assert.notNull(subscription);

		principal = this.studentService.findByPrincipal();

		Assert.notNull(principal);
		subscription.setStudent(principal);
		Assert.isTrue(subscription.getCourse().getIsClosed() == false);
		Collection<Course> subscribed = this.courseService.selectCoursesSubscriptedByUser(principal.getId());
		Assert.isTrue(!subscribed.contains(subscription.getCourse()));

		result = this.subscriptionRepository.save(subscription);
		
		Student student = result.getStudent();
		final Collection<Subscription> subscriptions = student.getSubscriptions();
		updated = new ArrayList<Subscription>(subscriptions);
		updated.add(result);
		student.setSubscriptions(updated);
		
		Course course = result.getCourse();
		Collection<Subscription> subscriptions2 = course.getSubscriptions();
		updated2 = new ArrayList<Subscription>(subscriptions2);
		updated2.add(result);
		course.setSubscriptions(updated2);

		return result;
	}

	

	public Subscription findOne(final int id) {
		final Student principal = this.studentService.findByPrincipal();
		Assert.notNull(principal);
		final Subscription res = this.subscriptionRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}
	
	public Collection<Subscription> findAll() {
		Collection<Subscription> result;

		result = this.subscriptionRepository.findAll();

		return result;
	}

	
	public void delete (Subscription subcription){
		Admin admin = this.adminService.findByPrincipal();
		Assert.notNull(admin);
		Collection<Subscription> toUpdate,toUpdate2,updated,updated2;
		
		Student c = subcription.getStudent();
		toUpdate = c.getSubscriptions();
		updated = new ArrayList<Subscription>(toUpdate);
		updated.remove(subcription);
		subcription.getStudent().setSubscriptions(updated);
		
		Course news = subcription.getCourse();
		toUpdate2 = news.getSubscriptions();
		updated2 = new ArrayList<Subscription>(toUpdate2);
		updated2.remove(subcription);
		subcription.getCourse().setSubscriptions(updated2);
		
		this.subscriptionRepository.delete(subcription);
		
	}
	
	public CreditCard reconstructCreditCard(String cookie){
		String[] creditCard= cookie.split("\\.",6);
		String holder = creditCard[0];
		String brand = creditCard[1];
		String number = creditCard[2];
		Integer expirationMonth = Integer.parseInt(creditCard[3]);
		Integer expirationYear = Integer.parseInt(creditCard[4]);
		Integer CVV = Integer.parseInt(creditCard[5]);
		
		CreditCard creditCard2 = new CreditCard();
		creditCard2.setHolderName(holder);
		creditCard2.setBrandName(brand);
		creditCard2.setNumber(number);
		creditCard2.setExpirationMonth(expirationMonth);
		creditCard2.setExpirationYear(expirationYear);
		creditCard2.setCVV(CVV);
		
		
		return creditCard2;
	}
	
	public void flush() {
	this.subscriptionRepository.flush();
		
	}
	
	public void checkDate(CreditCard creditCard, BindingResult binding){
		try{
		LocalDate date = new LocalDate();
		Integer actualYear = date.getYearOfCentury();
		Integer actualMonth = date.getMonthOfYear();
		Integer ccYear      = creditCard.getExpirationYear();
		Integer ccMonth     = creditCard.getExpirationMonth();
		
		if (ccYear < actualYear){
			binding.rejectValue("creditCard.expirationMonth", "subscription.creditCard.expired");
		}
		else if(ccYear == actualYear){
			if(ccMonth <actualMonth || ccMonth == actualMonth){
				binding.rejectValue("creditCard.expirationMonth", "subscription.creditCard.expired");
			}
		}} catch (Throwable oops){
			binding.rejectValue("creditCard.expirationMonth", "subscription.creditCard.expired");
		}
		
	}
	
	
	public Subscription findByStudentAndCourseint (int studentId, int courseId){
		Subscription res = this.subscriptionRepository.findByStudentAndCourse(studentId, courseId);
		return res;
	}

	public Subscription checkSubscriptionType(Subscription subscription, BindingResult binding) {
		Student principal = this.studentService.findByPrincipal();
		Assert.notNull(principal);
		if (subscription.getSubscriptionType().equals(Subscription.FREE)){
			subscription.setCreditCard(null);
		} else {
			this.checkDate(subscription.getCreditCard(), binding);
		}
		validator.validate(subscription, binding);
		return subscription;
	}
}