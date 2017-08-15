package com.ciic.studynet.pdf2swf.utils;

import javax.swing.*;

/**
 * Created by kakasun on 2017/8/9.
 */
public class JTextAreaUtil {
    /**
     * 向JTextArea中append字符串，并实时显示
     *
     * @param console
     * @param text
     * @param scroll  文本域的滚动条，可以为空
     */
    public static synchronized void append(JTextArea console, JScrollPane scroll, String text) {
        console.append(text);
    }

    /**
     * 向JTextArea中设置字符串，并实时显示
     *
     * @param console
     * @param text
     * @param scroll  文本域的滚动条，可以为空
     */
    public static synchronized void setText(JTextArea console, JScrollPane scroll, String text) {
        console.setText(text);
    }
}
