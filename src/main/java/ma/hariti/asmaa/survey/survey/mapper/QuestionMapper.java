package ma.hariti.asmaa.survey.survey.mapper;


import ma.hariti.asmaa.survey.survey.dto.question.QuestionDTO;
import ma.hariti.asmaa.survey.survey.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {AnswerMapper.class, ChapterMapper.class, SubjectMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface QuestionMapper {
    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chapter", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "answers", ignore = true)
    Question toEntity(QuestionDTO questionDTO);

    @Mapping(source = "chapter.id", target = "chapterId")
    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "answers", target = "answers")
    QuestionDTO toDTO(Question question);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chapter", ignore = true)
    @Mapping(target = "subject", ignore = true)
    void updateQuestionFromDTO(QuestionDTO questionDTO, @MappingTarget Question question);

    List<QuestionDTO> toDTOList(List<Question> questions);

    List<Question> toEntityList(List<QuestionDTO> questionDTOs);
}