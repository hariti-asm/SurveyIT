package ma.hariti.asmaa.survey.survey.dto.survey;

import ma.hariti.asmaa.survey.survey.dto.chapter.ChapterResultDTO;

import java.util.List;

public class SurveyResultsDTO {
    private String surveyTitle;
    private List<ChapterResultDTO> chapterResults;

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public List<ChapterResultDTO> getChapterResults() {
        return chapterResults;
    }

    public void setChapterResults(List<ChapterResultDTO> chapterResults) {
        this.chapterResults = chapterResults;
    }
}
