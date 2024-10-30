package ma.hariti.asmaa.survey.survey.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.survey.survey.dto.chapter.ChapterDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.SurveyDTO;
import ma.hariti.asmaa.survey.survey.entity.Chapter;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import ma.hariti.asmaa.survey.survey.entity.SurveyEdition;
import ma.hariti.asmaa.survey.survey.exception.ResourceNotFoundException;
import ma.hariti.asmaa.survey.survey.mapper.ChapterMapper;
import ma.hariti.asmaa.survey.survey.repository.ChapterRepository;
import ma.hariti.asmaa.survey.survey.repository.SurveyRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChapterService {
    private final SurveyRepository surveyRepository;
    private final ChapterRepository chapterRepository;
    private final ChapterMapper chapterMapper;

    public ChapterDTO addChapterToSurvey(Long surveyId, ChapterDTO chapterDTO) {
        // Find the survey and its latest edition
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found with id: " + surveyId));

        SurveyEdition latestEdition = survey.getSurveyEditions().stream()
                .max(Comparator.comparing(SurveyEdition::getStartDate))
                .orElseThrow(() -> new ResourceNotFoundException("No editions found for survey: " + surveyId));

        Chapter chapter = chapterMapper.toEntity(chapterDTO);
        chapter.setSurveyEdition(latestEdition);

        Chapter savedChapter = chapterRepository.save(chapter);
        return chapterMapper.toDto(savedChapter);
    }

    public List<ChapterDTO> getChaptersBySurveyId(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found with id: " + surveyId));

        SurveyEdition latestEdition = survey.getSurveyEditions().stream()
                .max(Comparator.comparing(SurveyEdition::getStartDate))
                .orElseThrow(() -> new ResourceNotFoundException("No editions found for survey: " + surveyId));

        return latestEdition.getChapters().stream()
                .map(chapterMapper::toDto)
                .collect(Collectors.toList());
    }
}