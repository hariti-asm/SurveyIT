package ma.hariti.asmaa.survey.survey.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<SurveyEdition> editions;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
}