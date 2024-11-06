package ma.hariti.asmaa.survey.survey.dto.surveyEdition;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import ma.hariti.asmaa.survey.survey.util.BaseDTO;

import java.time.LocalDate;

@Data
public class UpdateSurveyEditionRequestDTO extends BaseDTO {

    @NotNull(message = "Start date is required.")
    @PastOrPresent(message = "Start date cannot be in the future.")
    private LocalDate startDate;

    @NotNull(message = "Creation date is required.")
    @PastOrPresent(message = "Creation date cannot be in the future.")
    private LocalDate creationDate;

    @NotNull(message = "Survey ID is required.")
    @Positive(message = "Survey ID must be a positive number.")
    private Long surveyId;

    @NotNull(message = "Year is required.")
    @Min(value = 1900, message = "Year must be no earlier than 1900.")
    private Integer year;
}
