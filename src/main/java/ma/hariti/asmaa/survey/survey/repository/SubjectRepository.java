package ma.hariti.asmaa.survey.survey.repository;


import ma.hariti.asmaa.survey.survey.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}