package ma.hariti.asmaa.survey.survey.dto.survey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.hariti.asmaa.survey.survey.dto.surveyEdition.SurveyEditionResponseDTO;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSurveyResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Long ownerId;
    private List<SurveyEditionResponseDTO> surveyEditions = new ArrayList<>();
}
