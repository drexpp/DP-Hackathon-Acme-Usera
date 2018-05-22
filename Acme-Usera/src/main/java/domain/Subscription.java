package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;


@Entity
@Access(AccessType.PROPERTY)
public class Subscription extends DomainEntity {
	
	private CreditCard creditCard;
	private String subscriptionType;
	private Course course;
	private Student student;
	
	
	// Values -----------------------------------------------------------------

		public static final String	FREE	= "FREE";
		public static final String	STANDARD	= "STANDARD";
		public static final String	PREMIUM	= "PREMIUM";
		//-----------------------------------------------------------------	
	
	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Pattern(regexp = "^" + Subscription.FREE + "|" + Subscription.STANDARD + "|" + Subscription.PREMIUM + "$")
	public String getSubscriptionType() {
		return subscriptionType;
	}
	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}
	
	//Relationships
	@Valid
	@ManyToOne(optional = false)
	@NotNull
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}

	
	

}
