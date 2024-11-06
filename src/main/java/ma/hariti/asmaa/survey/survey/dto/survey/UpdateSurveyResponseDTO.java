package ma.hariti.asmaa.survey.survey.dto.survey;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.hariti.asmaa.survey.survey.dto.surveyEdition.SurveyEditionResponseDTO;
import ma.hariti.asmaa.survey.survey.util.BaseDTO;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSurveyResponseDTO extends BaseDTO {

    @Min(value = 1, message = "ID must be a positive number")
    private Long id;

    @NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "Description is required.")
    private String description;

    @Min(value = 1, message = "Owner ID must be a positive number")
    private Long ownerId;

    @NotNull(message = "Survey editions list cannot be null.")
    @Valid
    private List<SurveyEditionResponseDTO> surveyEditions = new ArrayList<>();
}
