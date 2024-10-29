package ma.hariti.asmaa.survey.survey.dto.survey;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateSurveyRequest {
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    @NotNull(message = "Year is required")
    private Integer year;
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
}
