
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import domain.Actor;
import domain.Folder;
import domain.MailMessage;

@Service
@Transactional
public class FolderService {

	// Managed Repository
	@Autowired
	private FolderRepository	folderRepository;

	// Supporting services

	@Autowired
	private ActorService		actorService;


	// Constructors

	public FolderService() {
		super();
	}

	// Simple CRUD methods

	public Folder create() {
		Folder result;
		Actor principal;
		Collection<MailMessage> messages;

		messages = new ArrayList<MailMessage>();

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Folder();
		Assert.notNull(result);

		result.setMessages(messages);

		return result;
	}

	public Folder save(final Folder folder) {
		Folder result;
		Actor principal;
		Collection<MailMessage> messages;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		if (folder.getId() == 0) {
			
			Assert.isTrue(folder.getName() != "");
			folder.setIsSystem(false);
			messages = new ArrayList<MailMessage>();
			folder.setMessages(messages);

		} else {

			Assert.isTrue(!folder.getIsSystem());
			Assert.isTrue(principal.getFolders().contains(folder));

			

		}
		result = this.folderRepository.save(folder);
		Assert.notNull(result);

		if (!principal.getFolders().contains(result))
			principal.getFolders().add(result);

		return result;

	}
	/*
	 * public Folder saveSystemFolder(final Folder folder) {
	 * Administrator principal;
	 * Folder saved;
	 * 
	 * principal = this.administratorService.findByPrincipal();
	 * Assert.notNull(principal);
	 * 
	 * Assert.notNull(folder);
	 * Assert.isTrue(folder.getIsSystem());
	 * 
	 * saved = this.folderRepository.save(folder);
	 * Assert.notNull(saved);
	 * 
	 * return saved;
	 * 
	 * }
	 */
	public void delete(final Folder folder) {
		Actor principal;
		Assert.isTrue(folder.getId() != 0);

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(!folder.getIsSystem());
		Assert.isTrue(principal.getFolders().contains(folder));


		this.folderRepository.delete(folder);
	}

	public List<Folder> createSystemFolders() {

		List<Folder> result;
		List<String> names;
		Collection<MailMessage> messages;
		Folder saved;

		names = new ArrayList<String>();
		names.add("in box");
		names.add("out box");
		names.add("trash box");

		result = new ArrayList<Folder>();
		for (final String name : names) {
			final Folder folder = new Folder();
			folder.setName(name);
			folder.setIsSystem(true);
			messages = new ArrayList<MailMessage>();
			folder.setMessages(messages);
			saved = this.folderRepository.save(folder);
			Assert.notNull(saved);

			result.add(saved);

		}

		return result;

	}

	// Other business methods

	public Folder findInBoxFolderActor(final Actor a) {
		Actor principal;
		Folder result;

		principal = this.actorService.findByPrincipal();

		Assert.notNull(principal);
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);

		result = this.folderRepository.findInBoxFolderActorId(a.getId());
		Assert.notNull(result);
		return result;
	}

	public Folder findOutBoxFolderActor(final Actor a) {
		Actor principal;
		Folder result;

		principal = this.actorService.findByPrincipal();

		Assert.notNull(principal);
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);

		result = this.folderRepository.findOutBoxFolderActorId(a.getId());
		Assert.notNull(result);
		return result;
	}

	public Folder findTrashBoxFolderActor(final Actor a) {
		Actor principal;
		Folder result;
		principal = this.actorService.findByPrincipal();

		Assert.notNull(principal);
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);

		result = this.folderRepository.findTrashFolderActorId(a.getId());
		Assert.notNull(result);
		return result;
	}

	public Collection<Folder> findAllByPrincipal() {
		Collection<Folder> result;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		result = this.folderRepository.findAllByPrincipal(principal.getId());
		Assert.notNull(result);
		return result;
	}
	public Folder findOne(final int folderId) {
		Folder result;
		Actor principal;
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		result = this.folderRepository.findOne(folderId);
		Assert.notNull(result);
		//		Assert.isTrue(result.getOwner().equals(principal));
		return result;
	}

}
