package com.yagsog.api.user;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    User createUser(String cookieId);
    void updateUserInfo(String cookieId, Map<String, String> updateInfo);
    void updateUserMedications(String cookieId, String medicationsJson);
    Optional<User> getUserByCookieId(String cookieId);
    public User getUser(String cookieId);
}
