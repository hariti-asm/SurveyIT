import ma.hariti.asmaa.survey.survey.dto.answer.AnswerResponseDTO;
import ma.hariti.asmaa.survey.survey.dto.participant.SurveyParticipationDTO;
import ma.hariti.asmaa.survey.survey.dto.question.QuestionResponseDTO;
import ma.hariti.asmaa.survey.survey.entity.Answer;
import ma.hariti.asmaa.survey.survey.entity.Question;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import ma.hariti.asmaa.survey.survey.repository.AnswerRepository;
import ma.hariti.asmaa.survey.survey.repository.QuestionRepository;
import ma.hariti.asmaa.survey.survey.repository.SurveyRepository;
import ma.hariti.asmaa.survey.survey.service.SurveyParticipationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SurveyParticipationServiceTest {

    @Mock
    private SurveyRepository surveyRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private SurveyParticipationService surveyParticipationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void testSaveParticipationResponses_surveyNotFound() {
        // Arrange
        Long surveyId = 1L;
        SurveyParticipationDTO participationDTO = new SurveyParticipationDTO();

        // Use lenient to avoid unnecessary stubbing exception
        lenient().when(surveyRepository.findById(surveyId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> surveyParticipationService.saveParticipationResponses(surveyId, participationDTO));
    }

    @Test
    void testSaveParticipationResponses_questionNotFound() {
        // Arrange
        Long surveyId = 1L;
        Survey survey = new Survey();

        // Use lenient stubbing for unnecessary mocks
        lenient().when(surveyRepository.findById(surveyId)).thenReturn(Optional.of(survey));
        lenient().when(questionRepository.findById(anyLong())).thenReturn(Optional.empty());

        SurveyParticipationDTO participationDTO = new SurveyParticipationDTO();
        QuestionResponseDTO responseDTO = new QuestionResponseDTO();
        responseDTO.setId(1L);
        participationDTO.setResponses(List.of(responseDTO));

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> surveyParticipationService.saveParticipationResponses(surveyId, participationDTO));
    }

    @Test
    void testProcessAnswerId_answerNotFound() {

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> surveyParticipationService.processAnswerId("1"));
    }


    @Test
    void testProcessMultipleAnswers_answerNotFound() {
        // Arrange
        AnswerResponseDTO answerDTO = new AnswerResponseDTO();
        answerDTO.setId(1L);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> surveyParticipationService.processMultipleAnswers(List.of(answerDTO)));
    }


    @Test
    void testIncrementAnswerCount_answerNotFound() {
        assertThrows(EntityNotFoundException.class, () -> surveyParticipationService.incrementAnswerCount(1L));

    }
}
