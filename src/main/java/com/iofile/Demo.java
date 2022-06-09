package com.iofile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import joinery.DataFrame;
import org.apache.poi.ss.formula.functions.Count;

/**
 * @author lby
 * @create 2022-06-09 19:38
 */
public class Demo {
    public static String fileName = "two.txt";


    public static void main(String[] args) throws Exception {

        File file=new File("D:\\project\\iofile\\src\\main\\resources\\two.txt");
        // 读取本地文件
        InputStream in = new FileInputStream(file);
        // 可指定CSV文件的分隔符
        DataFrame<Object> df = DataFrame.readCsv(in, ",");

        //啊哈
        df = df.sortBy("交易金额");
        Set<Object> indexs = df.index();
        Set<Object> columns = df.columns();
        int count = 0;
//        for (int i = 0; i < columns.size(); i++) {
//            File crefile = new File(i+fileName);
//            // 判断当前路径下是否存在同名文件
//            boolean isExist = crefile.exists();
//            if (isExist) {
//                // 文件存在，删除
//                crefile.delete();
//            }
            StringBuffer stringBuffer = new StringBuffer();
            StringBuffer stbu = new StringBuffer();
            columns.stream().forEach(item -> {
                stbu.append(item+"\t\t");
            });
            stbu.append("\n");
                for (Object index : indexs) {
                    for (Object column : columns) {
                        stringBuffer.append(df.get(index, column) + "\t\t");
                    }
                    stringBuffer.append("\n");
                    count++;
                    if (count%5 == 0 && count != 0 ){
                        // 写入文件
                        try {
                            File crefile = new File(count+fileName);
                            // 判断当前路径下是否存在同名文件
                            boolean isExist = crefile.exists();
                            if (isExist) {
                                // 文件存在，删除
                                crefile.delete();
                            }
                            // 文件写入对象
                            FileOutputStream fos = new FileOutputStream(crefile);
                            // 输入流写入----默认字符为GBK
                            stringBuffer.insert(0,stbu);
                            OutputStreamWriter osw = new OutputStreamWriter(fos);
                            // 写入
                            osw.write(String.valueOf(stringBuffer));
//                stringBuffer.delete(0,stringBuffer.length());
                            // 写入完毕后关闭
                            osw.close();
                            System.out.println("成功创建文件:\t"+count+fileName);
                        } catch (IOException e) {
                            System.out.println("写入文件失败:\t" + e.getMessage());
                        }finally {
                            stringBuffer.delete(0,stringBuffer.length());
                        }
                    }
                    }
//                }


    }


}
