package ma.hariti.asmaa.survey.survey.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @OneToMany(
            mappedBy = "survey",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            targetEntity = Chapter.class
    )
    private List<Chapter> chapters = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;
}