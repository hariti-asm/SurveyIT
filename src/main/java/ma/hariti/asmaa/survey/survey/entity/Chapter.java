package ma.hariti.asmaa.survey.survey.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "chapters")
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_edition_id", nullable = false)
    private SurveyEdition surveyEdition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_chapter_id")
    private Chapter parentChapter;

    @OneToMany(mappedBy = "parentChapter", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Chapter> subChapters = new ArrayList<>();
}
