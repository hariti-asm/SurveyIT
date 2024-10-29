package ma.hariti.asmaa.survey.survey.mapper;

import ma.hariti.asmaa.survey.survey.dto.answer.AnswerDTO;
import ma.hariti.asmaa.survey.survey.entity.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);

    @Mapping(source = "question.id", target = "questionId")
    AnswerDTO toDto(Answer answer);

    @Mapping(target = "question.id", source = "questionId")
    Answer toEntity(AnswerDTO answerDTO);

    default Answer updateAnswerFromDTO(AnswerDTO answerDTO, Answer answer) {
        if (answerDTO == null) {
            return answer;
        }

        if (answerDTO.getText() != null) {
            answer.setText(answerDTO.getText());
        }
        if (answerDTO.getSelectionCount() != null) {
            answer.setSelectionCount(answerDTO.getSelectionCount());
        }

        return answer;
    }
}