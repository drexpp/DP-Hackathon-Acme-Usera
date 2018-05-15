package domain;

import java.sql.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;
@Entity
@Access(AccessType.PROPERTY)
public class Lesson extends DomainEntity {
	
	private String		title;
	private Date 		creationDate;
	private String		body;
	private String		description;
	private String		photoURL;
	private String		videoURL;
	
	
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@URL
	public String getPhotoURL() {
		return photoURL;
	}
	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}
	@URL
	public String getVideoURL() {
		return videoURL;
	}
	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}
	
	

}
