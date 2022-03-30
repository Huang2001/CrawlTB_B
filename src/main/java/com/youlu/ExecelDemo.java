package com.youlu;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.youlu.ui.PopWindow;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import javax.swing.filechooser.FileSystemView;

public class ExecelDemo {
    private static ExcelWriter excelWriter = null;

    static File file = null;

    public static void setFile(File f)
    {
        file=f;
    }

    public static LinkedList<String> read() throws IOException {
        ExcelReader reader = null;
        ExcelListener listener = new ExcelListener();
        listener.setSet(FileWR.readOld());
        reader = EasyExcel.read(file, (ReadListener)listener).build();
        ReadSheet sheet = EasyExcel.readSheet(Integer.valueOf(0)).build();
        reader.read(new ReadSheet[] { sheet });
        LinkedList<String> isbnList = listener.getArrayList();
        if (reader != null)
            reader.finish();
        return isbnList;
    }

    public static void write() throws IOException {
        File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
        String desktopPath = desktopDir.getAbsolutePath();
        StringBuilder wUrl = new StringBuilder(desktopPath);

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(1);

        int month = calendar.get(2) + 1;

        int day = calendar.get(5);

        int minute = calendar.get(Calendar.MINUTE);

        int second= calendar.get(Calendar.SECOND);

        wUrl.append("\\");
        wUrl.append(year + ".");
        wUrl.append(month + ".");
        wUrl.append(day + ".");
        wUrl.append(minute+".");
        wUrl.append(second);
        wUrl.append("book.xlsx");
        File file = new File(wUrl.toString());
        if (!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                PopWindow.alert("创建文件失败！");
                        e.printStackTrace();
            }
        excelWriter = EasyExcel.write(file).build();
        WriteSheet sheet = EasyExcel.writerSheet().build();
        List<Book> list = FileWR.readAll();
        excelWriter.write(list, sheet);
        if (excelWriter != null)
            excelWriter.finish();
    }
}

