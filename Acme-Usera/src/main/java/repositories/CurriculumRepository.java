
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {
	
	@Query("select t.curriculum from Teacher t where t.id = ?1")
	Curriculum prueba(int teacherId);
	
	@Query("select c from Curriculum c where c.teacher.id = ?1")
	Curriculum findCurriculumByPrincipal(int teacherId);
}
