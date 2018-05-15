package domain;


import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.Valid;

import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Customisation extends DomainEntity {
	
	private String banner;
	private Integer normalPrize;
	private Integer premiumPrize;
	private Collection<Category> categories;
	
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


	//Relationships
	@Valid
	@ElementCollection
	public Collection<Category> getCategories() {
		return categories;
	}
	public void setCategories(Collection<Category> categories) {
		this.categories = categories;
	}
	
}
