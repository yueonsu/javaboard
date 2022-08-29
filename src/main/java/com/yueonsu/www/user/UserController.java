package com.yueonsu.www.user;

import com.yueonsu.www.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/join")
    public String join() {

        return "user/join";
    }

    /**
     * 회원가입
     * @param entity
     * @return
     */
    @PostMapping("/join")
    public String joinProc(UserEntity entity) {
        int result = userService.insUser(entity);
        String path = (result == 1) ? "/login" : "/join";
        return "redirect:/user" + path;
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }
}
