package ma.hariti.asmaa.survey.survey.dto.chapter;

import lombok.Data;
import ma.hariti.asmaa.survey.survey.dto.question.QuestionDTO;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterDTO {
    private Long id;
    private Long surveyEditionId;
    private String title;
    private String description;
    private List<QuestionDTO> questions;
    private Long parentChapterId;
    private List<ChapterDTO> subChapters = new ArrayList<>();

    public void addSubChapter(ChapterDTO subChapter) {
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