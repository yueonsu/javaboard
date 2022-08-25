package com.yueonsu.www.aop;

import com.yueonsu.www.auth.AuthenticationFacade;
import com.yueonsu.www.board.BoardMapper;
import com.yueonsu.www.board.BoardService;
import com.yueonsu.www.board.model.BoardResultVo;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Component
@Aspect
@RequiredArgsConstructor
public class AuthenticationAop {

    private final AuthenticationFacade authenticationFacade;
    private final BoardMapper boardMapper;

    /**
     * 로그인 검증 aop
     * @param joinPoint
     * @return
     */
    @Around("execution(* com.yueonsu.www.board.BoardController.write(..))")
    public Object loginAuth1(ProceedingJoinPoint joinPoint) {
        int loginUserPk = authenticationFacade.getLoginUserPk();
        
        /**
         * 로그인 안되어 있을 때 로그인 페이지로 이동
         */
        if(0 == loginUserPk) {
            return "redirect:/user/login";
        }

        int nBoardSeq = Integer.parseInt(joinPoint.getArgs()[1].toString());
        
        /**
         * 글 수정 페이지일 때 자기가 쓴 글이 아니면 수정페이지로 못들어가게 막음
         */
        if(0 < nBoardSeq) {
            int fkUserSeq = boardMapper.selModData(nBoardSeq).getFkUserSeq();
            if (loginUserPk != fkUserSeq) {
                return "redirect:/board/detail?nBoardSeq=" + nBoardSeq;
            }
        }

        return "board/write";
    }
}