package ma.hariti.asmaa.survey.survey.dto.survey;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import ma.hariti.asmaa.survey.survey.entity.Owner;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import ma.hariti.asmaa.survey.survey.validation.annotation.Exists;

import java.time.LocalDate;

@Data
public class CreateSurveyRequestDTO {
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    @NotNull(message = "Year is required")
    private Integer year;
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    @NotNull(message = "Owner ID is required")
    @Positive(message = "Owner ID must be a positive number")
    @Exists(
            entity = Owner.class,
            message = "owner with this id doesn't exist"
    )
    private Long ownerId;


}










