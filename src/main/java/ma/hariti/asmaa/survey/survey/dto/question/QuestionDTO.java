package ma.hariti.asmaa.survey.survey.dto.question;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ma.hariti.asmaa.survey.survey.dto.answer.AnswerDTO;
import ma.hariti.asmaa.survey.survey.enums.QuestionType;
import ma.hariti.asmaa.survey.survey.util.BaseDTO;

import java.util.List;

@Data
public class QuestionDTO extends BaseDTO {
    @Min(value = 1, message = "ID must be a positive number")
    private Long id;

    @NotBlank(message = "Question text is required.")
    private String text;

    @NotNull(message = "Sub-chapter ID is required.")
    private Long subChapterId;

    @NotNull(message = "Chapter ID is required.")
    private Long chapterId;

    @Valid
    private List<AnswerDTO> answers;

    @NotNull(message = "Question type is required.")
    private QuestionType type;

    @Min(value = 0, message = "Answer count must be zero or greater.")
    private int answerCount;

    @NotNull(message = "The 'required' field cannot be null.")
    private Boolean required;
}
