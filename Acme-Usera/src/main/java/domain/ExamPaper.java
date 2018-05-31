package domain;

import java.util.Collection;
import java.util.Date;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class ExamPaper extends DomainEntity {
	
	private	Date					moment;
	private Integer					mark;
	private Boolean 				isFinished;
	private Exam 					exam;
	private Certification 			certification;
	private Collection<ExamAnswer> 	examAnswer;
	private Student 				student;
	
	
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	@Range(min = 0, max = 100)
	public Integer getMark() {
		return mark;
	}
	public void setMark(Integer mark) {
		this.mark = mark;
	}
	
	@NotNull
	public Boolean getIsFinished() {
		return this.isFinished;
	}

	public void setIsFinished(final Boolean isFinished) {
		this.isFinished = isFinished;
	}
	
	
	//Relationships
	
	@Valid
	@ManyToOne(optional = false)
	public Exam getExam() {
		return exam;
	}
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	@Valid
	@OneToOne(optional = true)
	public Certification getCertification() {
		return certification;
	}
	public void setCertification(Certification certification) {
		this.certification = certification;
	}
	
	@Valid
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "examPaper")
	public Collection<ExamAnswer> getExamAnswer() {
		return examAnswer;
	}
	public void setExamAnswer(Collection<ExamAnswer> examAnswer) {
		this.examAnswer = examAnswer;
	}

	@Valid
	@ManyToOne(optional = false)
	public Student getStudent(){
		return student;
	}
	public void setStudent(Student student){
		this.student = student;
	}
}
