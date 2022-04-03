package com.demo.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

/**
 * 枚举-描述返回给前端的状态值
 * @author lihongjie
 * @date 2022/3/29
 */
@Getter
//枚举类上不可以使用lombok的@Setter注解，但是可以加set方法设置枚举类型的值，
// 但是一般不建议这么干，因为既然是枚举类型，就意味着是列举出来的是保存常量值对象，一般不进行修改
//@Setter
public enum ResultCode {
    Sucess(200, "操作成功"),
    Fail(500, "操作失败");
    private Integer code;
    private String message;

    private ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /*public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }*/

}
