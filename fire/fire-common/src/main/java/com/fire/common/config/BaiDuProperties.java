package com.fire.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author xinzhifu
 * @description
 * @date    2020/7/27 15:06
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "baidu")
public class BaiDuProperties {

    /**
     * 客户端id	API Key
     */
    private String clientId;

    /**
     * 客户端秘钥 Secret Key
     */
    private String clientSecret;

    /**
     * 获取token url
     */
    private String accessTokenUrl;

    /**
     * 人脸搜索 url
     */
    private String faceSearchUrl;

    /**
     * 人脸搜索 url
     */
    private String faceDetectUrl;

    /**
     * 添加人像 url
     */
    private String addfaceUrl;
}
