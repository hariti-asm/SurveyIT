package ma.hariti.asmaa.survey.survey.implementation;

import ma.hariti.asmaa.survey.survey.dto.chapter.ChapterResultDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.SurveyResultsDTO;
import ma.hariti.asmaa.survey.survey.entity.Chapter;

import java.util.List;

public interface ISurveyResultsService {
    SurveyResultsDTO getSurveyResults(Long surveyId);
    List<ChapterResultDTO> processChapters(List<Chapter> chapters);
}