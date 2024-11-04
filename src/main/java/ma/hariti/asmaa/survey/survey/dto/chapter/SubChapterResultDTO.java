package ma.hariti.asmaa.survey.survey.dto.chapter;

import lombok.Data;

import java.util.Map;
@Data
public class SubChapterResultDTO {
    private String title;
    private String question;
    private Map<String, Integer> answers;
    private int totalAnswers;
}
