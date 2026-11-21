package org.springbootairag.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class OpenAIChatService {

    private final ChatClient chatClient;

    public OpenAIChatService(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    public String chatWithOpenAILLM(String message) {
        return chatClient
                .prompt()
                .user(message)
                .call()
                .content();
    }
}
