package ma.hariti.asmaa.survey.survey.dto.question;

import jakarta.validation.Valid;
import lombok.Data;
import ma.hariti.asmaa.survey.survey.dto.answer.AnswerDTO;
import ma.hariti.asmaa.survey.survey.dto.answer.AnswerResponseDTO;
import ma.hariti.asmaa.survey.survey.entity.Question;
import ma.hariti.asmaa.survey.survey.enums.QuestionType;
import ma.hariti.asmaa.survey.survey.validation.annotation.Exists;

import java.util.List;
@Data
public class QuestionResponseDTO {
    private Long id;
    private String answerId;
    @Valid
    private List<AnswerResponseDTO> answers;
}