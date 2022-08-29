package com.yueonsu.www.board.comment;

import com.yueonsu.www.board.comment.model.CommentEntity;
import com.yueonsu.www.board.comment.model.CommentPageable;
import com.yueonsu.www.board.comment.model.CommentVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    /**
     * 댓글 등록
     * @param entity
     * @return
     */
    int insComment(CommentEntity entity);

    /**
     * 대댓글 등록
     * @param entity
     * @return
     */
    int insReply(CommentEntity entity);

    /**
     * 댓글 마지막 번호
     * @return
     */
    CommentVo selCommentLastSeq();

    /**
     * 댓글 리스트 출력
     * @param pageable
     * @return
     */
    List<CommentVo> selCommentList(CommentPageable pageable);

    /**
     * 대댓글 리스트 출력
     * @param nCommentSeq
     * @return
     */
    List<CommentVo> selReplyList(int nCommentSeq);

    /**
     * 대댓글 1개 출력
     * @param entity
     * @return
     */
    CommentVo selReply(CommentEntity entity);

    /**
     * 댓글 수정
     * @param entity
     * @return
     */
    int updComment(CommentEntity entity);

    /**
     * 댓글 전체 갯수
     * @param entity
     * @return
     */
    CommentPageable selTotalPage(CommentEntity entity);

    /**
     * 댓글 수 업데이트
     * @param entity
     */
    void updCommentCount(CommentEntity entity);

    /**
     * 댓글 삭제
     * @param entity
     * @return
     */
    int delComment(CommentEntity entity);

    /**
     * 대댓글 삭제
     * @param entity
     * @return
     */
    int delReply(CommentEntity entity);
}
