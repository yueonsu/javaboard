package com.yueonsu.www.aop;

import com.yueonsu.www.auth.AuthenticationFacade;
import com.yueonsu.www.board.BoardMapper;
import com.yueonsu.www.board.model.BoardResultVo;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class AuthenticationAop {

    private final AuthenticationFacade authenticationFacade;
    private final BoardMapper boardMapper;

    /**
     * 로그인 검증 Controller
     * @param joinPoint
     * @return
     */
    @Around("execution(* com.yueonsu.www.board.BoardController.write(..))")
    public Object boardAop(ProceedingJoinPoint joinPoint) throws Throwable {
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

        return joinPoint.proceed();
    }

    /**
     * 로그인 검증 RestController
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.yueonsu.www.board.BoardRestController.auth*(..))")
    public Object boardRestAop(ProceedingJoinPoint joinPoint) throws Throwable {
        return getObject(joinPoint);
    }

    @Around("execution(* com.yueonsu.www.board.comment.CommentRestController.auth*(..))")
    public Object commentAop(ProceedingJoinPoint joinPoint) throws Throwable {
        return getObject(joinPoint);
    }

    private Object getObject(ProceedingJoinPoint joinPoint) throws Throwable {
        int loginUserPk = authenticationFacade.getLoginUserPk();
        if (0 == loginUserPk) {
            BoardResultVo vo = new BoardResultVo();
            vo.setStatus("400");
            vo.setDesc("fail");
            vo.setResult(null);
            return vo;
        }
        return joinPoint.proceed();
    }
}
