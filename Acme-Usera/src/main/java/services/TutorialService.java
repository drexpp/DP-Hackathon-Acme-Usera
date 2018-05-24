package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Student;
import domain.Teacher;
import domain.Tutorial;

import repositories.TutorialRepository;

@Service
@Transactional
public class TutorialService {
	
	// Managed Repository
	@Autowired
	private TutorialRepository			tutorialRepository;
	
	@Autowired
	private TeacherService			teacherService;
	
	@Autowired
	private StudentService			studentService;

	
	
	
	public Tutorial create() {
		Student principal;
		Tutorial tutorial = new Tutorial();
		principal = this.studentService.findByPrincipal();
		Assert.notNull(principal);
		tutorial.setStudent(principal);
		
		return tutorial;
	}
	
	
	public Collection<Tutorial> findAll() {
		final Collection<Tutorial> result = this.tutorialRepository.findAll();
		return result;
	}
	
	
	
	public Tutorial findOne(final int tutorialId) {


		Tutorial result = this.tutorialRepository.findOne(tutorialId);
		Assert.notNull(result);

		return result;
	}
	
	public Tutorial save(final Tutorial tutorialToSave) {
		Student principal;
		Tutorial result;
		Assert.notNull(tutorialToSave);
		principal = this.studentService.findByPrincipal();

		Assert.notNull(principal);
		Assert.isTrue(tutorialToSave.getStudent().equals(principal));
		Collection<Teacher> tutors = this.teacherService.findTutorsByStudent(principal.getId());
		Assert.isTrue(tutors.contains(tutorialToSave.getTeacher()));
		
		result = this.tutorialRepository.save(tutorialToSave);
		
		Collection<Tutorial> toUpdate = principal.getTutorials();
		Collection<Tutorial> updated = new ArrayList<Tutorial>(toUpdate);
		updated.add(result);
		principal.setTutorials(updated);
		
		Collection<Tutorial> toUpdate2 = result.getTeacher().getTutorials();
		Collection<Tutorial> updated2 = new ArrayList<Tutorial>(toUpdate2);
		updated2.add(result);
		result.getTeacher().setTutorials(updated);
		
		return result;
	}
	
	
	public void delete(final Tutorial tutorialToDelete) {
		Teacher principal;
		Assert.notNull(tutorialToDelete);
		principal = this.teacherService.findByPrincipal();

		Assert.notNull(principal);
		Assert.isTrue(tutorialToDelete.getTeacher().equals(principal));
		
		Collection<Tutorial> toUpdate = principal.getTutorials();
		Collection<Tutorial> updated = new ArrayList<Tutorial>(toUpdate);
		updated.remove(tutorialToDelete);
		principal.setTutorials(updated);
		
		Collection<Tutorial> toUpdate2 = tutorialToDelete.getTeacher().getTutorials();
		Collection<Tutorial> updated2 = new ArrayList<Tutorial>(toUpdate2);
		updated2.remove(tutorialToDelete);
		tutorialToDelete.getTeacher().setTutorials(updated);
		
		this.tutorialRepository.delete(tutorialToDelete);
		
	}
	
	
}
