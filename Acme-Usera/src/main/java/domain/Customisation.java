package domain;


import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Customisation extends DomainEntity {
	
	private String banner;
	private Integer normalPrize;
	private Integer premiumPrize;
	
	@URL
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
	
	
	public Integer getNormalPrize() {
		return normalPrize;
	}
	public void setNormalPrize(Integer normalPrize) {
		this.normalPrize = normalPrize;
	}
	
	
	public Integer getPremiumPrize() {
		return premiumPrize;
	}
	public void setPremiumPrize(Integer premiumPrize) {
		this.premiumPrize = premiumPrize;
	}


	
}
