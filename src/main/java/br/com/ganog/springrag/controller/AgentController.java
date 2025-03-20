package br.com.ganog.springrag.controller;

import br.com.ganog.springrag.controller.dto.MyQuestion;
import br.com.ganog.springrag.rag.Assistant;
import br.com.ganog.springrag.rag.RagService;
import dev.langchain4j.http.client.jdk.JdkHttpClientBuilder;
import dev.langchain4j.model.ollama.OllamaChatModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
public class AgentController {

    private final RagService ragService;

    @PostMapping("/rag")
    public String messageRag(@RequestBody MyQuestion myQuestion) {

        Assistant assistant;
        try {
            assistant = ragService.configure();
        } catch (Exception e) {
            throw new RuntimeException("Error configuring the assistant", e);
        }

        return assistant.aswer(myQuestion.question());
    }

    @PostMapping("/norag")
    public String messageNoRag(@RequestBody MyQuestion myQuestion) {

        final var chat = OllamaChatModel.builder()
                .httpClientBuilder(new JdkHttpClientBuilder())
                .baseUrl("http://localhost:11434")
                .logRequests(true)
                .logResponses(true)
                .modelName("llama3")
                .timeout(Duration.ofSeconds(300))
                .maxRetries(2)
                .build();

        return chat.chat(myQuestion.question());
    }
}