package com.demo.pojo;

import lombok.Data;

import java.util.List;

/**
 * 员工信息javabean
 * @author lihongjie
 * @date 2022/3/29
 */
@Data
public class Staff {

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
     * 员工工资
     */
    private List<StaffWage> staffWages;
}
