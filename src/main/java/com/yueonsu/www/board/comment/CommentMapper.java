package com.yueonsu.www.board.comment;

import com.yueonsu.www.board.comment.model.CommentEntity;
import com.yueonsu.www.board.comment.model.CommentPageable;
import com.yueonsu.www.board.comment.model.CommentVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    int insComment(CommentEntity entity);
    int insReply(CommentEntity entity);
    List<CommentVo> selCommentList(CommentPageable pageable);
    List<CommentVo> selReplyList(int nCommentSeq);
    CommentVo selReply(CommentEntity entity);
    int updComment(CommentEntity entity);
    CommentPageable selTotalPage(CommentEntity entity);
}
