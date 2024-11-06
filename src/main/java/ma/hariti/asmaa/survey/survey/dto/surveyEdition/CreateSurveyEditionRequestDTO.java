package ma.hariti.asmaa.survey.survey.dto.surveyEdition;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ma.hariti.asmaa.survey.survey.dto.chapter.ChapterRequestDTO;
import ma.hariti.asmaa.survey.survey.util.BaseDTO;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateSurveyEditionRequestDTO extends BaseDTO {
    @NotNull(message = "ID cannot be null")
    private Long id;

    @NotNull(message = "Creation date cannot be null")
    @PastOrPresent(message = "Creation date must be in the past or present")
    private LocalDate creationDate;

    @NotNull(message = "Start date cannot be null")
    @PastOrPresent(message = "Start date must be in the past or present")
    private LocalDate startDate;

    @NotNull(message = "Year cannot be null")
    @Positive(message = "Year must be a positive number")
    private Integer year;

    @NotNull(message = "Survey id cannot be null")
    private Long surveyId;

    @Size(min = 1, message = "There must be at least one chapter")
    private List<ChapterRequestDTO> chapters;
}
