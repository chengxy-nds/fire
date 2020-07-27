package com.fire.common.domain;

import lombok.Data;

/**
 * @Author: xiaofu
 * @Description:
 */
@Data
public class BaiDuTokenResult {

    private String refresh_token;

    private int expires_in;

    private String session_key;

    private String access_token;

    private String scope;

    private String session_secret;
}
