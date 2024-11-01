package ma.hariti.asmaa.survey.survey.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.survey.survey.dto.chapter.ChapterRequestDTO;
import ma.hariti.asmaa.survey.survey.entity.Chapter;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import ma.hariti.asmaa.survey.survey.mapper.ChapterMapper;
import ma.hariti.asmaa.survey.survey.repository.ChapterRepository;
import ma.hariti.asmaa.survey.survey.repository.SurveyRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@Transactional
@RequiredArgsConstructor
public class ChapterService {
    private final SurveyRepository surveyRepository;
    private final ChapterRepository chapterRepository;
    private final ChapterMapper chapterMapper;

    public ChapterRequestDTO addChapterToSurvey(Long surveyId, ChapterRequestDTO chapterRequestDTO) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found with id: " + surveyId));

        Chapter chapter = chapterMapper.toEntity(chapterRequestDTO);
        chapter.setSurvey(survey);

        // Handle parent chapter if specified
        if (chapterRequestDTO.getParentChapterId() != null) {
            Chapter parentChapter = survey.getChapters().stream()
                    .filter(ch -> ch.getId().equals(chapterRequestDTO.getParentChapterId()))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("Parent chapter not found with id: " +
                            chapterRequestDTO.getParentChapterId()));
            chapter.setParentChapter(parentChapter);
        }

        Chapter savedChapter = chapterRepository.save(chapter);
        survey.getChapters().add(savedChapter);
        surveyRepository.save(survey);

        return chapterMapper.toDto(savedChapter);
    }

    public List<ChapterRequestDTO> getChaptersBySurveyId(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found with id: " + surveyId));

        // Get only top-level chapters (no parent)
        return survey.getChapters().stream()
                .filter(chapter -> chapter.getParentChapter() == null)
                .map(chapterMapper::toDto)
                .collect(Collectors.toList());
    }

    public ChapterRequestDTO getChapterById(Long surveyId, Long chapterId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found with id: " + surveyId));

        Chapter chapter = survey.getChapters().stream()
                .filter(ch -> ch.getId().equals(chapterId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Chapter not found with id: " + chapterId));

        return chapterMapper.toDto(chapter);
    }

    public ChapterRequestDTO updateChapter(Long surveyId, Long chapterId, ChapterRequestDTO chapterRequestDTO) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found with id: " + surveyId));

        Chapter existingChapter = survey.getChapters().stream()
                .filter(ch -> ch.getId().equals(chapterId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Chapter not found with id: " + chapterId));

        Chapter updatedChapter = chapterMapper.toEntity(chapterRequestDTO);
        updatedChapter.setId(existingChapter.getId());
        updatedChapter.setSurvey(survey);

        if (chapterRequestDTO.getParentChapterId() != null) {
            Chapter parentChapter = survey.getChapters().stream()
                    .filter(ch -> ch.getId().equals(chapterRequestDTO.getParentChapterId()))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("Parent chapter not found with id: " +
                            chapterRequestDTO.getParentChapterId()));
            updatedChapter.setParentChapter(parentChapter);
        }

        Chapter savedChapter = chapterRepository.save(updatedChapter);
        return chapterMapper.toDto(savedChapter);
    }

    public void deleteChapter(Long surveyId, Long chapterId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found with id: " + surveyId));

        Chapter chapter = survey.getChapters().stream()
                .filter(ch -> ch.getId().equals(chapterId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Chapter not found with id: " + chapterId));

        if (chapter.getParentChapter() != null) {
            chapter.getParentChapter().getSubChapters().remove(chapter);
        }

        chapter.getSubChapters().clear();

        survey.getChapters().remove(chapter);
        chapterRepository.delete(chapter);
    }

    public ChapterRequestDTO addSubChapter(Long parentChapterId, ChapterRequestDTO subChapterDTO) {
        // Find the parent chapter
        Chapter parentChapter = chapterRepository.findById(parentChapterId)
                .orElseThrow(() -> new EntityNotFoundException("Parent chapter not found with id: " + parentChapterId));

        // Create the sub-chapter
        Chapter subChapter = chapterMapper.toEntity(subChapterDTO);
        subChapter.setParentChapter(parentChapter);
        subChapter.setSurvey(parentChapter.getSurvey()); // Inherit the survey from parent

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