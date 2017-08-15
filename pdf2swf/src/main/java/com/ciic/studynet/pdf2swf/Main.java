package com.ciic.studynet.pdf2swf;

import com.ciic.studynet.pdf2swf.clickevent.MyChooseDir;
import com.ciic.studynet.pdf2swf.clickevent.StartPdf2Swf;
import com.ciic.studynet.pdf2swf.windows.Pdf2SwfJFrame;

import javax.swing.*;

/**
 * pdf2swf的主程序入口
 * Created by kakasun on 2017/8/9.
 */
public class Main {

    public static void main(String[] arg0){
        int len = Pdf2SwfJFrame.WIDTH/12;
        JButton choosePdfDir = new JButton("选择pdf文件所在文件夹");
        JTextField pdfDirPath = new JTextField(len);
        JButton chooseSwfDir = new JButton("选择生成的swf文件存放目录");
        JTextField swfDirPath = new JTextField(len);
        JButton characterLibraryDir = new JButton("选择字库目录");
        JTextField characterLibraryPath  = new JTextField(len);
        characterLibraryPath.setText("此项不选，则使用系统默认字库");
        JButton start = new JButton("开始转换");
        JTextArea console = new JTextArea();
        console.setColumns(len);
        console.setRows(22);
        Pdf2SwfJFrame frame = new Pdf2SwfJFrame();
        frame.setChoosePdfDir(choosePdfDir);
        frame.setPdfDirPath(pdfDirPath);
        frame.setChooseSwfDir(chooseSwfDir);
        frame.setSwfDirPath(swfDirPath);
        frame.setCharacterLibraryDir(characterLibraryDir);
        frame.setCharacterLibraryPath(characterLibraryPath);
        frame.setStart(start);
        frame.setConsole(console);
        frame.init();
        /*
         *为选择文件夹按钮添加点击事件
         */
        addChooseFileClickEvent(frame);
        addStartPdf2SwfClickEvent(frame);
    }

    /**
     * 给frame中选择文件夹的按钮添加点击选择文件夹事件
     * @param frame
     */
    private static void addChooseFileClickEvent(Pdf2SwfJFrame frame){
        /*
        选择pdf源文件文件夹点击事件
         */
        JButton button = frame.getChoosePdfDir();
        MyChooseDir chooseDir = new MyChooseDir();
        chooseDir.setFrame(frame);
        chooseDir.setTextField(frame.getPdfDirPath());
        button.addActionListener(chooseDir);
        /*
        选择swf存放目录点击事件
        */
        button = frame.getChooseSwfDir();
        chooseDir = new MyChooseDir();
        chooseDir.setFrame(frame);
        chooseDir.setTextField(frame.getSwfDirPath());
        button.addActionListener(chooseDir);
        /*
        选择字库目录
         */
        button = frame.getCharacterLibraryDir();
        chooseDir = new MyChooseDir();
        chooseDir.setFrame(frame);
        chooseDir.setTextField(frame.getCharacterLibraryPath());
        button.addActionListener(chooseDir);

    }

    /**
     * 添加开始pdf转swf事件
     * @param frame
     */
    private static void addStartPdf2SwfClickEvent(Pdf2SwfJFrame frame){
        JButton button = frame.getStart();
        StartPdf2Swf start = new StartPdf2Swf();
        start.setFrame(frame);
        start.setConsole(frame.getConsole());
        button.addActionListener(start);
    }
}
