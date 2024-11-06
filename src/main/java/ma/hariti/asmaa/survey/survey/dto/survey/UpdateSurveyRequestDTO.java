package ma.hariti.asmaa.survey.survey.dto.survey;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.hariti.asmaa.survey.survey.dto.surveyEdition.CreateSurveyEditionRequestDTO;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import ma.hariti.asmaa.survey.survey.util.BaseDTO;
import ma.hariti.asmaa.survey.survey.validation.annotation.Exists;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSurveyRequestDTO extends BaseDTO {
    @NotNull(message = "ID is required for updating a survey")
    @Exists(entity =  Survey.class , message = "id is obligatory for update")
    private Long id;

    @NotBlank(message = "Survey title cannot be blank")
    @Size(min = 3, max = 100, message = "Survey title must be between 3 and 100 characters")
    private String title;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Owner ID is required")
    @Positive(message = "Owner ID must be a positive number")
    @Exists(
            entity = Survey.class,
            message = "owner with this id doesn't exist"
    )
    private Long ownerId;

    @Valid
    private List<CreateSurveyEditionRequestDTO> surveyEditions = new ArrayList<>();
}



