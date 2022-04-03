package com.demo.util;

/**
 * 自定义员工信息异常
 * @author lihongjie
 * @date 2022/3/29
 */
public class MyStaffException extends Exception{
    public MyStaffException() {

    }

    public MyStaffException(String message) {
        super("员工信息异常：" + message);
    }
}
