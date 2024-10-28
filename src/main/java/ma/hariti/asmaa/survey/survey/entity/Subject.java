package ma.hariti.asmaa.survey.survey.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import java.util.ArrayList;

@Entity
@Data
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "subSubject")
    private List<Subject> subSubjects = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "sub_subject_id")
    private Subject subSubject;
}