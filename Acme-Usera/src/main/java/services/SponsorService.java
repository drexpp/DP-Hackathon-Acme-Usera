
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Advertisement;
import domain.MailMessage;
import domain.Sponsor;
import forms.ActorForm;

@Service
@Transactional
public class SponsorService {

	// Managed Repository
	@Autowired
	private SponsorRepository	sponsorRepository;
	
	@Autowired
	private FolderService folderService;

	@Autowired
	private Validator		validator;


	// Supporting services

	// Constructors

	public SponsorService() {
		super();
	}

	// Simple CRUD methods
	public Sponsor create() {
		Sponsor result;

		result = new Sponsor();
		result.setAdvertisements(new ArrayList<Advertisement>());
		result.setReceivedMessages(new ArrayList<MailMessage>());
		result.setSentMessages(new ArrayList<MailMessage>());
		result.setFolders(this.folderService.createSystemFolders());
		
		return result;
	}

	public Sponsor save(final Sponsor sponsor) {
		Sponsor saved;
		Assert.notNull(sponsor);

		if (sponsor.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			sponsor.getUserAccount().setPassword(passwordEncoder.encodePassword(sponsor.getUserAccount().getPassword(), null));
		}

		saved = this.sponsorRepository.save(sponsor);

		//TEST ASSERT - Testing if the user is in the system after saving him/her
		Assert.isTrue(this.sponsorRepository.findAll().contains(saved));
		//TEST ASSERT =========================================
		return saved;
	}

	public Sponsor findOne(final int sponsorId) {
		Sponsor result;
		result = this.sponsorRepository.findOne(sponsorId);
		return result;
	}

	public Collection<Sponsor> findAll() {
		Collection<Sponsor> result;
		result = this.sponsorRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	//Other business methods
	public Sponsor findByPrincipal() {
		Sponsor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;

	}

	public Sponsor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Sponsor result;
		result = this.sponsorRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public Sponsor reconstruct(final ActorForm actorForm, final BindingResult binding) {
		final Sponsor sponsor = this.create();
		sponsor.setName(actorForm.getName());
		sponsor.setSurname(actorForm.getSurname());
		sponsor.setEmail(actorForm.getEmail());
		sponsor.setId(actorForm.getId());
		sponsor.setAddress(actorForm.getAddress());
		sponsor.setVersion(actorForm.getVersion());
		sponsor.setPhone(actorForm.getPhone());
		sponsor.setUserAccount(actorForm.getUserAccount());
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		auth.setAuthority("SPONSOR");
		authorities.add(auth);
		sponsor.getUserAccount().setAuthorities(authorities);

		this.validator.validate(actorForm, binding);
		if (!(actorForm.getConfirmPassword().equals((actorForm.getUserAccount().getPassword()))) || actorForm.getConfirmPassword() == null)
			binding.rejectValue("confirmPassword", "user.passwordMiss");
		if ((actorForm.getCheck() == false))
			binding.rejectValue("check", "user.uncheck");
		return sponsor;
	}

	public void flush() {
		this.sponsorRepository.flush();
	}

}
