package ma.hariti.asmaa.survey.survey.repository;


import ma.hariti.asmaa.survey.survey.entity.Chapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    Page<Chapter> findBySurveyEditionId(Long surveyEditionId, Pageable pageable);

}