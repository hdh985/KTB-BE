package com.yagsog.api.chatBot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatbotServiceImpl implements ChatbotService {

    private static final String CHATBOT_URL = "https://cqb43gln-8000.asse.devtunnels.ms/chat?message=";

    private final RestTemplate restTemplate;
    private final ChatbotRepository chatbotRepository;

    @Override
    public Chatbot saveChat(String cookieId, String question) {
        // 1. AI 챗봇 API 호출
        String apiUrl = CHATBOT_URL + question;
        String answer = restTemplate.getForObject(apiUrl, String.class);


        // 2. 대화 내역 저장
        Chatbot chat = new Chatbot();
        chat.setCookieId(cookieId);
        chat.setQuestion(question);
        chat.setAnswer(answer);

        return chatbotRepository.save(chat);
    }

    @Override
    public ChatBotResponseDto getChatHistory(String cookieId) {
        List<ChatResponseDto> chatResponses = chatbotRepository.findByCookieId(cookieId)
                .stream()
                .map(entity -> new ChatResponseDto(entity.getId(), entity.getQuestion(), entity.getAnswer()))
                .collect(Collectors.toList());

        return new ChatBotResponseDto(chatResponses);
    }

    @Override
    public int getChatHistoryCount(String cookieId) {
        return chatbotRepository.countByCookieId(cookieId);
    }

}
