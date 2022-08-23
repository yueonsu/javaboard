package com.yueonsu.www.board.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BoardEntity {
    private int nBoardSeq;
    private String sTitle;
    private String sContent;
    private int fkUserSeq;
    private int nLikeCount;
    private int nHitCount;
    private int nCommentCount;
    private Timestamp dtCreateDate;
}
