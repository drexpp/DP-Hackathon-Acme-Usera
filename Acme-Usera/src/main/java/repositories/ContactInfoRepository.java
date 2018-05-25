package repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.ContactInfo;
@Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfo, Integer> {

}
