package ma.hariti.asmaa.survey.survey.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import ma.hariti.asmaa.survey.survey.dto.survey.CreateSurveyRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.UpdateSurveyRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.UpdateSurveyResponseDTO;
import ma.hariti.asmaa.survey.survey.entity.Chapter;
import ma.hariti.asmaa.survey.survey.entity.Owner;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import ma.hariti.asmaa.survey.survey.entity.SurveyEdition;
import ma.hariti.asmaa.survey.survey.mapper.ChapterMapper;
import ma.hariti.asmaa.survey.survey.mapper.SurveyMapper;
import ma.hariti.asmaa.survey.survey.repository.ChapterRepository;
import ma.hariti.asmaa.survey.survey.repository.OwnerRepository;
import ma.hariti.asmaa.survey.survey.repository.SurveyEditionRepository;
import ma.hariti.asmaa.survey.survey.repository.SurveyRepository;
import ma.hariti.asmaa.survey.survey.util.AbstractGenericService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@Transactional
public class SurveyService extends AbstractGenericService<Survey, Long, CreateSurveyRequestDTO, UpdateSurveyRequestDTO, UpdateSurveyResponseDTO> {
    private final OwnerRepository ownerRepository;
    private final SurveyRepository surveyRepository;
    private final SurveyMapper surveyMapper;
    private final ChapterMapper chapterMapper;
    private final SurveyEditionRepository surveyEditionRepository;
    private final ChapterRepository chapterRepository;
    public SurveyService(
            SurveyRepository surveyRepository,
            OwnerRepository ownerRepository,
            SurveyMapper surveyMapper,
            ChapterMapper chapterMapper,
            SurveyEditionRepository surveyEditionRepository,
            ChapterRepository chapterRepository
    ) {
        super(surveyRepository);
        this.ownerRepository = ownerRepository;
        this.surveyRepository = surveyRepository;
        this.surveyMapper = surveyMapper;
        this.chapterMapper = chapterMapper;
        this.surveyEditionRepository = surveyEditionRepository;
        this.chapterRepository = chapterRepository;
    }

    @Override
    protected CreateSurveyRequestDTO mapToCreateDto(Survey entity) {
        return surveyMapper.toDto(entity);
    }

    @Override
    protected Survey mapToEntity(CreateSurveyRequestDTO createDto) {
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

    public List<SurveyEdition> getSurveyEditionsBySurveyId(Long surveyId) {
        return surveyEditionRepository.findBySurveyId(surveyId);
    }

    public int getQuestionCountBySurveyEditionId(Long surveyEditionId) {
//        int totalQuestions = 0;
//        List<Chapter> chapters = chapterRepository.findBySurveyEditionId(surveyEditionId);
//        for (Chapter chapter : chapters) {
//            totalQuestions += chapter.getQuestions().size();
//        }
//        return totalQuestions;
        return  2;
    }
}