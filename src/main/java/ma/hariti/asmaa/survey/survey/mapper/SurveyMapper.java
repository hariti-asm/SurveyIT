package ma.hariti.asmaa.survey.survey.mapper;

import ma.hariti.asmaa.survey.survey.dto.survey.CreateSurveyRequest;
import ma.hariti.asmaa.survey.survey.dto.survey.SurveyDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.SurveyResultsDTO;
import ma.hariti.asmaa.survey.survey.entity.Answer;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")

public interface SurveyMapper {

    @Mapping(target = "id", ignore = true)
    Survey toEntity(CreateSurveyRequest request);

    SurveyDTO toDTO(Survey survey);

    @Mapping(source = "answers", target = "results")
    SurveyResultsDTO toResultsDTO(Survey survey);

    List<Answer> toAnswerEntities(List<SurveyResponseDTO> responses);

}
