package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class ExamAnswer extends DomainEntity {

	private Double mark;
	private String text;
	
	@Range(min = 0, max = 100)
	public Double getMark() {
		return mark;
	}
	public void setMark(Double mark) {
		this.mark = mark;
	}
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
	
}
