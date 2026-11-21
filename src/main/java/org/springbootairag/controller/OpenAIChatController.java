package org.springbootairag.controller;


import org.springbootairag.service.OpenAIChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAIChatController {

    private final OpenAIChatService openAIChatService;

    public OpenAIChatController(OpenAIChatService openAIChatService) {
        this.openAIChatService = openAIChatService;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
        return openAIChatService.chatWithOpenAILLM(message);
    }

}
