package com.yagsog.api.user;

import java.util.Optional;

public interface UserService {
    User createUser();
    void updateUserInfo(Long id, String field, String value);
    void updateUserMedications(Long id, String medicationsJson);
    Optional<User> getUserById(Long id);

}
