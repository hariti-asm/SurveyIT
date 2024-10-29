package ma.hariti.asmaa.survey.survey.mapper;
import ma.hariti.asmaa.survey.survey.dto.chapter.ChapterDTO;
import ma.hariti.asmaa.survey.survey.dto.chapter.ChapterResultDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.CreateSurveyRequest;
import ma.hariti.asmaa.survey.survey.dto.survey.SurveyDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.SurveyResultsDTO;
import ma.hariti.asmaa.survey.survey.entity.Chapter;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SurveyMapper {
    SurveyMapper INSTANCE = Mappers.getMapper(SurveyMapper.class);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chapters", ignore = true)
    Survey toEntity(CreateSurveyRequest request);

    @Mapping(source = "owner.id", target = "ownerId")
    SurveyDTO toDTO(Survey survey);

    @Mapping(target = "chapterResults", source = "chapters", qualifiedByName = "mapChapterResult")
    SurveyResultsDTO toResultsDTO(Survey survey);

    @IterableMapping(elementTargetType = ChapterDTO.class, qualifiedByName = "mapChapter")
    List<ChapterDTO> mapChapters(List<Chapter> chapters);

    @Named("mapChapter")
    ChapterDTO mapChapter(Chapter chapter);

    @Named("mapChapterResult")
    ChapterResultDTO map(Chapter chapter);


    void updateSurveyFromDTO(SurveyDTO surveyDTO, @MappingTarget Survey survey);
}
