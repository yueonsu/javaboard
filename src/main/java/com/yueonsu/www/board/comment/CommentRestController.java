package com.yueonsu.www.board.comment;

import com.yueonsu.www.auth.AuthenticationFacade;
import com.yueonsu.www.board.comment.model.CommentEntity;
import com.yueonsu.www.board.comment.model.CommentPageable;
import com.yueonsu.www.board.comment.model.CommentVo;
import com.yueonsu.www.board.model.BoardResultVo;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/ajax/comment")
@RequiredArgsConstructor
public class CommentRestController {
    private final CommentService commentService;
    private final AuthenticationFacade authenticationFacade;


    /**
     * 댓글 쓰기
     * @param data
     */
    @PostMapping
    public BoardResultVo authInsComment(@RequestBody Map<String, Object> data) {
        String sContent = data.get("sContent").toString();
        int fkBoardSeq = Integer.parseInt(data.get("fkBoardSeq").toString());
        CommentEntity entity = new CommentEntity();
        entity.setSContent(sContent);
        entity.setFkBoardSeq(fkBoardSeq);
        entity.setFkUserSeq(authenticationFacade.getLoginUserPk());

        return commentService.authInsComment(entity);
    }

    /**
     * 댓글 리스트
     * @param fkBoardSeq
     * @return
     */
    @GetMapping
    public BoardResultVo getCommentlist(int fkBoardSeq, int page) {
        CommentPageable pageable = new CommentPageable();
        pageable.setFkBoardSeq(fkBoardSeq);
        pageable.setPage(page);

        return commentService.selCommentList(pageable);
    }

    /**
     * 대댓글 여부 확인
     * @param nCommentSeq
     * @return
     */
    @GetMapping("/reply")
    public BoardResultVo isReply(int nCommentSeq) {
        return commentService.selReplyList(nCommentSeq);
    }

    /**
     * 대댓글 등록
     * @param replyData
     * @return
     */
    @PostMapping("/reply")
    public BoardResultVo authInsReply(@RequestBody Map<String, Object> replyData) {
        String sContent = replyData.get("sContent").toString();
        int nReply = Integer.parseInt(replyData.get("nReply").toString());
        int fkBoardSeq = Integer.parseInt(replyData.get("fkBoardSeq").toString());

        CommentEntity entity = new CommentEntity();
        entity.setSContent(sContent);
        entity.setNReply(nReply);
        entity.setFkBoardSeq(fkBoardSeq);
        entity.setFkUserSeq(authenticationFacade.getLoginUserPk());

        return commentService.insReply(entity);
    }

    @PutMapping
    public BoardResultVo authUpdComment(@RequestBody Map<String, Object> modData) {
        String sContent = modData.get("sContent").toString();
        int fkUserSeq =  Integer.parseInt(modData.get("fkUserSeq").toString());
        int nCommentSeq = Integer.parseInt(modData.get("nCommentSeq").toString());

        CommentEntity entity = new CommentEntity();
        entity.setSContent(sContent);
        entity.setFkUserSeq(fkUserSeq);
        entity.setNCommentSeq(nCommentSeq);

        return commentService.updComment(entity);
    }

    @GetMapping("/page/total")
    public BoardResultVo getTotalPage(int fkBoardSeq, int page) {
        CommentPageable pageable = new CommentPageable();
        pageable.setFkBoardSeq(fkBoardSeq);
        pageable.setPage(page);

        return commentService.selTotalPage(pageable);
    }
}
