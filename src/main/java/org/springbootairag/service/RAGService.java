package org.springbootairag.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RAGService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    @Value("classpath:/prompts/prompt.st")
    private Resource template;

    public RAGService(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    public String getResults(String query, List<String> similarResults) {
        //G
        String documentsContext = String.join("\n---\n", similarResults);
        return chatClient.prompt()
                .user("""
            Here is some context:
            %s
            
            Based strictly on the context above, answer this question: %s
            """.formatted(documentsContext, query))
                .call()
                .content();
    }

    public List<String> getSimilarResults(List<Document> documents) {
        //A
        return documents.stream()
                .map(Document::getText)
                .toList();
    }

    public List<Document> getDocuments(String query) {
        //R
        SearchRequest searchRequest = SearchRequest
                .builder()
                .query(query)
                .topK(3)
                .similarityThreshold(0.1)
                .build();

        return vectorStore.similaritySearch(searchRequest);
    }

}
