
package services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdminRepository;
import security.LoginService;
import security.UserAccount;
import domain.Admin;

@Service
@Transactional
public class AdminService {

	// Managed Repository
	@Autowired
	private AdminRepository	adminRepository;


	// Supporting services

	// Constructors

	public AdminService() {
		super();
	}

	// Simple CRUD methods
	public Admin create() {
		Admin principal;
		Admin result;
		principal = this.findByPrincipal();
		Assert.notNull(principal);
		result = new Admin();
		return result;
	}

	public Admin save(final Admin Admin) {
		Admin saved;
		Assert.notNull(Admin);

		if (Admin.getId() == 0) {
			Admin principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			Admin.getUserAccount().setPassword(passwordEncoder.encodePassword(Admin.getUserAccount().getPassword(), null));
		} else {
			Admin principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);

		}

		saved = this.adminRepository.save(Admin);

		return saved;
	}

	public Admin findOne(final int AdminId) {
		Admin result;
		result = this.adminRepository.findOne(AdminId);
		return result;
	}

	public Admin findByPrincipal() {
		Admin result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;

	}

	public Admin findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Admin result;
		result = this.adminRepository.findByUserAccountId(userAccount.getId());
		return result;
	}


}
