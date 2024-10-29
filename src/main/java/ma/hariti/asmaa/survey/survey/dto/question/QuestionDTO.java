package ma.hariti.asmaa.survey.survey.dto.question;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ma.hariti.asmaa.survey.survey.enums.QuestionType;

import java.util.List;

@Data
@Getter
@Setter
public class QuestionDTO {
    private Long id;
    private String text;
    private Integer answerCount;
    private QuestionType questionType;
    private Long subjectId;
    private List<Long> answerIds;
}
