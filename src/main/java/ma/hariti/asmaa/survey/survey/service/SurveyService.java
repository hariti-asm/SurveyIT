package ma.hariti.asmaa.survey.survey.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import ma.hariti.asmaa.survey.survey.dto.survey.CreateSurveyRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.UpdateSurveyRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.UpdateSurveyResponseDTO;
import ma.hariti.asmaa.survey.survey.entity.Owner;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import ma.hariti.asmaa.survey.survey.mapper.ChapterMapper;
import ma.hariti.asmaa.survey.survey.mapper.SurveyMapper;
import ma.hariti.asmaa.survey.survey.repository.OwnerRepository;
import ma.hariti.asmaa.survey.survey.repository.SurveyRepository;
import ma.hariti.asmaa.survey.survey.util.AbstractGenericService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Transactional
public class SurveyService extends AbstractGenericService<Survey, Long, CreateSurveyRequestDTO, UpdateSurveyRequestDTO, UpdateSurveyResponseDTO> {
    private final OwnerRepository ownerRepository;
    private final SurveyRepository surveyRepository;
    private final SurveyMapper surveyMapper;
    private final ChapterMapper chapterMapper;

    public SurveyService(
            SurveyRepository surveyRepository,
            OwnerRepository ownerRepository, SurveyRepository surveyRepository1,
            SurveyMapper surveyMapper,
            ChapterMapper chapterMapper
    ) {
        super(surveyRepository);
        this.ownerRepository = ownerRepository;
        this.surveyRepository = surveyRepository1;
        this.surveyMapper = surveyMapper;
        this.chapterMapper = chapterMapper;
    }

    @Override
    protected CreateSurveyRequestDTO mapToCreateDto(Survey entity) {
        return surveyMapper.toDto(entity);
    }

    @Override
    protected Survey mapToEntity(CreateSurveyRequestDTO createDto) {
        // Validate before mapping
        validateSurveyCreation(createDto);

        Survey survey = surveyMapper.toEntity(createDto);
        setOwnerForSurvey(survey, createDto.getOwnerId());
        return survey;
    }

    @Override
    protected void mapToEntity(UpdateSurveyRequestDTO updateDto, Survey entity) {
        validateSurveyUpdate(updateDto, entity.getId());
        surveyMapper.updateEntityFromUpdateDto(updateDto, entity);
    }

    @Override
    protected UpdateSurveyResponseDTO mapToResponseDto(Survey entity) {
        return surveyMapper.toUpdateResponseDto(entity);
    }

    private void validateSurveyCreation(CreateSurveyRequestDTO createDto) {
        validateSurveyTitle(createDto.getTitle(), null);
        validateOwnerExists(createDto.getOwnerId());
    }

    private void validateSurveyUpdate(UpdateSurveyRequestDTO updateDto, Long surveyId) {
        validateSurveyTitle(updateDto.getTitle(), surveyId);
    }

    protected void validateSurveyTitle(String title, Long excludeId) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalStateException("Survey title cannot be empty");
        }

        ((SurveyRepository) repository).findByTitle(title.trim())
                .ifPresent(existingSurvey -> {
                    if (excludeId == null || !existingSurvey.getId().equals(excludeId)) {
                        throw new IllegalStateException("Survey with title '" + title + "' already exists");
                    }
                });
    }

    private void validateOwnerExists(Long ownerId) {
        if (ownerId == null) {
            throw new IllegalStateException("Owner ID is required");
        }

        if (!ownerRepository.existsById(ownerId)) {
            throw new EntityNotFoundException("Owner not found with id: " + ownerId);
        }
    }

    private void setOwnerForSurvey(Survey survey, Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found with id: " + ownerId));
        survey.setOwner(owner);
    }

}