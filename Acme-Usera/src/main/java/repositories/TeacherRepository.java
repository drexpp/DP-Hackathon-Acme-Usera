
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

	@Query("select t from Teacher t where t.userAccount.id = ?1")
	Teacher findByUserAccountId(int userAccountId);
	
	@Query("select distinct t from Subscription s join s.course c join c.teachers t where s.student.id = ?1")
	Collection<Teacher> findTutorsByStudent(int studentId);
}
