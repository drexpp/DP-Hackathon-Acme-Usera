package domain;


import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Student extends Actor {
	
	private Integer score;
	private Collection<Tutorial> tutorials;

	private Collection<Question> questions;
	private Collection <Subscription> subscriptions;
	private Collection<Certification> certifications;

	@Min(value = 0)
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	@Valid
	@OneToMany(mappedBy="student")
	public Collection<Tutorial> getTutorials() {
		return tutorials;
	}

	public void setTutorials(Collection<Tutorial> tutorials) {
		this.tutorials = tutorials;
	}

	@Valid
	@OneToMany (mappedBy="student")
	public Collection<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Collection<Question> questions) {
		this.questions = questions;
	}
	@Valid
	@OneToMany (mappedBy="student")
	public Collection<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(Collection<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}
	@Valid
	@OneToMany (mappedBy="student")
	public Collection<Certification> getCertifications() {
		return certifications;
	}

	public void setCertifications(Collection<Certification> certifications) {
		this.certifications = certifications;
	}

	
	
	
}
