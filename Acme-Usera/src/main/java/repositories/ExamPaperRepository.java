
package repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.ExamPaper;

@Repository
public interface ExamPaperRepository extends JpaRepository<ExamPaper, Integer> {

}
