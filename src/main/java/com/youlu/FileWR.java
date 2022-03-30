package com.youlu;

import com.youlu.ui.PopWindow;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashSet;

public class FileWR {


    private static String fileName;// = "C:\\Users\\Administrator\\Desktop\\youlu.txt";

    static
    {
        File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
        String desktopPath = desktopDir.getAbsolutePath();
        fileName=desktopPath+"\\youlu.txt";
    }

    private static FileWriter writerFile;

    private static RandomAccessFile readFile;

    private static StringBuilder string = new StringBuilder("");

    static {
        File file1 = new File(fileName);
        File file2 = new File(fileName);
        try {
            writerFile = new FileWriter(file1, true);
            readFile = new RandomAccessFile(file2, "r");
        } catch (IOException e) {
            PopWindow.alert("写入临时文件异常");
        }
    }

    public static HashSet<String> readOld() throws IOException {
        RandomAccessFile readFile = new RandomAccessFile(new File(fileName), "r");
        HashSet<String> set = new HashSet<String>();
        String string;
        while ((string = readFile.readLine()) != null) {
            string = new String(string.getBytes("ISO-8859-1"), "gbk");
            if (string != null) {
                try {
                    string = string.substring(0, string.indexOf("&"));
                } catch (Exception e) {
                    continue;
                }
                set.add(string);
            }
        }
        readFile.close();
        return set;
    }

    public static void writeLine(String isbn, String price, String title, String deal, boolean isNull) {
        string.delete(0, string.length());
        if (isNull) {
            string.append(isbn);
            string.append("&");
        } else {
            string.append(isbn);
            string.append("&");
            string.append(price);
            string.append("&");
            string.append(title);
            string.append("&");
            string.append(deal);
        }
        try {
            writerFile.write(string.toString());
            writerFile.write("\n");
            writerFile.flush();
        } catch (IOException e) {
            PopWindow.alert("读取临时文件异常！");
        }
    }

    public static ArrayList<Book> readAll() throws IOException {
        if (writerFile != null)
            writerFile.flush();
        ArrayList<Book> bookList = new ArrayList<Book>();
        ArrayList<String> content = new ArrayList<String>();
        try {
            String s;
            while ((s = readFile.readLine()) != null) {
                s = new String(s.getBytes("ISO-8859-1"), "gbk");
                content.add(s);
            }
        } catch (IOException e) {
            PopWindow.alert("未加载到临时文件！");
        }
        for (String s1 : content) {
            String[] strings = s1.split("&");
            if (strings.length == 4) {
                bookList.add(new Book(strings[0], strings[1], strings[2], strings[3]));
            }
        }
        clearFile();
        return bookList;
    }

    private static void clearFile() throws IOException {
        FileWriter writer = new FileWriter(new File(fileName));
        writer.write("");
        writer.close();
    }

    public static void closeAll() throws IOException {
        if (readFile != null) {
            readFile.close();
        }
        if (writerFile != null) {
            writerFile.flush();
            writerFile.close();
        }
    }
}

