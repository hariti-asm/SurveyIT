package ma.hariti.asmaa.survey.survey.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.survey.survey.dto.api.ApiResponseDTO;
import ma.hariti.asmaa.survey.survey.dto.question.QuestionDTO;
import ma.hariti.asmaa.survey.survey.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/surveys/{surveyId}/chapters/{chapterId}/subchapters")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/{subChapterId}/questions")
    public ResponseEntity<QuestionDTO> addQuestion(
            @PathVariable Long surveyId,
            @PathVariable Long chapterId,
            @PathVariable Long subChapterId,
            @Valid @RequestBody QuestionDTO questionDTO) {
        QuestionDTO savedQuestion = questionService.addQuestionToSubChapter(subChapterId, questionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedQuestion);
    }
    @GetMapping("/{subChapterId}/questions")
    public ResponseEntity<ApiResponseDTO<Page<QuestionDTO>>> getQuestions(
            @PathVariable Long subChapterId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<QuestionDTO> questionsPage = questionService.getQuestionsForSubChapter(subChapterId, page, size);
        return ResponseEntity.ok(ApiResponseDTO.success(questionsPage, (int) questionsPage.getTotalElements()));
    }

    @PutMapping("/{subChapterId}/questions/{questionId}")
    public ResponseEntity<QuestionDTO> updateQuestion(
            @PathVariable Long surveyId,
            @PathVariable Long chapterId,
            @PathVariable Long subChapterId,
            @PathVariable Long questionId,
            @Valid @RequestBody QuestionDTO questionDTO) {
        QuestionDTO updatedQuestion = questionService.updateQuestion(subChapterId, questionId, questionDTO);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/{subChapterId}/questions/{questionId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteQuestion(
            @PathVariable Long surveyId,
            @PathVariable Long chapterId,
            @PathVariable Long subChapterId,
            @PathVariable Long questionId) {
        questionService.deleteQuestion(subChapterId, questionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
