package ma.hariti.asmaa.survey.survey.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.survey.survey.dto.survey.SurveyDTO;
import ma.hariti.asmaa.survey.survey.entity.Chapter;
import ma.hariti.asmaa.survey.survey.entity.Owner;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import ma.hariti.asmaa.survey.survey.exception.DuplicateTitleException;
import ma.hariti.asmaa.survey.survey.exception.ResourceNotFoundException;
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

    public SurveyDTO createSurvey(SurveyDTO surveyDTO) {
        validateSurveyTitle(surveyDTO.getTitle());

        Survey survey = createBasicSurvey(surveyDTO);
        setOwnerForSurvey(survey, surveyDTO.getOwnerId());
        addChaptersIfPresent(survey, surveyDTO);

        Survey savedSurvey = surveyRepository.save(survey);
        return surveyMapper.toDto(savedSurvey);
    }

    private Survey createBasicSurvey(SurveyDTO surveyDTO) {
        return surveyMapper.toEntity(surveyDTO);
    }

    private void setOwnerForSurvey(Survey survey, Long ownerId) {
        Owner owner = findAndValidateOwner(ownerId);
        survey.setOwner(owner);
    }

    private void addChaptersIfPresent(Survey survey, SurveyDTO surveyDTO) {
        if (surveyDTO.getChapters() != null && !surveyDTO.getChapters().isEmpty()) {
            addChaptersToSurvey(survey, surveyDTO);
        }
    }

    private void addChaptersToSurvey(Survey survey, SurveyDTO surveyDTO) {
        surveyDTO.getChapters().forEach(chapterDTO -> {
            Chapter chapter = chapterMapper.toEntity(chapterDTO);
            chapter.setSurvey(survey);
            survey.getChapters().add(chapter);
        });
    }

    // Read Operations
    public SurveyDTO getSurveyById(Long id) {
        Survey survey = findSurveyOrThrow(id);
        return surveyMapper.toDto(survey);
    }

    public List<SurveyDTO> getAllSurveys() {
        List<Survey> surveys = surveyRepository.findAll();
        return surveys.stream()
                .map(surveyMapper::toDto)
                .collect(Collectors.toList());
    }

    // Update Operations
    public SurveyDTO updateSurvey(Long id, SurveyDTO surveyDTO) {
        Survey existingSurvey = findSurveyOrThrow(id);

        if (!existingSurvey.getTitle().equals(surveyDTO.getTitle())) {
            validateSurveyTitle(surveyDTO.getTitle());
        }

        surveyMapper.updateEntityFromDto(surveyDTO, existingSurvey);
        Survey updatedSurvey = surveyRepository.save(existingSurvey);
        return surveyMapper.toDto(updatedSurvey);
    }

    // Validation Methods
    private void validateSurveyTitle(String title) {
        Optional<Survey> existingSurvey = surveyRepository.findByTitle(title);
        if (existingSurvey.isPresent()) {
            throw new DuplicateTitleException("A survey with this title already exists.");
        }
    }

    private Owner findAndValidateOwner(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + ownerId));
    }

    private Survey findSurveyOrThrow(Long id) {
        return surveyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found with id: " + id));
    }
}