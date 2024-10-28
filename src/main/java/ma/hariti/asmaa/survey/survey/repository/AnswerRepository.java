package ma.hariti.asmaa.survey.survey.repository;

import ma.hariti.asmaa.survey.survey.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
