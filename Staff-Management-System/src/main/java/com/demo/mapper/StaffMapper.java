package com.demo.mapper;

import com.demo.pojo.Staff;
import com.demo.pojo.StaffVO;

import java.util.List;

/**
 * @author lihongjie
 * @date 2022/3/29
 */
public interface StaffMapper {
    /**
     * 查询员工信息
     * @param staffVO 查询参数VO
     * @return  List<Staff> 员工信息
     */
    public List<Staff> selStaff(StaffVO staffVO);

    /**
     * 通过uid查询员工信息
     * @param staffVO 查询参数VO
     * @return  List<Staff> 员工信息
     */
    public List<Staff> selStaffByUid(StaffVO staffVO);

    /**
     * 查询身份证号员工信息
     * @param staffVO 查询参数VO
     * @return  List<Staff> 员工信息
     */
    public List<Staff> selStaffByIdCard(StaffVO staffVO);

    /**
     * 保存员工信息
     * @param staffVOs 员工信息集合
     * @return 保存结果
     */
    public int insStaff(StaffVO staffVOs);

    /**
     * 更新员工信息
     * @param staffVO 查询参数VO
     * @return  更新结果
     */
    public int updStaff(StaffVO staffVO);

    /**
     * 删除员工信息
     * @param staffVO 查询参数VO
     * @return  删除结果
     */
    public int delStaff(StaffVO staffVO);
}
