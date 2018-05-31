package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Admin;
import domain.Folder;
import domain.MailMessage;

@Service
@Transactional
public class MessageService {

	// Managed Repository
	@Autowired
	private MessageRepository		messageRepository;

	// Supporting services
	@Autowired
	private AdminService			adminService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private FolderService			folderService;


	// Constructors

	public MessageService() {
		super();
	}

	// Simple CRUD methods
	public MailMessage create() {
		MailMessage result;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = new MailMessage();
		result.setSender(principal);
		result.setFolder(this.folderService.findOutBoxFolderActor(principal));
		result.setMoment(new Date(System.currentTimeMillis() - 1));

		return result;
	}

	// An actor who is authenticated must be able to exchange messages with other actors
	public MailMessage save(final MailMessage message) {
		MailMessage result;
		Actor principal;
		Date moment;
		Folder folder;
		MailMessage copy;

		Assert.notNull(message);

		principal = this.actorService.findByPrincipal();

		Assert.notNull(principal);

		moment = new Date(System.currentTimeMillis() - 1);


		message.setMoment(moment);
		message.setSender(principal);
	
		folder = this.folderService.findInBoxFolderActor(message.getRecipient());
		Assert.notNull(folder);
		message.setFolder(folder);

		result = this.messageRepository.save(message);
		Assert.notNull(result);

		copy = new MailMessage();
		copy.setSubject(message.getSubject());
		copy.setBody(message.getBody());
		copy.setMoment(message.getMoment());
		copy.setRecipient(message.getRecipient());
		copy.setSender(message.getSender());
		copy.setFolder(this.folderService.findOutBoxFolderActor(principal));
		this.messageRepository.save(copy);

		/*
		 * Collection<Message> sentMessagesUpdated;
		 * Collection<Message> receivedMessagesUpdated;
		 * 
		 * receivedMessagesUpdated = copy.getRecipient().getReceivedMessages();
		 * sentMessagesUpdated = principal.getSentMessages();
		 * 
		 * 
		 * receivedMessagesUpdated.remove(m);
		 * sentMessagesUpdated.remove(m);
		 * copy.getRecipient().setReceivedMessages(receivedMessagesUpdated);
		 * principal.setSentMessages(sentMessagesUpdated);
		 */

		return result;

	}

	public void delete(final MailMessage message) {
		Actor principal;
		Folder folder;
		Folder trashBox;

		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(principal.getReceivedMessages().contains(message) || principal.getSentMessages().contains(message));

		folder = message.getFolder();

		if (folder.getIsSystem() && folder.getName().equals("trash box"))
			this.messageRepository.delete(message);
		else {
			trashBox = this.folderService.findTrashBoxFolderActor(principal);
			this.move(message, trashBox);

		}

	}

	// Other business methods

	public void move(final MailMessage message, final Folder destination) {

		Actor principal;
		Folder origin;
		Collection<MailMessage> updatedOriginFolder;
		Collection<MailMessage> updatedDestinationFolder;
		MailMessage m;

		Assert.notNull(message);
		Assert.notNull(destination);

		Assert.isTrue(message.getId() != 0);
		Assert.isTrue(destination.getId() != 0);

		origin = message.getFolder();

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(principal.getReceivedMessages().contains(message) || principal.getSentMessages().contains(message));
		Assert.isTrue(principal.getFolders().contains(origin));
		Assert.isTrue(principal.getFolders().contains(destination));

		message.setFolder(destination);
		m = this.messageRepository.save(message);

		updatedOriginFolder = origin.getMessages();
		updatedDestinationFolder = destination.getMessages();

		updatedOriginFolder.remove(m);
		updatedDestinationFolder.add(m);

		origin.setMessages(updatedOriginFolder);
		destination.setMessages(updatedDestinationFolder);

	}

	public void broadcast(final MailMessage m) {
		Admin principle;
		String subject, body;
		Collection<Actor> actors;
		Date currentMoment;
		MailMessage outboxMessage;

		Assert.notNull(m);

		principle = this.adminService.findByPrincipal();
		Assert.notNull(principle);

		subject = m.getSubject();
		body = m.getBody();


		currentMoment = new Date(System.currentTimeMillis() - 1);

		actors = this.actorService.findAll();
		for (final Actor actor : actors)
			if (!(actor instanceof Admin || actor.getName().equals("DELETED/BORRADO"))) {
				final MailMessage message = new MailMessage();
				message.setSubject(subject);
				message.setBody(body);
				message.setSender(principle);
				message.setRecipient(actor);
				message.setMoment(currentMoment);
				message.setFolder(this.folderService.findInBoxFolderActor(actor));
				this.messageRepository.save(message);
			}

		//mensaje guardado en el out box del admin que realizó el broadcast:
		outboxMessage = new MailMessage();
		outboxMessage.setSubject(subject);
		outboxMessage.setBody(body);
		outboxMessage.setSender(principle);
		outboxMessage.setMoment(currentMoment);
		outboxMessage.setFolder(this.folderService.findOutBoxFolderActor(principle));
		//El receptor del mensaje es el mismo que lo envía, así podemos distinguir los mensajes normales que envía un admin
		// de los mensajes broadcast que envía ese admin
		outboxMessage.setRecipient(principle);
		this.messageRepository.save(outboxMessage);
	}

	public MailMessage findOne(final int messageId) {
		MailMessage result;

		result = this.messageRepository.findOne(messageId);
		Assert.notNull(result);

		return result;
	}

	public MailMessage createAndSaveNotificationStatusApplicationChanged(final Actor actor, final String body, final Date moment) {
		MailMessage result;

		result = new MailMessage();
		result.setSender(actor);
		result.setRecipient(actor);
		result.setMoment(moment);
		result.setFolder(this.folderService.findInBoxFolderActor(actor));
		result.setBody(body);
		result.setSubject("New status update");

		this.messageRepository.save(result);

		return result;
	}
	
	public void flush(){
		this.messageRepository.flush();
	}
}