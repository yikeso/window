package com.ciic.studynet.pdf2swf.windows;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

/**
 * pdf2swf的操作窗口容器
 * Created by kakasun on 2017/8/9.
 */
public class Pdf2SwfJFrame extends JFrame {

    private static final long serialVersionUID = -9011115580489502071L;
    /**
     * 选择pdf源文件目录
     */
    JButton choosePdfDir;
    JTextField pdfDirPath;
    /**
     * 选择生成swf文件存放目录
     */
    JButton chooseSwfDir;
    JTextField swfDirPath;
    /**
     * 选择字库目录
     */
    JButton characterLibraryDir;
    JTextField characterLibraryPath;
    /**
     * 开始按钮
     */
    JButton start;
    JTextArea console;
    JScrollPane scroll;
    public static final int WIDTH = 350;
    public static final int HEIGHT = 600;
    /**
     * 构造方法
     */
    public Pdf2SwfJFrame(){

    }

    /**
     * 初始化窗口方法
     */
    public void init(){
        //窗口标题
        this.setTitle("pdf转swf");
        //窗口关闭退出
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //窗口上部组件面板
        JPanel panel = new JPanel();
        //边界管理器
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        //添加组件
        panel.add(choosePdfDir);
        panel.add(pdfDirPath);
        panel.add(chooseSwfDir);
        panel.add(swfDirPath);
        panel.add(characterLibraryDir);
        panel.add(characterLibraryPath);
        panel.add(start);
        console.setEditable(false);
        console.setLineWrap(true);
        console.setWrapStyleWord(true);
        DefaultCaret caret = (DefaultCaret)console.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        scroll = new JScrollPane(console);
//        scroll.setVerticalScrollBarPolicy(
//                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scroll);
        this.getContentPane().add(panel);
        //设置窗口大小
        this.setSize(WIDTH,HEIGHT);
        //不可缩放大小
        this.setResizable(false);
        //居中
        this.setLocationRelativeTo(this.getOwner());
        //显示窗口
        this.setVisible(true);
    }

    public JButton getChoosePdfDir() {
        return choosePdfDir;
    }

    public void setChoosePdfDir(JButton choosePdfDir) {
        this.choosePdfDir = choosePdfDir;
    }

    public JTextField getPdfDirPath() {
        return pdfDirPath;
    }

    public void setPdfDirPath(JTextField pdfDirPath) {
        this.pdfDirPath = pdfDirPath;
    }

    public JButton getChooseSwfDir() {
        return chooseSwfDir;
    }

    public void setChooseSwfDir(JButton chooseSwfDir) {
        this.chooseSwfDir = chooseSwfDir;
    }

    public JTextField getSwfDirPath() {
        return swfDirPath;
    }

    public void setSwfDirPath(JTextField swfDirPath) {
        this.swfDirPath = swfDirPath;
    }

    public JButton getCharacterLibraryDir() {
        return characterLibraryDir;
    }

    public void setCharacterLibraryDir(JButton characterLibraryDir) {
        this.characterLibraryDir = characterLibraryDir;
    }

    public JTextField getCharacterLibraryPath() {
        return characterLibraryPath;
    }

    public void setCharacterLibraryPath(JTextField characterLibraryPath) {
        this.characterLibraryPath = characterLibraryPath;
    }

    public JButton getStart() {
        return start;
    }

    public void setStart(JButton start) {
        this.start = start;
    }

    public JTextArea getConsole() {
        return console;
    }

    public void setConsole(JTextArea console) {
        this.console = console;
    }

    public JScrollPane getScroll() {
        return scroll;
    }
}
