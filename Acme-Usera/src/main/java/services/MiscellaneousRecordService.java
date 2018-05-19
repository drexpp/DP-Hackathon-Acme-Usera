
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.MiscellaneousRecord;
import domain.Teacher;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Managed Repository
	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	// Supporting services

	@Autowired
	private TeacherService					teacherService;


	// Constructors

	public MiscellaneousRecordService() {
		super();
	}

	// Simple CRUD methods

	public Collection<MiscellaneousRecord> findByPrincipal() {
		Collection<MiscellaneousRecord> result;
		Teacher principal;

		principal = this.teacherService.findByPrincipal();
		Assert.notNull(principal);

		result = principal.getCurriculum().getMiscellaneousRecord();

		Assert.notNull(result);

		return result;
	}

	public MiscellaneousRecord create() {
		MiscellaneousRecord result;
		Teacher principal;

		principal = this.teacherService.findByPrincipal();
		Assert.notNull(principal);

		result = new MiscellaneousRecord();
		Assert.notNull(result);

		result.setComments(new ArrayList<String>());

		return result;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		MiscellaneousRecord result;
		Teacher principal;
		List<MiscellaneousRecord> miscellaneousRecords;

		principal = this.teacherService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(principal.getCurriculum());

		miscellaneousRecords = principal.getCurriculum().getMiscellaneousRecord();
		result = this.miscellaneousRecordRepository.save(miscellaneousRecord);
		Assert.notNull(result);
		if (miscellaneousRecord.getId() == 0) {
			miscellaneousRecords.add(result);
			principal.getCurriculum().setMiscellaneousRecord(miscellaneousRecords);
		}
		return result;

	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		Teacher principal;
		List<MiscellaneousRecord> listMiscellaneousRecord;

		Assert.notNull(miscellaneousRecord);
		Assert.isTrue(miscellaneousRecord.getId() != 0);

		principal = this.teacherService.findByPrincipal();
		Assert.notNull(principal);

		listMiscellaneousRecord = principal.getCurriculum().getMiscellaneousRecord();
		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
		listMiscellaneousRecord.remove(miscellaneousRecord);

		principal.getCurriculum().setMiscellaneousRecord(listMiscellaneousRecord);

	}

	public MiscellaneousRecord findOne(final int id) {
		MiscellaneousRecord result;
		result = this.miscellaneousRecordRepository.findOne(id);
		return result;
	}

	public Collection<MiscellaneousRecord> findAll() {
		Collection<MiscellaneousRecord> result;
		result = this.miscellaneousRecordRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	// Other business methods

}
