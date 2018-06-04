
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

	@Query("select distinct t.exams from Teacher t join t.coursesJoined cJ join cJ.exam e where t.id = ?1")
	Collection<Exam> selectAllExamOfCoursedJoinedByTeacher(int teacherId);
	
	@Query("select distinct e from Student s join s.examPapers eP join eP.exam e where s.id = ?1")
	Collection<Exam> selectExamsFromStudent(int studentId);
	
}
