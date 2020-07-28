package com.fire.common.enums;

/**
 * @author xiaofu
 * @description 状态码
 * @date 2020/7/27 14:11
 */
public enum HttpCodeEnum {

    SUCCESS(200, "SUCCESS"),
    NOT_FOUND_FACE(9999, "未检测到人脸"),
    UNKNOWN(1, "未知错误");

    private Integer code;
    private String description;

    HttpCodeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static HttpCodeEnum getDescriptionByCode(Integer code) {
        for (HttpCodeEnum errorCodeEnum : HttpCodeEnum.values()) {
            if (code.equals(errorCodeEnum.getCode())) {
                return errorCodeEnum;
            }
        }
        return HttpCodeEnum.UNKNOWN;
    }

}
