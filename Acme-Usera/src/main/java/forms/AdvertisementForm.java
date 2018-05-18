package forms;

import java.util.Collection;


import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;


import domain.Course;
import domain.CreditCard;
import domain.DomainEntity;

public class AdvertisementForm extends DomainEntity {

	private String 				title;
	private String				bannerURL;
	private String				targetPageURL;
	private CreditCard			creditCard;
	private Collection<Course> 	courses;
	
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getBannerURL() {
		return bannerURL;
	}
	public void setBannerURL(String bannerURL) {
		this.bannerURL = bannerURL;
	}
	
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTargetPageURL() {
		return targetPageURL;
	}
	public void setTargetPageURL(String targetPageURL) {
		this.targetPageURL = targetPageURL;
	}
	

	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	@NotEmpty
	public Collection<Course> getCourses() {
		return courses;
	}
	public void setCourses(Collection<Course> courses) {
		this.courses = courses;
	}
	
}
