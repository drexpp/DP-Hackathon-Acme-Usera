package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
		@Index(columnList = "name")
	})
public class Category extends DomainEntity {

	private String name;
	private Collection<Category>		parentCategories;
	private Collection<Category>		childCategories;
	private Collection<Course>			courses;
	
	
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull
	@ManyToMany
	public Collection<Category> getParentCategories() {
		return parentCategories;
	}
	public void setParentCategories(Collection<Category> parentCategories) {
		this.parentCategories = parentCategories;
	}
	
	@NotNull
	@ManyToMany(mappedBy = "parentCategories")
	public Collection<Category> getChildCategories() {
		return childCategories;
	}
	public void setChildCategories(Collection<Category> childCategories) {
		this.childCategories = childCategories;
	}
	

	@NotNull
	@OneToMany(mappedBy = "category")
	public Collection<Course> getCourses() {
		return courses;
	}
	public void setCourses(Collection<Course> courses) {
		this.courses = courses;
	}


}

	
	

