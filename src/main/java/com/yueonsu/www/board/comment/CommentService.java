package com.yueonsu.www.board.comment;

import com.yueonsu.www.auth.AuthenticationFacade;
import com.yueonsu.www.board.comment.model.CommentEntity;
import com.yueonsu.www.board.comment.model.CommentPageable;
import com.yueonsu.www.board.comment.model.CommentVo;
import com.yueonsu.www.board.model.BoardResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
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
        } catch (DuplicateKeyException ignored) {
            vo.setStatus("500");
            vo.setDesc("fail");
        }
        if (1 == result) {
            commentMapper.updCommentCount(entity);
        }

        vo.setResult(result);

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
        } catch (DuplicateKeyException ignored) {
            vo.setStatus("500");
            vo.setDesc("fail");
        }

        CommentVo selReply = commentMapper.selReply(entity);
        vo.setResult((1 == result) ? selReply : 0);

        return vo;
    }

    /**
     * 댓글 리스트
     * @param pageable
     */
    public BoardResultVo selCommentList(CommentPageable pageable) {
        BoardResultVo vo = new BoardResultVo();
        List<CommentVo> list = null;
        try {
            list = commentMapper.selCommentList(pageable);
        } catch (NullPointerException ignored) {
            vo.setStatus("500");
            vo.setDesc("fail");
        }

        if (null != list) {
            for (CommentVo item : list) {
                item.setSContent(item.getSContent().replace("<", "&lt;"));
            }
        }

        vo.setResult(list);
        vo.setLoginUserPk(authenticationFacade.getLoginUserPk());

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
        } catch (NullPointerException ignored) {
            vo.setStatus("500");
            vo.setDesc("fail");
        }

        /**
         * 댓글 리스트가 null이 아닐 때
         */
        if (null != list) {
            for (CommentVo item : list) {
                item.setSContent(item.getSContent().replace("<", "&lt;"));
            }
        }
        vo.setResult(list);

        return vo;
    }

    /**
     * 댓글 전체 페이지 수
     * @param pageable
     */
    public BoardResultVo selTotalPage(CommentPageable pageable) {
        BoardResultVo vo = new BoardResultVo();
        CommentPageable dbPageable = new CommentPageable();
        dbPageable.setPage(pageable.getPage());
        try {
            dbPageable = commentMapper.selTotalPage(pageable);
        } catch (NullPointerException ignored) {
            vo.setStatus("500");
            vo.setDesc("fail");
            dbPageable = null;
        }
        assert dbPageable != null;
        dbPageable.setPage(pageable.getPage());
        vo.setResult(dbPageable);

        return vo;
    }

    public int selCommentLastSeq() {
        return commentMapper.selCommentLastSeq().getNCommentSeq();
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
            vo.setStatus("500");
            vo.setDesc("fail");
        }
        vo.setResult(result);
        return vo;
    }

    /**
     * 댓글 삭제 ( 부모 댓글 )
     * @param entity
     * @return
     */
    public BoardResultVo delComment(CommentEntity entity) {
        BoardResultVo vo = new BoardResultVo();
        int result = 0;

        /**
         * 부모 댓글 삭제
         */
        try {
            result = commentMapper.delComment(entity);
        } catch (Exception e) {
            vo.setStatus("500");
            vo.setDesc("fail");
        }

        /**
         * 자식댓글 전체 삭제
         */
        if (1 == result){
            try {
                result = commentMapper.delReply(entity);
                
                /**
                 * 댓글, 대댓글 삭제 성공했을 때 댓글 개수 업데이트
                 */
                commentMapper.updCommentCount(entity);
            } catch (Exception e) {
                vo.setStatus("500");
                vo.setDesc("fail");
            }
        }

        vo.setResult(result);

        return vo;
    }

    /**
     * 대댓글 삭제
     * @param entity
     * @return
     */
    public BoardResultVo delReply(CommentEntity entity) {
        BoardResultVo vo = new BoardResultVo();
        int result = 0;

        try {
            result = commentMapper.delComment(entity);
        } catch (Exception e) {
            vo.setStatus("500");
            vo.setDesc("fail");
        }
        vo.setResult(result);

        return vo;
    }
}
