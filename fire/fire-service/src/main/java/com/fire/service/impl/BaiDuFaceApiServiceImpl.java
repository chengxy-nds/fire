package com.fire.service.impl;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fire.common.config.BaiDuProperties;
import com.fire.common.config.GitHubProperties;
import com.fire.common.domain.*;
import com.fire.common.util.Base64Util;
import com.fire.common.util.GsonUtils;
import com.fire.common.util.HttpUtil;
import com.fire.service.service.BaiDuFaceApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiaofu
 * @Description: 百度人脸识别api
 */
@Slf4j
@Component
public class BaiDuFaceApiServiceImpl implements BaiDuFaceApiService {

    @Autowired
    private BaiDuProperties baiduProperties;

    @Autowired
    private GitHubProperties gitHubProperties;

    @Override
    public String getAccessToken() {

        try {
            StringBuilder sb = new StringBuilder(baiduProperties.getAccessTokenUrl());
            sb.append("grant_type=client_credentials")
                    .append("&client_id=")
                    .append(baiduProperties.getClientId())
                    .append("&client_secret=")
                    .append(baiduProperties.getClientSecret());
            String generalUrl = HttpUtil.getGeneralUrl(sb.toString());

            BaiDuTokenResult parse = JSONObject.parseObject(generalUrl, BaiDuTokenResult.class);
            log.info("getAccessToken token: {}", JSON.toJSONString(parse));
            return parse.getAccess_token();
        } catch (Exception e) {
            log.error("get baidu token error {}", e.getStackTrace());
        }
        return null;
    }

    @Override
    public BaiDuFaceSearchResult faceSearch(String file) {

        try {
            byte[] decode = Base64.decode(Base64Util.base64Process(file));
            String faceFile = Base64Util.encode(decode);

            Map<String, Object> map = new HashMap<>();
            map.put("image", file);
            map.put("liveness_control", "NORMAL");
            map.put("group_id_list", "group_repeat,group_233");
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");
            String param = GsonUtils.toJson(map);

            String result = HttpUtil.post(baiduProperties.getFaceSearchUrl(), this.getAccessToken(), "application/json", param);
            BaiDuFaceSearchResult searchResult = JSONObject.parseObject(result, BaiDuFaceSearchResult.class);
            log.info(" faceSearch: {}", JSON.toJSONString(searchResult));
            return searchResult;
        } catch (Exception e) {
            log.error("get faceSearch error {}", e.getStackTrace());
        }
        return null;
    }

    @Override
    public BaiDuFaceDetectResult faceDetect(String file) {

        try {
            byte[] decode = Base64.decode(Base64Util.base64Process(file));
            String faceFile = Base64Util.encode(decode);

            Map<String, Object> map = new HashMap<>();
            map.put("image", faceFile);
            map.put("face_field", "faceshape,facetype");
            map.put("image_type", "BASE64");
            String param = GsonUtils.toJson(map);

            String result = HttpUtil.post(baiduProperties.getFaceDetectUrl(), this.getAccessToken(), "application/json", param);
            BaiDuFaceDetectResult detectResult = JSONObject.parseObject(result, BaiDuFaceDetectResult.class);
            log.info(" detectResult: {}", JSON.toJSONString(detectResult));
            return detectResult;
        } catch (Exception e) {
            log.error("get faceDetect error {}", e.getStackTrace());
        }
        return null;
    }

    @Override
    public BaiDuFaceAddResult addFace(String file, UserFaceInfo userFaceInfo) {

        try {
            byte[] decode = Base64.decode(Base64Util.base64Process(file));
            String faceFile = Base64Util.encode(decode);

            Map<String, Object> map = new HashMap<>();
            map.put("image", faceFile);
            map.put("group_id", "user");
            map.put("user_id", userFaceInfo.getUserId());
            map.put("user_info", JSON.toJSONString(userFaceInfo));
            map.put("liveness_control", "NORMAL");
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");
            String param = GsonUtils.toJson(map);

            String result = HttpUtil.post(baiduProperties.getAddfaceUrl(), this.getAccessToken(), "application/json", param);
            BaiDuFaceAddResult addResult = JSONObject.parseObject(result, BaiDuFaceAddResult.class);
            log.info("addResult: {}", JSON.toJSONString(addResult));
            return addResult;
        } catch (Exception e) {
            log.error("get addFace error {}", e.getStackTrace());
            e.getStackTrace();
        }
        return null;
    }
}
