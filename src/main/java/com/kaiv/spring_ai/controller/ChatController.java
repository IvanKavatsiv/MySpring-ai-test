package com.kaiv.spring_ai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final OpenAiChatModel chatModel;

    @GetMapping("/")
    public String index() {
        return "chat";
    }

    @PostMapping("/chat")
    public String chat(@RequestParam String type,
                       @RequestParam String topic,
                       Model model) {

        String promptText = "Tell me a " + type + " joke about " + topic;

        UserMessage userMessage = new UserMessage(promptText);

        Prompt prompt = new Prompt(
                userMessage,
                OpenAiChatOptions.builder()
                        .model(OpenAiApi.ChatModel.GPT_3_5_TURBO.getValue())
                        .build()
        );

        ChatResponse response = chatModel.call(prompt);

        model.addAttribute("response", response.getResult().getOutput().getText());
        model.addAttribute("type", type);
        model.addAttribute("topic", topic);

        return "chat";
    }
}
