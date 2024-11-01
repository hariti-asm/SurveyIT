package ma.hariti.asmaa.survey.survey.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.survey.survey.dto.question.QuestionDTO;
import ma.hariti.asmaa.survey.survey.entity.Chapter;
import ma.hariti.asmaa.survey.survey.entity.Question;
import ma.hariti.asmaa.survey.survey.mapper.QuestionMapper;
import ma.hariti.asmaa.survey.survey.repository.QuestionRepository;
import ma.hariti.asmaa.survey.survey.repository.ChapterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {
    private final ChapterService chapterService;
    private final QuestionRepository questionRepository;
    private final ChapterRepository chapterRepository;  // Added ChapterRepository
    private final QuestionMapper questionMapper;

    public QuestionDTO addQuestionToSubChapter(Long subChapterId, QuestionDTO questionDTO) {
        // Find the subchapter
        Chapter subChapter = chapterService.findById(subChapterId)
                .orElseThrow(() -> new EntityNotFoundException("Subchapter not found with id: " + subChapterId));

        // Ensure it's a subchapter (has a parent)
        if (subChapter.getParentChapter() == null) {
            throw new IllegalArgumentException("Chapter with id: " + subChapterId + " is not a subchapter");
        }

        // Map DTO to entity
        Question question = questionMapper.toEntity(questionDTO);

        // Set relationships
        question.setSubChapter(subChapter);
        subChapter.getQuestions().add(question);

        // Save the question
        Question savedQuestion = questionRepository.save(question);

        // Update the chapter with the new question
        chapterRepository.save(subChapter);  // Changed from chapterService.save to chapterRepository.save

        // Return the DTO
        return questionMapper.toDTO(savedQuestion);
    }

    public List<QuestionDTO> getQuestionsForSubChapter(Long subChapterId) {
        Chapter subChapter = chapterService.findById(subChapterId)
                .orElseThrow(() -> new EntityNotFoundException("Subchapter not found with id: " + subChapterId));

        if (subChapter.getParentChapter() == null) {
            throw new IllegalArgumentException("Chapter with id: " + subChapterId + " is not a subchapter");
        }

        return subChapter.getQuestions().stream()
                .map(questionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public QuestionDTO updateQuestion(Long subChapterId, Long questionId, QuestionDTO questionDTO) {
        Chapter subChapter = chapterService.findById(subChapterId)
                .orElseThrow(() -> new EntityNotFoundException("Subchapter not found with id: " + subChapterId));

        Question existingQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + questionId));

        // Verify the question belongs to this subchapter
        if (!existingQuestion.getSubChapter().getId().equals(subChapterId)) {
            throw new IllegalArgumentException("Question does not belong to this subchapter");
        }

        // Update question fields
        questionMapper.updateQuestionFromDTO(questionDTO, existingQuestion);
        existingQuestion.setSubChapter(subChapter);

        Question savedQuestion = questionRepository.save(existingQuestion);
        return questionMapper.toDTO(savedQuestion);
    }

    public void deleteQuestion(Long subChapterId, Long questionId) {
        Chapter subChapter = chapterService.findById(subChapterId)
                .orElseThrow(() -> new EntityNotFoundException("Subchapter not found with id: " + subChapterId));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + questionId));

        // Verify the question belongs to this subchapter
        if (!question.getSubChapter().getId().equals(subChapterId)) {
            throw new IllegalArgumentException("Question does not belong to this subchapter");
        }

        subChapter.getQuestions().remove(question);
        chapterRepository.save(subChapter);  // Save the updated subchapter
        questionRepository.delete(question);
    }

    public QuestionDTO getQuestionById(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + questionId));
        return questionMapper.toDTO(question);
    }
}