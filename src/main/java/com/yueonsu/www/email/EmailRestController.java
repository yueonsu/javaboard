package com.yueonsu.www.email;

import com.yueonsu.www.ResultVo;
import com.yueonsu.www.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ajax/email")
public class EmailRestController {

    @Autowired private EmailService emailService;

    @GetMapping("/code")
    public ResultVo sendCode(UserEntity entity) {
        ResultVo vo = new ResultVo();
        vo.setResultString(emailService.sendCode(entity));

        return vo;
    }
}
