package ma.hariti.asmaa.survey.survey.mapper;

import ma.hariti.asmaa.survey.survey.dto.chapter.ChapterRequestDTO;
import ma.hariti.asmaa.survey.survey.entity.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface ChapterMapper {

    @Mapping(target = "surveyEditionId", source = "surveyEdition.id")
    @Mapping(target = "parentChapterId", source = "parentChapter.id")
    ChapterRequestDTO toDto(Chapter chapter);

    @Mapping(target = "surveyEdition", ignore = true)
    @Mapping(target = "parentChapter", ignore = true)
    @Mapping(target = "subChapters", ignore = true)
    @Mapping(target = "questions", ignore = true)
    Chapter toEntity(ChapterRequestDTO chapterRequestDTO);

    List<ChapterRequestDTO> toDtoList(List<Chapter> chapters);
}
