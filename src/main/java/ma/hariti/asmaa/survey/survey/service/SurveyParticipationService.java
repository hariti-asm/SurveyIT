package ma.hariti.asmaa.survey.survey.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.survey.survey.dto.answer.AnswerResponseDTO;
import ma.hariti.asmaa.survey.survey.dto.participant.SurveyParticipationDTO;
import ma.hariti.asmaa.survey.survey.dto.question.QuestionResponseDTO;
import ma.hariti.asmaa.survey.survey.entity.Answer;
import ma.hariti.asmaa.survey.survey.entity.Question;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import ma.hariti.asmaa.survey.survey.implementation.ISurveyParticipationService;
import ma.hariti.asmaa.survey.survey.repository.AnswerRepository;
import ma.hariti.asmaa.survey.survey.repository.QuestionRepository;
import ma.hariti.asmaa.survey.survey.repository.SurveyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class SurveyParticipationService implements ISurveyParticipationService {
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Override
    public void saveParticipationResponses(Long surveyId, SurveyParticipationDTO participationDTO) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found"));

        for (QuestionResponseDTO responseDTO : participationDTO.getResponses()) {
            Question question = questionRepository.findById(responseDTO.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Question not found"));

            if (responseDTO.getAnswerId() != null) {
                processAnswerId(responseDTO.getAnswerId());
            } else if (responseDTO.getAnswers() != null && !responseDTO.getAnswers().isEmpty()) {
                processMultipleAnswers(responseDTO.getAnswers());
            }
        }
    }

    private void processAnswerId(String answerId) {
        if (answerId.contains("-")) {
            String[] answerIds = answerId.split("-");
            for (String id : answerIds) {
                incrementAnswerCount(Long.parseLong(id));
            }
        } else {
            incrementAnswerCount(Long.parseLong(answerId));
        }
    }

    private void processMultipleAnswers(List<AnswerResponseDTO> answers) {
        for (AnswerResponseDTO answer : answers) {
            incrementAnswerCount(answer.getId());
        }
    }

    private void incrementAnswerCount(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException("Answer not found"));

        if (answer.getSelectionCount() == null) {
            answer.setSelectionCount(1);
        } else {
            answer.setSelectionCount(answer.getSelectionCount() + 1);
        }

        answerRepository.save(answer);
    }
}