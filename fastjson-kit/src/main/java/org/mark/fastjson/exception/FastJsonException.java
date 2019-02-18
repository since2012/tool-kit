package org.mark.fastjson.exception;

import lombok.Data;

/**
 * 封装guns的异常
 *
 * @author fengshuonan
 * @Date 2017/12/28 下午10:32
 */
@Data
public class FastJsonException extends RuntimeException {

    private Integer code;
    private String message;

    public FastJsonException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
