package org.springbootairag.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RAGPdfDataLoader {

    private final VectorStore vectorStore;

    @Value("classpath:rag_test_corpus.pdf")
    Resource pdfResource;

    public RAGPdfDataLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadDataPdf(){
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(pdfResource);
        List<Document> documents = tikaDocumentReader.get();
        TokenTextSplitter tokenTextSplitter = TokenTextSplitter.builder()
                .withChunkSize(20)
                .withMaxNumChunks(200)
                .build();
        List<Document> documentChunks = tokenTextSplitter.split(documents);
        vectorStore.add(documentChunks);
    }
}
