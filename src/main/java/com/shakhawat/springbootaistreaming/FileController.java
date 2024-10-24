package com.shakhawat.springbootaistreaming;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.Media;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin
public class FileController {

    private final ChatClient chatClient;

    public FileController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    private static final String UPLOAD_DIR = "src/main/resources/uploads/";

    @PostMapping("/upload")
    public Flux<String> uploadImage(@RequestParam("image") MultipartFile uploadedFile) {
        if (uploadedFile.isEmpty()) {
            return Flux.error(new RuntimeException("Please select a file to upload."));
        }

        try {
            // Create upload directory if it doesn't exist
            File uploadDirectory = new File(UPLOAD_DIR);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }

            // Save the uploaded file
            String fileName = uploadedFile.getOriginalFilename();
            Path fileSavePath = Paths.get(UPLOAD_DIR + fileName);
            Files.write(fileSavePath, uploadedFile.getBytes());

            // Return a success response
            return Flux.just("File uploaded successfully: " + fileName);

        } catch (IOException e) {
            return Flux.error(e);
        }
    }

    @GetMapping("/explainPhoto")
    public Flux<String> explainPhoto(@RequestParam("fileName") String fileName) {

        if (fileName.isEmpty()) {
            return Flux.error(new RuntimeException("Please provide a valid file name."));
        }

        ClassPathResource imageResource = new ClassPathResource("/uploads/" + fileName + ".png");
        UserMessage userMessage = new UserMessage("Explain what do you see on this picture?", new Media(MimeTypeUtils.IMAGE_PNG, imageResource));

        Prompt prompt = new Prompt(userMessage, OllamaOptions.builder().withModel(OllamaModel.LLAVA).build());

        return chatClient.prompt(prompt)
                .stream()
                .content();
    }

}
