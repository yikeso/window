package com.ciic.studynet.pdf2swf.utils;

/**
 * Created by kakasun on 2017/8/9.
 */
public class FileUtil {

    /**
     * 格式化文件路径
     * @param path
     * @return
     */
    public static String formatPath(String path){
        return path.replaceAll("[\\\\/]+","/");
    }
}
