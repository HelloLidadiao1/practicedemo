package com.demo.service.impl;

import com.demo.mapper.StaffMapper;
import com.demo.mapper.StaffWageMapper;
import com.demo.pojo.Staff;
import com.demo.pojo.StaffVO;
import com.demo.pojo.StaffWage;
import com.demo.pojo.StaffWageVO;
import com.demo.service.StaffService;
import com.demo.util.ExportExcelUtil;
import com.demo.util.Result;
import com.demo.util.StaffTransfer;
import com.demo.util.StringUtil;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lihongjie
 * @date 2022/3/29
 */
@Service("staffService")
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private StaffWageMapper staffWageMapper;

    @Autowired
    private StaffTransfer staffTransfer;

    @Autowired
    private ExportExcelUtil<T> exportExcelUtil;

    @Override
    public Result selStaff(StaffVO paramVO) {
        List<Staff> staffs = staffMapper.selStaff(paramVO);
        /*List<StaffVO> staffVOS = staffTransfer.vo2Staffs(staffMapper.selStaff(staffVO));*/
        List<StaffVO> results = new ArrayList<>();
        staffs.forEach(tempStaff->{
            // 获取工人的工资集合
            List<StaffWage> staffWages = tempStaff.getStaffWages();
            //按年份进行分组
            if(StringUtil.collectionIsNotEmpty(staffWages)){
                Map<Integer, List<StaffWage>> year2StaffWageMap = staffWages.stream().collect(
                        Collectors.groupingBy(StaffWage::getYear));
                //如果该员工存在工资，则需要按照年份分组统计
                year2StaffWageMap.forEach((year, groupStaffs)->{
                    StaffVO staffVO = staffTransfer.staff2VO(tempStaff);
                    //求该年份工资总合
                    Double wages = groupStaffs.stream().map(StaffWage::getWage).reduce(Double::sum).orElse(0D);
                    String monthsJoin = groupStaffs.stream().map(temp -> {
                        return temp.getMonth() + "";
                    }).collect(Collectors.joining(","));
                    staffVO.setYear(year);
                    staffVO.setWages(wages);
                    staffVO.setMonths(monthsJoin);
                    results.add(staffVO);
                });
            } else {
                //如果该员工不存在工资
                StaffVO staffVO = staffTransfer.staff2VO(tempStaff);
                results.add(staffVO);
            }

        });
        return Result.success(results);
    }

    @Override
    public Result selStaffByUid(StaffVO staffVO) {
        List<Staff> staffs = staffMapper.selStaffByUid(staffVO);
        return Result.success(staffs);
    }

    @Override
    public Result delStaff(StaffVO staffVO) {
        Integer resNum = staffMapper.delStaff(staffVO);
        Integer delStaffWageNum = staffWageMapper.delStaffWage(staffVO.getUid());
        return Result.success(resNum);
    }

    @Override
    public Result saveOrUpdStuff(StaffVO paramVO) {
        //保存更新员工，在这里有两种方式：1、直接先删除后插入  2、先查询员工信息是否存在，然后根据查询结果，判断是更新还是保存
        //这里采取第二种方式
        // 如果走的是保存，则通过身份证号查询员工信息【保存时获取不到uid】；如果执行的是更新逻辑，则使用uid来查询用户信息【为的是让员工可以更新身份证号信息】
        List<Staff> staff = paramVO.getUid() == null ?
                staffMapper.selStaffByIdCard(paramVO): staffMapper.selStaffByUid(paramVO);
        Integer resNum = null;
        if(StringUtil.collectionIsNotEmpty(staff)){
            paramVO.setUid(staff.get(0).getUid());
            //若存在，则更新
            resNum = staffMapper.updStaff(paramVO);
        } else {
            //否则执行新增
            resNum = staffMapper.insStaff(paramVO);
        }
        String months = paramVO.getMonths();
        Integer year = paramVO.getYear();
        Double wages = paramVO.getWages();
        /* 用来测试事务，业务报错是否会回滚，要注意：事务也是基于切面的，切面是有执行顺序的（优先级越高，越先进去，越晚出来），
        如果让别的切面先把异常给捕获处理了，事务的切面就没办法监听异常并发生回滚了
        if(year > 1){
            throw new RuntimeException("李洪杰测试事务！");
        }*/
        //如果工资信息不为空，则插入工资表
        if(!StringUtil.isEmpty(months)
                && year != null && wages != null){
            staffWageMapper.insStaff(
                    new StaffWageVO(null, paramVO.getUid(), wages, Integer.parseInt(months), year));
        }
        return Result.success(resNum);
    }

    @Override
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, StaffVO paramVO) {
        Result result = this.selStaff(paramVO);
        List staffVOS = (List<StaffVO>)result.getData();
        // 创建文件目录
        File staffDir = new File("E:\\员工信息管理系统excel存放路径");
        if(!staffDir.exists()){
            staffDir.mkdir();
        }
        Date now = new Date();
        String pattern = "yyyy-MM-DD_HHmmss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String formatDate = simpleDateFormat.format(now);
        OutputStream fileOutputStream = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            String fileName = formatDate + "员工信息" + ".xlsx";
            File staffFile = new File("E:\\员工信息管理系统excel存放路径\\" + fileName );
            if(!staffFile.exists()){
                staffFile.createNewFile();
            }
            fileOutputStream = new FileOutputStream(staffFile);
            exportExcelUtil.exportExcel(fileName,new String[]{"序号", "姓名", "身份证号码", "员工工种",
                    "银行账号", "开户银行", "电话号码", "月份（合集）", "年份", "工资总和"}, staffVOS, fileOutputStream, pattern);
            // 读取已经处理输出的excel
            bis = new BufferedInputStream(new FileInputStream(staffFile));
            byte[] buffer = new byte[bis.available()];
            bis.read(buffer);
            // 清空response
            response.reset();
            // 设置response的Header
            //response.setCharacterEncoding("utf-8");
            // new String(as.getBytes("GB2312"), "ISO_8859_1"); 解决下载文件名乱码问题
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GB2312"), "ISO_8859_1"));
            response.addHeader("Content-Length", "" + staffFile.length());
            response.setContentType("application/vnd.ms-excel;charset=gb2312");
            bos = new BufferedOutputStream(response.getOutputStream());
            bos.write(buffer);
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
