package demo;

import com.demo.util.ResultCode;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author lihongjie
 * @date 2022/3/30
 */
public class TestDemo {
    @Test
    public void test1(){
        //ResultCode.Fail.setCode(123);
        System.out.println(ResultCode.Fail.getCode());
    }

    /**
     * 判断文件目录&文件是否存在，若不存在则创建文件和文件目录
     */
    @Test
    public void test2(){
        //使用输出流，创建一个不存在的新的文件【该方法有局限性，只能创建不存在的文件，但是不可以创建目录】
        /*File file = new File("D:\\lihongjie.txt");
        //new FileOutputStream("D:\\aaa\\lihongjie.xlsx");   若aaa文件夹不存在，会报错
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        try {
            File file = new File("D:\\aaa");
            if(!file.exists()){
                file.mkdir();
            }

            File file1 = new File("D:\\aaa\\lihongjie.xlsx");
            if(!file1.exists()){
                file1.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * 导出excel，理解简略版
    */
    @Test
    public void exportExcel(){
        FileInputStream fis = null;
        List<String> list = new ArrayList<>();
        list.add("张三");
        list.add("李四");
        list.add("王五");
        String[] headers = {"名字1","名字2","名字3"};
        try {
            //fis = new FileInputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet firstSheet = workbook.createSheet("第一个页签");
            //第一行是表头
            HSSFRow row = firstSheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(headers[i]);
            }
            //创建表头之外的值
            for (int i = 1; i <= list.size(); i++) {
                row = firstSheet.createRow(i);
                for (int j = 0; j < list.size(); j++) {
                    HSSFCell cell = row.createCell(j);
                    cell.setCellValue(list.get(j));
                }
            }
            File file = new File("E:\\lihongjieTestExportExcel.xlsx");
            if(!file.exists()){
                file.createNewFile();
            }
            workbook.write(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

    }
}
