
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

	@Query("select s from Student s where s.userAccount.id = ?1")
	Student findByUserAccountId(int userAccountId);
	
	@Query("select s from Student s order by s.score desc")
	Collection<Student> findStudentsRankedByScore();
}
