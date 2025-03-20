package br.com.ganog.springrag.controller;

import br.com.ganog.springrag.controller.dto.MyQuestion;
import br.com.ganog.springrag.controller.dto.MyStructuredTemplate;
import br.com.ganog.springrag.controller.dto.MyStructuredTemplate.PromptDish;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.structured.StructuredPromptProcessor;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiImageModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.util.List;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_3_5_TURBO;

@RestController
@RequiredArgsConstructor
public class AgentController {

    @Value("${OPENAI_KEY}")
    private String openAiKey;

    @PostMapping("/answer")
    public String chatOpenAi(@RequestBody MyQuestion myQuestion) {

        ChatLanguageModel customModel = new OpenAiChatModel.OpenAiChatModelBuilder()
                .apiKey(openAiKey)
                .modelName(GPT_3_5_TURBO)
                .temperature(0.7)
                .build();

        return customModel.chat(myQuestion.question());
    }

    @PostMapping("/revenue")
    public String makeARecipe() {
        PromptDish promptDish = new PromptDish();
        promptDish.dish = "Cozido";
        promptDish.ingredients = List.of("batata", "cenoura", "carne", "cebola");

        Prompt prompt = StructuredPromptProcessor.toPrompt(promptDish);

        ChatLanguageModel customModel = new OpenAiChatModel.OpenAiChatModelBuilder()
                .apiKey(openAiKey)
                .modelName(GPT_3_5_TURBO)
                .temperature(0.7)
                .build();

        return customModel.chat(prompt.text());
    }

    @PostMapping("/image")
    public String generateImage(@RequestBody MyQuestion myQuestion) throws MalformedURLException {

        ImageModel customImageModel = new OpenAiImageModel.OpenAiImageModelBuilder()
                .apiKey(openAiKey)
                .modelName("dall-e-2")
                .build();

        return customImageModel.generate(myQuestion.question()).content().url().toURL().toString();
    }
}