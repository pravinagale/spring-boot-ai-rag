package org.springbootairag.ragdataload;

import jakarta.annotation.PostConstruct;
import org.springbootairag.entity.Article;
import org.springbootairag.repository.ArticleRepository;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;
import org.springframework.ai.document.Document;

import java.util.List;
import java.util.Map;

@Component
public class RAGDBDataLoader {

    private final ArticleRepository articleRepository;
    private final VectorStore vectorStore;

    public RAGDBDataLoader(ArticleRepository articleRepository, VectorStore vectorStore) {
        this.articleRepository = articleRepository;
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadDataFromDB() {

        List<Article> articles = articleRepository.findAll();

        List<Document> rawDocuments = articles.stream()
                .map(article -> {
                    String content = String.format("Title: %s\nContent: %s",
                            article.getTitle(),
                            article.getDescription());

                    Map<String, Object> metadata = Map.of(
                            "db_id", article.getId(),
                            "category", article.getCategory()
                    );

                    return new Document(content, metadata);
                })
                .toList();

        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(800)              // Target tokens per chunk
                .withMinChunkSizeChars(350)      // Minimum character length per chunk
                .withMinChunkLengthToEmbed(5)    // Ignore tiny fragments
                .withMaxNumChunks(10000)         // Max chunks generated per document
                .withKeepSeparator(true)         // Retain punctuation/newlines
                .build();

        List<Document> chunkedDocuments = splitter.apply(rawDocuments);

        vectorStore.add(chunkedDocuments);
    }

}
