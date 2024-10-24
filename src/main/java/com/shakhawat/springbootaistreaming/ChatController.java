package com.shakhawat.springbootaistreaming;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.Media;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
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

    @GetMapping("/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatModel.stream(prompt);
    }

    @GetMapping("/read-photo")
    public ChatResponse readPhoto() {
        ClassPathResource imageResource = new ClassPathResource("/uploads/multimodal.png");
        UserMessage userMessage = new UserMessage("Explain what do you see on this picture?", new Media(MimeTypeUtils.IMAGE_PNG, imageResource));

        Prompt prompt = new Prompt(userMessage, OllamaOptions.builder().withModel(OllamaModel.LLAVA).build());

        return chatModel.call(prompt);
    }

}
