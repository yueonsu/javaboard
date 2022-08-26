package com.yueonsu.www.board.comment;

import com.yueonsu.www.auth.AuthenticationFacade;
import com.yueonsu.www.board.comment.model.CommentEntity;
import com.yueonsu.www.board.comment.model.CommentPageable;
import com.yueonsu.www.board.comment.model.CommentVo;
import com.yueonsu.www.board.model.BoardResultVo;
import com.yueonsu.www.board.model.BoardVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;
    private final AuthenticationFacade authenticationFacade;

    /**
     * 댓글 등록
     * @param entity
     * @return
     */
    public BoardResultVo authInsComment(CommentEntity entity) {
        BoardResultVo vo = new BoardResultVo();
        int result = 0;
        try {
            result = commentMapper.insComment(entity);
        } catch (Exception ignored) {
            vo.setStatus("400");
            vo.setDesc("fail");
        }
        vo.setResult(result);

        return vo;
    }

    /**
     * 댓글 리스트
     * @param entity
     * @return
     */
    public BoardResultVo selCommentList(CommentPageable pageable) {
        BoardResultVo vo = new BoardResultVo();
        List<CommentVo> list = null;
        try {
            list = commentMapper.selCommentList(pageable);
        } catch (Exception ignored) {
            vo.setStatus("400");
            vo.setDesc("fail");
        }
        vo.setResult(list);
        vo.setLoginUserPk(authenticationFacade.getLoginUserPk());

        return vo;
    }

    /**
     * 대댓글 등록
     * @param entity
     * @return
     */
    public BoardResultVo insReply(CommentEntity entity) {
        BoardResultVo vo = new BoardResultVo();
        int result = 0;
        try {
            result = commentMapper.insReply(entity);
        } catch (Exception ignored) {
            vo.setStatus("400");
            vo.setDesc("fail");
        }

        CommentVo selReply = commentMapper.selReply(entity);
        vo.setResult((1 == result) ? selReply : 0);

        return vo;
    }

    /**
     * 대댓글 리스트
     * @param nCommentSeq
     * @return
     */
    public BoardResultVo selReplyList(int nCommentSeq) {
        BoardResultVo vo = new BoardResultVo();
        List<CommentVo> list = null;
        try {
            list = commentMapper.selReplyList(nCommentSeq);
        } catch (Exception ignored) {
            vo.setStatus("400");
            vo.setDesc("fail");
        }
        vo.setResult(list);

        return vo;
    }

    /**
     * 댓글 / 대댓글 수정
     * @param entity
     * @return
     */
    public BoardResultVo updComment(CommentEntity entity) {
        BoardResultVo vo = new BoardResultVo();
        int result = 0;
        try {
            result = commentMapper.updComment(entity);
        } catch (Exception ignored) {
            vo.setStatus("400");
            vo.setDesc("fail");
        }
        vo.setResult(result);

        return vo;
    }

    public BoardResultVo selTotalPage(CommentPageable pageable) {
        BoardResultVo vo = new BoardResultVo();
        CommentPageable dbPageable = new CommentPageable();
        dbPageable.setPage(pageable.getPage());
        try {
            dbPageable = commentMapper.selTotalPage(pageable);
        } catch (Exception ignored) {
            vo.setStatus("400");
            vo.setDesc("fail");
            dbPageable = null;
        }
        assert dbPageable != null;
        dbPageable.setPage(pageable.getPage());
        vo.setResult(dbPageable);

        return vo;
    }
}
