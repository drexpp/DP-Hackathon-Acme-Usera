package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import security.UserAccount;
import utilities.AbstractTest;
import domain.Sponsor;
import forms.ActorForm;
import forms.EditActorForm;


@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class SponsorServiceTest extends AbstractTest {
	
	@Autowired
	SponsorService		sponsorService;
	
	
	@Test
	public void driverRegisterSponsor(){
		//UC07-Registrar como patrocinador en el sistema
		Object testingData[][] = {
				//Test 1 positivo, probando el registro de un usuario con todos sus campos
				{"sponsorPrueba1", "sur1","email@prueba.com", "111222333", "calle1", "30/10/1987",true,"prueba1", "prueba1pass", "prueba1pass",null},
				//Test 2 negativo, probando el registro de un usuario con su campo "Nombre" vacio.
				{"", "sur2","email@prueba.com", "111222333", "calle2", "30/10/1987",true,"prueba2", "prueba2pass", "prueba2pass",NullPointerException.class},
				//Test 3 negativo, probando el registro de un usuario con su campo "Apellido" vacio.
				{"sponsorPrueba3", "","email@prueba.com", "111222333", "calle3", "30/10/1987",true,"prueba3", "prueba3pass", "prueba3pass",NullPointerException.class}
				
		};
		for(int i = 0; i < testingData.length; i++){
			this.startTransaction();
			templateRegisterSponsor(((String) testingData[i][0]),((String) testingData[i][1]),((String) testingData[i][2]),((String) testingData[i][3]),((String) testingData[i][4]),((String) testingData[i][5]),((Boolean) testingData[i][6]),((String) testingData[i][7]),((String) testingData[i][8]),((String) testingData[i][9]),((Class<?>) testingData[i][10]));
			this.rollbackTransaction();
		}
	}


	private void templateRegisterSponsor(String username,String surname, String email,String phone, String address, String dateBirth,Boolean checkTerms, String userAccountName, String userAccountPassword, String userAccountConfirmPassword, Class<?> expected) {
		Class<?> caught = null;
		Sponsor sponsor = this.sponsorService.create();
		
		//==== Registrar datos =========
		ActorForm actorForm = registeringActorForm(username, surname, email, phone, address, dateBirth, checkTerms, userAccountName, userAccountPassword, userAccountConfirmPassword);
		BindingResult binding = null;
		try{
			sponsor = this.sponsorService.reconstruct(actorForm, binding);
			sponsor = this.sponsorService.save(sponsor);
		}catch(Throwable oops){
			caught = oops.getClass();
		}
		
		checkExceptions(expected, caught);
	}
	
	protected ActorForm registeringActorForm(String name, String surname, String email,String phone, String address, String dateBirth,Boolean checkTerms, String userAccountName, String userAccountPassword, String userAccountConfirmPassword) {
		ActorForm actorForm = new ActorForm();
		Date d_dateBirth = null;
		if(!dateBirth.equals("")){
			try {				
				d_dateBirth = new SimpleDateFormat("dd/MM/yyyy").parse(dateBirth);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		
		actorForm.setName(name);
		actorForm.setSurname(surname);
		actorForm.setEmail(email);
		actorForm.setDateBirth(d_dateBirth);
		actorForm.setPhone(phone);
		actorForm.setAddress(address);
		
		UserAccount userAccount = new UserAccount();
		userAccount.setUsername(userAccountName);
		userAccount.setPassword(userAccountPassword);
		actorForm.setConfirmPassword(userAccountConfirmPassword);
		actorForm.setUserAccount(userAccount);
		
		actorForm.setCheck(checkTerms);	
		
		return actorForm;
	}
	
	@Test
	public void driverEditPersonalInfoSponsor(){
		//UC10-Mostrar y editar información personal como patrocinador
		Object testingData[][] = {
				//Test 1 positivo, probando el editar el perfil de un sponsor con un nuevo nombre y apellido.
				{"sponsor1","sponsor1","newName1", "newSurname1",null},
				//Test 2 negativo, probando el editar el perfil de un sponsor con el "Nombre" vacio
				{"sponsor1","sponsor1","", "newSurname2", NullPointerException.class},
				//Test 3 negativo, probando el editar el perfil de un sponsor con el "Apellido" vacio
				{"sponsor1","sponsor1","newName3", "",NullPointerException.class}
				
		};
		for(int i = 0; i < testingData.length; i++){
			this.startTransaction();
			templateEditPersonalInfoSponsor(((String) testingData[i][0]), super.getEntityId((String) testingData[i][1]),((String) testingData[i][2]),((String) testingData[i][3]),((Class<?>) testingData[i][4]));
			this.rollbackTransaction();
		}
	}


	protected void templateEditPersonalInfoSponsor(String SponsorAccount, int SponsorId, String newName,
			String newSurname, Class<?> expected) {
		Class<?> caught;
		caught = null;
		BindingResult binding;
		EditActorForm editActorForm;
		binding = null;
		Sponsor principal;
		super.authenticate(SponsorAccount);
		//Obteniendo el editActorForm a partir del sponsor (como se haría en el controlador y servicio).
		principal = this.sponsorService.findOne(SponsorId);
		editActorForm = generateAndEditActorFormFromSponsor(principal, newName, newSurname);
		
		try{
			Sponsor sponsorToSave;
			Sponsor sponsorSaved;
			sponsorToSave = this.sponsorService.reconstruct(editActorForm, binding);
			sponsorSaved = this.sponsorService.save(sponsorToSave);
			Assert.isTrue(sponsorSaved.getName().equals(newName));
			Assert.isTrue(sponsorSaved.getSurname().equals(newSurname));
		}catch(Throwable oops){
			caught = oops.getClass();
		}
		
		checkExceptions(expected, caught);
	}


	protected EditActorForm generateAndEditActorFormFromSponsor(Sponsor principal, String newName, String newSurname) {
		EditActorForm result = new EditActorForm();
		
		result.setId(principal.getId());
		result.setName(newName);
		result.setSurname(newSurname);
		result.setAddress(principal.getAddress());
		result.setDateBirth(principal.getDateBirth());
		result.setPhone(principal.getPhone());
		result.setEmail(principal.getEmail());
		
		return result;
	}

}
