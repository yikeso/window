package com.ciic.studynet.pdf2swf.clickevent;

import com.ciic.studynet.pdf2swf.windows.Pdf2SwfJFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 点击事件选择文件夹
 * Created by kakasun on 2017/8/9.
 */
public class MyChooseDir implements ActionListener {

    JTextField textField;
    Pdf2SwfJFrame frame;

    public MyChooseDir(){

    }

    /**
     * 选择文件夹的点击事件
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();
        jfc.setMultiSelectionEnabled(false);
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setDialogTitle("选择文件夹");
        if(jfc.showOpenDialog(frame)==JFileChooser.APPROVE_OPTION ){
            //解释下这里,弹出个对话框,可以选择要上传的文件,如果选择了,就把选择的文件的绝对路径打印出来,
            // 有了绝对路径,通过JTextField的settext就能设置进去了,那个我没写
            textField.setText(jfc.getSelectedFile().getAbsolutePath());
        }
    }

    public JTextField getTextField() {
        return textField;
    }

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }

    public Pdf2SwfJFrame getFrame() {
        return frame;
    }

    public void setFrame(Pdf2SwfJFrame frame) {
        this.frame = frame;
    }
}
