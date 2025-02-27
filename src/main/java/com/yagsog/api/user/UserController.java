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

        return ResponseEntity.ok(requestData);
        //ApiResponse apiResponse = new ApiResponse("이름이 성공적으로 저장되었습니다.", requestData);
        //return ResponseEntity.ok(apiResponse);
    }

    @PatchMapping("/age")
    public ResponseEntity<ApiResponse> updateAge(@CookieValue(value = "USER_COOKIE", required = false) String cookieId,
                                            @RequestBody Map<String, String> requestData) {
        String age = requestData.get("age");
        updateInfo.put("age", age);
        userService.updateUserInfo(cookieId, updateInfo);

        ApiResponse apiResponse = new ApiResponse("나이가 성공적으로 저장되었습니다.", age);
        return ResponseEntity.ok(apiResponse);
    }

    @PatchMapping("/gender")
    public ResponseEntity<ApiResponse> updateGender(@CookieValue(value = "USER_COOKIE", required = false) String cookieId,
                                               @RequestBody Map<String, String> requestData) {
        String gender = requestData.get("gender");
        updateInfo.put("gender", gender);
        userService.updateUserInfo(cookieId, updateInfo);

        ApiResponse apiResponse = new ApiResponse("성별이 성공적으로 저장되었습니다.", gender);
        return ResponseEntity.ok(apiResponse);
    }

    @PatchMapping("/medications")
    public ResponseEntity<ApiResponse> updateMedications(@CookieValue(value = "USER_COOKIE", required = false) String cookieId,
                                                    @RequestBody Map<String, String> requestData) {
        String medications = requestData.get("medications");
        updateInfo.put("medications", medications);
        userService.updateUserInfo(cookieId, updateInfo);

        ApiResponse apiResponse = new ApiResponse("복약 정보가 성공적으로 저장되었습니다.", medications);
        return ResponseEntity.ok(apiResponse);
    }


}
