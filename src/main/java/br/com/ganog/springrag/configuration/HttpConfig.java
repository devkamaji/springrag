package br.com.ganog.springrag.configuration;

import dev.langchain4j.http.client.HttpClientBuilderFactory;
import dev.langchain4j.http.client.jdk.JdkHttpClientBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpConfig {

    @Bean
    public HttpClientBuilderFactory httpClientBuilderFactory() {
        return new JdkHttpClientBuilderFactory();
    }
}
