package com.yagsog.api.chatBot;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chatBot")
@RequiredArgsConstructor
public class ChatbotController {
    private final ChatbotService chatbotService;

    // 대화 내역 저장
    @PostMapping
    public ResponseEntity<Map<String, String>> chat(@CookieValue(value = "cookie_id") String cookieId,
                                        @RequestBody Map<String, String> request) {
        String userMessage = request.get("question");
        Chatbot chat = chatbotService.saveChat(cookieId, userMessage);

        Map<String, String> response = new HashMap<>();
        response.put("answer", chat.getBotResponse());
        return ResponseEntity.ok(response);
    }

    // 대화 내역 출력
    @GetMapping("/history")
    public ResponseEntity<List<Chatbot>> getHistory(@CookieValue(value = "cookie_id") String cookieId) {

        return ResponseEntity.ok(chatbotService.getChatHistory(cookieId));
    }
}
