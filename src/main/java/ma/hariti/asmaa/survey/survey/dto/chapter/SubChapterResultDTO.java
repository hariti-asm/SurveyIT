package ma.hariti.asmaa.survey.survey.dto.chapter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
public class SubChapterResultDTO {

    @NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "Question is required.")
    private String question;

    @NotNull(message = "Answers map cannot be null.")
    private Map<String, Integer> answers;

    @Min(value = 0, message = "Total answers must be zero or greater.")
    private int totalAnswers;
}
