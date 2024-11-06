package ma.hariti.asmaa.survey.survey.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import ma.hariti.asmaa.survey.survey.dto.surveyEdition.CreateSurveyEditionRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.surveyEdition.SurveyEditionResponseDTO;
import ma.hariti.asmaa.survey.survey.dto.surveyEdition.UpdateSurveyEditionRequestDTO;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import ma.hariti.asmaa.survey.survey.entity.SurveyEdition;
import ma.hariti.asmaa.survey.survey.mapper.SurveyEditionMapper;
import ma.hariti.asmaa.survey.survey.repository.SurveyEditionRepository;
import ma.hariti.asmaa.survey.survey.repository.SurveyRepository;
import ma.hariti.asmaa.survey.survey.util.AbstractGenericService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Transactional
public class SurveyEditionService extends AbstractGenericService<SurveyEdition, Long, CreateSurveyEditionRequestDTO, UpdateSurveyEditionRequestDTO, SurveyEditionResponseDTO> {
    private final SurveyRepository surveyRepository;
    private final SurveyEditionRepository surveyEditionRepository;
    private final SurveyEditionMapper surveyEditionMapper;

    public SurveyEditionService(
            SurveyEditionRepository surveyEditionRepository,
            SurveyRepository surveyRepository,
            SurveyEditionMapper surveyEditionMapper
    ) {
        super(surveyEditionRepository);
        this.surveyEditionRepository = surveyEditionRepository;
        this.surveyRepository = surveyRepository;
        this.surveyEditionMapper = surveyEditionMapper;
    }

    @Override
    protected CreateSurveyEditionRequestDTO mapToCreateDto(SurveyEdition entity) {
        return surveyEditionMapper.toCreateDto(entity);
    }

    @Override
    protected SurveyEdition mapToEntity(CreateSurveyEditionRequestDTO createDto) {
        validateSurveyEditionCreation(createDto);
        SurveyEdition surveyEdition = surveyEditionMapper.toEntity(createDto);
        setSurveyForEdition(surveyEdition, createDto.getSurveyId());
        return surveyEdition;
    }

    @Override
    protected void mapToEntity(UpdateSurveyEditionRequestDTO updateDto, SurveyEdition entity) {
        validateSurveyEditionUpdate(updateDto, entity.getId());
        entity.setStartDate(updateDto.getStartDate());
        entity.setCreationDate(updateDto.getCreationDate());
        entity.setYear(updateDto.getYear());
        entity.setSurvey(surveyRepository.findById(updateDto.getSurveyId())
                .orElseThrow(() -> new EntityNotFoundException("Survey not found with id: " + updateDto.getSurveyId())));
    }

    @Override
    protected SurveyEditionResponseDTO mapToResponseDto(SurveyEdition entity) {
        return surveyEditionMapper.toDto(entity);
    }

    @Override
    public SurveyEditionResponseDTO update(Long id, UpdateSurveyEditionRequestDTO updateDto) {
        SurveyEdition existingEdition = surveyEditionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SurveyEdition not found with id: " + id));

        mapToEntity(updateDto, existingEdition);

        SurveyEdition updatedEdition = surveyEditionRepository.save(existingEdition);

        return mapToResponseDto(updatedEdition);
    }

    private void validateSurveyEditionCreation(CreateSurveyEditionRequestDTO createDto) {
        validateSurveyExists(createDto.getSurveyId());
    }

    private void validateSurveyEditionUpdate(UpdateSurveyEditionRequestDTO updateDto, Long surveyEditionId) {
        validateSurveyExists(updateDto.getSurveyId());
    }

    private void validateSurveyExists(Long surveyId) {
        if (surveyId == null) {
            throw new IllegalStateException("Survey ID is required");
        }

        if (!surveyRepository.existsById(surveyId)) {
            throw new EntityNotFoundException("Survey not found with id: " + surveyId);
        }
    }

    private void setSurveyForEdition(SurveyEdition surveyEdition, Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found with id: " + surveyId));
        surveyEdition.setSurvey(survey);
    }
}