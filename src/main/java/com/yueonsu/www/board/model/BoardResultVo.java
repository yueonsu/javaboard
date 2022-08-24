package com.yueonsu.www.board.model;

import lombok.Data;

import java.util.List;

@Data
public class BoardResultVo {
    private String status;
    private String desc;
    private Object result;
}
