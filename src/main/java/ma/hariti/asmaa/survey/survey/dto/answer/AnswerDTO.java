package ma.hariti.asmaa.survey.survey.dto.answer;


import lombok.Data;


@Data
public class AnswerDTO {
    private Long id;
    private String text;
    private Integer selectionCount;
    private Long questionId;
}