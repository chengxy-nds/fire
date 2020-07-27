package com.fire.service.service;

import com.fire.common.domain.BaiDuFaceDetectResult;
import com.fire.common.domain.BaiDuFaceSearchResult;

/**
 * @Author: xinzhifu
 * @Description:
 */
public interface BaiDuFaceApiService {

    /**
     * @author xiaofu
     * @description 获取API访问token ,该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @date 2020/7/25 18:07
     */
    String getAccessToken();

    /**
     * @param file 图片文件流
     * @author xiaofu
     * @description 查找人脸
     * @date 2020/7/26 16:37
     */
    BaiDuFaceSearchResult faceSearch(String file);

    /**
     * @param file
     * @author xiaofu
     * @description 人脸识别
     * @date 2020/7/25 19:57
     */
    BaiDuFaceDetectResult faceDetect(String file);

    /**
     * @param file
     * @author xiaofu
     * @description 增加人脸图像到百度
     * @date 2020/7/27 11:13
     */
    String addFace(String file);
}
