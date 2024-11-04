package ma.hariti.asmaa.survey.survey.controller;

import jakarta.validation.Valid;
import ma.hariti.asmaa.survey.survey.dto.api.ApiResponseDTO;
import ma.hariti.asmaa.survey.survey.dto.question.QuestionDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.CreateSurveyRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.UpdateSurveyRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.UpdateSurveyResponseDTO;
import ma.hariti.asmaa.survey.survey.service.SurveyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/surveys")
public class SurveyController {
    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping
    public ResponseEntity<CreateSurveyRequestDTO> createSurvey(@Valid @RequestBody CreateSurveyRequestDTO createSurveyRequestDTO) {
        CreateSurveyRequestDTO createdSurvey = surveyService.createSurvey(createSurveyRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSurvey);

    }
    @PutMapping("/{id}")
    public ResponseEntity<UpdateSurveyResponseDTO> updateSurvey(
            @PathVariable Long id,
            @RequestBody UpdateSurveyRequestDTO updateSurveyRequestDTO) {
        UpdateSurveyResponseDTO updatedSurvey = surveyService.updateSurvey(id, updateSurveyRequestDTO);
        return ResponseEntity.ok(updatedSurvey);
    }
    @GetMapping
    public ResponseEntity<ApiResponseDTO<Page<CreateSurveyRequestDTO>>> getAllSurveys(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                sortDirection.equalsIgnoreCase("desc")
                        ? Sort.by(sortBy).descending()
                        : Sort.by(sortBy).ascending()
        );

        Page<CreateSurveyRequestDTO> surveysPage = surveyService.getAllSurveys(pageable);


        return ResponseEntity.ok(
                ApiResponseDTO.success(
                        surveysPage,
                        (int) surveysPage.getTotalElements()
                )
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<CreateSurveyRequestDTO> getSurveyById(@PathVariable Long id) {
        CreateSurveyRequestDTO survey = surveyService.getSurveyById(id);
        return ResponseEntity.ok(survey);
    }

}