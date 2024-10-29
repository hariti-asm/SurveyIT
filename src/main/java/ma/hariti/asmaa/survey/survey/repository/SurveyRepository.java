package ma.hariti.asmaa.survey.survey.repository;

import ma.hariti.asmaa.survey.survey.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    Optional<Survey> findByTitle(String title);

//    @Query("SELECT s FROM Survey s " +
//            "LEFT JOIN FETCH s.subjects sub " +
//            "LEFT JOIN FETCH sub.questions q " +
//            "LEFT JOIN FETCH q.answers " +
//            "WHERE s.id = :surveyId")
//    Optional<Survey> findByIdWithResults(@Param("surveyId") Long surveyId);
}
