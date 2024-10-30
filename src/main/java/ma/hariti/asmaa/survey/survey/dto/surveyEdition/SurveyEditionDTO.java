package ma.hariti.asmaa.survey.survey.dto.surveyEdition;


import lombok.Data;
import ma.hariti.asmaa.survey.survey.dto.chapter.ChapterDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.SurveyDTO;

import java.time.LocalDate;
import java.util.List;

@Data
public class SurveyEditionDTO {
    private Long id;
    private LocalDate creationDate;
    private LocalDate startDate;
    private Integer year;
    private SurveyDTO survey;
    private List<ChapterDTO> chapters;
}