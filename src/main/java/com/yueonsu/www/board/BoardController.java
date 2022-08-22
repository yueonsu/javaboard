package com.yueonsu.www.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class BoardController {

    @GetMapping
    public String main() {

        return "index";
    }
}
