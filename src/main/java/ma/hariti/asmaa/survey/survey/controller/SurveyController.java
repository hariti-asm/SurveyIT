package ma.hariti.asmaa.survey.survey.controller;

import jakarta.validation.Valid;
import ma.hariti.asmaa.survey.survey.dto.api.ApiResponseDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.CreateSurveyRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.UpdateSurveyRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.UpdateSurveyResponseDTO;
import ma.hariti.asmaa.survey.survey.service.SurveyService;
import ma.hariti.asmaa.survey.survey.util.AbstractCrudController;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/surveys")
public class SurveyController extends AbstractCrudController<
        CreateSurveyRequestDTO,
        UpdateSurveyRequestDTO,
        UpdateSurveyResponseDTO
        > {
    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        super(surveyService);
        this.surveyService = surveyService;
    }

    @Override
    @PostMapping
    public ResponseEntity<CreateSurveyRequestDTO> create(
            @RequestBody CreateSurveyRequestDTO createSurveyRequestDTO
    ) {
        return super.create(createSurveyRequestDTO);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<UpdateSurveyResponseDTO> update(
            @Valid @PathVariable Long id,
            @RequestBody UpdateSurveyRequestDTO updateSurveyRequestDTO
    ) {
        return super.update(id, updateSurveyRequestDTO);
    }

    @Override
    @GetMapping
    public ResponseEntity<ApiResponseDTO<Page<CreateSurveyRequestDTO>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        return super.getAll(page, size, sortBy, sortDirection);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CreateSurveyRequestDTO> getById(@PathVariable Long id) {
        return super.getById(id);
    }


}