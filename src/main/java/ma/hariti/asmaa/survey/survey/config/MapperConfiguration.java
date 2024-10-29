package ma.hariti.asmaa.survey.survey.config;

import ma.hariti.asmaa.survey.survey.mapper.SurveyMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {
    @Bean
    public SurveyMapper surveyMapper() {
        return Mappers.getMapper(SurveyMapper.class);
    }}