package ma.hariti.asmaa.survey.survey.dto.question;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ma.hariti.asmaa.survey.survey.dto.answer.AnswerDTO;
import ma.hariti.asmaa.survey.survey.enums.QuestionType;

import java.util.List;
@Data
public class QuestionDTO {
    private Long id;

    @NotBlank(message = "Question text is required.")
    private String text;

    @NotNull(message = "Sub-chapter ID is required.")
    private Long subChapterId;

    @NotNull(message = "Chapter ID is required.")
    private Long chapterId;

    private List<AnswerDTO> answers;
    private QuestionType type;
    private int answerCount;
    private Boolean required;
}