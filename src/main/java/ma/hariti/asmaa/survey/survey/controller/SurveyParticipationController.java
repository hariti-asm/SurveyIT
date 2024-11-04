package ma.hariti.asmaa.survey.survey.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.survey.survey.dto.api.ApiResponseDTO;
import ma.hariti.asmaa.survey.survey.dto.participant.SurveyParticipationDTO;
import ma.hariti.asmaa.survey.survey.service.SurveyParticipationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/surveys")
@RequiredArgsConstructor
public class SurveyParticipationController {
    private final SurveyParticipationService participationService;

    @PostMapping("/{surveyId}/participate")
    public ResponseEntity<ApiResponseDTO<Void>> participateInSurvey(
            @PathVariable Long surveyId,
            @Valid @RequestBody SurveyParticipationDTO participationDTO) {
        participationService.saveParticipationResponses(surveyId, participationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.success(null));
    }
}