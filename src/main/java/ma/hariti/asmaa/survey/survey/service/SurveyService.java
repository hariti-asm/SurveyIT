package ma.hariti.asmaa.survey.survey.service;

import jakarta.transaction.Transactional;
import ma.hariti.asmaa.survey.survey.dto.survey.SurveyDTO;
import ma.hariti.asmaa.survey.survey.entity.Chapter;
import ma.hariti.asmaa.survey.survey.entity.Owner;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import ma.hariti.asmaa.survey.survey.exception.ResourceNotFoundException;
import ma.hariti.asmaa.survey.survey.repository.OwnerRepository;
import ma.hariti.asmaa.survey.survey.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@Transactional
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final OwnerRepository ownerRepository;

    public SurveyService(SurveyRepository surveyRepository, OwnerRepository ownerRepository) {
        this.surveyRepository = surveyRepository;
        this.ownerRepository = ownerRepository;
    }

    public Survey createSurvey(SurveyDTO surveyDTO) {
        Survey survey = new Survey();
        survey.setTitle(surveyDTO.getTitle());
        survey.setDescription(surveyDTO.getDescription());

        // Find and set the owner
        Owner owner = ownerRepository.findById(surveyDTO.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + surveyDTO.getOwnerId()));
        survey.setOwner(owner);

        // Handle chapters if needed
        if (surveyDTO.getChapters() != null) {
            surveyDTO.getChapters().forEach(chapterDTO -> {
                Chapter chapter = new Chapter();
                chapter.setTitle(chapterDTO.getTitle());
                chapter.setSurvey(survey);
                survey.getChapters().add(chapter);
            });
        }

        return surveyRepository.save(survey);
    }
}