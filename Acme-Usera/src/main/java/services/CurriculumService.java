
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import domain.Curriculum;
import domain.EducationRecord;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;
import domain.Teacher;

@Service
@Transactional
public class CurriculumService {

	// Managed Repository
	@Autowired
	private CurriculumRepository	curriculumRepository;

	// Supporting services
	@Autowired
	private TeacherService			teacherService;

	// Constructors

	public CurriculumService() {
		super();
	}

	// Additional functions
	private String generateTicker() {
		String result;
		final Calendar now = Calendar.getInstance();
		String year = String.valueOf(now.get(Calendar.YEAR));
		year = year.substring(year.length() - 2, year.length());
		final String month = String.valueOf(now.get(Calendar.MONTH) + 1);
		String date = String.valueOf(now.get(Calendar.DATE));
		date = date.length() == 1 ? "0".concat(date) : date;
		final Random r = new Random();
		final char a = (char) (r.nextInt(26) + 'a');
		final char b = (char) (r.nextInt(26) + 'a');
		final char c = (char) (r.nextInt(26) + 'a');
		final char d = (char) (r.nextInt(26) + 'a');
		String code = String.valueOf(a) + String.valueOf(b) + String.valueOf(c) + String.valueOf(d);
		code = code.toUpperCase();
		result = year + month + date + "-" + code;
		return result;
	}

	// Simple CRUD methods
	public Curriculum findOne(final int curriculumId) {
		Curriculum result;

		result = this.curriculumRepository.findOne(curriculumId);
		Assert.notNull(result);

		return result;
	}
	public Curriculum create() {
		Curriculum result;
		Teacher principal;

		PersonalRecord personalRecord;
		principal = this.teacherService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isNull(this.findCurriculumByPrincipal());
		result = new Curriculum();
		result.setTeacher(principal);
		result.setTicker(this.generateTicker());
		personalRecord = new PersonalRecord();
		personalRecord.setName(principal.getName());
		personalRecord.setSurname(principal.getSurname());
		result.setPersonalRecord(personalRecord);
		result.setEducationRecord(new ArrayList<EducationRecord>());
		result.setMiscellaneousRecord(new ArrayList<MiscellaneousRecord>());
		result.setProfessionalRecords(new ArrayList<ProfessionalRecord>());

		return result;

	}
	public Curriculum save(final Curriculum curriculum) {
		Teacher principal;
		Curriculum result;
		Assert.notNull(curriculum);
		principal = this.teacherService.findByPrincipal();
		Assert.isTrue(curriculum.getTeacher() == principal);
		result = this.curriculumRepository.save(curriculum);
		result.getTeacher().setCurriculum(result);

		return result;
	}
	public void delete(final Curriculum curriculum) {
		Teacher principal;
		Curriculum result;
		Assert.isTrue(curriculum.getId() != 0);
		principal = this.teacherService.findByPrincipal();
		Assert.notNull(principal);
		result = this.findCurriculumByPrincipal();
		Assert.notNull(result);
		Assert.isTrue(curriculum.equals(result));
		this.curriculumRepository.delete(curriculum);
		principal.setCurriculum(null);
	}

	// Other business methods
	public Curriculum findCurriculumByPrincipal() {
		Curriculum result;
		Teacher principal;
		principal = this.teacherService.findByPrincipal();
		Assert.notNull(principal);
		result = this.curriculumRepository.findCurriculumByPrincipal(principal.getId());
		Assert.isTrue(principal.getCurriculum() == result);
		return result;

	}

}
