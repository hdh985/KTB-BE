package com.yagsog.api.chatBot;

import java.util.List;

public interface ChatbotService {
    Chatbot saveChat(String cookieId, String userMessage);
    List<Chatbot> getChatHistory(String cookieId);
}
