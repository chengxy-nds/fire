package com.fire.common.domain;


import lombok.Data;

import java.util.Date;

@Data
public class UserFaceInfo {

    private Integer id;

    private Integer groupId;

    private String faceId;

    private String name;

    private Integer age;

    private String email;

    private Short gender;

    private String phoneNumber;

    private Date createTime;

    private Date updateTime;

    private byte[] faceFeature;


}

