package ma.hariti.asmaa.survey.survey.dto.survey;

import lombok.Data;
import ma.hariti.asmaa.survey.survey.dto.chapter.ChapterResultDTO;

import java.util.ArrayList;
import java.util.List;
@Data
public class SurveyResultsDTO {
    private String surveyTitle;
    private List<ChapterResultDTO> chapters = new ArrayList<>();
}
