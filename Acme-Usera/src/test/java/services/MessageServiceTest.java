package services;



import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Actor;
import domain.Folder;
import domain.MailMessage;

import utilities.AbstractTest;


@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class MessageServiceTest extends AbstractTest {
	
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private FolderService folderService;
	
	@Test
	public void writeMessageAndMove(){
		final Object testingData[][] = {

				//TEST POSITIVO
				//El student1 envía un mensaje al student2 y él lo mueve a una carpeta de su propiedad
				{"student1","Hola", "esto es el test1","student2","folder12", null}, 
				//
				//==========================================================================//

				//TEST NEGATIVO
				//El student1 envia un mensaje con el titulo vacio (no es posible)
				{"student1", "", "esto es el test2","student2","folder12", ConstraintViolationException.class},
				//El student1 envía un mensaje con el campo cuerpo vacio (no es posible)
				{"student1","Hola","", "student2","folder12", ConstraintViolationException.class}
				
		};
		
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.templateWriteMessageAndMove((String) testingData[i][0],(String) testingData[i][1],(String) testingData[i][2], super.getEntityId((String) testingData[i][3]), super.getEntityId((String) testingData[i][4]),(Class<?>) testingData[i][5]);
			this.rollbackTransaction();
		}
}

	private void templateWriteMessageAndMove(String username, String messageSubject, String messageBody,
			int recipentId, int folderId, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			
			super.authenticate(username);
			
			Actor recipent = this.actorService.findOne(recipentId);
			MailMessage message = this.messageService.create();
			message.setRecipient(recipent);
			message.setSubject(messageSubject);
			message.setBody(messageBody);
			
			MailMessage result = this.messageService.save(message);
			this.messageService.flush();
			super.unauthenticate();
			
			//Move message
			
			super.authenticate(recipent.getUserAccount().getUsername());
			Folder folder = this.folderService.findOne(folderId);
			this.messageService.move(result, folder);
			super.unauthenticate();
			
			 

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	
	@Test
	public void writeBroadcastMessage(){
		final Object testingData[][] = {

				//TEST POSITIVO
				//El admin envía un mensaje a todos los usuarios
				{"admin","Hola", "esto es el test1", null}, 
				//
				//==========================================================================//

				//TEST NEGATIVO
				//
				//El user1 intenta enviar un mensaje que llegue a todos los usuarios
				{"student1", "Hola", "esto es el test2", IllegalArgumentException.class},
				//El customer1 intenta enviar un mensaje que llegue a todos los usuarios
				{"sponsor1","Hola","esto es el test3", IllegalArgumentException.class}
		};
		
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			this.templateWriteBroadcastMessage((String) testingData[i][0],(String) testingData[i][1],(String) testingData[i][2], (Class<?>) testingData[i][3]);
			this.rollbackTransaction();
		}
}

	private void templateWriteBroadcastMessage(String username, String messageSubject, String messageBody, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			
			this.actorService.findByPrincipal();
			MailMessage message = this.messageService.create();
			message.setSubject(messageSubject);
			message.setBody(messageBody);
			
			this.messageService.broadcast(message);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

		super.unauthenticate();
	}
}
