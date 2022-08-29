package com.yueonsu.www.board.comment;

import com.yueonsu.www.auth.AuthenticationFacade;
import com.yueonsu.www.board.BoardService;
import com.yueonsu.www.board.comment.model.CommentEntity;
import com.yueonsu.www.board.comment.model.CommentPageable;
import com.yueonsu.www.board.model.BoardResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/ajax/comment")
@RequiredArgsConstructor
public class CommentRestController {
    private final CommentService commentService;
    private final AuthenticationFacade authenticationFacade;
    private final BoardService boardService;


    /**
     * 댓글 쓰기
     */
    @PostMapping
    public BoardResultVo authInsComment(@RequestBody Map<String, Object> data) {
        BoardResultVo vo = new BoardResultVo();

        int lastSeq = boardService.selBoardLastSeq();

        String content = "";
        int boardSeq = 0;

        /**
         * 유효성 검증
         */
        try {
            content = data.get("sContent").toString();
            boardSeq = Integer.parseInt(data.get("fkBoardSeq").toString());
        } catch (NumberFormatException e) {
            vo.setStatus("400");
            vo.setDesc("fail");
            return vo;
        }
        if (0 == content.length() || 1 > boardSeq  || lastSeq < boardSeq) {
            vo.setStatus("400");
            vo.setDesc("fail");
            return vo;
        }

        CommentEntity entity = new CommentEntity();
        entity.setSContent(content);
        entity.setFkBoardSeq(boardSeq);
        entity.setFkUserSeq(authenticationFacade.getLoginUserPk());

        return commentService.authInsComment(entity);
    }

    /**
     * 댓글 리스트
     */
    @GetMapping
    public BoardResultVo getCommentList(@RequestParam(required = true) int fkBoardSeq,
                                        @RequestParam(required = true, defaultValue = "1") int page) {
        /**
         * 유효성 검증
         */
        int lastSeq = boardService.selBoardLastSeq();
        if (1 > fkBoardSeq || lastSeq < fkBoardSeq) {
            BoardResultVo vo = new BoardResultVo();
            vo.setStatus("400");
            vo.setDesc("fail");
            return vo;
        }

        CommentPageable pageable = new CommentPageable();
        pageable.setFkBoardSeq(fkBoardSeq);
        pageable.setPage(page);

        return commentService.selCommentList(pageable);
    }

    /**
     * 대댓글 여부 확인 / 리스트 출력
     */
    @GetMapping("/reply")
    public BoardResultVo isReply(@RequestParam(required = true) int nCommentSeq) {
        /**
         * 유효성 검증
         */
        int lastSeq = commentService.selCommentLastSeq();
        if (1 > nCommentSeq || lastSeq < nCommentSeq) {
            BoardResultVo vo = new BoardResultVo();
            vo.setStatus("400");
            vo.setDesc("fail");
            return vo;
        }
        return commentService.selReplyList(nCommentSeq);
    }

    /**
     * 대댓글 등록
     */
    @PostMapping("/reply")
    public BoardResultVo authInsReply(@RequestBody Map<String, Object> replyData) {

        BoardResultVo vo = new BoardResultVo();

        String content = "";
        int reply = 0;
        int boardSeq = 0;

        /**
         * 유효성 검증
         */
        int lastSeq = boardService.selBoardLastSeq();
        try {
            content = replyData.get("sContent").toString();
            reply = Integer.parseInt(replyData.get("nReply").toString());
            boardSeq = Integer.parseInt(replyData.get("fkBoardSeq").toString());
        } catch (NumberFormatException e) {
            vo.setStatus("400");
            vo.setDesc("fail");
            return vo;
        }
        if(1 > boardSeq || lastSeq < boardSeq || 1 > content.length()) {
            vo.setStatus("400");
            vo.setDesc("fail");
            return vo;
        }

        CommentEntity entity = new CommentEntity();
        entity.setSContent(content);
        entity.setNReply(reply);
        entity.setFkBoardSeq(boardSeq);
        entity.setFkUserSeq(authenticationFacade.getLoginUserPk());
        return commentService.insReply(entity);
    }

