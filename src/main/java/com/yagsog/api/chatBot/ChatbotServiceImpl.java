package com.yagsog.api.chatBot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatbotServiceImpl implements ChatbotService {

    private final RestTemplate restTemplate;
    private final ChatbotRepository chatbotRepository;

    @Override
    public Chatbot saveChat(String cookieId, String userMessage) {
        // 1. AI 챗봇 API 호출
        String apiUrl = "https://api.example.com/chatbot?message=" + userMessage;
        String botResponse = restTemplate.getForObject(apiUrl, String.class);

        // 2. 대화 내역 저장
        Chatbot chat = new Chatbot();
        chat.setCookieId(cookieId);
        chat.setUserMessage(userMessage);
        chat.setBotResponse(botResponse);

        return chatbotRepository.save(chat);
    }

    @Override
    public List<Chatbot> getChatHistory(String cookieId) {
        return chatbotRepository.findByCookieIdOrderByCreatedAtAsc(cookieId);
    }
}
