package ma.hariti.asmaa.survey.survey.service;

import ma.hariti.asmaa.survey.survey.dto.answer.AnswerDTO;
import ma.hariti.asmaa.survey.survey.entity.Answer;
import ma.hariti.asmaa.survey.survey.mapper.AnswerMapper;
import ma.hariti.asmaa.survey.survey.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, AnswerMapper answerMapper) {
        this.answerRepository = answerRepository;
        this.answerMapper = answerMapper;
    }

    public List<AnswerDTO> getAnswerDTOsByQuestionId(Long questionId) {
        List<Answer> answers = answerRepository.findByQuestionId(questionId);
        return answerMapper.toDtoList(answers);
    }

    public AnswerDTO saveAnswer(AnswerDTO answerDTO) {
        Answer answer = answerMapper.toEntity(answerDTO);
        Answer savedAnswer = answerRepository.save(answer);
        return answerMapper.toDto(savedAnswer);
    }

    public void updateAnswer(Long id, AnswerDTO answerDTO) {
        Answer existingAnswer = answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer not found"));
        answerMapper.updateAnswerFromDTO(answerDTO, existingAnswer);
        answerRepository.save(existingAnswer);
    }
}
