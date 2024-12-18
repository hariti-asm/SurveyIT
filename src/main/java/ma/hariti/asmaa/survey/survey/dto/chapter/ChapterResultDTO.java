package ma.hariti.asmaa.survey.survey.dto.chapter;

import lombok.Data;
import ma.hariti.asmaa.survey.survey.dto.question.QuestionDTO;

import java.util.ArrayList;
import java.util.List;
@Data
public class ChapterResultDTO {
    private Long id;
    private String title;
    private List<SubChapterResultDTO> subChapters = new ArrayList<>();
    private List<QuestionDTO> questions = new ArrayList<>();

}
