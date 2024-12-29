package ma.hariti.asmaa.survey.survey.controller;

import ma.hariti.asmaa.survey.survey.dto.answer.AnswerDTO;
import ma.hariti.asmaa.survey.survey.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<AnswerDTO>> getAnswersByQuestionId(@PathVariable Long questionId) {
        List<AnswerDTO> answers = answerService.getAnswerDTOsByQuestionId(questionId);
        return ResponseEntity.ok(answers);
    }

    @PostMapping
    public ResponseEntity<AnswerDTO> saveAnswer(@RequestBody AnswerDTO answerDTO) {
        AnswerDTO savedAnswer = answerService.saveAnswer(answerDTO);
        return ResponseEntity.ok(savedAnswer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAnswer(@PathVariable Long id, @RequestBody AnswerDTO answerDTO) {
        answerService.updateAnswer(id, answerDTO);
        return ResponseEntity.noContent().build();
    }
}
