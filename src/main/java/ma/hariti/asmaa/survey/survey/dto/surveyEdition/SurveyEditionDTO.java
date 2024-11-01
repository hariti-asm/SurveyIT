package ma.hariti.asmaa.survey.survey.dto.surveyEdition;


import lombok.Data;
import ma.hariti.asmaa.survey.survey.dto.chapter.ChapterRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.CreateSurveyRequestDTO;

import java.time.LocalDate;
import java.util.List;

@Data
public class SurveyEditionDTO {
    private Long id;
    private LocalDate creationDate;
    private LocalDate startDate;
    private Integer year;
    private CreateSurveyRequestDTO survey;
    private List<ChapterRequestDTO> chapters;
}