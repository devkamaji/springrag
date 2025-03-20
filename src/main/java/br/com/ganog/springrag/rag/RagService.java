package br.com.ganog.springrag.rag;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.http.client.jdk.JdkHttpClientBuilder;
import dev.langchain4j.internal.Utils;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RagService {

    public Assistant configure() {
        List<Document> documents;

        documents = FileSystemDocumentLoader.loadDocuments(toPath("documents/"), new TextDocumentParser());

        return AiServices.builder(Assistant.class)
                .chatLanguageModel(OllamaChatModel.builder()
                        .httpClientBuilder(new JdkHttpClientBuilder())
                        .baseUrl("http://localhost:11434")
                        .logRequests(true)
                        .logResponses(true)
                        .modelName("llama3")
                        .timeout(Duration.ofSeconds(300))
                        .maxRetries(2)
                        .build())
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .contentRetriever(createContentRetriever(documents))
                .build();
    }

    public ContentRetriever createContentRetriever(List<Document> documents) {

        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        // conversao de tokens do documento em vetores
        EmbeddingStoreIngestor.ingest(documents, embeddingStore);

        return EmbeddingStoreContentRetriever.from(embeddingStore);
    }

    public Path toPath(String path) {
        try {
            final var fileUrl = Utils.class.getClassLoader().getResource(path);
            return Paths.get(fileUrl.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Error creating path: " + path, e);
        }
    }
}
