package com.yueonsu.www.email;

import com.yueonsu.www.ResultVo;
import com.yueonsu.www.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ajax/email")
@RequiredArgsConstructor
public class EmailRestController {

    private final EmailService emailService;

    @GetMapping("/code")
    public ResultVo sendCode(UserEntity entity) {
        ResultVo vo = new ResultVo();
        vo.setResultString(emailService.sendCode(entity));

        return vo;
    }
}
