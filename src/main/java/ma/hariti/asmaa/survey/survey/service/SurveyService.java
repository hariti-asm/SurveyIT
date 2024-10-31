package ma.hariti.asmaa.survey.survey.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.survey.survey.dto.survey.CreateSurveyRequestDTO;
import ma.hariti.asmaa.survey.survey.entity.Owner;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import ma.hariti.asmaa.survey.survey.entity.SurveyEdition;
import ma.hariti.asmaa.survey.survey.mapper.ChapterMapper;
import ma.hariti.asmaa.survey.survey.mapper.SurveyMapper;
import ma.hariti.asmaa.survey.survey.repository.OwnerRepository;
import ma.hariti.asmaa.survey.survey.repository.SurveyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final OwnerRepository ownerRepository;
    private final SurveyMapper surveyMapper;
    private final ChapterMapper chapterMapper;

    public CreateSurveyRequestDTO createSurvey(CreateSurveyRequestDTO createSurveyRequestDTO) {
        validateSurveyTitle(createSurveyRequestDTO.getTitle(), null);

        Survey survey = surveyMapper.toEntity(createSurveyRequestDTO);
        setOwnerForSurvey(survey, createSurveyRequestDTO.getOwnerId());

        SurveyEdition surveyEdition = new SurveyEdition();
        surveyEdition.setSurvey(survey);
        surveyEdition.setStartDate(surveyEdition.getStartDate());
        surveyEdition.setYear(surveyEdition.getYear());

        survey.getSurveyEditions().add(surveyEdition);

        Survey savedSurvey = surveyRepository.save(survey);
        return surveyMapper.toDto(savedSurvey);
    }

    private void setOwnerForSurvey(Survey survey, Long ownerId) {
        Owner owner = findAndValidateOwner(ownerId);
        survey.setOwner(owner);
    }

    public CreateSurveyRequestDTO getSurveyById(Long id) {
        Survey survey = findSurveyOrThrow(id);
        return surveyMapper.toDto(survey);
    }

    public List<CreateSurveyRequestDTO> getAllSurveys() {
        List<Survey> surveys = surveyRepository.findAll();
        return surveys.stream()
                .map(surveyMapper::toDto)
                .collect(Collectors.toList());
    }

    public CreateSurveyRequestDTO updateSurvey(Long id, CreateSurveyRequestDTO createSurveyRequestDTO) {
        Survey existingSurvey = findSurveyOrThrow(id);

        if (!existingSurvey.getTitle().equals(createSurveyRequestDTO.getTitle())) {
            validateSurveyTitle(createSurveyRequestDTO.getTitle(), id); // Pass the id for updates
        }
        surveyMapper.updateEntityFromDto(createSurveyRequestDTO, existingSurvey);
        Survey updatedSurvey = surveyRepository.save(existingSurvey);
        return surveyMapper.toDto(updatedSurvey);
    }

    private void validateSurveyTitle(String title, Long surveyId) {
        Optional<Survey> existingSurvey = surveyRepository.findByTitle(title);
        if (existingSurvey.isPresent() && !existingSurvey.get().getId().equals(surveyId)) {
            throw new IllegalStateException("A survey with this title already exists.");
        }
    }

    private Owner findAndValidateOwner(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found with id: " + ownerId));
    }

    private Survey findSurveyOrThrow(Long id) {
        return surveyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found with id: " + id));
    }
}
