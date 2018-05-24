package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Folder extends DomainEntity {

	private String				name;
	private Boolean				isSystem;
	private Collection<MailMessage>	messages;
	

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	public Boolean getIsSystem() {
		return this.isSystem;
	}
	public void setIsSystem(final Boolean isSystem) {
		this.isSystem = isSystem;
	}

	@NotNull
	@OneToMany(mappedBy = "folder")
	public Collection<MailMessage> getMessages() {
		return this.messages;
	}
	public void setMessages(final Collection<MailMessage> messages) {
		this.messages = messages;
	}



}
