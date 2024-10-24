# Spring Boot AI Streaming

## A Spring Boot project to demonstrate the use of AI models in a web application with streaming support.

This project uses the Spring AI library to integrate AI models into a web application. The application provides a simple chat interface where users can input text and receive a response from an AI model. The application also supports streaming, allowing users to receive a stream of responses from the AI model as they type.

The project uses the following technologies:

* Spring Boot 3.3.4
* Spring AI 1.0.0-M3
* Ollama AI models
* OpenAI API
* Anthropic API

### Running the application

To run the application, execute the following command:

```bash 
./mvnw spring-boot:run
```

# Ollama Setup Instructions

1. Download and Install Ollama:
    - Visit https://ollama.com/download.
    - Follow installation instructions to install locally.

2. Running Ollama Models:
    - Run `mistral` model:
      > ollama run mistral
    - Run `llama3.2:3b` model:
      > ollama run llama3.2:3b
    - Stop running `llama3.2:3b` model:
      > ollama stop llama3.2:3b
    - Remove `llama3.2:3b` model:
      > ollama rm llama3.2:3b
    - Run `llava` model (for image generation):
      > ollama run llava

3. Managing Models:
    - Pull model from Hugging Face:
      > ollama pull hf.co/bartowski/gemma-2-2b-it-GGUF
    - Set model in Spring application:
      > spring.ai.ollama.chat.options.model=hf.co/bartowski/gemma-2-2b-it-GGUF

# API Endpoints:

1. **Basic Chatbot (Input: Prompt, Output: Response)**
   URL: http://localhost:8080

2. **Image Explanation Chatbot (Input: Image-Name, Output: Explanation)**
   URL: http://localhost:8080/image.html

3. **Image Upload Chatbot (Input: Image, Output: Store in Directory)**
   URL: http://localhost:8080/upload.html

4. **Stream Chatbot (Input: Prompt, Output: Stream)**
   URL: http://localhost:8080/stream.html

5. **Ask & Get Response (Input: Prompt, Output: Response)**
   URL: http://localhost:8080/ask?prompt=what%20is%20love

6. **Read Image (Input: Static Image-Name, Output: Explanation)**
   URL: http://localhost:8080/read-photo
