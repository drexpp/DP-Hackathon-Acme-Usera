package forms;





import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import domain.DomainEntity;

public class EditActorTeacherForm extends DomainEntity {

	private String		name;
	private String		surname;
	private String		email;
	private String		phone;
	private String		address;
	private Date		dateBirth;
	private String		skype;
	private String		contactPhone;
	private List<String> comments;
	private List<String> links;
	
	
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getSkype() {
		return skype;
	}
	public void setSkype(String skype) {
		this.skype = skype;
	}
	
	@ElementCollection
	@NotNull
	public List<String> getComments() {
		return comments;
	}
	public void setComments(List<String> comments) {
		this.comments = comments;
	}
	
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Pattern(regexp="\\+?([0-9]{9})?")
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	@ElementCollection
	@NotNull
	public List<String> getLinks() {
		return links;
	}
	public void setLinks(List<String> links) {
		this.links = links;
	}
	
	



	@Past
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull
	public Date getDateBirth() {
		return dateBirth;
	}
	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}


	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String emails) {
		this.email = emails;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Pattern(regexp="\\+?([0-9]+)?")
	public String getPhone() {
		return this.phone;
	}
	
	
	public void setPhone(final String phones) {
		this.phone = phones;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String addresses) {
		this.address = addresses;
	}
}