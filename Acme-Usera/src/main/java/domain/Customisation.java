package domain;



import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Customisation extends DomainEntity {
	
	private String bannerEs;
	private String bannerEn;
	private Integer standardPrice;
	private Integer premiumPrice;
	
	@URL
	@NotBlank
	public String getBannerEs() {
		return bannerEs;
	}
	public void setBannerEs(String bannerEs) {
		this.bannerEs = bannerEs;
	}
	
	@URL
	@NotBlank
	public String getBannerEn() {
		return bannerEn;
	}
	public void setBannerEn(String bannerEn) {
		this.bannerEn = bannerEn;
	}
	
	@NotNull
	@Range(min=0)
	public Integer getStandardPrice() {
		return standardPrice;
	}
	public void setStandardPrize(Integer standardPrice) {
		this.standardPrice = standardPrice;
	}
	
	@NotNull
	@Range(min=0)
	public Integer getPremiumPrice() {
		return premiumPrice;
	}
	public void setPremiumPrice(Integer premiumPrice) {
		this.premiumPrice = premiumPrice;
	}
	
}
