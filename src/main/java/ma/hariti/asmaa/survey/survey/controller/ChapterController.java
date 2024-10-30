package ma.hariti.asmaa.survey.survey.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.survey.survey.dto.chapter.ChapterDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.SurveyDTO;
import ma.hariti.asmaa.survey.survey.response.ApiResponse;
import ma.hariti.asmaa.survey.survey.service.ChapterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/surveys/{surveyId}/chapters")
@RequiredArgsConstructor
public class ChapterController {
    private final ChapterService chapterService;

    @PostMapping
    public ResponseEntity<ApiResponse<ChapterDTO>> addChapter(
            @PathVariable Long surveyId,
            @Valid @RequestBody ChapterDTO chapterDTO) {
        ChapterDTO savedChapter = chapterService.addChapterToSurvey(surveyId, chapterDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(savedChapter));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ChapterDTO>>> getChapters(@PathVariable Long surveyId) {
        List<ChapterDTO> chapters = chapterService.getChaptersBySurveyId(surveyId);
        return ResponseEntity.ok(ApiResponse.success(chapters));
    }
}