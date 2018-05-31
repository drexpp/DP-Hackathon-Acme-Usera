package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;

import security.UserAccount;
import utilities.AbstractTest;
import domain.Teacher;
import forms.ActorFormTeacher;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class TeacherServiceTest extends AbstractTest {
	
	@Autowired
	TeacherService		teacherService;
	
	
	@Test
	public void driverRegisterSponsor(){
		Object testingData[][] = {
				//Test 1 positivo, probando el registro de un usuario con todos sus campos
				{"teacherPrueba1", "sur1","email@prueba.com", "111222333", "calle1", "30/10/1987",true,"prueba1", "prueba1pass", "prueba1pass", "skype1", "619110254", "comment1", "link1",null},
				//Test 2 negativo, probando el registro de un usuario con su campo "Nombre" vacio.
				{"", "sur2","email@prueba.com", "111222333", "calle2", "30/10/1987",true,"prueba2", "prueba2pass", "prueba2pass","skype2", "+619110254", "comment1", "link1", NullPointerException.class},
				//Test 3 negativo, probando el registro de un usuario con su campo "Apellido" vacio.
				{"teacherPrueba3", "","email@prueba.com", "111222333", "calle3", "30/10/1987",true,"prueba3", "prueba3pass", "prueba3pass","skype3", "619110254", "comment1", "link1", NullPointerException.class},
				
		};
		for(int i = 0; i < testingData.length; i++){
			this.startTransaction();
			templateRegisterSponsor(((String) testingData[i][0]),((String) testingData[i][1]),((String) testingData[i][2]),((String) testingData[i][3]),((String) testingData[i][4]),((String) testingData[i][5]),((Boolean) testingData[i][6]),((String) testingData[i][7]),((String) testingData[i][8]),((String) testingData[i][9]),((String) testingData[i][10]),((String) testingData[i][11]),((String) testingData[i][12]),((String) testingData[i][13]) ,((Class<?>) testingData[i][14]));
			this.rollbackTransaction();
		}
	}


	private void templateRegisterSponsor(String username,String surname, String email,String phone, String address, String dateBirth,Boolean checkTerms, String userAccountName, String userAccountPassword, String userAccountConfirmPassword, String skype, String contactPhone, String comment1, String link1, Class<?> expected) {
		Class<?> caught = null;
		Teacher teacher = this.teacherService.create();
		
		//==== Registrar datos =========
		ActorFormTeacher actorFormTeacher = registeringActorForm(username, surname, email, phone, address, dateBirth, checkTerms, userAccountName, userAccountPassword, userAccountConfirmPassword, skype, contactPhone, comment1, link1);
		BindingResult binding = null;
		try{
			teacher = this.teacherService.reconstruct(actorFormTeacher, binding);
			teacher = this.teacherService.save(teacher);
		}catch(Throwable oops){
			caught = oops.getClass();
		}
		
		checkExceptions(expected, caught);
	}
	
	protected ActorFormTeacher registeringActorForm(String name, String surname, String email,String phone, String address, String dateBirth,Boolean checkTerms, String userAccountName, String userAccountPassword, String userAccountConfirmPassword, String skype, String contactPhone, String comment1, String link1) {
		ActorFormTeacher actorFormTeacher = new ActorFormTeacher();
		Date d_dateBirth = null;
		List<String> comments = new ArrayList<String>();
		comments.add(comment1);
		List<String> links = new ArrayList<String>();
		comments.add(link1);
		
		if(!dateBirth.equals("")){
			try {				
				d_dateBirth = new SimpleDateFormat("dd/MM/yyyy").parse(dateBirth);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		
		actorFormTeacher.setName(name);
		actorFormTeacher.setSurname(surname);
		actorFormTeacher.setEmail(email);
		actorFormTeacher.setDateBirth(d_dateBirth);
		actorFormTeacher.setPhone(phone);
		actorFormTeacher.setAddress(address);
		
		UserAccount userAccount = new UserAccount();
		userAccount.setUsername(userAccountName);
		userAccount.setPassword(userAccountPassword);
		actorFormTeacher.setConfirmPassword(userAccountConfirmPassword);
		actorFormTeacher.setUserAccount(userAccount);
		
		actorFormTeacher.setCheck(checkTerms);
		actorFormTeacher.setSkype(skype);
		actorFormTeacher.setContactPhone(contactPhone);
		
		actorFormTeacher.setComments(comments);
		actorFormTeacher.setLinks(links);
		
		return actorFormTeacher;
	}
}
