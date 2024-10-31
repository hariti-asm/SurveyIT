package ma.hariti.asmaa.survey.survey.dto.surveyEdition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyEditionResponseDTO {
    private Long id;

    private Integer year;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status;

    private Long surveyId;
}
