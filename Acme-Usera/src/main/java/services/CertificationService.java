package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

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
	
	public Certification create(){
		Certification result;
		String ticker;
		Date now = new Date(System.currentTimeMillis()-1);
		
		ticker = generateTicker();

		result = new Certification();
		result.setTicker(ticker);
		result.setMoment(now);
		
		return result;
		
	}
	
	
	public Certification save(final Certification certification){
		Assert.isTrue(certification.getStudent() != null);
		Certification result;
		Collection<Certification> oldList;
		
		result = this.certificationRepository.save(certification);
		
		certification.getExamPaper().setCertification(result);
		
		oldList = certification.getStudent().getCertifications();
		oldList.add(result);
		certification.getStudent().setCertifications(oldList);
		
		return result;
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
	
	protected String generateTicker() {
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

	public Certification findById(int certificationId) {
		Certification result;
		
		result = this.certificationRepository.findById(certificationId);
		
		return result;
	}
	
}
