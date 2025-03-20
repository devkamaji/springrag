package br.com.ganog.springrag.controller.dto;

import dev.langchain4j.model.input.structured.StructuredPrompt;

import java.util.List;

public class MyStructuredTemplate {

    @StructuredPrompt({
            "Crie uam receita de {{dish}} com os seguintes ingredientes: {{ingredients}}",
            "Estruture sua resposta da seguinte forma:",
            "Nome da receita: ...",
            "Descrição: ...",
            "Tempo de preparo: ...",
            "Ingredientes Necessarios: ...",
            "- ...",
            "- ...",
            "Modo de preparo: ...",
            "- ...",
            "- ..."
    })
    public static class PromptDish {
        public String dish;
        public List<String> ingredients;
    }
}
