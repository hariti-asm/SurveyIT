package ma.hariti.asmaa.survey.survey.dto.answer;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AnswerDTO {

    private Long id ;
    @NotBlank(message = "Answer text is required")
    private String text;
    private Integer selectionCount;
    private Long questionId;
}
