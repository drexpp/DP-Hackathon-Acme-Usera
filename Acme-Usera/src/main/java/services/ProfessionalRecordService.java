
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import domain.ProfessionalRecord;
import domain.Teacher;

@Service
@Transactional
public class ProfessionalRecordService {

	// Managed Repository
	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	// Supporting services

	@Autowired
	private TeacherService					teacherService;


	// Constructors

	public ProfessionalRecordService() {
		super();
	}

	// Simple CRUD methods

	public Collection<ProfessionalRecord> findByPrincipal() {
		Collection<ProfessionalRecord> result;
		Teacher principal;

		principal = this.teacherService.findByPrincipal();
		Assert.notNull(principal);

		result = principal.getCurriculum().getProfessionalRecords();

		Assert.notNull(result);

		return result;
	}

	public ProfessionalRecord create() {
		ProfessionalRecord result;
		Teacher principal;

		principal = this.teacherService.findByPrincipal();
		Assert.notNull(principal);

		result = new ProfessionalRecord();
		Assert.notNull(result);
		result.setComments(new ArrayList<String>());

		return result;
	}

	public ProfessionalRecord save(final ProfessionalRecord professionalRecord) {
		ProfessionalRecord result;
		Teacher principal;
		List<ProfessionalRecord> professionalRecords;

		principal = this.teacherService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(principal.getCurriculum());

		Assert.isTrue(professionalRecord.getEndDate().after(professionalRecord.getStartDate()));

		professionalRecords = principal.getCurriculum().getProfessionalRecords();
		result = this.professionalRecordRepository.save(professionalRecord);
		Assert.notNull(result);
		if (professionalRecord.getId() == 0) {
			professionalRecords.add(result);
			principal.getCurriculum().setProfessionalRecords(professionalRecords);
		}
		return result;

	}

	public void delete(final ProfessionalRecord professionalRecord) {
		Teacher principal;
		List<ProfessionalRecord> listProfessionalRecord;

		Assert.notNull(professionalRecord);
		Assert.isTrue(professionalRecord.getId() != 0);

		principal = this.teacherService.findByPrincipal();
		Assert.notNull(principal);

		listProfessionalRecord = principal.getCurriculum().getProfessionalRecords();

		this.professionalRecordRepository.delete(professionalRecord);

		listProfessionalRecord.remove(professionalRecord);

		principal.getCurriculum().setProfessionalRecords(listProfessionalRecord);

	}

	public ProfessionalRecord findOne(final int id) {
		ProfessionalRecord result;
		result = this.professionalRecordRepository.findOne(id);
		return result;
	}

	public Collection<ProfessionalRecord> findAll() {
		Collection<ProfessionalRecord> result;
		result = this.professionalRecordRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public void flush() {
		this.professionalRecordRepository.flush();
	}

	// Other business methods

}
