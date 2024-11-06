package ma.hariti.asmaa.survey.survey.dto.question;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import ma.hariti.asmaa.survey.survey.dto.answer.AnswerResponseDTO;

import java.util.List;

@Data
public class QuestionResponseDTO {
    @NotNull(message = "ID cannot be null")
    @Positive(message = "ID must be a positive number")
    private Long id;

    @NotNull(message = "Answer ID cannot be null")
    @NotEmpty(message = "Answer ID cannot be empty")
    private String answerId;

    @NotNull(message = "Answers cannot be null")
    @NotEmpty(message = "Answers cannot be empty")
    @Valid
    private List<AnswerResponseDTO> answers;
}
