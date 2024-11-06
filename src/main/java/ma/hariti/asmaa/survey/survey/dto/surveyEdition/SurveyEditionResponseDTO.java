package ma.hariti.asmaa.survey.survey.dto.surveyEdition;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.hariti.asmaa.survey.survey.util.BaseDTO;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyEditionResponseDTO extends BaseDTO {

    @NotNull(message = "ID cannot be null")
    private Long id;

    @NotNull(message = "Year cannot be null")
    @Positive(message = "Year must be a positive number")
    private Integer year;

    @NotNull(message = "Start date cannot be null")
    @PastOrPresent(message = "Start date must be in the past or present")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    @FutureOrPresent(message = "creation date must be in the present or future")
    private LocalDate creationDate;

    @NotNull(message = "Survey ID cannot be null")
    @Positive(message = "Survey ID must be a positive number")
    private Long surveyId;
}
