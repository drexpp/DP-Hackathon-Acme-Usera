
package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ExamQuestion;

@Repository
public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Integer> {

	@Query("select eQ from ExamQuestion eQ join eQ.exam.examPaper eP join eP.examAnswer eA where eQ.number in eA.number and eP.id = ?1")
	Collection<ExamQuestion> findAnsweredQuestions(int examPaperId);

}
