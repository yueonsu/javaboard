package com.yueonsu.www.board.comment.model;

import lombok.Data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Data
public class CommentEntity {
    private int nCommentSeq;
    private int fkUserSeq;
    private int fkBoardSeq;
    private String sContent;
    private Timestamp dtCreateDate;
    private int nReply;

    public String getDtCreateDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(this.dtCreateDate);
    }
}
