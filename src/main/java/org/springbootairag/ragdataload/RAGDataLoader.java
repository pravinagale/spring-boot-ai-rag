package org.springbootairag.ragdataload;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RAGDataLoader {

    private final VectorStore vectorStore;

    public RAGDataLoader(VectorStore vectorStore){
        this.vectorStore = vectorStore;
    }

    private List<String> getSampleData() {
        return List.of(
                // HR Policies
                "Employees are entitled to 24 days of paid annual leave per calendar year. Unused leave up to 5 days can be carried over to the next year.",
                "Remote work is permitted up to 3 days per week. Employees must be available on Slack during core hours from 10:00 AM to 4:00 PM IST.",
                "Expense reimbursements for travel and client meals must be submitted within 15 days with valid GST receipts.",

                // Tech Troubleshooting & AI
                "If the Spring Boot application fails to connect to Qdrant on port 6334, verify that the gRPC port is exposed in Docker Compose and initialize-schema is set to true.",
                "To resolve out-of-memory crashes in JVM microservices, increase the heap limit using the -Xmx2g flag and check for unclosed database connections.",
                "Ollama embedding models like mxbai-embed-large convert text into 1024-dimensional dense vectors for semantic similarity search.",

                // Products
                "ErgoChair Pro: Mesh ergonomic office chair with adjustable lumbar support, 4D armrests, and breathable headrest. Ideal for long coding sessions.",
                "Keychron K2 Mechanical Keyboard: Wireless 75% compact keyboard with tactile brown switches, aluminum frame, and Mac/Windows compatibility.",
                "Sony WH-1000XM5 Headphones: Industry-leading active noise cancellation, 30-hour battery life, and multipoint Bluetooth connectivity."
        );
    }

    //@PostConstruct
    public void loadSampleData(){
        List<String> sampleData = getSampleData();
        List<Document> documents = sampleData.stream()
                .map(Document::new)
                .toList();
        vectorStore.add(documents);
    }

}
