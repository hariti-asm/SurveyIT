package ma.hariti.asmaa.survey.survey.mapper;

import ma.hariti.asmaa.survey.survey.dto.chapter.ChapterDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.SurveyDTO;
import ma.hariti.asmaa.survey.survey.entity.Chapter;
import ma.hariti.asmaa.survey.survey.entity.Survey;
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
    @Mapping(target = "owner", ignore = true)
    Survey toEntity(SurveyDTO dto);

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "chapters", target = "chapters")
    SurveyDTO toDto(Survey survey);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    void updateEntityFromDto(SurveyDTO dto, @MappingTarget Survey entity);

    @Named("mapChapters")
    List<ChapterDTO> mapChapters(List<Chapter> chapters);

    @Named("mapChapter")
    ChapterDTO mapChapter(Chapter chapter);
}