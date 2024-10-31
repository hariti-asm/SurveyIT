
package ma.hariti.asmaa.survey.survey.dto.chapter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ma.hariti.asmaa.survey.survey.dto.question.QuestionResponseDTO;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterResponseDTO {
    @NotNull(message="Id must not be null")
    @Positive (message = "id must be positive")
    private Long id;
    @NotNull(message = "survey edition is is required")
    @Positive(message = " survey edition must be positive")
    private Long surveyEditionId;
    @NotNull(message = "title is required")
    @Size( min = 3 , max = 100, message = "title must be between 3 and 100 characters")
    private String title;
    @Positive(message = "parent chap id must be positive")
    private Long parentChapterId;
    @Valid
    private List<ChapterResponseDTO> subChapters = new ArrayList<>();
    @Valid
    private List<QuestionResponseDTO> questions;



}



