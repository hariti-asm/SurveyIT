package ma.hariti.asmaa.survey.survey.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.survey.survey.dto.chapter.ChapterResultDTO;
import ma.hariti.asmaa.survey.survey.dto.chapter.SubChapterResultDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.SurveyResultsDTO;
import ma.hariti.asmaa.survey.survey.entity.Chapter;
import ma.hariti.asmaa.survey.survey.entity.Question;
import ma.hariti.asmaa.survey.survey.entity.Answer;

import ma.hariti.asmaa.survey.survey.entity.Survey;
import ma.hariti.asmaa.survey.survey.repository.SurveyRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class SurveyResultsService {
    private final SurveyRepository surveyRepository;

    public SurveyResultsDTO getSurveyResults(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found with id: " + surveyId));

        SurveyResultsDTO resultsDTO = new SurveyResultsDTO();
        resultsDTO.setSurveyTitle(survey.getTitle());
        resultsDTO.setChapters(processChapters(survey.getChapters()));
        return resultsDTO;
    }

    private List<ChapterResultDTO> processChapters(List<Chapter> chapters) {
        return chapters.stream()
                .filter(chapter -> chapter.getParentChapter() == null)
                .map(this::processChapter)
                .filter(chapter -> !chapter.getSubChapters().isEmpty())
                .collect(Collectors.toList());
    }

    private ChapterResultDTO processChapter(Chapter chapter) {
        ChapterResultDTO chapterDTO = new ChapterResultDTO();
        chapterDTO.setTitle(chapter.getTitle());

        List<SubChapterResultDTO> validSubChapters = chapter.getSubChapters().stream()
                .map(this::createSubChapterDTO)
                .filter(this::isValidSubChapter)
                .collect(Collectors.toList());

        chapterDTO.setSubChapters(validSubChapters);
        return chapterDTO;
    }

    private boolean isValidSubChapter(SubChapterResultDTO subChapter) {
        return subChapter.getQuestion() != null
                && subChapter.getAnswers() != null
                && !subChapter.getAnswers().isEmpty();
    }

    private SubChapterResultDTO createSubChapterDTO(Chapter subChapter) {
        SubChapterResultDTO dto = new SubChapterResultDTO();
        dto.setTitle(subChapter.getTitle());

        if (!subChapter.getQuestions().isEmpty()) {
            Question question = subChapter.getQuestions().get(0);
            dto.setQuestion(question.getText());

            Map<String, Integer> validAnswers = question.getAnswers().stream()
                    .filter(answer -> answer.getSelectionCount() != null && answer.getSelectionCount() > 0)
                    .collect(Collectors.toMap(
                            Answer::getText,
                            Answer::getSelectionCount
                    ));

            if (!validAnswers.isEmpty()) {
                dto.setAnswers(validAnswers);
                dto.setTotalAnswers(validAnswers.values().stream()
                        .mapToInt(Integer::intValue)
                        .sum());
            }
        }

        return dto;
    }
}