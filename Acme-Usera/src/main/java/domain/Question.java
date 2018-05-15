package domain;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Question extends DomainEntity {

	private String		title;
	private String		question;
	private Date 		moment;
	private String		photoURL;
	private Boolean		isAnswered;
	private Forum 		forum;
	private Student		student;
	private Collection<Answer> answers;
	
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	@Past
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	@URL
	public String getPhotoURL() {
		return photoURL;
	}
	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}
	public Boolean getIsAnswered() {
		return isAnswered;
	}
	public void setIsAnswered(Boolean isAnswered) {
		this.isAnswered = isAnswered;
	}
	
	@Valid
	@OneToOne(optional = false)
	public Forum getForum() {
		return forum;
	}
	public void setForum(Forum forum) {
		this.forum = forum;
	}
	@ManyToOne(optional = false)
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	@OneToMany(mappedBy="question", cascade = CascadeType.ALL)
	public Collection<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(Collection<Answer> answers) {
		this.answers = answers;
	}
	
	
}
