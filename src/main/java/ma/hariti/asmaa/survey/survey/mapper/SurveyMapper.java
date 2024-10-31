package ma.hariti.asmaa.survey.survey.mapper;
import ma.hariti.asmaa.survey.survey.dto.survey.CreateSurveyRequestDTO;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ChapterMapper.class, OwnerMapper.class, SurveyEditionMapper.class})
public interface SurveyMapper {

    @Mapping(target = "ownerId", source = "owner.id")
    CreateSurveyRequestDTO toDto(Survey survey);

    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "surveyEditions", ignore = true)

    Survey toEntity(CreateSurveyRequestDTO createSurveyRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "surveyEditions", ignore = true)
    void updateEntityFromDto(CreateSurveyRequestDTO createSurveyRequestDTO, @MappingTarget Survey survey);
}
