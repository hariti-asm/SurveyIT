package ma.hariti.asmaa.survey.survey.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import ma.hariti.asmaa.survey.survey.dto.answer.AnswerDTO;
import ma.hariti.asmaa.survey.survey.dto.question.QuestionDTO;
import ma.hariti.asmaa.survey.survey.entity.Answer;
import ma.hariti.asmaa.survey.survey.entity.Chapter;
import ma.hariti.asmaa.survey.survey.entity.Question;
import ma.hariti.asmaa.survey.survey.enums.QuestionType;
import ma.hariti.asmaa.survey.survey.mapper.QuestionMapper;
import ma.hariti.asmaa.survey.survey.repository.AnswerRepository;
import ma.hariti.asmaa.survey.survey.repository.ChapterRepository;
import ma.hariti.asmaa.survey.survey.repository.QuestionRepository;
import ma.hariti.asmaa.survey.survey.util.AbstractGenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionService extends AbstractGenericService<Question, Long, QuestionDTO, QuestionDTO, QuestionDTO> {

    private final ChapterService chapterService;
    private final QuestionRepository questionRepository;
    private final ChapterRepository chapterRepository;
    private final QuestionMapper questionMapper;
    private final AnswerRepository answerRepository;
    public QuestionService(
            QuestionRepository questionRepository,
            ChapterService chapterService,
            ChapterRepository chapterRepository,
            QuestionMapper questionMapper, AnswerRepository answerRepository
    ) {
        super(questionRepository);
        this.questionRepository = questionRepository;
        this.chapterService = chapterService;
        this.chapterRepository = chapterRepository;
        this.questionMapper = questionMapper;
        this.answerRepository = answerRepository;
    }

    @Override
    protected QuestionDTO mapToCreateDto(Question entity) {
        return questionMapper.toDTO(entity);
    }

    @Override
    protected Question mapToEntity(QuestionDTO createDto) {
        return questionMapper.toEntity(createDto);
    }

    @Override
    protected void mapToEntity(QuestionDTO updateDto, Question entity) {
        questionMapper.updateQuestionFromDTO(updateDto, entity);
    }

    @Override
    protected QuestionDTO mapToResponseDto(Question entity) {
        return questionMapper.toDTO(entity);
    }

    @Transactional
    public QuestionDTO addQuestionToSubChapter(Long subChapterId, QuestionDTO questionDTO) {
        Chapter subChapter = chapterService.findById(subChapterId)
                .orElseThrow(() -> new EntityNotFoundException("Subchapter not found with id: " + subChapterId));

        if (subChapter.getParentChapter() == null) {
            throw new IllegalArgumentException("Chapter with id: " + subChapterId + " is not a subchapter");
        }

        // First save the question
        Question question = mapToEntity(questionDTO);
        question.setSubChapter(subChapter);
        subChapter.getQuestions().add(question);
        Question savedQuestion = questionRepository.save(question);

        // If it's a multiple choice question, save the options as answers
        if (QuestionType.MULTIPLE_CHOICE.equals(questionDTO.getType()) &&
                questionDTO.getAnswers() != null &&
                !questionDTO.getAnswers().isEmpty()) {

            for (AnswerDTO answerDTO : questionDTO.getAnswers()) {
                Answer answer = new Answer();
                answer.setText(answerDTO.getText());
                answer.setSelectionCount(0);
                answer.setQuestion(savedQuestion);
                answerRepository.save(answer);
            }
        }

        chapterRepository.save(subChapter);
        return mapToResponseDto(savedQuestion);
    }
    public QuestionDTO updateQuestion(Long subChapterId, Long questionId, QuestionDTO questionDTO) {
        Chapter subChapter = chapterService.findById(subChapterId)
                .orElseThrow(() -> new EntityNotFoundException("Subchapter not found with id: " + subChapterId));

        Question existingQuestion = findOrThrow(questionId);

        if (!existingQuestion.getSubChapter().getId().equals(subChapterId)) {
            throw new IllegalArgumentException("Question does not belong to this subchapter");
        }

        mapToEntity(questionDTO, existingQuestion);
        existingQuestion.setSubChapter(subChapter);

        Question savedQuestion = questionRepository.save(existingQuestion);
        return mapToResponseDto(savedQuestion);
    }

    public void deleteQuestion(Long subChapterId, Long questionId) {
        Chapter subChapter = chapterService.findById(subChapterId)
                .orElseThrow(() -> new EntityNotFoundException("Subchapter not found with id: " + subChapterId));

        Question question = findOrThrow(questionId);

        if (!question.getSubChapter().getId().equals(subChapterId)) {
            throw new IllegalArgumentException("Question does not belong to this subchapter");
        }

        subChapter.getQuestions().remove(question);
        chapterRepository.save(subChapter);
        delete(questionId);
    }

    public Page<QuestionDTO> getQuestionsForSubChapter(Long subChapterId, int page, int size) {
        Page<Question> questionsPage = questionRepository.findBySubChapterId(subChapterId, PageRequest.of(page, size));

        return questionsPage.map(question -> {
            QuestionDTO dto = new QuestionDTO();
            dto.setId(question.getId());
            dto.setText(question.getText());
            dto.setSubChapterId(question.getSubChapter().getId());
            dto.setChapterId(question.getChapter().getId());
            dto.setType(question.getType());
            dto.setAnswerCount(question.getAnswerCount());
            dto.setRequired(question.getRequired());
            return dto;
        });
    }

    public QuestionDTO getQuestionWithAnswers(Long questionId) {
        Question question = findOrThrow(questionId);
        QuestionDTO questionDTO = mapToResponseDto(question);

        // Fetch answers for multiple choice questions
        if (QuestionType.MULTIPLE_CHOICE.equals(question.getType())) {
            List<Answer> answers = answerRepository.findByQuestionId(questionId);
            List<AnswerDTO> answerDTOs = answers.stream()
                    .map(answer -> {
                        AnswerDTO dto = new AnswerDTO();
                        dto.setId(answer.getId());
                        dto.setText(answer.getText());
                        dto.setSelectionCount(answer.getSelectionCount());
                        dto.setQuestionId(questionId);
                        return dto;
                    })
                    .collect(Collectors.toList());
            questionDTO.setAnswers(answerDTOs);
        }

        return questionDTO;
    }

}