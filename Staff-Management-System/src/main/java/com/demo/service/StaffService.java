package com.demo.service;

import com.demo.pojo.StaffVO;
import com.demo.util.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lihongjie
 * @date 2022/3/29
 */
public interface StaffService {
    /**
     * 查询员工信息
     * @param staffVO 查询参数VO
     * @return  List<Staff> 员工信息
     */
    public Result selStaff(StaffVO staffVO);

    /**
     * 通过uid查询员工信息
     * @param staffVO 查询参数VO
     * @return  List<Staff> 员工信息
     */
    public Result selStaffByUid(StaffVO staffVO);

    /**
     * 删除员工信息
     * @param staffVO 查询参数VO
     * @return  删除结果
     */
    public Result delStaff(StaffVO staffVO);

    /**
     * 保存或更新员工信息
     * @param paramVO 员工信息
     * @return 保存或更新记录数
     */
    Result saveOrUpdStuff(StaffVO paramVO);

    /**
     * 导出excel方法
     * @param request request
     * @param response response
     * @param paramVO
     */
    void exportExcel(HttpServletRequest request, HttpServletResponse response, StaffVO paramVO);
}
