package com.yueonsu.www.user;

import com.yueonsu.www.board.model.BoardResultVo;
import com.yueonsu.www.email.EmailService;
import com.yueonsu.www.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ajax/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    /**
     * 아이디 중복 체크
     * @param sId
     * @return
     */
    @GetMapping("/idCheck")
    public BoardResultVo idCheck(@RequestParam String sId) {
        BoardResultVo vo = new BoardResultVo();

        if (1 > sId.length()) {
            vo.setStatus("400");
            vo.setDesc("fail");
            return vo;
        }

        UserEntity reqEntity = new UserEntity();
        reqEntity.setSId(sId);
        
        UserEntity entity = userService.selUser(reqEntity);
        vo.setResult(1);
        if(entity != null) {
            vo.setResult(0);
        }
        return vo;
    }

    /**
     * 이메일 중복체크
     * @param sEmail
     * @return
     */
    @GetMapping("/emailCheck")
    public BoardResultVo emailCheck(@RequestParam String sEmail) {
        BoardResultVo vo = new BoardResultVo();

        if(1 > sEmail.length()) {
            vo.setStatus("400");
            vo.setDesc("fail");
            return vo;
        }

        UserEntity entity = new UserEntity();
        entity.setSEmail(sEmail);

        int result = (userService.selUser(entity) == null) ? 1 : 0;
        vo.setResult(result);
        return vo;
    }
}
