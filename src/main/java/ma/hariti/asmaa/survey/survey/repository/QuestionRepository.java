package ma.hariti.asmaa.survey.survey.repository;

import ma.hariti.asmaa.survey.survey.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository  extends JpaRepository<Question, Long> {
}
