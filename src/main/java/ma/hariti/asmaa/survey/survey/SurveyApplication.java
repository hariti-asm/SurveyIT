package ma.hariti.asmaa.survey.survey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "ma.hariti.asmaa.survey")
public class SurveyApplication {
    public static void main(String[] args) {
        SpringApplication.run(SurveyApplication.class, args);
    }
}