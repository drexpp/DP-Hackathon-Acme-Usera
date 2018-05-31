
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Certification;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Integer> {


}
