package ma.hariti.asmaa.survey.survey.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.survey.survey.dto.answer.AnswerDTO;
import ma.hariti.asmaa.survey.survey.dto.chapter.ChapterResultDTO;
import ma.hariti.asmaa.survey.survey.dto.chapter.SubChapterResultDTO;
import ma.hariti.asmaa.survey.survey.dto.question.QuestionDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.SurveyResultsDTO;
import ma.hariti.asmaa.survey.survey.entity.Answer;
import ma.hariti.asmaa.survey.survey.entity.Chapter;
import ma.hariti.asmaa.survey.survey.entity.Question;
import ma.hariti.asmaa.survey.survey.entity.SurveyEdition;
import ma.hariti.asmaa.survey.survey.implementation.ISurveyResultsService;
import ma.hariti.asmaa.survey.survey.repository.SurveyEditionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyResultsService implements ISurveyResultsService {
    private final SurveyEditionRepository surveyEditionRepository;

    public SurveyResultsDTO getSurveyResults(Long surveyEditionId) {
        SurveyEdition surveyEdition = surveyEditionRepository.findById(surveyEditionId)
                .orElseThrow(() -> new EntityNotFoundException("Survey Edition not found with id: " + surveyEditionId));

        SurveyResultsDTO resultsDTO = new SurveyResultsDTO();
        resultsDTO.setSurveyTitle(surveyEdition.getSurvey().getTitle());

        List<ChapterResultDTO> chapterResults = processChapters(surveyEdition.getChapters());
        resultsDTO.setChapters(chapterResults);

        return resultsDTO;
    }

    public List<ChapterResultDTO> processChapters(List<Chapter> chapters) {
        return chapters.stream()
                .map(this::processChapter)
                .collect(Collectors.toList());
    }

    private ChapterResultDTO processChapter(Chapter chapter) {
        ChapterResultDTO chapterDTO = new ChapterResultDTO();
        chapterDTO.setId(chapter.getId());
        chapterDTO.setTitle(chapter.getTitle());

        // Process chapter's questions
        List<QuestionDTO> questions = chapter.getQuestions().stream()
                .map(question -> processQuestion(question, chapter.getId(), null))
                .collect(Collectors.toList());
        chapterDTO.setQuestions(questions != null ? questions : new ArrayList<>());

        // Process subchapters
        List<SubChapterResultDTO> subChapters = chapter.getSubChapters().stream()
                .map(this::createSubChapterDTO)
                .collect(Collectors.toList());
        chapterDTO.setSubChapters(subChapters);

        return chapterDTO;
    }

    private SubChapterResultDTO createSubChapterDTO(Chapter subChapter) {
        SubChapterResultDTO dto = new SubChapterResultDTO();
        dto.setId(subChapter.getId());
        dto.setTitle(subChapter.getTitle());

        // Process subchapter's questions
        List<QuestionDTO> questions = subChapter.getQuestions().stream()
                .map(question -> processQuestion(question, subChapter.getParentChapter().getId(), subChapter.getId()))
                .collect(Collectors.toList());
        dto.setQuestions(questions != null ? questions : new ArrayList<>());

        return dto;
    }

    private QuestionDTO processQuestion(Question question, Long chapterId, Long subChapterId) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setText(question.getText());
        dto.setType(question.getType());
        dto.setRequired(question.getRequired());
        dto.setChapterId(chapterId);
        dto.setSubChapterId(subChapterId);
        dto.setAnswerCount(question.getAnswerCount());

        List<AnswerDTO> answers = question.getAnswers().stream()
                .filter(answer -> answer.getSelectionCount() != null && answer.getSelectionCount() > 0)
                .map(this::convertToAnswerDTO)
                .collect(Collectors.toList());
        dto.setAnswers(answers);

        return dto;
    }

    private AnswerDTO convertToAnswerDTO(Answer answer) {
        AnswerDTO dto = new AnswerDTO();
        dto.setId(answer.getId());
        dto.setText(answer.getText());
        dto.setSelectionCount(answer.getSelectionCount());
        return dto;
    }
}