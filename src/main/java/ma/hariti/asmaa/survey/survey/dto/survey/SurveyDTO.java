package ma.hariti.asmaa.survey.survey.dto.survey;

import lombok.Data;
import ma.hariti.asmaa.survey.survey.dto.chapter.ChapterDTO;

import java.util.List;

@Data
public class SurveyDTO {
    private Long id;
    private String title;
    private String description;
    private List<ChapterDTO> chapters;
    private Long ownerId; }
