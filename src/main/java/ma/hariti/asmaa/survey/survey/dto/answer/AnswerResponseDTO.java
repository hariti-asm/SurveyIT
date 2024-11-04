package ma.hariti.asmaa.survey.survey.dto.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnswerResponseDTO {
    private Long id;
    private String text;
    private Integer selectionCount;
    private Long questionId;
}