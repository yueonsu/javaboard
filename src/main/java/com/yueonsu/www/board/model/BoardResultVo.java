package com.yueonsu.www.board.model;

import lombok.Data;

@Data
public class BoardResultVo {
    private String status = "200";
    private String desc = "success";
    private Object result;
    private int loginUserPk;
}
