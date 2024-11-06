package ma.hariti.asmaa.survey.survey.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.hariti.asmaa.survey.survey.enums.QuestionType;
import ma.hariti.asmaa.survey.survey.util.BaseEntity;

import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "questions")
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;
    private int answerCount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_chapter_id", nullable = false)
    private Chapter subChapter;

    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Answer> answers = new ArrayList<>();

    private QuestionType type;

    private Boolean required;
}