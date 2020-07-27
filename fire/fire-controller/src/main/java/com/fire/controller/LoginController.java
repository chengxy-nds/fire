package com.fire.controller;

import com.alibaba.fastjson.JSON;
import com.fire.common.base.Resp;
import com.fire.common.config.GitHubProperties;
import com.fire.common.domain.BaiDuFaceDetectResult;
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

    /**
     * @author xiaofu
     * @description 跳转人脸登录页
     * @date 2020/7/24 15:32
     */
    @RequestMapping("/face")
    public String face() {
        return "face";
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

        BaiDuFaceDetectResult baiDuFaceDetectResult = baiDuFaceApiService.faceDetect(file);
        FireUser fireUser = new FireUser();
        fireUser.setUserName("11");
        fireUserMapper.insert(fireUser);

        return Resp.ok(baiDuFaceDetectResult);
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
