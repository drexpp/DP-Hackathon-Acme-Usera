package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;

import domain.DomainEntity;
import domain.ExamPaper;

@Entity
@Access(AccessType.PROPERTY)
public class ExamAnswerForm extends DomainEntity {

	private String text;
	private Integer number;
	private ExamPaper examPaper;
	
	
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@NotNull
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	//Relationships
	@Valid
	@ManyToOne(optional = false)
	public ExamPaper getExamPaper() {
		return examPaper;
	}
	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}
	
}
