package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Admin;
import domain.Certification;
import domain.ExamPaper;
import domain.Student;

import repositories.CertificationRepository;

@Service
@Transactional
public class CertificationService {

	
	// Managed Repository
	@Autowired
	private CertificationRepository			certificationRepository;

	@Autowired
	private AdminService			adminService;
	
	public CertificationService() {
		super();
	}
	
	public void deleteByAdmin (Certification certification){
		Admin principal = this.adminService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(certification);
		
		Student student = certification.getStudent();
		Collection<Certification> updated1 = new ArrayList<Certification>(student.getCertifications());
		updated1.remove(certification);
		student.setCertifications(updated1);
		
		ExamPaper exampaper = certification.getExamPaper();
		exampaper.setCertification(null);
		this.certificationRepository.delete(certification);
		
		
	}
	
}
