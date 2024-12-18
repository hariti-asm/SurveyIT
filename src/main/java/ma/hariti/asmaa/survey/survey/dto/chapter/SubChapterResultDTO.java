package ma.hariti.asmaa.survey.survey.dto.chapter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ma.hariti.asmaa.survey.survey.dto.question.QuestionDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class SubChapterResultDTO {
    @Min(value = 1, message = "ID must be a positive number")
    private Long id;
    @NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "Question is required.")
    private String question;

    @NotNull(message = "Answers map cannot be null.")
    private Map<String, Integer> answers;

    @Min(value = 0, message = "Total answers must be zero or greater.")
    private int totalAnswers;
    private List<QuestionDTO> questions = new ArrayList<>();

}
