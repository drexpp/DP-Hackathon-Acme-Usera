package services;

import org.springframework.util.Assert;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;

import domain.Admin;
import forms.EditActorForm;

import utilities.AbstractTest;


@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class AdminServiceTest extends AbstractTest {
	
	@Autowired
	AdminService		adminService;
	

	@Test
	public void driverEditPersonalInfoStudent(){
		Object testingData[][] = {
				//Test 1 positivo, probando el editar el perfil de un admin con un nuevo nombre y apellido.
				{"admin","admin1","newName1", "newSurname1",null},
				//Test 2 negativo, probando el editar el perfil de un admin con el "Nombre" vacio
				{"admin","admin1","", "newSurname2", ConstraintViolationException.class},
				//Test 3 negativo, probando el editar el perfil de un admin con el "Apellido" vacio
				{"admin","admin1","newName3", "",ConstraintViolationException.class}
				
		};
		for(int i = 0; i < testingData.length; i++){
			this.startTransaction();
			templateEditPersonalInfoStudent(((String) testingData[i][0]), super.getEntityId((String) testingData[i][1]),((String) testingData[i][2]),((String) testingData[i][3]),((Class<?>) testingData[i][4]));
			this.rollbackTransaction();
		}
	}


	protected void templateEditPersonalInfoStudent(String adminAccount, int adminId, String newName,
			String newSurname, Class<?> expected) {
		Class<?> caught;
		caught = null;
		BindingResult binding;
		EditActorForm editActorForm;
		binding = null;
		Admin principal;
		super.authenticate(adminAccount);
		//Obteniendo el editActorForm a partir del estudiante (como se haría en el controlador y servicio).
		principal = this.adminService.findOne(adminId);
		editActorForm = generateAndEditActorFormFromStudent(principal, newName, newSurname);
		
		try{
			Admin adminToSave;
			Admin adminSaved;
			adminToSave = this.adminService.reconstruct(editActorForm, binding);
			adminSaved = this.adminService.save(adminToSave);
			Assert.isTrue(adminSaved.getName().equals(newName));
			Assert.isTrue(adminSaved.getSurname().equals(newSurname));
		}catch(Throwable oops){
			caught = oops.getClass();
		}
		
		checkExceptions(expected, caught);
	}


	protected EditActorForm generateAndEditActorFormFromStudent(Admin principal, String newName, String newSurname) {
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
