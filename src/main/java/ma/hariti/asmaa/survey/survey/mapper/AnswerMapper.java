package ma.hariti.asmaa.survey.survey.mapper;


import ma.hariti.asmaa.survey.survey.dto.answer.AnswerDTO;
import ma.hariti.asmaa.survey.survey.entity.Answer;
import ma.hariti.asmaa.survey.survey.entity.Question;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AnswerMapper {
    AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", source = "questionId", qualifiedByName = "questionIdToQuestion")
    Answer toEntity(AnswerDTO answerDTO);

    @Mapping(source = "question.id", target = "questionId")
    AnswerDTO toDto(Answer answer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", source = "questionId", qualifiedByName = "questionIdToQuestion")
    void updateAnswerFromDTO(AnswerDTO answerDTO, @MappingTarget Answer answer);

    List<AnswerDTO> toDtoList(List<Answer> answers);

    List<Answer> toEntityList(List<AnswerDTO> answerDTOs);

    @Named("questionIdToQuestion")
    default Question questionIdToQuestion(Long questionId) {
        if (questionId == null) {
            return null;
        }
        Question question = new Question();
        question.setId(questionId);
        return question;
    }

    @Named("questionToQuestionId")
    default Long questionToQuestionId(Question question) {
        return question != null ? question.getId() : null;
    }
}
