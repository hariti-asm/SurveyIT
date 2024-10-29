package ma.hariti.asmaa.survey.survey.dto.question;

import lombok.Data;
import ma.hariti.asmaa.survey.survey.dto.answer.AnswerDTO;
import ma.hariti.asmaa.survey.survey.enums.QuestionType;

import java.util.List;
@Data
public class QuestionDTO {
    private Long id;
    private String text;
    private Long chapterId;
    private Long subjectId;
    private List<AnswerDTO> answers;
    private QuestionType type;
    private Boolean required;
}