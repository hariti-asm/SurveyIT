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

    private Long surveyId;

    @NotBlank(message = "Title is required.")
    @Size(max = 100, message = "Title must not exceed 100 characters.")
    private String title;


    private List<QuestionDTO> questions;

    private Long parentChapterId;

    private List<ChapterRequestDTO> subChapters = new ArrayList<>();

    public void addSubChapter(ChapterRequestDTO subChapter) {
        if (this.subChapters == null) {
            this.subChapters = new ArrayList<>();
        }
        subChapter.setParentChapterId(this.id);
        this.subChapters.add(subChapter);
    }

    public boolean isRootChapter() {
        return parentChapterId == null;
    }

    public boolean hasSubChapters() {
        return subChapters != null && !subChapters.isEmpty();
    }
}
