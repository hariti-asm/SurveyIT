package ma.hariti.asmaa.survey.survey.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
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