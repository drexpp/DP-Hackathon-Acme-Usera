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
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class ExamPaper extends DomainEntity {
	
	private	Date	moment;
	private Double 	score;
	private Integer mark;
	private Exam exam;
	private Certification certification;
	private Collection<ExamAnswer> exmanAnswer;
	
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	@Min(value = 0)
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
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
	public Collection<ExamAnswer> getExmanAnswer() {
		return exmanAnswer;
	}
	public void setExmanAnswer(Collection<ExamAnswer> exmanAnswer) {
		this.exmanAnswer = exmanAnswer;
	}

	
	
}
