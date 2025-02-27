package com.yagsog.api.user;

import com.yagsog.api.response.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Transactional
public class UserController {
    private UserService userService;
    private Map<String, String> updateInfo = new HashMap<>();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getUser(@CookieValue(value = "USER_COOKIE", required = false) String cookieId,
                                               HttpServletResponse response) {
        User user = null;
        // 쿠키가 없으면 새로운 세션 ID 생성
        if (cookieId == null) {
            cookieId = UUID.randomUUID().toString(); // 랜덤한 값 생성
            Cookie cookie = new Cookie("USER_COOKIE", cookieId);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60); // 1시간 동안 유지
            response.addCookie(cookie);
            user = userService.createUser(cookieId);
        } else {
            // 쿠키 ID로 사용자 식별
            user = userService.getUser(cookieId);
        }

        ApiResponse apiResponse = new ApiResponse("초기 페이지 접속", user);
        return ResponseEntity.ok(apiResponse);
    }

    @PatchMapping("/name")
    public ResponseEntity<Map<String, String>> updateName(@CookieValue(value = "USER_COOKIE", required = false) String cookieId,
                                             @RequestBody Map<String, String> requestData) {
        String name = requestData.get("name");
        updateInfo.put("name", name);
        userService.updateUserInfo(cookieId, updateInfo);

        Map<String, String> response = new HashMap<>();
        response.put("name", name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name")
    public ResponseEntity<Map<String, String>> getName(@CookieValue(value = "USER_COOKIE", required = false) String cookieId) {
        String name = userService.getUserInfo(cookieId, "name");

        Map<String, String> response = new HashMap<>();
        response.put("name", name);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/age")
    public ResponseEntity<Map<String, String>> updateAge(@CookieValue(value = "USER_COOKIE", required = false) String cookieId,
                                            @RequestBody Map<String, String> requestData) {
        String age = requestData.get("age");
        updateInfo.put("age", age);
        userService.updateUserInfo(cookieId, updateInfo);

        Map<String, String> response = new HashMap<>();
        response.put("age", age);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/age")
    public ResponseEntity<Map<String, String>> getAge(@CookieValue(value = "USER_COOKIE", required = false) String cookieId) {
        String age = userService.getUserInfo(cookieId, "age");

        Map<String, String> response = new HashMap<>();
        response.put("age", age);
        return ResponseEntity.ok(response);
    }



    @PatchMapping("/gender")
    public ResponseEntity<Map<String, String>> updateGender(@CookieValue(value = "USER_COOKIE", required = false) String cookieId,
                                               @RequestBody Map<String, String> requestData) {
        String gender = requestData.get("gender");
        updateInfo.put("gender", gender);
        userService.updateUserInfo(cookieId, updateInfo);

        Map<String, String> response = new HashMap<>();
        response.put("gender", gender);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/gender")
    public ResponseEntity<Map<String, String>> getGender(@CookieValue(value = "USER_COOKIE", required = false) String cookieId) {
        String gender = userService.getUserInfo(cookieId, "gender");

        Map<String, String> response = new HashMap<>();
        response.put("gender", gender);
        return ResponseEntity.ok(response);
    }



    @PatchMapping("/medications")
    public ResponseEntity<Map<String, String>> updateMedications(@CookieValue(value = "USER_COOKIE", required = false) String cookieId,
                                                    @RequestBody Map<String, String> requestData) {
        String medications = requestData.get("medications");
        updateInfo.put("medications", medications);
        userService.updateUserInfo(cookieId, updateInfo);

        Map<String, String> response = new HashMap<>();
        response.put("medications", medications);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/medications")
    public ResponseEntity<Map<String, String>> getMedications(@CookieValue(value = "USER_COOKIE", required = false) String cookieId) {
        String medications = userService.getUserInfo(cookieId, "medications");

        Map<String, String> response = new HashMap<>();
        response.put("medications", medications);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/personal-info")
    public ResponseEntity<Map<String, String>> getUserInfo(@CookieValue(value = "USER_COOKIE", required = false) String cookieId) {
        Map<String, String> userInfo = userService.getAllUserInfo(cookieId);
        return ResponseEntity.ok(userInfo);
    }

}