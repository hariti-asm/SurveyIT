package ma.hariti.asmaa.survey.survey.controller;

import ma.hariti.asmaa.survey.survey.dto.survey.CreateSurveyRequestDTO;
import ma.hariti.asmaa.survey.survey.service.SurveyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}