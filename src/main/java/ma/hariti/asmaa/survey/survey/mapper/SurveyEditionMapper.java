package ma.hariti.asmaa.survey.survey.mapper;

import ma.hariti.asmaa.survey.survey.dto.surveyEdition.SurveyEditionDTO;
import ma.hariti.asmaa.survey.survey.entity.SurveyEdition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ChapterMapper.class, SurveyMapper.class})
public interface SurveyEditionMapper {

    @Mapping(target = "survey", source = "survey")
    SurveyEditionDTO toDto(SurveyEdition surveyEdition);

    @Mapping(target = "survey", ignore = true)
    @Mapping(target = "chapters", ignore = true)
    SurveyEdition toEntity(SurveyEditionDTO dto);
}