    /**
     * 댓글 수정
     */
    @PutMapping
    public BoardResultVo authUpdComment(@RequestBody Map<String, Object> modData) {
        BoardResultVo bv = new BoardResultVo();

        String content = "";
        int userSeq = 0;
        int commentSeq = 0;

        /**
         * 유효성 검증
         */
        int lastSeq = commentService.selCommentLastSeq();
        try {
            content = modData.get("sContent").toString();
            userSeq = Integer.parseInt(modData.get("fkUserSeq").toString());
            commentSeq = Integer.parseInt(modData.get("nCommentSeq").toString());
        } catch (NumberFormatException e) {
            bv.setStatus("400");
            bv.setDesc("fail");
            return bv;
        }
        if (0 == userSeq || 1 > commentSeq || lastSeq < commentSeq || 0 == content.length()) {
            bv.setStatus("400");
            bv.setDesc("fail");
            return bv;
        }

        CommentEntity entity = new CommentEntity();
        entity.setSContent(content);
        entity.setFkUserSeq(userSeq);
        entity.setNCommentSeq(commentSeq);

        bv.setResult(commentService.updComment(entity));

        return bv;
    }

    /**
     * 댓글 전체 페이지
     */
    @GetMapping("/page/total")
    public BoardResultVo getTotalPage(@RequestParam(required = true) int fkBoardSeq, @RequestParam(required = true) int page) {
        /**
         * 유효성 검증
         */
        int lastSeq = boardService.selBoardLastSeq();
        if (1 > fkBoardSeq || lastSeq < fkBoardSeq) {
            BoardResultVo vo = new BoardResultVo();
            vo.setStatus("400");
            vo.setDesc("fail");
            return vo;
        }

        CommentPageable pageable = new CommentPageable();
        pageable.setFkBoardSeq(fkBoardSeq);
        pageable.setPage(page);
        return commentService.selTotalPage(pageable);
    }

    /**
     * 댓글 삭제 / 대댓글 전체 삭제
     * @param nCommentSeq
     * @return
     */
    @DeleteMapping
    public BoardResultVo authDelComment(@RequestParam(required = true) int nCommentSeq,
                                        @RequestParam int fkBoardSeq) {
        BoardResultVo vo = new BoardResultVo();

        /**
         * 마지막 댓글 번호
         */
        int cmtLastSeq = commentService.selCommentLastSeq();

        /**
         * 마지막 게시글 번호
         */
        int boardLastSeq = boardService.selBoardLastSeq();
        
        /**
         * 유효성 검증
         */
        if (1 > nCommentSeq || cmtLastSeq < nCommentSeq || 1 > fkBoardSeq || boardLastSeq < fkBoardSeq) {
            vo.setStatus("400");
            vo.setDesc("fail");
            return vo;
        }

        CommentEntity entity = new CommentEntity();
        entity.setNCommentSeq(nCommentSeq);
        entity.setFkBoardSeq(fkBoardSeq);
        entity.setFkUserSeq(authenticationFacade.getLoginUserPk());
        
        vo = commentService.delComment(entity);

        return vo;
    }

    /**
     * 대댓글 삭제
     * @param nCommentSeq
     * @return
     */
    @DeleteMapping("/reply")
    public BoardResultVo authDelReply(@RequestParam int nCommentSeq) {
        BoardResultVo vo = new BoardResultVo();

        /**
         * 마지막 댓글 번호
         */
        int cmtLastSeq = commentService.selCommentLastSeq();

        /**
         * 1 <= 댓글번호 <= 마지막 댓글 번호일 때만 통과
         */
        if (1 > nCommentSeq || cmtLastSeq < nCommentSeq) {
            vo.setStatus("400");
            vo.setDesc("fail");
            return vo;
        }

        CommentEntity entity = new CommentEntity();
        entity.setNCommentSeq(nCommentSeq);
        entity.setFkUserSeq(authenticationFacade.getLoginUserPk());

        vo = commentService.delReply(entity);

        return vo;
    }
}
