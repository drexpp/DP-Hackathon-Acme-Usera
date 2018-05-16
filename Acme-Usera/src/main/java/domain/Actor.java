
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

	private String		name;
	private String		surname;
	private String		email;
	private String		phone;
	private String		address;
	private Date		dateBirth;
	private UserAccount	userAccount;
	private Collection<MailMessage>			sentMessages;
	private Collection<MailMessage>			receivedMessages;
	private Collection<Folder>			folders;
	private Collection<Answer>	answers;
	

	
	
	@Past
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getDateBirth() {
		return this.dateBirth;
	}

	public void setDateBirth(final Date dateBirth) {
		this.dateBirth = dateBirth;
	}
	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}
	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String emails) {
		this.email = emails;
	}

	
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phones) {
		this.phone = phones;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String addresses) {
		this.address = addresses;
	}
	
	@ElementCollection
	@OneToMany(mappedBy = "sender")
	public Collection<MailMessage> getSentMessages() {
		return sentMessages;
	}
	public void setSentMessages(Collection<MailMessage> sentMessages) {
		this.sentMessages = sentMessages;
	}

	@ElementCollection
	@OneToMany(mappedBy = "recipient")
	public Collection<MailMessage> getReceivedMessages() {
		return receivedMessages;
	}
	public void setReceivedMessages(Collection<MailMessage> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}

	@ElementCollection
	@OneToMany
	public Collection<Folder> getFolders() {
		return folders;
	}

	public void setFolders(Collection<Folder> folders) {
		this.folders = folders;
	}


	@NotNull
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@Valid
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
	@ElementCollection
	@OneToMany
	public Collection<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Collection<Answer> answers) {
		this.answers = answers;
	}

	
}
