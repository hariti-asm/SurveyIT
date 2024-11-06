package ma.hariti.asmaa.survey.survey.dto.answer;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnswerResponseDTO {
    @Min(value = 1, message = "ID must be a positive number")
    private Long id;

    @NotBlank(message = "Answer text is required.")
    private String text;

    @Min(value = 0, message = "Selection count must be zero or greater.")
    private Integer selectionCount;

    @Min(value = 1, message = "Question ID must be a positive number")
    private Long questionId;
}
