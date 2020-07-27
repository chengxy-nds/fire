package com.fire.common.pojo;

import lombok.Data;

/**
 * @author xiaofu
 * @description github用户信息实体类
 * @date 2020/7/13 11:22
 */
@Data
public class UserInfo {
    private String login;
    private int id;
    private String node_id;
    private String avatar_url;
    private String gravatar_id;
    private String url;
    private String html_url;
    private String followers_url;
    private String following_url;
    private String gists_url;
    private String starred_url;
    private String subscriptions_url;
    private String organizations_url;
    private String repos_url;
    private String events_url;
    private String received_events_url;
    private String type;
    private boolean site_admin;
    private String name;
    private Object company;
    private String blog;
    private Object location;
    private String email;
    private Object hireable;
    private Object bio;
    private Object twitter_username;
    private int public_repos;
    private int public_gists;
    private int followers;
    private int following;
    private String created_at;
    private String updated_at;
}
