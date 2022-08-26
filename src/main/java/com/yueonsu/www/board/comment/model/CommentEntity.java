package com.yueonsu.www.board.comment.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentEntity {
    private int nCommentSeq;
    private int fkUserSeq;
    private int fkBoardSeq;
    private String sContent;
    private Timestamp dtCreateDate;
    private int nReply;
}
