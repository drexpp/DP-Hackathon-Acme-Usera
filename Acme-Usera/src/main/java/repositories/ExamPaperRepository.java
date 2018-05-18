
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Admin;
import domain.ExamPaper;

@Repository
public interface ExamPaperRepository extends JpaRepository<ExamPaper, Integer> {

}
