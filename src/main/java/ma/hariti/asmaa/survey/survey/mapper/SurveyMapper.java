package ma.hariti.asmaa.survey.survey.mapper;
import ma.hariti.asmaa.survey.survey.dto.survey.SurveyDTO;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ChapterMapper.class, OwnerMapper.class, SurveyEditionMapper.class})
public interface SurveyMapper {

    @Mapping(target = "ownerId", source = "owner.id")
    SurveyDTO toDto(Survey survey);

    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "surveyEditions", ignore = true)
    Survey toEntity(SurveyDTO surveyDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "surveyEditions", ignore = true)
    void updateEntityFromDto(SurveyDTO surveyDTO, @MappingTarget Survey survey);
}
