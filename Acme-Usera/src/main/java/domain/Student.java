package domain;


import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Student extends Actor {
	
	private Integer score;
	private Collection<Tutorial> tutorials;
	private Collection<Lesson>	lessons;
	private Collection<Question> questions;
	private Collection<Subscription> subscriptions;
	private Collection<Certification> certifications;
	private Collection<ExamPaper> examPapers;

	

	@Min(value = 0)
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	
	//Relationships
	@Valid
	@OneToMany(mappedBy="student")
	public Collection<Tutorial> getTutorials() {
		return tutorials;
	}

	public void setTutorials(Collection<Tutorial> tutorials) {
		this.tutorials = tutorials;
	}

	@Valid
	@ManyToMany
	public Collection<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(Collection<Lesson> lessons) {
		this.lessons = lessons;
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

	@Valid
	@OneToMany(mappedBy="student")
	public Collection<ExamPaper> getExamPapers() {
		return examPapers;
	}

	public void setExamPapers(Collection<ExamPaper> examPapers) {
		this.examPapers = examPapers;
	}
	
	
}
