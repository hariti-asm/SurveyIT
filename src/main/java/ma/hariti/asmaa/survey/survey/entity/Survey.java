package ma.hariti.asmaa.survey.survey.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.hariti.asmaa.survey.survey.util.BaseEntity;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "surveys")
@NoArgsConstructor
@Data
public class Survey extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @JsonBackReference("owner-surveys")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<SurveyEdition> surveyEditions = new ArrayList<>();


}