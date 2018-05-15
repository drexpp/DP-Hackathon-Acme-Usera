package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Teacher extends Actor {
	
	private Collection<Tutorial> tutorials;
	private ContactInfo	contactInfo;
	
	
	
	@NotNull
	@OneToMany(mappedBy="teacher")
	public Collection<Tutorial> getTutorials() {
		return tutorials;
	}
	public void setTutorials(Collection<Tutorial> tutorials) {
		this.tutorials = tutorials;
	}
	
	@NotNull
	@Valid
	@OneToOne(optional = false)
	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}
	
	
}
