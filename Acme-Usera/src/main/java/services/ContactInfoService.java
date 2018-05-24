package services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ContactInfoRepository;
import domain.ContactInfo;
import forms.ActorFormTeacher;

@Service
@Transactional
public class ContactInfoService {

	// Managed Repository
	@Autowired
	private ContactInfoRepository contactInfoRepository;

	@Autowired
	private Validator		validator;


	// Supporting services

	// Constructors

	public ContactInfoService() {
		super();
	}

	// Simple CRUD methods
	public ContactInfo create() {
		ContactInfo result;
		
		result = new ContactInfo();
		
		return result;
	}

	public ContactInfo save(final ContactInfo contactInfo) {
		ContactInfo result;
		
		
		result = this.contactInfoRepository.save(contactInfo);
		
		
		return result;
	}

	public ContactInfo findOne(final int contactInfoId) {
		ContactInfo result;
		result = this.contactInfoRepository.findOne(contactInfoId);
		return result;
	}

	//Other business methods
	public ContactInfo reconstruct(final ActorFormTeacher actorFormTeacher, final BindingResult binding) {
		final ContactInfo contactInfo = this.create();
		
		contactInfo.setSkype(actorFormTeacher.getSkype());
		contactInfo.setContactPhone(actorFormTeacher.getContactPhone());
		contactInfo.setComments(actorFormTeacher.getComments());
		contactInfo.setLinks(actorFormTeacher.getLinks());
		
		this.validator.validate(actorFormTeacher, binding);
	
		return contactInfo;
	}

	public void flush() {
		this.contactInfoRepository.flush();
	}
	
}
