package ma.hariti.asmaa.survey.survey.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.survey.survey.dto.survey.CreateSurveyRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.UpdateSurveyRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.UpdateSurveyResponseDTO;
import ma.hariti.asmaa.survey.survey.entity.Owner;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import ma.hariti.asmaa.survey.survey.mapper.ChapterMapper;
import ma.hariti.asmaa.survey.survey.mapper.SurveyMapper;
import ma.hariti.asmaa.survey.survey.repository.OwnerRepository;
import ma.hariti.asmaa.survey.survey.repository.SurveyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

        Survey survey = surveyMapper.toEntity(createSurveyRequestDTO);
        setOwnerForSurvey(survey, createSurveyRequestDTO.getOwnerId());
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

    private void validateSurveyTitle(String title, Long excludeId) {
        surveyRepository.findByTitle(title)
                .ifPresent(existingSurvey -> {
                    if (!existingSurvey.getId().equals(excludeId)) {
                        throw new IllegalStateException("Survey with title '" + title + "' already exists");
                    }
                });
    }


    private Owner findAndValidateOwner(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found with id: " + ownerId));
    }
    private Survey findSurveyOrThrow(Long id) {
        return surveyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found with id: " + id));
    }
    public UpdateSurveyResponseDTO updateSurvey(Long id, UpdateSurveyRequestDTO updateSurveyRequestDTO) {
        Survey existingSurvey = findSurveyOrThrow(id);

        if (!id.equals(updateSurveyRequestDTO.getId())) {
            throw new IllegalArgumentException("Path ID and request body ID must match");
        }

        if (!existingSurvey.getTitle().equals(updateSurveyRequestDTO.getTitle())) {
            validateSurveyTitle(updateSurveyRequestDTO.getTitle(), id);
        }

        surveyMapper.updateEntityFromUpdateDto(updateSurveyRequestDTO, existingSurvey);

        Survey updatedSurvey = surveyRepository.save(existingSurvey);

        return surveyMapper.toUpdateResponseDto(updatedSurvey);
    }



}
