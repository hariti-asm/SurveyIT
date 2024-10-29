package ma.hariti.asmaa.survey.survey.mapper;


import ma.hariti.asmaa.survey.survey.dto.survey.SubjectDTO;
import ma.hariti.asmaa.survey.survey.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SubjectMapper {
    SubjectMapper INSTANCE = Mappers.getMapper(SubjectMapper.class);

    @Mapping(target = "id", ignore = true)
    Subject toEntity(SubjectDTO subjectDTO);

    SubjectDTO toDto(Subject subject);

    @Mapping(target = "id", ignore = true)
    void updateSubjectFromDTO(SubjectDTO subjectDTO, @MappingTarget Subject subject);

    List<SubjectDTO> toDtoList(List<Subject> subjects);

    List<Subject> toEntityList(List<SubjectDTO> subjectDTOs);
}