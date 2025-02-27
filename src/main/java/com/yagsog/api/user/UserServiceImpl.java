package com.yagsog.api.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    public User createUser(String cookieId) {
        User newUser = new User(cookieId);
        return userRepository.save(newUser);
    }

    public User getUser(String cookieId) {
        return userRepository.findByCookieId(cookieId)
                .orElseThrow(() -> new IllegalArgumentException("getUser :: User not found"));
    }

    @Override
    public void updateUserInfo(String cookieId, Map<String, String> updateInfo) {
        User user = userRepository.findByCookieId(cookieId)
                .orElseThrow(() -> new IllegalArgumentException("updateUserInfo :: User not found"));

        updateInfo.forEach((key, value) -> {
            switch(key) {
                case "name":
                    user.setName(value);
                    break;
                case "age":
                    user.setAge(value);
                    break;
                case "gender":
                    user.setGender(value);
                    break;
                default:
                    throw new IllegalArgumentException("updateUserInfo :: Invalid key: " + key);
            }
        });
    }

    @Override
    public void updateUserMedications(String cookieId, String medicationsStr) {
        try {
            Optional<User> optionalUser = userRepository.findByCookieId(cookieId);
            if(optionalUser.isPresent()) {
                User user = optionalUser.get();
                String medicationsJson = convertToJson(medicationsStr);
                user.setMedications(medicationsJson);
                userRepository.save(user);
            } else {
                throw new RuntimeException("User not found : " + optionalUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // String -> Json convert
    private String convertToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    @Override
    public Optional<User> getUserByCookieId(String cookieId) {
        return Optional.empty();
    }
}
