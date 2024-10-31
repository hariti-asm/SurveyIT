package ma.hariti.asmaa.survey.survey.mapper;

import ma.hariti.asmaa.survey.survey.dto.surveyEdition.SurveyEditionDTO;
import ma.hariti.asmaa.survey.survey.dto.surveyEdition.SurveyEditionRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.surveyEdition.SurveyEditionResponseDTO;
import ma.hariti.asmaa.survey.survey.entity.SurveyEdition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ChapterMapper.class, SurveyMapper.class})
public interface SurveyEditionMapper {

    @Mapping(target = "surveyId", source = "survey.id")
    SurveyEditionResponseDTO toDto(SurveyEdition surveyEdition);

    @Mapping(target = "survey", ignore = true)
    SurveyEdition toEntity(SurveyEditionRequestDTO surveyEditionRequestDTO);

    @Mapping(target = "survey", ignore = true)
    SurveyEdition toEntity(SurveyEditionResponseDTO surveyEditionResponseDTO);
}
