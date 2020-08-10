package com.fire.controller;

import com.alibaba.fastjson.JSON;
import com.fire.common.base.Resp;
import com.fire.common.config.GitHubProperties;
import com.fire.common.domain.BaiDuFaceAddResult;
import com.fire.common.domain.BaiDuFaceDetectResult;
import com.fire.common.domain.BaiDuFaceSearchResult;
import com.fire.common.domain.UserFaceInfo;
import com.fire.common.enums.HttpCodeEnum;
import com.fire.common.pojo.UserInfo;
import com.fire.common.util.OkHttpClientUtil;
import com.fire.repository.mapper.FireUserMapper;
import com.fire.repository.model.FireUser;
import com.fire.service.service.BaiDuFaceApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.constraints.NotEmpty;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xinzhifu
 * @description
 * @date 2020/7/27 15:20
 */
@Slf4j
@Controller
public class LoginController {

    @Autowired
    private GitHubProperties gitHubProperties;

    @Autowired
    private BaiDuFaceApiService baiDuFaceApiService;

    @Autowired
    private FireUserMapper fireUserMapper;

    @RequestMapping("/face")
    public String face() {
        return "face";
    }

    @RequestMapping("/token")
    @ResponseBody
    public String token() {

        return baiDuFaceApiService.getAccessToken();
    }

    /**
     * @param file
     * @author xiaofu
     * @description 对前端传送的图片进行识别
     * @date 2020/7/24 15:47
     */
    @RequestMapping(value = "/faceDiscern", method = RequestMethod.POST)
    @ResponseBody
    public Resp faceSearch(@RequestParam("file") String file) throws Exception {

        String tip = "";

        BaiDuFaceDetectResult faceDetect = baiDuFaceApiService.faceDetect(file);

        /**
         * 检测是否识别到人脸
         */
        if (faceDetect.getError_code() != 0) {
            return Resp.error(HttpCodeEnum.NOT_FOUND_FACE.getCode(), HttpCodeEnum.NOT_FOUND_FACE.getDescription());
        }

        /**
         * 搜索百度人脸库是否存在,不存在则注册人脸
         * Face_token 为人脸图像的唯一识别表标识
         */
        BaiDuFaceSearchResult faceSearch = baiDuFaceApiService.faceSearch(file);

        if (faceSearch.getError_code() == 0) {
            FireUser fireUsers = fireUserMapper.selectByFaceToken(faceSearch.getResult().getFace_token());
            tip = "用户已存在";
        } else {

            /**
             * 注册新用户
             */
            FireUser fireUser = new FireUser();
            int userId = fireUserMapper.insert(fireUser);
            UserFaceInfo userFaceInfo = new UserFaceInfo();
            userFaceInfo.setUserId(userId);

            BaiDuFaceAddResult addResult = baiDuFaceApiService.addFace(file, userFaceInfo);
            if (addResult.getError_code() == 0
                    && addResult.getError_msg().equals(HttpCodeEnum.SUCCESS.getDescription())) {
                fireUser.setFaceToken(addResult.getResult().getFace_token());
                fireUserMapper.updateByPrimaryKey(fireUser);
            }
            tip = "新用户注册";
        }
        return Resp.ok(tip);
    }

    /**
     * @param code
     * @author xiaofu
     * @description 授权回调
     * @date 2020/7/10 15:42
     */
    @RequestMapping("/authorize/redirect")
    public ModelAndView authorize(@NotEmpty String code) {

        log.info("授权码code: {}", code);

        /**
         * 重新到前端主页
         */
        String redirectHome = "http://47.93.6.5/home";

        try {
            /**
             * 1、拼装获取accessToken url
             */
            String accessTokenUrl = gitHubProperties.getAccesstokenUrl()
                    .replace("clientId", gitHubProperties.getClientId())
                    .replace("clientSecret", gitHubProperties.getClientSecret())
                    .replace("authorize_code", code);

            /**
             * 返回结果中直接返回token
             */
            String result = OkHttpClientUtil.sendByGetUrl(accessTokenUrl);
            log.info(" 请求 token 结果：{}", result);

            String accessToken = null;
            Pattern p = Pattern.compile("=(\\w+)&");
            Matcher m = p.matcher(result);
            while (m.find()) {
                accessToken = m.group(1);
                log.info("令牌token：{}", m.group(1));
                break;
            }

            /**
             * 成功获取token后，开始请求用户信息
             */
            String userInfoUrl = gitHubProperties.getUserUrl().replace("accessToken", accessToken);

            String userResult = OkHttpClientUtil.sendByGetUrl(userInfoUrl);

            log.info("用户信息：{}", userResult);

            UserInfo userInfo = JSON.parseObject(userResult, UserInfo.class);

            redirectHome += "?name=" + userInfo.getName();

        } catch (Exception e) {
            log.error("授权回调异常={}", e);
        }
        return new ModelAndView(new RedirectView(redirectHome));
    }
}
