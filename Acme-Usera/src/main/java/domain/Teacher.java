package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Teacher extends Actor {
	
	private Collection<Tutorial> tutorials;
	private ContactInfo	contactInfo;
	private Collection<Lesson> lessons;
	private Collection<Exam> exams;
	private Collection<Course> coursesCreated;
	private Collection<Course> coursesJoined;
	private Curriculum	curriculum;

	
	
	@NotNull
	@OneToMany(mappedBy="teacher")
	public Collection<Tutorial> getTutorials() {
		return tutorials;
	}
	public void setTutorials(Collection<Tutorial> tutorials) {
		this.tutorials = tutorials;
	}
	
	@NotNull
	@Valid
	@OneToOne(optional = false)
	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}
	
	//Relationships
	
	@Valid
	@OneToMany(mappedBy="teacher")
	public Collection<Lesson> getLessons() {
		return lessons;
	}
	public void setLessons(Collection<Lesson> lessons) {
		this.lessons = lessons;
	}
	@OneToMany(mappedBy="teacher")
	public Collection<Exam> getExams() {
		return exams;
	}
	public void setExams(Collection<Exam> exams) {
		this.exams = exams;
	}
	@OneToMany(mappedBy="creator")
	public Collection<Course> getCoursesCreated() {
		return coursesCreated;
	}
	public void setCoursesCreated(Collection<Course> coursesCreated) {
		this.coursesCreated = coursesCreated;
	}
	@ManyToMany
	public Collection<Course> getCoursesJoined() {
		return coursesJoined;
	}
	public void setCoursesJoined(Collection<Course> coursesJoined) {
		this.coursesJoined = coursesJoined;
	}
	@OneToOne(optional = true)
	public Curriculum getCurriculum() {
		return curriculum;
	}
	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}
	
	
	
	
	
}
