package com.yueonsu.www.user;

import com.yueonsu.www.ResultVo;
import com.yueonsu.www.email.EmailService;
import com.yueonsu.www.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/ajax/user")
public class UserRestController {

    @Autowired private UserService userService;
    @Autowired private EmailService emailService;

    @GetMapping("/idCheck")
    public ResultVo idCheck(UserEntity param) {
        ResultVo vo = new ResultVo();
        UserEntity entity = userService.selUser(param);
        vo.setResult(1);
        if(entity != null) {
            vo.setResult(0);
        }
        return vo;
    }

    @GetMapping("/emailCheck")
    public ResultVo emailCheck(UserEntity entity) {
        ResultVo vo = new ResultVo();
        int result = (userService.selUser(entity) == null) ? 1 : 0;
        vo.setResult(result);
        return vo;
    }
}
