
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Admin;
import domain.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

	@Query("select distinct from Teacher t join t.coursesJoined cJ join cJ.Exam e a where t.id = ?1")
	Collection<Exam> selectAllExamOfCoursedJoinedByTeacher(int teacherId);
	
	
}
