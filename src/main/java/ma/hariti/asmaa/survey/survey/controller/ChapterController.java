package ma.hariti.asmaa.survey.survey.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.survey.survey.dto.api.ApiResponseDTO;
import ma.hariti.asmaa.survey.survey.dto.chapter.ChapterRequestDTO;
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

    // Create Chapter
    @PostMapping
    public ResponseEntity<ApiResponseDTO<ChapterRequestDTO>> addChapter(
            @PathVariable Long surveyId,
            @Valid @RequestBody ChapterRequestDTO chapterRequestDTO) {
        ChapterRequestDTO savedChapter = chapterService.addChapterToSurvey(surveyId, chapterRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success(savedChapter));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<ChapterRequestDTO>>> getChapters(@PathVariable Long surveyId) {
        List<ChapterRequestDTO> chapters = chapterService.getChaptersBySurveyId(surveyId);
        return ResponseEntity.ok(ApiResponseDTO.success(chapters));
    }

    @GetMapping("/{chapterId}")
    public ResponseEntity<ApiResponseDTO<ChapterRequestDTO>> getChapter(
            @PathVariable Long surveyId, @PathVariable Long chapterId) {
        ChapterRequestDTO chapter = chapterService.getChapterById(surveyId, chapterId);
        return ResponseEntity.ok(ApiResponseDTO.success(chapter));
    }

    @PutMapping("/{chapterId}")
    public ResponseEntity<ApiResponseDTO<ChapterRequestDTO>> updateChapter(
            @PathVariable Long surveyId, @PathVariable Long chapterId,
            @Valid @RequestBody ChapterRequestDTO chapterRequestDTO) {
        ChapterRequestDTO updatedChapter = chapterService.updateChapter(surveyId, chapterId, chapterRequestDTO);
        return ResponseEntity.ok(ApiResponseDTO.success(updatedChapter));
    }

    @DeleteMapping("/{chapterId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteChapter(
            @PathVariable Long surveyId, @PathVariable Long chapterId) {
        chapterService.deleteChapter(surveyId, chapterId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponseDTO.success(null));
    }
    @PostMapping("/{chapterId}/subchapters")
    public ResponseEntity<ApiResponseDTO<ChapterRequestDTO>> addSubChapter(
            @PathVariable Long chapterId,
            @Valid @RequestBody ChapterRequestDTO subChapterDTO) {
        ChapterRequestDTO savedSubChapter = chapterService.addSubChapter(chapterId, subChapterDTO);
        return ResponseEntity.ok(ApiResponseDTO.success(savedSubChapter));
    }
}
