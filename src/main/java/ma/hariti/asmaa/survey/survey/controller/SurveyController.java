package ma.hariti.asmaa.survey.survey.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.survey.survey.dto.survey.CreateSurveyRequest;
import ma.hariti.asmaa.survey.survey.dto.survey.SurveyDTO;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import ma.hariti.asmaa.survey.survey.mapper.SurveyMapper;
import ma.hariti.asmaa.survey.survey.response.ApiResponse;
import ma.hariti.asmaa.survey.survey.service.SurveyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/surveys")
public class SurveyController {
    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping
    public ResponseEntity<Survey> createSurvey(@RequestBody SurveyDTO surveyDTO) {
        Survey createdSurvey = surveyService.createSurvey(surveyDTO);
        return new ResponseEntity<>(createdSurvey, HttpStatus.CREATED);
    }
}