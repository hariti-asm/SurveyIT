package ma.hariti.asmaa.survey.survey.mapper;

import ma.hariti.asmaa.survey.survey.dto.question.QuestionDTO;
import ma.hariti.asmaa.survey.survey.entity.Answer;
import ma.hariti.asmaa.survey.survey.entity.Question;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "answers", target = "answerIds")
    QuestionDTO toDTO(Question question);

    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "answers", ignore = true)
    Question toEntity(QuestionDTO questionDTO);

    List<QuestionDTO> toDTO(List<Question> questions);

    List<Question> toEntity(List<QuestionDTO> questionDTOs);

    @Named("answersToAnswerIds")
    default List<Long> answersToAnswerIds(List<Answer> answers) {
        if (answers == null) {
            return null;
        }
        return answers.stream()
                .map(Answer::getId)
                .collect(Collectors.toList());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateQuestionFromDTO(QuestionDTO questionDTO, @MappingTarget Question question);
}