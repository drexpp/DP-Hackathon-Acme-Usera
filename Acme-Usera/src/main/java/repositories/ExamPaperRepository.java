
package repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ExamPaper;

@Repository
public interface ExamPaperRepository extends JpaRepository<ExamPaper, Integer> {

	@Query("select eP from Student s join s.examPapers eP where s.id=?1 and eP.exam.course.id=?2")
	public ExamPaper findExamPaperFromCourseAndStudent(Integer studentId, Integer courseId);
}
