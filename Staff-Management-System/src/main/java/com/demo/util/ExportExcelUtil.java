package com.demo.util;

/**
 * @author lihongjie
 * @date 2022/4/1
 */

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xslf.model.geom.IfElseExpression;
import org.springframework.stereotype.Component;

import javax.swing.text.Style;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 导出excel通用工具类
 * @param <T>
 */
@Component
public class ExportExcelUtil<T> {
    public void exportExcel(String title, String[] headers,
                            Collection<T> dataSet, OutputStream out, String pattern){
        // 声明一个工作薄
        HSSFWorkbook workBook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workBook.createSheet(title);
        // 设置表格默认队列宽度为15个字节
        sheet.setDefaultColumnWidth(15);
        // 生成一个样式
        HSSFCellStyle cellStyle = workBook.createCellStyle();
        //设置样式
        cellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workBook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体要用到当前的样式
        cellStyle.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workBook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workBook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);
        // 声明一个画图的顶级管理器
        HSSFPatriarch drawingPatriarch = sheet.createDrawingPatriarch();
        HSSFComment comment = drawingPatriarch.createComment(new HSSFClientAnchor(
                0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        /*comment.setString(new HSSFRichTextString("你好，我是POI注释！"));
        // 设置注释作者，当鼠标移动到单元格上时可以在状态栏中看到该内容
        comment.setAuthor("lihongjie");*/
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            HSSFRichTextString textValue = new HSSFRichTextString(headers[i]);
            cell.setCellValue(textValue);
        }

        // 遍历集合，产生数据行
        Iterator<T> iterator = dataSet.iterator();
        int index = 0;
        while (iterator.hasNext()){
            index ++;
            row = sheet.createRow(index);
            T val = iterator.next();
            Class valCls = val.getClass();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] declaredFields = valCls.getDeclaredFields();
            for (int i = 0; i < declaredFields.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style2);
                Field field = declaredFields[i];
                String fieldName = field.getName();
                String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try {
                    Method method = valCls.getDeclaredMethod(methodName, new Class[]{});
                    Object value = method.invoke(val, new Object[]{});
                    String textValue = null;
                    if(value == null){
                        textValue = "";
                    } else if(value instanceof Date){
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        textValue = simpleDateFormat.format(value);
                    } else {
                        // 其他数据类型都当作字符串简单处理
                        textValue = value.toString();
                    }
                    Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                    Matcher matcher = p.matcher(textValue);
                    if(matcher.matches()){
                        // 如果是数字，就当作double处理
                        cell.setCellValue(Double.parseDouble(textValue));
                    } else {
                        HSSFRichTextString richString = new HSSFRichTextString(
                                textValue);
                        HSSFFont font3 = workBook.createFont();
                        font3.setColor(HSSFColor.BLUE.index);
                        richString.applyFont(font3);
                        cell.setCellValue(richString);
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            // 输出excel
            workBook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
