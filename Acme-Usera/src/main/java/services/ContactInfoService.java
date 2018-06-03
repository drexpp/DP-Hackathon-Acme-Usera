package services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import repositories.ContactInfoRepository;
import domain.ContactInfo;
import domain.Teacher;
import forms.ActorFormTeacher;

@Service
@Transactional
public class ContactInfoService {

	// Managed Repository
	@Autowired
	private ContactInfoRepository contactInfoRepository;


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

	public ContactInfo save(final ContactInfo contactInfo, final Teacher teacher) {
		ContactInfo result;
		
		
		result = this.contactInfoRepository.save(contactInfo);
		teacher.setContactInfo(result);
		
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
		if(actorFormTeacher.getLinks().get(0).equals("")){
			List<String> linksEmpty;
			linksEmpty = new ArrayList<String>();
			contactInfo.setLinks(linksEmpty);
		}else
			contactInfo.setLinks(actorFormTeacher.getLinks());
		
		if(actorFormTeacher.getComments().get(0).equals("")){
			List<String> commentsEmpty;
			commentsEmpty = new ArrayList<String>();
			contactInfo.setComments(commentsEmpty);
		}else
			contactInfo.setComments(actorFormTeacher.getComments());
		
			
		contactInfo.setContactPhone(actorFormTeacher.getContactPhone());
		
		
		
		return contactInfo;
	}

	public void flush() {
		this.contactInfoRepository.flush();
	}
	
}
