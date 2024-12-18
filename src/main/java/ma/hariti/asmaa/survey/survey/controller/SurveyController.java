package ma.hariti.asmaa.survey.survey.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import ma.hariti.asmaa.survey.survey.dto.api.ApiResponseDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.CreateSurveyRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.SurveyEditionWithQuestionCountDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.UpdateSurveyRequestDTO;
import ma.hariti.asmaa.survey.survey.dto.survey.UpdateSurveyResponseDTO;
import ma.hariti.asmaa.survey.survey.entity.SurveyEdition;
import ma.hariti.asmaa.survey.survey.mapper.SurveyEditionMapper;
import ma.hariti.asmaa.survey.survey.service.SurveyService;
import ma.hariti.asmaa.survey.survey.util.AbstractCrudController;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/surveys")
@Tag(name = "Surveys", description = "Manage surveys")
public class SurveyController extends AbstractCrudController<CreateSurveyRequestDTO, UpdateSurveyRequestDTO, UpdateSurveyResponseDTO> {
    private final SurveyService surveyService;
    private final SurveyEditionMapper surveyEditionMapper;

    public SurveyController(SurveyService surveyService, SurveyEditionMapper surveyEditionMapper) {
        super(surveyService);
        this.surveyService = surveyService;
        this.surveyEditionMapper = surveyEditionMapper;
    }

    @Override
    @PostMapping
    @Operation(summary = "Create a new survey")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateSurveyRequestDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<CreateSurveyRequestDTO> create(@Valid @RequestBody CreateSurveyRequestDTO createSurveyRequestDTO) {
        return super.create(createSurveyRequestDTO);
    }

    @Override
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing survey")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateSurveyResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Survey not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<UpdateSurveyResponseDTO> update(@Valid @PathVariable Long id, @RequestBody UpdateSurveyRequestDTO updateSurveyRequestDTO) {
        return super.update(id, updateSurveyRequestDTO);
    }

    @Override
    @GetMapping
    @Operation(summary = "Get all surveys")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateSurveyRequestDTO.class, type = "array")))
    })
    public ResponseEntity<ApiResponseDTO<Page<CreateSurveyRequestDTO>>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String sortDirection) {
        return super.getAll(page, size, sortBy, sortDirection);
    }

    @Override
    @GetMapping("/{id}")
    @Operation(summary = "Get a survey by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateSurveyRequestDTO.class))),
            @ApiResponse(responseCode = "404", description = "Survey not found")
    })
    public ResponseEntity<CreateSurveyRequestDTO> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @GetMapping("/{surveyId}/editions")
    public ResponseEntity<List<SurveyEditionWithQuestionCountDTO>> getSurveyEditions(@PathVariable Long surveyId) {
        List<SurveyEdition> surveyEditions = surveyService.getSurveyEditionsBySurveyId(surveyId);

        List<SurveyEditionWithQuestionCountDTO> surveyEditionDTOs = surveyEditions.stream()
                .map(edition -> {
                    SurveyEditionWithQuestionCountDTO dto = new SurveyEditionWithQuestionCountDTO();
                    dto.setId(edition.getId());
                    dto.setYear(edition.getYear());
                    dto.setQuestionCount(surveyService.getQuestionCountBySurveyEditionId(edition.getId()));
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(surveyEditionDTOs);
    }

}