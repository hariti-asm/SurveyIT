package ma.hariti.asmaa.survey.survey.dto.chapter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ma.hariti.asmaa.survey.survey.dto.question.QuestionDTO;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterRequestDTO {

    private Long id;

    private Long surveyEditionId;

    @NotBlank(message = "Title is required.")
    @Size(max = 100, message = "Title must not exceed 100 characters.")
    private String title;

    @NotNull(message = "Questions list cannot be null.")
    private List<QuestionDTO> questions = new ArrayList<>();

    private Long parentChapterId;

    private List<ChapterRequestDTO> subChapters = new ArrayList<>();
}
