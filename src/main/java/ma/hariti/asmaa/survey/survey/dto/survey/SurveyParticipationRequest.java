package ma.hariti.asmaa.survey.survey.dto.survey;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class SurveyParticipationRequest {
    @NotEmpty(message = "Response are required")
    private List<SurveyResponseDTO> responses;
}
