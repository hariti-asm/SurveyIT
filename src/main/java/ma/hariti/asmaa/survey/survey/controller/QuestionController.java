package ma.hariti.asmaa.survey.survey.controller;

import jakarta.validation.Valid;
import ma.hariti.asmaa.survey.survey.dto.api.ApiResponseDTO;
import ma.hariti.asmaa.survey.survey.dto.question.QuestionDTO;
import ma.hariti.asmaa.survey.survey.service.QuestionService;
import ma.hariti.asmaa.survey.survey.util.AbstractCrudController;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/surveys/{surveyId}/chapters/{chapterId}/subchapters")
public class QuestionController extends AbstractCrudController<
        QuestionDTO,
        QuestionDTO,
        QuestionDTO
        > {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        super(questionService);
        this.questionService = questionService;
    }

    @PostMapping("/{subChapterId}/questions")
    public ResponseEntity<ApiResponseDTO<QuestionDTO>> addQuestion(
            @PathVariable Long surveyId,
            @PathVariable Long chapterId,
            @PathVariable Long subChapterId,
            @Valid @RequestBody QuestionDTO questionDTO) {
        QuestionDTO savedQuestion = questionService.addQuestionToSubChapter(subChapterId, questionDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success(savedQuestion));
    }

    @GetMapping("/{subChapterId}/questions")
    public ResponseEntity<ApiResponseDTO<List<QuestionDTO>>> getQuestions(
            @PathVariable Long subChapterId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        List<QuestionDTO> questionsPage = questionService.getQuestionsForSubChapter(subChapterId, page, size);
        return ResponseEntity.ok(ApiResponseDTO.success(questionsPage));
    }

    @PutMapping("/{subChapterId}/questions/{questionId}")
    public ResponseEntity<ApiResponseDTO<QuestionDTO>> updateQuestion(
            @PathVariable Long subChapterId,
            @PathVariable Long questionId,
            @Valid @RequestBody QuestionDTO questionDTO) {
        QuestionDTO updatedQuestion = questionService.updateQuestion(subChapterId, questionId, questionDTO);
        return ResponseEntity.ok(ApiResponseDTO.success(updatedQuestion));
    }

    @DeleteMapping("/{subChapterId}/questions/{questionId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteQuestion(
            @PathVariable Long subChapterId,
            @PathVariable Long questionId) {
        questionService.deleteQuestion(subChapterId, questionId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponseDTO.success(null));
    }


    @Override
    public ResponseEntity<ApiResponseDTO<Page<QuestionDTO>>> getAll(
            int page,
            int size,
            String sortBy,
            String sortDirection) {
        return super.getAll(page, size, sortBy, sortDirection);
    }
}