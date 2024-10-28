package ma.hariti.asmaa.survey.survey.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "owner")
    private List<Survey> surveys;
}