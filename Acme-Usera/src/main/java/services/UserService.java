
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

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.User;
import forms.ActorForm;

@Service
@Transactional
public class UserService {

	// Managed Repository
	@Autowired
	private UserRepository	UserRepository;
	@Autowired
	private Validator		validator;


	// Supporting services

	// Constructors

	public UserService() {
		super();
	}

	// Simple CRUD methods
	public User create() {
		User result;

		result = new User();

		return result;
	}

	public User save(final User User) {
		User saved;
		Assert.notNull(User);

		if (User.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			User.getUserAccount().setPassword(passwordEncoder.encodePassword(User.getUserAccount().getPassword(), null));
		} else {
			User principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);

		}

		saved = this.UserRepository.save(User);

		return saved;
	}

	public User findOne(final int UserId) {
		User result;
		result = this.UserRepository.findOne(UserId);
		return result;
	}

	public Collection<User> findAll() {
		Collection<User> result;
		result = this.UserRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	//Other business methods
	public User findByPrincipal() {
		User result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;

	}

	public User findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		User result;
		result = this.UserRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public User reconstruct(final ActorForm actorForm, final BindingResult binding) {
		final User user = this.create();
		user.setName(actorForm.getName());
		user.setSurname(actorForm.getSurname());
		user.setEmail(actorForm.getEmail());
		user.setId(actorForm.getId());
		user.setVersion(actorForm.getVersion());
		user.setPhone(actorForm.getPhone());
		user.setUserAccount(actorForm.getUserAccount());
		user.setDateBirth(actorForm.getDateBirth());
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		auth.setAuthority("USER");
		authorities.add(auth);
		user.getUserAccount().setAuthorities(authorities);

		this.validator.validate(actorForm, binding);
		if (!(actorForm.getConfirmPassword().equals((actorForm.getUserAccount().getPassword()))) || actorForm.getConfirmPassword() == null)
			binding.rejectValue("confirmPassword", "user.passwordMiss");
		if ((actorForm.getCheck() == false))
			binding.rejectValue("check", "user.uncheck");
		return user;
	}
}
