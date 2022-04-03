package com.demo.mapper;

import com.demo.pojo.StaffVO;
import com.demo.pojo.StaffWageVO;

/**
 * @author lihongjie
 * @date 2022/3/31
 */
public interface StaffWageMapper {
    /**
     * 删除员工工资
     * @param uid 对应员工信息uid
     * @return  删除结果
     */
    public int delStaffWage(Integer uid);

    /**
     * 保存员工工资
     * @param paramVO 员工信息集合
     * @return 保存结果
     */
    public int insStaff(StaffWageVO paramVO);
}
