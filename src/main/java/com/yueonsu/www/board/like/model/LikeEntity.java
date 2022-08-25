package com.yueonsu.www.board.like.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class LikeEntity {
    private int fkUserSeq;
    private int fkBoardSeq;
    private Timestamp dtCreateDate;
}
