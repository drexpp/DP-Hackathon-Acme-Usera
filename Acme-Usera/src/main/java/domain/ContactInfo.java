package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class ContactInfo extends DomainEntity {

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
	
	
	
}
