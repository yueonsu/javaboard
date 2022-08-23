package com.yueonsu.www.user;

import com.yueonsu.www.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired private UserService userService;

    @GetMapping("/join")
    public String join() {

        return "user/join";
    }

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
