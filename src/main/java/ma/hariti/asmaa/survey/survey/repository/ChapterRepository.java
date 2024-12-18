package ma.hariti.asmaa.survey.survey.repository;


import ma.hariti.asmaa.survey.survey.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findBySurveyEditionId(Long surveyEditionId);

}