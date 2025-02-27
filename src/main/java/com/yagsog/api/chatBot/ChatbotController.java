package com.yagsog.api.chatBot;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatbotController {
    private final ChatbotService chatbotService;

    // 대화 내역 저장
    @PostMapping("/chatBot")
    public ResponseEntity<Chatbot> chat(@CookieValue(value = "cookie_id") String cookieId,
                                        @RequestBody Map<String, String> request) {
        String userMessage = request.get("message");
        Chatbot chat = chatbotService.saveChat(cookieId, userMessage);
        return ResponseEntity.ok(chat);
    }

    // 대화 내역 출력
    @GetMapping("/chatBot")
    public ResponseEntity<List<Chatbot>> getHistory(@CookieValue(value = "cookie_id") String cookieId) {
        return ResponseEntity.ok(chatbotService.getChatHistory(cookieId));
    }
}
