package ma.hariti.asmaa.survey.survey.entity;

import jakarta.persistence.*;
import ma.hariti.asmaa.survey.survey.enums.QuestionType;

import java.util.List;
import jakarta.persistence.*;
import ma.hariti.asmaa.survey.survey.enums.QuestionType;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private Integer answerCount;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();
}