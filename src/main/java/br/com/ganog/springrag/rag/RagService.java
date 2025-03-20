package br.com.ganog.springrag.rag;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.internal.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class RagService {

    public Assistant configure() {
        List<Document> documents = List.of();
        return null;
    }

    public PathMatcher glob(String glob) {
        return FileSystems.getDefault().getPathMatcher(format("glob:%s", glob));
    }

    public Path path(String path) {
        try {
            final var fileUrl = Utils.class.getClassLoader().getResource(path);
            return Paths.get(fileUrl.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Error creating path: " + path, e);
        }
    }

    public String getFileContent(String filePath) {

        final var resource = new ClassPathResource("documents/perfil.txt");

        try {
            final var file = resource.getFile();

            return new String(Files.readAllBytes(file.toPath()));
        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + filePath, e);
        }
    }
}
