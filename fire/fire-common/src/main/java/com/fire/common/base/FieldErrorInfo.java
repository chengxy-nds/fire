package com.fire.common.base;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: xinzhifu
 * @Description:
 */
@Data
@AllArgsConstructor
public class FieldErrorInfo {
    /**
     * The Field name.
     */
    private String fieldName;
    /**
     * The Error message.
     */
    private String errorMessage;
}
