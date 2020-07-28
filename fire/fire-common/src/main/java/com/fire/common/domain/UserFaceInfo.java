package com.fire.common.domain;


import lombok.Data;

import java.util.Date;

@Data
public class UserFaceInfo {

    private Integer userId;

    private String faceToken;

    private String userName;

    private String email;

    private Short gender;

    private String phoneNumber;

    private Date createTime;

    private Date updateTime;

}

