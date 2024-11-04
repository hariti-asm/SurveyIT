package ma.hariti.asmaa.survey.survey.controller;

import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.survey.survey.dto.api.ApiResponseDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.SurveyResultsDTO;
import ma.hariti.asmaa.survey.survey.service.SurveyResultsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/surveys")
@RequiredArgsConstructor
public class SurveyResultsController {

    private final SurveyResultsService surveyResultsService;

    @GetMapping("/{surveyId}/results")
    public ResponseEntity<ApiResponseDTO<SurveyResultsDTO>> getSurveyResults(@PathVariable Long surveyId) {
        SurveyResultsDTO results = surveyResultsService.getSurveyResults(surveyId);
        return ResponseEntity.ok(ApiResponseDTO.success(results));
    }
}