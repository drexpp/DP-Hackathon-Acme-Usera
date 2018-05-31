package forms;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import domain.DomainEntity;
import domain.Exam;
@Entity
@Access(AccessType.PROPERTY)
public class ExamPaperForm extends DomainEntity {
	
	private Date		moment;
	private Integer 	mark;
	private Exam 		exam;
	private Boolean 	isFinished;


	
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
	
	
}
