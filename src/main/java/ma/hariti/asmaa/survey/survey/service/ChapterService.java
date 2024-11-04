package ma.hariti.asmaa.survey.survey.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.survey.survey.dto.chapter.ChapterRequestDTO;
import ma.hariti.asmaa.survey.survey.entity.Chapter;
import ma.hariti.asmaa.survey.survey.entity.SurveyEdition;
import ma.hariti.asmaa.survey.survey.mapper.ChapterMapper;
import ma.hariti.asmaa.survey.survey.repository.ChapterRepository;
import ma.hariti.asmaa.survey.survey.repository.SurveyEditionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChapterService {
    private final SurveyEditionRepository surveyEditionRepository;
    private final ChapterRepository chapterRepository;
    private final ChapterMapper chapterMapper;

    public ChapterRequestDTO addChapterToSurveyEdition(Long surveyEditionId, ChapterRequestDTO chapterRequestDTO) {
        SurveyEdition surveyEdition = surveyEditionRepository.findById(surveyEditionId)
                .orElseThrow(() -> new EntityNotFoundException("SurveyEdition not found with id: " + surveyEditionId));

        Chapter chapter = chapterMapper.toEntity(chapterRequestDTO);
        chapter.setSurveyEdition(surveyEdition);

        // Handle parent chapter if specified
        if (chapterRequestDTO.getParentChapterId() != null) {
            Chapter parentChapter = chapterRepository.findById(chapterRequestDTO.getParentChapterId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent chapter not found with id: " +
                            chapterRequestDTO.getParentChapterId()));
            chapter.setParentChapter(parentChapter);
            parentChapter.getSubChapters().add(chapter);
            chapterRepository.save(parentChapter); // Save changes to parent
        }

        Chapter savedChapter = chapterRepository.save(chapter);
        return chapterMapper.toDto(savedChapter);
    }

    public List<ChapterRequestDTO> getChaptersBySurveyEditionId(Long surveyEditionId) {
        SurveyEdition surveyEdition = surveyEditionRepository.findById(surveyEditionId)
                .orElseThrow(() -> new EntityNotFoundException("SurveyEdition not found with id: " + surveyEditionId));

        // Get only top-level chapters (no parent)
        return surveyEdition.getChapters().stream()
                .filter(chapter -> chapter.getParentChapter() == null)
                .map(chapterMapper::toDto)
                .collect(Collectors.toList());
    }

    public ChapterRequestDTO getChapterById(Long surveyEditionId, Long chapterId) {
        SurveyEdition surveyEdition = surveyEditionRepository.findById(surveyEditionId)
                .orElseThrow(() -> new EntityNotFoundException("SurveyEdition not found with id: " + surveyEditionId));

        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new EntityNotFoundException("Chapter not found with id: " + chapterId));

        // Ensure the chapter belongs to the correct survey edition
        if (!chapter.getSurveyEdition().equals(surveyEdition)) {
            throw new EntityNotFoundException("Chapter does not belong to the specified SurveyEdition");
        }

        return chapterMapper.toDto(chapter);
    }

    public ChapterRequestDTO updateChapter(Long surveyEditionId, Long chapterId, ChapterRequestDTO chapterRequestDTO) {
        SurveyEdition surveyEdition = surveyEditionRepository.findById(surveyEditionId)
                .orElseThrow(() -> new EntityNotFoundException("SurveyEdition not found with id: " + surveyEditionId));

        Chapter existingChapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new EntityNotFoundException("Chapter not found with id: " + chapterId));

        // Ensure the chapter belongs to the correct survey edition
        if (!existingChapter.getSurveyEdition().equals(surveyEdition)) {
            throw new EntityNotFoundException("Chapter does not belong to the specified SurveyEdition");
        }

        Chapter updatedChapter = chapterMapper.toEntity(chapterRequestDTO);
        updatedChapter.setId(existingChapter.getId());
        updatedChapter.setSurveyEdition(surveyEdition);

        if (chapterRequestDTO.getParentChapterId() != null) {
            Chapter parentChapter = chapterRepository.findById(chapterRequestDTO.getParentChapterId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent chapter not found with id: " +
                            chapterRequestDTO.getParentChapterId()));
            updatedChapter.setParentChapter(parentChapter);
        }

        Chapter savedChapter = chapterRepository.save(updatedChapter);
        return chapterMapper.toDto(savedChapter);
    }

    public void deleteChapter(Long surveyEditionId, Long chapterId) {
        SurveyEdition surveyEdition = surveyEditionRepository.findById(surveyEditionId)
                .orElseThrow(() -> new EntityNotFoundException("SurveyEdition not found with id: " + surveyEditionId));

        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new EntityNotFoundException("Chapter not found with id: " + chapterId));

        // Ensure the chapter belongs to the correct survey edition
        if (!chapter.getSurveyEdition().equals(surveyEdition)) {
            throw new EntityNotFoundException("Chapter does not belong to the specified SurveyEdition");
        }

        if (chapter.getParentChapter() != null) {
            chapter.getParentChapter().getSubChapters().remove(chapter);
        }

        chapter.getSubChapters().clear();

        chapterRepository.delete(chapter);
    }

    public ChapterRequestDTO addSubChapter(Long parentChapterId, ChapterRequestDTO subChapterDTO) {
        // Find the parent chapter
        Chapter parentChapter = chapterRepository.findById(parentChapterId)
                .orElseThrow(() -> new EntityNotFoundException("Parent chapter not found with id: " + parentChapterId));

        // Create the sub-chapter
        Chapter subChapter = chapterMapper.toEntity(subChapterDTO);
        subChapter.setParentChapter(parentChapter);
        subChapter.setSurveyEdition(parentChapter.getSurveyEdition()); // Inherit the survey edition from parent

        Chapter savedSubChapter = chapterRepository.save(subChapter);

        // Add to parent's subchapters list
        parentChapter.getSubChapters().add(savedSubChapter);
        chapterRepository.save(parentChapter);

        return chapterMapper.toDto(savedSubChapter);
    }

    public Optional<Chapter> findById(Long chapterId) {
        return chapterRepository.findById(chapterId);
    }
}
