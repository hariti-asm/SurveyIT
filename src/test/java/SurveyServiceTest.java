import ma.hariti.asmaa.survey.survey.dto.survey.CreateSurveyRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.UpdateSurveyRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.UpdateSurveyResponseDTO;
import ma.hariti.asmaa.survey.survey.entity.Owner;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import ma.hariti.asmaa.survey.survey.mapper.SurveyMapper;
import ma.hariti.asmaa.survey.survey.repository.OwnerRepository;
import ma.hariti.asmaa.survey.survey.repository.SurveyRepository;
import ma.hariti.asmaa.survey.survey.service.SurveyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SurveyServiceTest {

    @Mock
    private SurveyRepository surveyRepository;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private SurveyMapper surveyMapper;

    @InjectMocks
    private SurveyService surveyService;

    private CreateSurveyRequestDTO createDTO;
    private UpdateSurveyRequestDTO updateDTO;
    private Survey survey;
    private Owner owner;

    @BeforeEach
    void setUp() {
        owner = new Owner();
        owner.setId(1L);

        survey = new Survey();
        survey.setId(1L);
        survey.setTitle("Test Survey");
        survey.setOwner(owner);

        createDTO = new CreateSurveyRequestDTO();
        createDTO.setTitle("Test Survey");
        createDTO.setOwnerId(1L);

        updateDTO = new UpdateSurveyRequestDTO();
        updateDTO.setTitle("Updated Survey");
    }

    @Nested
    @DisplayName("Create Survey Tests")
    class CreateSurveyTests {

        @Test
        @DisplayName("Should create survey successfully")
        void shouldCreateSurveySuccessfully() {
            // Arrange
            Owner owner = new Owner();
            owner.setId(1L);
            when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
            when(surveyRepository.findByTitle(anyString())).thenReturn(Optional.empty());
            when(surveyMapper.toEntity(any(CreateSurveyRequestDTO.class))).thenReturn(survey);
            when(surveyRepository.save(any(Survey.class))).thenReturn(survey);
            when(surveyMapper.toDto(any(Survey.class))).thenReturn(createDTO);

            // Act
            CreateSurveyRequestDTO result = surveyService.create(createDTO);

            // Assert
            assertNotNull(result);
            assertEquals("Test Survey", result.getTitle());
            assertEquals(1L, result.getOwnerId());
            verify(surveyRepository).save(any(Survey.class));
            verify(surveyMapper).toDto(survey);
        }

        @Test
        @DisplayName("Should throw exception when title already exists")
        void shouldThrowExceptionWhenTitleExists() {
            // Arrange
            when(surveyRepository.findByTitle(anyString())).thenReturn(Optional.of(survey));

            // Act & Assert
            assertThrows(IllegalStateException.class, () -> surveyService.create(createDTO));
        }

        @Test
        @DisplayName("Should throw exception when owner not found")
        void shouldThrowExceptionWhenOwnerNotFound() {
            assertThrows(EntityNotFoundException.class, () -> surveyService.create(createDTO));
        }
        @Test
        @DisplayName("Should throw exception when title is empty")
        void shouldThrowExceptionWhenTitleIsEmpty() {
            // Arrange
            createDTO.setTitle("");

            // Act & Assert
            assertThrows(IllegalStateException.class, () -> surveyService.create(createDTO));
        }

        @Test
        @DisplayName("Should throw exception when title is null")
        void shouldThrowExceptionWhenTitleIsNull() {
            // Arrange
            createDTO.setTitle(null);

            // Act & Assert
            assertThrows(IllegalStateException.class, () -> surveyService.create(createDTO));
        }

        @Test
        @DisplayName("Should throw exception when ownerId is null")
        void shouldThrowExceptionWhenOwnerIdIsNull() {
            // Arrange
            createDTO.setOwnerId(null);

            // Act & Assert
            assertThrows(IllegalStateException.class, () -> surveyService.create(createDTO));
        }
    }

    @Nested
    @DisplayName("Update Survey Tests")
    class UpdateSurveyTests {

        @Test
        @DisplayName("Should update survey successfully")
        void shouldUpdateSurveySuccessfully() {
            // Arrange
            when(surveyRepository.findById(1L)).thenReturn(Optional.of(survey));
            when(surveyRepository.findByTitle(anyString())).thenReturn(Optional.empty());
            when(surveyRepository.save(any(Survey.class))).thenReturn(survey);
            when(surveyMapper.toUpdateResponseDto(any(Survey.class))).thenReturn(new UpdateSurveyResponseDTO());

            // Act
            UpdateSurveyResponseDTO result = surveyService.update(1L, updateDTO);

            // Assert
            assertNotNull(result);
            verify(surveyRepository).save(any(Survey.class));
        }

        @Test
        @DisplayName("Should throw exception when updating non-existent survey")
        void shouldThrowExceptionWhenUpdatingNonExistentSurvey() {
            // Arrange
            when(surveyRepository.findById(anyLong())).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(EntityNotFoundException.class, () -> surveyService.update(1L, updateDTO));
        }

        @Test
        @DisplayName("Should throw exception when updating to existing title")
        void shouldThrowExceptionWhenUpdatingToExistingTitle() {
            // Arrange
            Survey existingSurvey = new Survey();
            existingSurvey.setId(2L);
            existingSurvey.setTitle("Updated Survey");

            when(surveyRepository.findById(1L)).thenReturn(Optional.of(survey));
            when(surveyRepository.findByTitle(updateDTO.getTitle())).thenReturn(Optional.of(existingSurvey));

            // Act & Assert
            assertThrows(IllegalStateException.class, () -> surveyService.update(1L, updateDTO));
        }
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent survey")
    void shouldThrowExceptionWhenDeletingNonExistentSurvey() {
        lenient().when(surveyRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> surveyService.delete(1L));
    }

    @Nested
    @DisplayName("Get Survey Tests")
    class GetSurveyTests {


        @Test
        @DisplayName("Should throw exception when getting non-existent survey")
        void shouldThrowExceptionWhenGettingNonExistentSurvey() {
            // Arrange
            when(surveyRepository.findById(anyLong())).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(EntityNotFoundException.class, () -> surveyService.getById(1L));
        }
    }
}