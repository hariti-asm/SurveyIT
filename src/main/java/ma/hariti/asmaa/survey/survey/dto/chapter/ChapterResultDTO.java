package ma.hariti.asmaa.survey.survey.dto.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ChapterResultDTO {
    private String title;
    private List<SubChapterResultDTO> subChapters = new ArrayList<>();
}
