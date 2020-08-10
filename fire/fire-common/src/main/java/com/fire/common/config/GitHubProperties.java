package com.fire.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "github")
public class GitHubProperties {

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 客户端秘钥
     */
    private String clientSecret;

    /**
     * github回调我们项目的url
     */
    private String redirectUri;

    /**
     * 授权获取授权码url
     */
    private String authorizeUrl;

    /**
     * 授权获取token url
     */
    private String accesstokenUrl;

    /**
     * 获取用户信息 url
     */
    private String userUrl;
}
