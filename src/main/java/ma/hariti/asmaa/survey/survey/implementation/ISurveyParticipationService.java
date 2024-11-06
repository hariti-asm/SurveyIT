package ma.hariti.asmaa.survey.survey.implementation;

import ma.hariti.asmaa.survey.survey.dto.participant.SurveyParticipationDTO;

public interface ISurveyParticipationService {
    void saveParticipationResponses(Long surveyId, SurveyParticipationDTO participationDTO);
}