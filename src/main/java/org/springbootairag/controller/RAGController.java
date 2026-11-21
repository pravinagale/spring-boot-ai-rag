package org.springbootairag.controller;


import org.springbootairag.service.RAGService;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rag")
public class RAGController {

    private final RAGService ragService;

    public RAGController(RAGService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/fetch")
    public String fetchData(@RequestParam String query,
                            @RequestHeader String username) {
        List<Document> documents = ragService.getDocuments(query);
        List<String> similarResults = ragService.getSimilarResults(documents);
        return ragService.getResults(query, similarResults);
    }
}
