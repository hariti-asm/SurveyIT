package ma.hariti.asmaa.survey.survey.dto.survey;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ma.hariti.asmaa.survey.survey.dto.chapter.ChapterResultDTO;

import java.util.ArrayList;
import java.util.List;

@Data
public class SurveyResultsDTO {

    @NotBlank(message = "Survey title is required.")
    private String surveyTitle;

    @NotNull(message = "Chapters list cannot be null.")
    @Valid
    private List<ChapterResultDTO> chapters = new ArrayList<>();
}
