package br.com.ganog.springrag.controller;

import br.com.ganog.springrag.controller.dto.MyQuestion;
import br.com.ganog.springrag.rag.Assistant;
import br.com.ganog.springrag.rag.RagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AgentController {

    private final RagService ragService;
    private Assistant assistant;

    @PostMapping("/rag")
    public String messageRag(@RequestBody MyQuestion myQuestion) {

        if (assistant == null) {
            assistant = ragService.configure();
        }
        return assistant.aswer(myQuestion.question());
    }
}