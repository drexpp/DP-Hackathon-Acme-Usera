package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;

import domain.Student;

import forms.ActorForm;
import forms.EditActorForm;

import security.UserAccount;
import utilities.AbstractTest;


@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class StudentServiceTest extends AbstractTest {
	
	@Autowired
	StudentService		studentService;
	
	
	@Test
	public void driverRegisterStudent(){
		Object testingData[][] = {
				//Test 1 positivo, probando el registro de un usuario con todos sus campos
				{"studentPrueba1", "sur1","email@prueba.com", "111222333", "calle1", "30/10/1987",true,"prueba1", "prueba1pass", "prueba1pass",null},
				//Test 2 negativo, probando el registro de un usuario con su campo "Nombre" vacio.
				{"", "sur2","email@prueba.com", "111222333", "calle2", "30/10/1987",true,"prueba2", "prueba2pass", "prueba2pass",NullPointerException.class},
				//Test 3 negativo, probando el registro de un usuario con su campo "Apellido" vacio.
				{"studentPrueba3", "","email@prueba.com", "111222333", "calle3", "30/10/1987",true,"prueba3", "prueba3pass", "prueba3pass",NullPointerException.class},
				//Test 4 negativo, probando el registro de un usuario con su campo "Email" vacio.
				{"studentPrueba4", "sur4","", "111222333", "calle4", "30/10/1987",true,"prueba4", "prueba4pass", "prueba4pass",NullPointerException.class},
				//Test 5 negativo, probando el registro de un usuario con su campo "Email" incorrecto, no conteniendo una @.
				{"studentPrueba5", "sur5","emailFailing", "111222333", "calle5", "30/10/1987",true,"prueba5", "prueba5pass", "prueba5pass",NullPointerException.class},
				//Test 6 positivo, probando el registro de un usuario con su campo "Teléfono" (opcional) vacio.
				{"studentPrueba6", "sur6","email@prueba.com", "", "calle6", "30/10/1987",true,"prueba6", "prueba6pass", "prueba6pass", null},
				//Test 7 positivo, probando el registro de un usuario con su campo "Teléfono" (opcional) con un número de 9 dígitos.
				{"studentPrueba7", "sur7","email@prueba.com", "619110254", "calle7", "30/10/1987",true,"prueba7", "prueba7pass", "prueba7pass", null},
				//Test 8 positivo, probando el registro de un usuario con su campo "Teléfono" (opcional) con un número con un "+" (opcional) delante.
				{"studentPrueba8", "sur8","email@prueba.com", "+954724693", "calle8", "30/10/1987",true,"prueba8", "prueba8pass", "prueba8pass", null},
				//Test 9 positivo, probando el registro de un usuario con su campo "Dirección" (opcional) vacia.
				{"studentPrueba9", "sur9","email@prueba.com", "954724693", "", "30/10/1987",true,"prueba9", "prueba9pass", "prueba9pass", null},
				//Test 10 negativo, probando el registro de un usuario con su campo "Fecha de nacimiento" vacio.
				{"studentPrueba10", "sur10","email@prueba.com", "954724693", "calle10", "",true,"prueba10", "prueba10pass", "prueba10pass", NullPointerException.class}
				
		};
		for(int i = 0; i < testingData.length; i++){
			this.startTransaction();
			templateRegisterStudent(((String) testingData[i][0]),((String) testingData[i][1]),((String) testingData[i][2]),((String) testingData[i][3]),((String) testingData[i][4]),((String) testingData[i][5]),((Boolean) testingData[i][6]),((String) testingData[i][7]),((String) testingData[i][8]),((String) testingData[i][9]),((Class<?>) testingData[i][10]));
			this.rollbackTransaction();
		}
	}


	private void templateRegisterStudent(String username,String surname, String email,String phone, String address, String dateBirth,Boolean checkTerms, String userAccountName, String userAccountPassword, String userAccountConfirmPassword, Class<?> expected) {
		Class<?> caught = null;
		Student student = this.studentService.create();
		
		//==== Registrar datos =========
		ActorForm actorForm = registeringActorForm(username, surname, email, phone, address, dateBirth, checkTerms, userAccountName, userAccountPassword, userAccountConfirmPassword);
		BindingResult binding = null;
		try{
			student = this.studentService.reconstruct(actorForm, binding);
			student = this.studentService.save(student);
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
	public void driverEditPersonalInfoStudent(){
		Object testingData[][] = {
				//Test 1 positivo, probando el editar el perfil de un estudiante con un nuevo nombre y apellido.
				{"student1","student1","newName1", "newSurname1",null},
				//Test 2 negativo, probando el editar el perfil de un estudiante con el "Nombre" vacio
				{"student1","student1","", "newSurname2", ConstraintViolationException.class},
				//Test 3 negativo, probando el editar el perfil de un estudiante con el "Apellido" vacio
				{"student1","student1","newName3", "",ConstraintViolationException.class}
				
		};
		for(int i = 0; i < testingData.length; i++){
			this.startTransaction();
			templateEditPersonalInfoStudent(((String) testingData[i][0]), super.getEntityId((String) testingData[i][1]),((String) testingData[i][2]),((String) testingData[i][3]),((Class<?>) testingData[i][4]));
			this.rollbackTransaction();
		}
	}


	protected void templateEditPersonalInfoStudent(String studentAccount, int studentId, String newName,
			String newSurname, Class<?> expected) {
		Class<?> caught;
		caught = null;
		BindingResult binding;
		EditActorForm editActorForm;
		binding = null;
		Student principal;
		super.authenticate(studentAccount);
		//Obteniendo el editActorForm a partir del estudiante (como se haría en el controlador y servicio).
		principal = this.studentService.findOne(studentId);
		editActorForm = generateAndEditActorFormFromStudent(principal, newName, newSurname);
		
		try{
			Student studentToSave;
			Student studentSaved;
			studentToSave = this.studentService.reconstruct(editActorForm, binding);
			studentSaved = this.studentService.save(studentToSave);
			Assert.isTrue(studentSaved.getName().equals(newName));
			Assert.isTrue(studentSaved.getSurname().equals(newSurname));
		}catch(Throwable oops){
			caught = oops.getClass();
		}
		
		checkExceptions(expected, caught);
	}


	protected EditActorForm generateAndEditActorFormFromStudent(Student principal, String newName, String newSurname) {
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
