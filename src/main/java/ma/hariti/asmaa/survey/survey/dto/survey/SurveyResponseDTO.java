package ma.hariti.asmaa.survey.survey.dto.survey;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SurveyResponseDTO {
    @NotNull(message = "Question ID is required")
    private Long questionId;
    private String answerId;
    private List<String> answerIds;
}
