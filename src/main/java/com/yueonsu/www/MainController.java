package com.yueonsu.www;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    // 댓글
    // tHit fkUserSeq -> sIp

    @GetMapping
    public String main() {
        return "redirect:/board/list";
    }
}
