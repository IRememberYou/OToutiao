package com.example.pinan.otoutiao.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author pinan
 * @date 2017/12/4
 */

public class FileCloseUtil {
    /**
     * 统一关闭文件流方法
     */
    public static void close(Closeable... closeable) {
        if (closeable != null) {
            int length = closeable.length;
            for (int i = 0; i < length; i++) {
                try {
                    closeable[i].close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            new Throwable("FileCloseUtil close methods param is null");
        }
    }
}
