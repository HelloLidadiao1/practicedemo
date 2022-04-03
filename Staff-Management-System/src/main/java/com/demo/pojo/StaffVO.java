package com.demo.pojo;

import lombok.Data;

/**
 * 员工信息javabean
 * @author lihongjie
 * @date 2022/3/29
 */
@Data
public class StaffVO {

    /**
     * 员工主键
     */
    private Integer uid;

    /**
     * 员工姓名
     */
    private String uname;

    /**
     * 员工身份证号码
     */
    private String idCard;

    /**
     * 员工工种
     */
    private String job;

    /**
     * 员工银行账号
     */
    private String bankAccount;

    /**
     * 员工开户银行
     */
    private String bankInfo;

    /**
     * 员工电话号码
     */
    private String telephoneNum;

    /**
     * 展示当前年份的所有月份，若有多个则用,分隔
     */
    private String months;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 当前年份工资总和
     */
    private Double wages;

}
