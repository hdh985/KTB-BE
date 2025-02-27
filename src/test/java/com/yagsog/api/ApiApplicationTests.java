package com.yagsog.api;

import com.yagsog.api.chatBot.Chatbot;
import com.yagsog.api.chatBot.ChatbotService;
import com.yagsog.api.user.User;
import com.yagsog.api.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApiApplication.class)
@ActiveProfiles("test")
class ApiApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
		//
	}

}
