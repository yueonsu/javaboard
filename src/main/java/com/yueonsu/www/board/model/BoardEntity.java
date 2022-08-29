package com.yueonsu.www.board.model;

import lombok.Data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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

    public String getDtCreateDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(this.dtCreateDate);
    }
}
