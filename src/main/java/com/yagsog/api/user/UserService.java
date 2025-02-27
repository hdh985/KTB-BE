package com.yagsog.api.user;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    User createUser(String cookieId);
    void updateUserInfo(String cookieId, Map<String, String> updateInfo);
    Optional<User> getUserByCookieId(String cookieId);
    public User getUser(String cookieId);
    String getUserInfo(String cookieId, String key);
    Map<String, String> getAllUserInfo(String cookieId);
}
