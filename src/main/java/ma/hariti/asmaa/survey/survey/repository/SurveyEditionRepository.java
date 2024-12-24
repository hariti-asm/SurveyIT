package ma.hariti.asmaa.survey.survey.repository;

import ma.hariti.asmaa.survey.survey.entity.SurveyEdition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyEditionRepository extends JpaRepository<SurveyEdition, Long> {
    List<SurveyEdition> findBySurveyId(Long surveyId);

}
