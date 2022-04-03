package com.demo.pojo;

import lombok.Data;

/**
 * 员工工资信息javabean
 * @author lihongjie
 * @date 2022/3/29
 */
@Data
public class StaffWage {

    /**
     * 员工工资表主键
     */
    private Integer wageId;

    /**
     * 员工信息表主键
     */
    private Integer uid;

    /**
     * 每月应发工资
     */
    private Double wage;

    /**
     * 员工工资应发月份
     */
    private Integer month;

    /**
     * 员工工资年份
     */
    private Integer year;
}
