package ma.hariti.asmaa.survey.survey.controller;

import ma.hariti.asmaa.survey.survey.dto.survey.CreateSurveyRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.UpdateSurveyRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.UpdateSurveyResponseDTO;
import ma.hariti.asmaa.survey.survey.service.SurveyService;
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
    public ResponseEntity<CreateSurveyRequestDTO> createSurvey(@RequestBody CreateSurveyRequestDTO createSurveyRequestDTO) {
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
    public ResponseEntity<List<CreateSurveyRequestDTO>> getAllSurveys() {
        List<CreateSurveyRequestDTO> surveys = surveyService.getAllSurveys();
        return ResponseEntity.ok(surveys);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CreateSurveyRequestDTO> getSurveyById(@PathVariable Long id) {
        CreateSurveyRequestDTO survey = surveyService.getSurveyById(id);
        return ResponseEntity.ok(survey);
    }

}