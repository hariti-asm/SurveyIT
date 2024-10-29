package ma.hariti.asmaa.survey.survey.mapper;

import ma.hariti.asmaa.survey.survey.dto.chapter.ChapterDTO;
import ma.hariti.asmaa.survey.survey.entity.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {QuestionMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ChapterMapper {
    ChapterMapper INSTANCE = Mappers.getMapper(ChapterMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "survey", ignore = true)
    @Mapping(target = "title", source = "title")
    Chapter toEntity(ChapterDTO chapterDTO);

    @Mapping(source = "survey.id", target = "surveyId")
    @Mapping(source = "title", target = "title")
    ChapterDTO toDTO(Chapter chapter);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "survey", ignore = true)
    @Mapping(target = "title", source = "title")
    void updateChapterFromDTO(ChapterDTO chapterDTO, @MappingTarget Chapter chapter);

    List<ChapterDTO> toDTOList(List<Chapter> chapters);

    List<Chapter> toEntityList(List<ChapterDTO> chapterDTOs);
}