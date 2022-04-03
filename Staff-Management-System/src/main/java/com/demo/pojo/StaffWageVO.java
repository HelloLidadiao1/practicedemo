package com.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 员工工资信息VO
 * @author lihongjie
 * @date 2022/3/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffWageVO {

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
