package ma.hariti.asmaa.survey.survey.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Data
@Getter
@Setter
public class SurveyEdition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate creationDate;
    private LocalDate startDate;
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;
}