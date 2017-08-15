package com.ciic.studynet.pdf2swf.clickevent;

import com.ciic.studynet.pdf2swf.utils.FileUtil;
import com.ciic.studynet.pdf2swf.windows.Pdf2SwfJFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 开始pdf转swf
 * Created by kakasun on 2017/8/9.
 */
public class StartPdf2Swf implements ActionListener {

    static final String SwfToolPath = "./SWFTools/pdf2swf";
    static final String FileName = "Paper";
    JTextArea console;
    Pdf2SwfJFrame frame;
    int total;
    int current;

    public void actionPerformed(ActionEvent e) {
        new Thread(new Pdf2SwfThread()).start();
    }

    /**
     * 将pdf文件转换为swf文件
     */
    public void pdf2swf() {
        lockFrame();
        jtextAreaSetText("");
        String dirPath = frame.getPdfDirPath().getText().trim();
        dirPath = FileUtil.formatPath(dirPath);
        File pdfDir = new File(dirPath);
        if (!pdfDir.exists() || pdfDir.isFile()) {
            jtextAreaSetText("pdf源文件所在文件夹路径不合法\n");
            unLockFrame();
            return;
        }
        dirPath = frame.getSwfDirPath().getText().trim();
        dirPath = FileUtil.formatPath(dirPath);
        if (!dirPath.endsWith("/")) {
            dirPath += "/";
        }
        File swfDir = new File(dirPath);
        //文件夹不存在，创建文件夹
        if (!swfDir.exists()) {
            swfDir.mkdirs();
        }
        /*
        字库目录
         */
        String fontPath = null;
        String fontDirPath = frame.getCharacterLibraryPath().getText().trim();
        File fontDir = new File(fontDirPath);
        if (fontDir.exists() && fontDir.isDirectory()) {
            fontPath = fontDir.getAbsolutePath();
        }
        File mapping = new File(dirPath + "mapping.txt");
        if (!mapping.exists()) {
            try {
                mapping.createNewFile();
            } catch (IOException e1) {
                jtextAreaSetText("error：" + e1.getMessage() + "\n");
                unLockFrame();
                return;
            }
        }
        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            File[] pdfFiles = pdfDir.listFiles();
            total = pdfFiles.length;
            long timestamp;
            fos = new FileOutputStream(mapping);
            pw = new PrintWriter(fos);
            for (File f : pdfFiles) {
                current++;
                if (f.isDirectory()) {
                    continue;
                }
                String name = f.getName();
                if (!name.toLowerCase().endsWith(".pdf")) {
                    continue;
                }
                timestamp = System.currentTimeMillis();
                pdf2Swf(SwfToolPath, f.getAbsolutePath(), dirPath + timestamp + "/", FileName, fontPath);
                name = name.substring(0, name.lastIndexOf('.')) + "===========" + timestamp;
                System.out.println(name);
                pw.println(name);
            }
           append("------------------------\npdf转swf全部完成\n");
        } catch (Exception expt) {
            append("error：" + expt.getMessage() + "\n");
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        unLockFrame();
    }


    /**
     * @param toolsPath  pdf转swf工具所在路径
     * @param sourcePath 源pdf文件觉对路径
     * @param destPath   生成目标swf文件夹路径
     * @param fileName   生成swf文件名称
     * @param fontPath   字库文件目录
     * @throws Exception
     */
    public void pdf2Swf(String toolsPath, String sourcePath, String destPath, String fileName, String fontPath) throws Exception {
        // 源文件不存在则返回
        File source = new File(sourcePath);
        if (!source.exists()) {
            return;
        }
        source.setReadable(false);
        File destDir = new File(destPath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        String outputFile = destPath + fileName + "%.swf";
        jtextAreaSetText("当前转换pdf：" + source.getAbsolutePath() + "\n");
        /*
        拼接系统命令
         */
        List<String> command = new ArrayList<String>();
        command.add(toolsPath);// 从配置文件里读取
        command.add(sourcePath);
        command.add("-o");
        command.add(outputFile);
        command.add("-T");
        command.add("9");
        command.add("-f");
        command.add("-s");
        command.add("storeallcharacters");
        if (fontPath != null && fontPath.length() > 0) {
            command.add("languagedir=" + fontPath);
        }
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(command);
        Process process = processBuilder.start();
        /*
        另起一个线程接收错误信息
         */
        ErrorInputStreamWathThread errorInputWathThread = new ErrorInputStreamWathThread();
        errorInputWathThread.setInputStream(process.getErrorStream());
        errorInputWathThread.setConsole(this.console);
        new Thread(errorInputWathThread).start();
        /*
        打印输出信息
         */
        InputStream inputStream = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(isr);
        String buffer;
        try {
            while ((buffer = br.readLine()) != null) {
                append(buffer + "\n");
            }
            append(source.getAbsolutePath() + " 转换完成\n");
        } catch (IOException e) {
            append("error：" + e.getMessage() + "\n");
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * pdf转swf过程中锁定按钮
     */
    private void lockFrame() {
        frame.getChoosePdfDir().setEnabled(false);
        frame.getPdfDirPath().setEditable(false);
        frame.getChooseSwfDir().setEnabled(false);
        frame.getSwfDirPath().setEditable(false);
        frame.getCharacterLibraryDir().setEnabled(false);
        frame.getCharacterLibraryPath().setEditable(false);
        frame.getStart().setEnabled(false);
    }

    /**
     * 解锁按钮
     */
    private void unLockFrame() {
        frame.getChoosePdfDir().setEnabled(true);
        frame.getPdfDirPath().setEditable(true);
        frame.getChooseSwfDir().setEnabled(true);
        frame.getSwfDirPath().setEditable(true);
        frame.getCharacterLibraryDir().setEnabled(true);
        frame.getCharacterLibraryPath().setEditable(true);
        frame.getStart().setEnabled(true);
    }

    /**
     * 向JTextArea中append字符串
     *
     * @param text
     */
    private synchronized void append(String text) {
        console.append(text+"第 "+current+" 个，共 "+total+" 个file\n");
        frame.repaint();
    }

    private synchronized void jtextAreaSetText(String text){
        console.setText(text+"第 "+current+" 个，共 "+total+" 个file\n");
        frame.repaint();
    }
    public JTextArea getConsole() {
        return console;
    }

    public void setConsole(JTextArea console) {
        this.console = console;
    }

    public Pdf2SwfJFrame getFrame() {
        return frame;
    }

    public void setFrame(Pdf2SwfJFrame frame) {
        this.frame = frame;
    }

    /**
     * 另起线程进行pdf转swf任务
     */
    class Pdf2SwfThread implements Runnable{

        @Override
        public void run() {
            pdf2swf();
        }
    }

    /**
     * 获取错误信息并输出
     */
    class ErrorInputStreamWathThread implements Runnable {

        InputStream inputStream;
        JTextArea console;

        public void run() {
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);
            String buffer;
            try {
                while ((buffer = br.readLine()) != null) {
                    System.out.println(buffer);
                }
            } catch (IOException e) {
               e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public InputStream getInputStream() {
            return inputStream;
        }

        public void setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        public JTextArea getConsole() {
            return console;
        }

        public void setConsole(JTextArea console) {
            this.console = console;
        }
    }
}
