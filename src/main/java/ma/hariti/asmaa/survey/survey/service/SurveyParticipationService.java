package ma.hariti.asmaa.survey.survey.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.survey.survey.dto.answer.AnswerResponseDTO;
import ma.hariti.asmaa.survey.survey.dto.participant.SurveyParticipationDTO;
import ma.hariti.asmaa.survey.survey.dto.question.QuestionResponseDTO;
import ma.hariti.asmaa.survey.survey.entity.Answer;
import ma.hariti.asmaa.survey.survey.entity.Chapter;
import ma.hariti.asmaa.survey.survey.entity.Question;
import ma.hariti.asmaa.survey.survey.entity.SurveyEdition;
import ma.hariti.asmaa.survey.survey.implementation.ISurveyParticipationService;
import ma.hariti.asmaa.survey.survey.repository.AnswerRepository;
import ma.hariti.asmaa.survey.survey.repository.ChapterRepository;
import ma.hariti.asmaa.survey.survey.repository.QuestionRepository;
import ma.hariti.asmaa.survey.survey.repository.SurveyEditionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class SurveyParticipationService implements ISurveyParticipationService {
    private static final Logger log = LoggerFactory.getLogger(SurveyParticipationService.class);
    private final SurveyEditionRepository surveyEditionRepository;
    private final ChapterRepository chapterRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Override
    @Transactional
    public void saveParticipationResponses(Long surveyEditionId, SurveyParticipationDTO participationDTO) {
        SurveyEdition surveyEdition = surveyEditionRepository.findById(surveyEditionId)
                .orElseThrow(() -> new EntityNotFoundException("Survey Edition not found with ID: " + surveyEditionId));

        log.info("Processing participation for Survey Edition ID: {}", surveyEditionId);

        for (QuestionResponseDTO responseDTO : participationDTO.getResponses()) {
            processQuestionResponse(surveyEditionId, responseDTO);
        }

        // Flush changes to ensure they are committed
        surveyEditionRepository.flush();
    }

    @Transactional
    protected void processQuestionResponse(Long surveyEditionId, QuestionResponseDTO responseDTO) {
        Question question = questionRepository.findById(responseDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Question not found with ID: " + responseDTO.getId()));

        Chapter chapter = chapterRepository.findById(question.getChapter().getId())
                .orElseThrow(() -> new EntityNotFoundException("Chapter not found for Question ID: " + question.getId()));

        // Verify the question's chapter belongs to this survey edition
        if (!chapter.getSurveyEdition().getId().equals(surveyEditionId)) {
            throw new IllegalArgumentException(
                    "Question ID " + question.getId() + " belongs to Chapter " + chapter.getId() +
                            " which is not part of Survey Edition " + surveyEditionId
            );
        }

        try {
            if (responseDTO.getAnswerId() != null) {
                processAnswerId(responseDTO.getAnswerId());
            } else if (responseDTO.getAnswers() != null && !responseDTO.getAnswers().isEmpty()) {
                processMultipleAnswers(responseDTO.getAnswers());
            }

            // Flush changes for this question
            questionRepository.flush();
            answerRepository.flush();
        } catch (Exception e) {
            log.error("Error processing response for question ID {}: {}", responseDTO.getId(), e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void processAnswerId(String answerId) {
        if (answerId.contains("-")) {
            String[] answerIds = answerId.split("-");
            for (String id : answerIds) {
                incrementAnswerCount(Long.parseLong(id.trim()));
            }
        } else {
            incrementAnswerCount(Long.parseLong(answerId.trim()));
        }
    }

    @Transactional
    public void processMultipleAnswers(List<AnswerResponseDTO> answers) {
        for (AnswerResponseDTO answer : answers) {
            incrementAnswerCount(answer.getId());
        }
    }

    @Transactional
    public void incrementAnswerCount(Long answerId) {
        log.info("Incrementing answer count for answer ID: {}", answerId);

        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException("Answer not found with ID: " + answerId));

        log.info("Before increment, selection count: {}", answer.getSelectionCount());
        answer.setSelectionCount(answer.getSelectionCount() + 1);
        answerRepository.saveAndFlush(answer);
        log.info("After increment, selection count: {}", answer.getSelectionCount());

        Question question = questionRepository.findById(answer.getQuestion().getId())
                .orElseThrow(() -> new EntityNotFoundException("Question not found for Answer ID: " + answerId));

        long totalSelectionCount = answerRepository.findByQuestionId(question.getId())
                .stream()
                .mapToLong(Answer::getSelectionCount)
                .sum();

        log.info("Total selection count for question ID {}: {}", question.getId(), totalSelectionCount);
        question.setAnswerCount(Math.toIntExact(totalSelectionCount));
        questionRepository.saveAndFlush(question);
        log.info("Updated question answer count: {}", question.getAnswerCount());
    }

}