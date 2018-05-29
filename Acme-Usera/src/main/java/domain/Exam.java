package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Exam extends DomainEntity {
	
	private String title;
	private Integer mark;
	private Collection<ExamQuestion> examQuestions;
	private Teacher teacher;
	private Course course;
	private Collection<ExamPaper> examPaper;
	
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Range(min = 0, max = 100)
	public Integer getMark() {
		return mark;
	}
	public void setMark(Integer mark) {
		this.mark = mark;
	}
	
	//Relationships
	
	@Valid
	@OneToMany(mappedBy="exam", cascade = CascadeType.ALL)
	public Collection<ExamQuestion> getExamQuestions() {
		return examQuestions;
	}
	public void setExamQuestions(Collection<ExamQuestion> examQuestions) {
		this.examQuestions = examQuestions;
	}
	@Valid
	@ManyToOne(optional = false)
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	@Valid
	@OneToOne(optional = false)
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	@Valid
	@OneToMany(mappedBy="exam")
	public Collection<ExamPaper> getExamPaper() {
		return examPaper;
	}
	public void setExamPaper(Collection<ExamPaper> examPaper) {
		this.examPaper = examPaper;
	}
	
	

}
