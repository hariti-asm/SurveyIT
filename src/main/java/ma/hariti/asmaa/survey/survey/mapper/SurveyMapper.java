package ma.hariti.asmaa.survey.survey.mapper;
import ma.hariti.asmaa.survey.survey.dto.survey.CreateSurveyRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.UpdateSurveyRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.UpdateSurveyResponseDTO;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ChapterMapper.class, OwnerMapper.class, SurveyEditionMapper.class})
public interface SurveyMapper {

    @Mapping(target = "ownerId", source = "owner.id")
    CreateSurveyRequestDTO toDto(Survey survey);

    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "surveyEditions", ignore = true)

    Survey toEntity(CreateSurveyRequestDTO createSurveyRequestDTO);

    @Mapping(target = "owner", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CreateSurveyRequestDTO createSurveyRequestDTO, @MappingTarget Survey survey);

    @Mapping(target = "owner", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromUpdateDto(UpdateSurveyRequestDTO updateSurveyRequestDTO, @MappingTarget Survey survey);

    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "surveyEditions", source = "surveyEditions")
    UpdateSurveyResponseDTO toUpdateResponseDto(Survey survey);
}
