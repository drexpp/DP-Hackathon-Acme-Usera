package domain;



import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Customisation extends DomainEntity {
	
	private String bannerEs;
	private String bannerEn;
	private Integer standardPrice;
	private Integer premiumPrice;
	private Double	conversionRate;
	
	@NotNull
	@Max(1)
	public Double getConversionRate() {
		return conversionRate;
	}
	public void setConversionRate(Double conversionRate) {
		this.conversionRate = conversionRate;
	}
	
	@NotBlank
	public String getBannerEs() {
		return bannerEs;
	}
	public void setBannerEs(String bannerEs) {
		this.bannerEs = bannerEs;
	}
	
	@NotBlank
	public String getBannerEn() {
		return bannerEn;
	}
	public void setBannerEn(String bannerEn) {
		this.bannerEn = bannerEn;
	}
	
	@NotNull
	public Integer getStandardPrice() {
		return standardPrice;
	}
	public void setStandardPrice(Integer standardPrice) {
		this.standardPrice = standardPrice;
	}
	
	@NotNull
	public Integer getPremiumPrice() {
		return premiumPrice;
	}
	public void setPremiumPrice(Integer premiumPrice) {
		this.premiumPrice = premiumPrice;
	}
	
	
	
}
