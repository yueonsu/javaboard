package com.yueonsu.www.user.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserEntity {
    private int nUserSeq;
    private String sId;
    private String sPassword;
    private String sName;
    private String sEmail;
    private Timestamp dtCreateDate;
}
