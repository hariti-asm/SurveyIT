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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SurveyParticipationService implements ISurveyParticipationService {
    private static final Logger log = LoggerFactory.getLogger(SurveyParticipationService.class);
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

    public void processAnswerId(String answerId) {
        if (answerId.contains("-")) {
            String[] answerIds = answerId.split("-");
            for (String id : answerIds) {
                incrementAnswerCount(Long.parseLong(id));
            }
        } else {
            incrementAnswerCount(Long.parseLong(answerId));
        }
    }

    public void processMultipleAnswers(List<AnswerResponseDTO> answers) {
        for (AnswerResponseDTO answer : answers) {
            incrementAnswerCount(answer.getId());
        }
    }

    public void incrementAnswerCount(Long answerId) {
        log.info("Incrementing answer count for answer ID: {}", answerId);
        Optional<Answer> answerOptional = answerRepository.findById(answerId);
        if (answerOptional.isPresent()) {
            Answer answer = answerOptional.get();
            log.info("Found answer: {}", answer);
            answer.setSelectionCount(answer.getSelectionCount() + 1);
            answerRepository.save(answer);
            log.info("Answer count incremented: {}", answer);
        } else {
            log.error("Answer not found with ID: {}", answerId);
            throw new EntityNotFoundException("Answer not found with ID: " + answerId);
        }
    }
}