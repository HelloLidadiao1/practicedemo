package com.demo.controller;

import com.alibaba.fastjson.JSON;
import com.demo.pojo.Staff;
import com.demo.pojo.StaffVO;
import com.demo.pojo.User;
import com.demo.service.StaffService;
import com.demo.util.ExportExcelUtil;
import com.demo.util.MyStaffException;
import com.demo.util.Result;
import com.demo.util.StringUtil;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author lihongjie
 * @date 2022/3/29
 */
public class StaffServlet extends HttpServlet {

    private WebApplicationContext webApplicationContext;

    private StaffService staffService;
    @Override
    public void init() throws ServletException {
        //servlet 执行初始化方法的时候，获取SpringIOC容器，并且实例化staffService
        webApplicationContext = WebApplicationContextUtils.
                getWebApplicationContext(this.getServletContext());
        staffService = webApplicationContext.getBean("staffService", StaffService.class);
       /* exportExcelUtil = webApplicationContext.getBean("exportExcelUtil", ExportExcelUtil.class);*/
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String flag = request.getParameter("flag");
        flag = StringUtil.isEmpty(flag) ? "" : flag;
        StaffVO paramVO = parseParamFromReq(request);
        switch (flag){
            case "selStaff":
                this.selStaff(request, response, paramVO);
                break;
            case "selStaffByUid":
                this.selStaffByUid(request, response, paramVO);
                break;
            case "saveOrUpdStuff":
                this.saveOrUpdStuff(request, response, paramVO);
                break;
            case "delStaff":
                this.delStaff(request, response, paramVO);
                break;
            default:
                this.exportExcel(request, response, paramVO);
        }
    }

    /**
     * 通过uid查询员工信息
     * @param request
     * @param response
     * @param paramVO
     */
    private void selStaffByUid(HttpServletRequest request, HttpServletResponse response, StaffVO paramVO) throws IOException {
        Result result = staffService.selStaffByUid(paramVO);
        returnResFromResp(response, result);
    }

    /**
     * 下载导出excel
     * @param request
     * @param response
     * @param paramVO
     */
    private void exportExcel(HttpServletRequest request, HttpServletResponse response, StaffVO paramVO) throws IOException {
        // 导出excel
        staffService.exportExcel(request, response, paramVO);
    }

    /**
     * 删除员工信息
     * @param request
     * @param response
     * @param paramVO
     */
    private void delStaff(HttpServletRequest request, HttpServletResponse response, StaffVO paramVO) throws IOException {
        Result result = staffService.delStaff(paramVO);
        returnResFromResp(response, result);
    }

    /**
     * 保存更新员工信息
     * @param request
     * @param response
     * @param paramVO
     */
    private void saveOrUpdStuff(HttpServletRequest request, HttpServletResponse response, StaffVO paramVO) throws IOException {
        Result result = staffService.saveOrUpdStuff(paramVO);
        returnResFromResp(response, result);
    }

    /**
     * 调用业务层方法，查询员工信息并进行跳转
     * @param request
     * @param response
     * @param paramVO
     */
    private void selStaff(HttpServletRequest request, HttpServletResponse response, StaffVO paramVO) throws IOException {
        Result result = staffService.selStaff(paramVO);
        returnResFromResp(response, result);
    }

    /**
     * 以json的格式把数据写回前端
     * @param response 响应
     * @param data
     */
    private void returnResFromResp(HttpServletResponse response, Object data) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        String resultStr = JSON.toJSONString(data instanceof Result ? data: Result.success(data));
        writer.write(resultStr);
    }

    /**
     * 从request中获取解析参数
     * @param request
     * @return 前端传过来的参数
     */
    private StaffVO parseParamFromReq(HttpServletRequest request) throws IOException {
        StringBuffer paramVOSb = new StringBuffer();
        try {
            BufferedReader reader = request.getReader();
            String str = null;
            while ((str = reader.readLine()) != null){
                paramVOSb.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("从请求中获取StaffVO参数失败....");
        }
        String paramVOStr = paramVOSb.toString();
        return !StringUtil.isEmpty(paramVOStr) ? JSON.parseObject(paramVOStr,
                StaffVO.class): null;
    }


}
