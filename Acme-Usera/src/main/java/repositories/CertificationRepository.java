
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Certification;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Integer> {

	@Query("select c from Certification c where c.id = ?1")
	Certification findById(int certificationId);


}
