package com.example.pinan.otoutiao.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pinan on 2017/11/22.
 */

public class FileUtil {
    /**
     * 拷贝文件
     */
    public static void copyFile(File sourceFile, File targetFile) {
        copyFile(sourceFile, targetFile, false);
    }
    
    public static void copyFile(File sourceFile, File targerFile, boolean append) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(sourceFile);
            os = new FileOutputStream(targerFile, append);
            int len;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf)) > 0) {
                os.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileCloseUtil.close(is, os);
        }
    }
    
    /**
     * 遍历指定文件夹的指定类型文件
     */
    List<File> fileList;
    
    public List<File> traverseFile(String filePath, String endType) {
        if (fileList == null) {
            fileList = new ArrayList<>();
        }
        File dir = new File(filePath);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        traverseFile(files[i].getAbsolutePath(), endType);
                    } else if (files[i].isFile() && files[i].getName().endsWith(endType)) {
                        fileList.add(files[i]);
                    }
                }
            }
        }
        return fileList;
    }
    
    
}
