import jakarta.persistence.EntityNotFoundException;
import ma.hariti.asmaa.survey.survey.dto.surveyEdition.CreateSurveyEditionRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.surveyEdition.SurveyEditionResponseDTO;
import ma.hariti.asmaa.survey.survey.dto.surveyEdition.UpdateSurveyEditionRequestDTO;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import ma.hariti.asmaa.survey.survey.entity.SurveyEdition;
import ma.hariti.asmaa.survey.survey.mapper.SurveyEditionMapper;
import ma.hariti.asmaa.survey.survey.repository.SurveyEditionRepository;
import ma.hariti.asmaa.survey.survey.repository.SurveyRepository;
import ma.hariti.asmaa.survey.survey.service.SurveyEditionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

class SurveyEditionServiceTest {

    @Mock
    private SurveyEditionRepository surveyEditionRepository;

    @Mock
    private SurveyRepository surveyRepository;

    @Mock
    private SurveyEditionMapper surveyEditionMapper;

    @InjectMocks
    private SurveyEditionService surveyEditionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSurveyEditionFailDueToMissingSurveyId() {
        // Arrange
        CreateSurveyEditionRequestDTO createDto = new CreateSurveyEditionRequestDTO();
        createDto.setSurveyId(null);

        // Act and Assert
        Assertions.assertThrows(IllegalStateException.class, () -> surveyEditionService.create(createDto));
    }

    @Test
    void testCreateSurveyEditionFailDueToNonExistentSurvey() {
        // Arrange
        CreateSurveyEditionRequestDTO createDto = new CreateSurveyEditionRequestDTO();
        createDto.setSurveyId(2L);

        Mockito.when(surveyRepository.findById(2L)).thenReturn(Optional.empty());

        // Act and Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> surveyEditionService.create(createDto));
    }

    @Test
    void testGetSurveyEditionSuccess() {
        // Arrange
        Long surveyEditionId = 1L;
        SurveyEdition surveyEdition = new SurveyEdition();
        surveyEdition.setId(surveyEditionId);
        surveyEdition.setStartDate(LocalDate.now());
        surveyEdition.setCreationDate(LocalDate.now());
        surveyEdition.setYear(2023);

        SurveyEditionResponseDTO responseDto = new SurveyEditionResponseDTO();
        responseDto.setId(surveyEdition.getId());
        responseDto.setStartDate(surveyEdition.getStartDate());
        responseDto.setCreationDate(surveyEdition.getCreationDate());
        responseDto.setYear(surveyEdition.getYear());

        Mockito.when(surveyEditionRepository.findById(surveyEditionId)).thenReturn(Optional.of(surveyEdition));
        Mockito.when(surveyEditionMapper.toDto(surveyEdition)).thenReturn(responseDto);

        // Act
        CreateSurveyEditionRequestDTO result = surveyEditionService.getById(surveyEditionId);

        // Assert
        Assertions.assertEquals(responseDto.getId(), result.getId());
        Assertions.assertEquals(responseDto.getStartDate(), result.getStartDate());
        Assertions.assertEquals(responseDto.getCreationDate(), result.getCreationDate());
        Assertions.assertEquals(responseDto.getYear(), result.getYear());
    }

    @Test
    void testGetSurveyEditionFailDueToNonExistentId() {
        // Arrange
        Long nonExistentId = 2L;
        Mockito.when(surveyEditionRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act and Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> surveyEditionService.getById(nonExistentId));
    }


    @Test
    void testUpdateSurveyEditionFailDueToNonExistentId() {
        // Arrange
        Long nonExistentId = 2L;
        UpdateSurveyEditionRequestDTO updateDto = new UpdateSurveyEditionRequestDTO();
        updateDto.setSurveyId(1L);
        updateDto.setStartDate(LocalDate.now().plusDays(1));
        updateDto.setCreationDate(LocalDate.now().plusDays(1));
        updateDto.setYear(2024);

        Mockito.when(surveyEditionRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act and Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> surveyEditionService.update(nonExistentId, updateDto));
    }


    @Test
    void testDeleteSurveyEditionFailDueToNonExistentId() {
        // Arrange
        Long nonExistentId = 2L;
        Mockito.when(surveyEditionRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act and Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> surveyEditionService.delete(nonExistentId));
    }
}