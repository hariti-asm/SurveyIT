package ma.hariti.asmaa.survey.survey.mapper;

import ma.hariti.asmaa.survey.survey.dto.surveyEdition.CreateSurveyEditionRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.surveyEdition.SurveyEditionResponseDTO;
import ma.hariti.asmaa.survey.survey.dto.surveyEdition.UpdateSurveyEditionRequestDTO;
import ma.hariti.asmaa.survey.survey.entity.SurveyEdition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {ChapterMapper.class, SurveyMapper.class})
public interface SurveyEditionMapper {

    @Mapping(target = "surveyId", source = "survey.id")
    SurveyEditionResponseDTO toDto(SurveyEdition surveyEdition);

    @Mapping(target = "survey.id", source = "surveyId")
    SurveyEdition toEntity(CreateSurveyEditionRequestDTO createSurveyEditionRequestDTO);

    CreateSurveyEditionRequestDTO toCreateDto(SurveyEdition surveyEdition);

    @Mapping(target = "survey.id", source = "surveyId")
    void updateEntityFromUpdateDto(UpdateSurveyEditionRequestDTO updateDto, @MappingTarget SurveyEdition entity);

}
