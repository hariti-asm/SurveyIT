package ma.hariti.asmaa.survey.survey.service;

import jakarta.transaction.Transactional;
import ma.hariti.asmaa.survey.survey.dto.survey.SurveyDTO;
import ma.hariti.asmaa.survey.survey.entity.Chapter;
import ma.hariti.asmaa.survey.survey.entity.Owner;
import ma.hariti.asmaa.survey.survey.entity.Survey;
import ma.hariti.asmaa.survey.survey.exception.DuplicateTitleException;
import ma.hariti.asmaa.survey.survey.exception.ResourceNotFoundException;
import ma.hariti.asmaa.survey.survey.repository.OwnerRepository;
import ma.hariti.asmaa.survey.survey.repository.SurveyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Optional<Survey> existingSurvey = surveyRepository.findByTitle(surveyDTO.getTitle());
        if (existingSurvey.isPresent()) {
            throw new DuplicateTitleException("A survey with this title already exists.");
        }

        Survey survey = new Survey();
        survey.setTitle(surveyDTO.getTitle());
        survey.setDescription(surveyDTO.getDescription());

        Owner owner = ownerRepository.findById(surveyDTO.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + surveyDTO.getOwnerId()));
        survey.setOwner(owner);

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
