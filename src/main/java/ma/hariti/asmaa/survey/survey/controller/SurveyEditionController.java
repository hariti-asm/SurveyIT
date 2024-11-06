package ma.hariti.asmaa.survey.survey.controller;

import ma.hariti.asmaa.survey.survey.dto.api.ApiResponseDTO;
import ma.hariti.asmaa.survey.survey.dto.surveyEdition.CreateSurveyEditionRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.surveyEdition.SurveyEditionResponseDTO;
import ma.hariti.asmaa.survey.survey.dto.surveyEdition.UpdateSurveyEditionRequestDTO;
import ma.hariti.asmaa.survey.survey.service.SurveyEditionService;
import ma.hariti.asmaa.survey.survey.util.AbstractCrudController;
import ma.hariti.asmaa.survey.survey.util.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/survey-editions")
public class SurveyEditionController extends AbstractCrudController<
        CreateSurveyEditionRequestDTO,
        UpdateSurveyEditionRequestDTO,
        SurveyEditionResponseDTO> {

    private final SurveyEditionService surveyEditionService;

    public SurveyEditionController(GenericService<?, Long, CreateSurveyEditionRequestDTO, UpdateSurveyEditionRequestDTO, SurveyEditionResponseDTO> service, SurveyEditionService surveyEditionService) {
        super(service);
        this.surveyEditionService = surveyEditionService;
    }

    @Override
    @PostMapping
    public ResponseEntity<CreateSurveyEditionRequestDTO> create(@RequestBody CreateSurveyEditionRequestDTO createDTO) {
        CreateSurveyEditionRequestDTO createdResponse = surveyEditionService.create(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResponse);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<SurveyEditionResponseDTO> update(
            @PathVariable Long id,
            @RequestBody UpdateSurveyEditionRequestDTO updateDTO) {
        SurveyEditionResponseDTO updatedResponse = surveyEditionService.update(id, updateDTO);
        return ResponseEntity.ok(updatedResponse);
    }

    @Override
    @GetMapping
    public ResponseEntity<ApiResponseDTO<Page<CreateSurveyEditionRequestDTO>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        Page<CreateSurveyEditionRequestDTO> resultPage = surveyEditionService.getAll(createPageable(page, size, sortBy, sortDirection));
        return ResponseEntity.ok(ApiResponseDTO.success(resultPage, (int) resultPage.getTotalElements()));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CreateSurveyEditionRequestDTO> getById(@PathVariable Long id) {
        CreateSurveyEditionRequestDTO surveyEditionResponse = surveyEditionService.getById(id);
        return ResponseEntity.ok(surveyEditionResponse);
    }
}
