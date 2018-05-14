package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class ContactInfo {

	private String		skype;
	private Collection<String>		comments;
	private String		contactPhone;
	private Collection<String> links;
	
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getSkype() {
		return skype;
	}
	public void setSkype(String skype) {
		this.skype = skype;
	}
	
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public Collection<String> getComment() {
		return comments;
	}
	public void setComment(Collection<String> comments) {
		this.comments = comments;
	}
	
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Pattern(regexp="\\+?([0-9]+)?")
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public Collection<String> getLinks() {
		return links;
	}
	public void setLinks(Collection<String> links) {
		this.links = links;
	}
	
	
	
	
}
