package ma.hariti.asmaa.survey.survey.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import ma.hariti.asmaa.survey.survey.entity.SurveyEdition;
import ma.hariti.asmaa.survey.survey.repository.SurveyEditionRepository;
import ma.hariti.asmaa.survey.survey.repository.SurveyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SurveyEditionService {
    private final SurveyRepository surveyRepository;
    private final SurveyEditionRepository surveyEditionRepository;

    public SurveyEdition startEdition(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found with id: " + surveyId));

        SurveyEdition surveyEdition = new SurveyEdition();
        surveyEdition.setSurvey(survey);
        surveyEdition.setStartDate(LocalDate.from(LocalDateTime.now()));
        return surveyEditionRepository.save(surveyEdition);
    }

    public SurveyEdition finishEdition(Long surveyEditionId) {
        SurveyEdition surveyEdition = getSurveyEditionById(surveyEditionId);
        surveyEdition.setStartDate(LocalDate.now());
        surveyEdition.setCreationDate(LocalDate.now());
        surveyEdition.setYear(LocalDate.now().getYear());
        return surveyEditionRepository.save(surveyEdition);
    }

    public SurveyEdition getSurveyEditionById(Long surveyEditionId) {
        return surveyEditionRepository.findById(surveyEditionId)
                .orElseThrow(() -> new EntityNotFoundException("Survey edition not found with id: " + surveyEditionId));
    }

    public List<SurveyEdition> getAllSurveyEditions() {
        return surveyEditionRepository.findAll();
    }

    public SurveyEdition updateSurveyEdition(Long surveyEditionId, SurveyEdition updatedSurveyEdition) {
        SurveyEdition existingSurveyEdition = getSurveyEditionById(surveyEditionId);
        BeanUtils.copyProperties(updatedSurveyEdition, existingSurveyEdition, "id", "survey");
        return surveyEditionRepository.save(existingSurveyEdition);
    }

    public void deleteSurveyEdition(Long surveyEditionId) {
        surveyEditionRepository.deleteById(surveyEditionId);
    }
}