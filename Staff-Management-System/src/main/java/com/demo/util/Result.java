package com.demo.util;

import lombok.*;
import org.junit.jupiter.api.Test;

/**
 * @author lihongjie
 * @date 2022/3/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    /*public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }*/

    public static Result success(){
        return new Result(ResultCode.Sucess.getCode(), ResultCode.Sucess.getMessage(), null);
    }

    public static <T> Result success(T data){
        return new Result(ResultCode.Sucess.getCode(), ResultCode.Sucess.getMessage(), data);
    }

    public static Result fail(){
        return new Result(ResultCode.Fail.getCode(), ResultCode.Fail.getMessage(), null);
    }

    public static <T> Result fail(String message, T data){
        return new Result(ResultCode.Fail.getCode(), message, data);
    }
}
