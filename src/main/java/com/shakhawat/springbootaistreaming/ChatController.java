package com.shakhawat.springbootaistreaming;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@CrossOrigin
public class ChatController {

    private final ChatClient chatClient;

    private final ChatModel chatModel;

    public ChatController(ChatClient.Builder chatClient, ChatModel chatModel) {
        this.chatClient = chatClient.build();
        this.chatModel = chatModel;
    }

    @PostMapping("/chat")
    public String chat(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    @GetMapping("/stream")
    public Flux<String> chatWithStream(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }

    @GetMapping("/ask")
    public String ask(@RequestParam(name = "prompt") String prompt) {
        return chatModel.call(prompt);
    }



}
