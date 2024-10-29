package ma.hariti.asmaa.survey.survey.dto.chapter;

import lombok.Data;
import ma.hariti.asmaa.survey.survey.dto.question.QuestionDTO;

import java.util.List;

@Data
public class ChapterDTO {
    private Long id;
    private Long surveyId;
    private String title;
    private String description;
    private List<QuestionDTO> questions;
}