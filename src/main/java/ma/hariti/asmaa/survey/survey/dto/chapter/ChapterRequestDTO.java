package ma.hariti.asmaa.survey.survey.dto.chapter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ma.hariti.asmaa.survey.survey.dto.question.QuestionDTO;
import ma.hariti.asmaa.survey.survey.util.BaseDTO;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterRequestDTO extends BaseDTO {

    @Min(value = 1, message = "ID must be a positive number")
    private Long id;

    @Min(value = 1, message = "Survey Edition ID must be a positive number")
    private Long surveyEditionId;

    @NotBlank(message = "Title is required.")
    @Size(max = 100, message = "Title must not exceed 100 characters.")
    private String title;

    @NotNull(message = "Questions list cannot be null.")
    @Valid
    private List<QuestionDTO> questions = new ArrayList<>();

    @Min(value = 1, message = "Parent Chapter ID must be a positive number")
    private Long parentChapterId;

    @Valid
    private List<ChapterRequestDTO> subChapters = new ArrayList<>();
}
