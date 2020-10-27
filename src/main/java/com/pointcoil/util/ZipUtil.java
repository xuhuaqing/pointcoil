package com.pointcoil.util;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * Created by WuShuang on 2020/3/25.
 */
public class ZipUtil {

    public static void downloadLocal(HttpServletResponse response) throws IOException {
        //文件所在目录路径
        String filePath = "/code/point/download/small.zip";
        //得到该文件
        File file = new File(filePath);
        if(!file.exists()){
            System.out.println("Have no such file!");
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        //设置Http响应头告诉浏览器下载这个附件,下载的文件名也是在这里设置的
        response.setHeader("Content-Disposition", "attachment;Filename=" + URLEncoder.encode("small.zip", "UTF-8"));
        OutputStream outputStream = response.getOutputStream();
        byte[] bytes = new byte[2048];
        int len = 0;
        while ((len = fileInputStream.read(bytes))>0){
            outputStream.write(bytes,0,len);
        }
        fileInputStream.close();
        outputStream.close();
    }



    public static void downloadWord(HttpServletResponse response) throws IOException {
        //文件所在目录路径
        String filePath = "/code/point/download/small.docx";
        //得到该文件
        File file = new File(filePath);
        if(!file.exists()){
            System.out.println("Have no such file!");
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        //设置Http响应头告诉浏览器下载这个附件,下载的文件名也是在这里设置的
        response.setHeader("Content-Disposition", "attachment;Filename=" + URLEncoder.encode("small.docx", "UTF-8"));
        OutputStream outputStream = response.getOutputStream();
        byte[] bytes = new byte[2048];
        int len = 0;
        while ((len = fileInputStream.read(bytes))>0){
            outputStream.write(bytes,0,len);
        }
        fileInputStream.close();
        outputStream.close();
    }
}
