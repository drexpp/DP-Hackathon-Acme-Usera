package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Admin;
import domain.Advertisement;
import domain.Course;
import domain.CreditCard;
import domain.Sponsor;
import forms.AdvertisementForm;

import repositories.AdvertisementRepository;


@Transactional
@Service
public class AdvertisementService {

	@Autowired
	AdvertisementRepository advertisementRepository;
	
	@Autowired
	SponsorService sponsorService;
	
	@Autowired
	AdminService			adminService;
	
	@Autowired
	Validator validator;
	
	public AdvertisementService(){
		super();
	}
	
	public Advertisement create(){
		Advertisement result;
		Sponsor principal;
	
		principal = this.sponsorService.findByPrincipal();
		
		result = new Advertisement();
		result.setSponsor(principal);
		result.setCourses(new ArrayList<Course>());
		
		return result;
	}

	public Advertisement findOne(int advertisementId) {
		Advertisement result;
		
		result = this.advertisementRepository.findOne(advertisementId);
		
		return result;
	}

	public Advertisement reconstruct(AdvertisementForm advertisementForm, BindingResult binding) {
		Advertisement result;
		
		result = create();
		result.setBannerURL(advertisementForm.getBannerURL());
		result.setTargetURL(advertisementForm.getTargetPageURL());
		result.setTitle(advertisementForm.getTitle());
		result.setCreditCard(advertisementForm.getCreditCard());
		
		if(!(advertisementForm.getCourses() == null)){		
			result.setCourses(advertisementForm.getCourses());
		}
		
		validator.validate(result, binding);
		
		//Comprobación de que la creditcard no caduca en el mes actual o se encuentra caducada
		this.checkDate(result.getCreditCard(), binding);
		return result;
	}

	public void save(Advertisement advertisement) {
		Assert.notNull(advertisement);
		Assert.notNull(advertisement.getSponsor());
		Advertisement saved;
		Collection<Course> relatedCourses;
		Collection<Advertisement> beforeUpdate;
		
		Assert.hasText(advertisement.getTitle());
		Assert.hasText(advertisement.getBannerURL());
		Assert.hasText(advertisement.getTargetURL());
		
		relatedCourses = advertisement.getCourses();
		
		saved = this.advertisementRepository.save(advertisement);
		
		for (Course course : relatedCourses) {
			beforeUpdate = course.getAdvertisements();
			beforeUpdate.add(saved);
			course.setAdvertisements(beforeUpdate);
		}
		
	}

	public void delete(Advertisement advert) {
		Admin admin;
		Collection<Advertisement> updated;
		Sponsor sponsor;
		final Collection<Course> courses;
		Assert.notNull(advert);
		
		
		admin = this.adminService.findByPrincipal();
		Assert.notNull(admin);
		
		courses = advert.getCourses();
		sponsor = advert.getSponsor();
		
		for(Course course : courses){
			updated = course.getAdvertisements();
			updated.remove(advert);
			course.setAdvertisements(updated);
		}
		
		updated = sponsor.getAdvertisements();
		updated.remove(advert);
		sponsor.setAdvertisements(updated);
		
		this.advertisementRepository.delete(advert);
	}
	
	public void deleteAdmin(Advertisement advert) {
		Admin admin;
		Collection<Advertisement> updated;
		Sponsor sponsor;
		Assert.notNull(advert);
		
		
		admin = this.adminService.findByPrincipal();
		Assert.notNull(admin);
	
		sponsor = advert.getSponsor();
		
		updated = sponsor.getAdvertisements();
		updated.remove(advert);
		sponsor.setAdvertisements(updated);
		
		this.advertisementRepository.delete(advert);
	}
	
	
	public void checkDate(CreditCard creditCard, BindingResult binding){
		try{
		LocalDate date = new LocalDate();
		Integer actualYear = date.getYearOfCentury();
		Integer actualMonth = date.getMonthOfYear();
		Integer ccYear      = creditCard.getExpirationYear();
		Integer ccMonth     = creditCard.getExpirationMonth();
		
		if (ccYear < actualYear){
			binding.rejectValue("creditCard.expirationMonth", "advertisement.creditCard.expired");
		}
		else if(ccYear == actualYear){
			if(ccMonth <actualMonth || ccMonth == actualMonth){
				binding.rejectValue("creditCard.expirationMonth", "advertisement.creditCard.expired");
			}
		}} catch (Throwable oops){
			binding.rejectValue("creditCard.expirationMonth", "advertisement.creditCard.expired");
		}
		
	}

	public Collection<Advertisement> findAll() {
		Collection<Advertisement> advertisements;
		
		advertisements = this.advertisementRepository.findAll();
		
		return advertisements;
	}
	
	public Collection<Advertisement> findBySponsorId(final Integer sponsorId){
		Collection<Advertisement> result;
		
		result = this.advertisementRepository.findBySponsorId(sponsorId);
		
		return result;
	}
	

	public Advertisement findRandomAdvertisement(Course course) {
		Advertisement result = null;
		List<Advertisement> adverts = new ArrayList<Advertisement>();
		adverts = (List<Advertisement>) course.getAdvertisements();

		if (adverts.size() >= 2) {
			int selectedOne;
			final int limit = adverts.size();
			final Random rand = new Random();
			selectedOne = rand.nextInt(limit);
			result = adverts.get(selectedOne);
		} else if (adverts.size() == 1)
			result = adverts.get(0);

		return result;
	}
}
