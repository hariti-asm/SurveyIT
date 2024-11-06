package ma.hariti.asmaa.survey.survey.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.survey.survey.dto.chapter.ChapterResultDTO;
import ma.hariti.asmaa.survey.survey.dto.chapter.SubChapterResultDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.SurveyResultsDTO;
import ma.hariti.asmaa.survey.survey.entity.Answer;
import ma.hariti.asmaa.survey.survey.entity.Chapter;
import ma.hariti.asmaa.survey.survey.entity.Question;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import ma.hariti.asmaa.survey.survey.entity.SurveyEdition;
import ma.hariti.asmaa.survey.survey.implementation.ISurveyResultsService;
import ma.hariti.asmaa.survey.survey.repository.SurveyRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyResultsService implements ISurveyResultsService {
    private final SurveyRepository surveyRepository;

    public SurveyResultsDTO getSurveyResults(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found with id: " + surveyId));

        List<SurveyEdition> surveyEditions = survey.getSurveyEditions();
        SurveyEdition latestSurveyEdition = surveyEditions.stream()
                .max(Comparator.comparing(SurveyEdition::getCreationDate))
                .orElseThrow(() -> new EntityNotFoundException("No survey editions found for survey with id: " + surveyId));

        SurveyResultsDTO resultsDTO = new SurveyResultsDTO();
        resultsDTO.setSurveyTitle(survey.getTitle());

        List<ChapterResultDTO> chapterResults = processChapters(latestSurveyEdition.getChapters());
        resultsDTO.setChapters(chapterResults);

        return resultsDTO;
    }

    public List<ChapterResultDTO> processChapters(List<Chapter> chapters) {
        return chapters.stream()
                .filter(chapter -> chapter.getParentChapter() == null)
                .map(this::processChapter)
                .collect(Collectors.toList());
    }

    private ChapterResultDTO processChapter(Chapter chapter) {
        ChapterResultDTO chapterDTO = new ChapterResultDTO();
        chapterDTO.setTitle(chapter.getTitle());

        List<SubChapterResultDTO> subChapters = chapter.getSubChapters().stream()
                .map(this::createSubChapterDTO)
                .collect(Collectors.toList());

        chapterDTO.setSubChapters(subChapters);
        return chapterDTO;
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

            dto.setAnswers(validAnswers);
            dto.setTotalAnswers(validAnswers.values().stream().mapToInt(Integer::intValue).sum());
        }

        return dto;
    }
}