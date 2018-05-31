
package repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ExamAnswer;
@Repository
public interface ExamAnswerRepository extends JpaRepository<ExamAnswer, Integer> {
	
	@Query("select eA from ExamAnswer eA join eA.examPaper.exam.examQuestions eQ where eA.number =?1 and eQ.number =?1 and eA.examPaper.id =?2")
	public ExamAnswer findExamAnswerByNumbers(int number, int examPaperId);
}
