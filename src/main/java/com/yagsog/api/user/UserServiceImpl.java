package com.yagsog.api.user;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    public User createUser() {
        User user = new User();
        return userRepository.save(user);
    }

    @Override
    public void updateUserInfo(Long id, String field, String value) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            switch(field) {
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
                    throw new IllegalArgumentException("Invalid field: " + field);
            }
        }
    }

    @Override
    public void updateUserMedications(Long id, String medicationsStr) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
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
    public Optional<User> getUserById(Long id) {
        return Optional.empty();
    }
}
