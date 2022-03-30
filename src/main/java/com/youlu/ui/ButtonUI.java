package com.youlu.ui;

import com.youlu.Crawl;
import com.youlu.ExecelDemo;
import com.youlu.GetMessageThread;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ButtonUI
{
    private static Button startButton=new Button("已经登录开始爬取");
    private static Button stopButton=new Button("暂停/开始");
    private static Button inputExcelButton=new Button("导入Excel");
    private static Button outputExcelButton=new Button("导出文件");
    private static ComboBox comboBox = new ComboBox();

    protected static Button getStartButton()
    {
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GetMessageThread.setFlag(true);
                startButton.setDisable(true);
            }
        });
        return startButton;
    }

    protected static Button getStopButton()
    {
        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(GetMessageThread.reSetStopFlag())
                {
                    PopWindow.alert("暂停");
                }
                else
                {
                    PopWindow.alert("开启");
                }

            }
        });
        return stopButton;
    }

    protected static Button getInputExcelButton(Stage stage)
    {
        inputExcelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = FileChooseUI.getFileChoose().showOpenDialog(stage);
                if (file != null)
                {
                    ExecelDemo.setFile(file);
                    PopWindow.alert("选择文件成功!");
                    try {
                        GetMessageThread.isbnList = ExecelDemo.read();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    PopWindow.alert("choosed file is null,please choose again!");
                }
            }
        });
        return inputExcelButton;
    }

    protected static Button getOutputExcelButton()
    {
        outputExcelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    ExecelDemo.write();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                PopWindow.alert("文件已放置桌面!");
            }
        });
        return outputExcelButton;
    }

    protected static ComboBox getComboBox()
    {
        comboBox.getItems().add("前三最低");
        comboBox.getItems().add("前二最低");
        comboBox.getItems().add("第一个");
        comboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String content= (String) comboBox.getValue();
                System.out.println("choose:"+content);
                if(content.equals("前三最低"))
                {
                    Crawl.priceIndex = 2;
                }
                else
                {
                    if(content.equals("前二最低"))
                    {
                        Crawl.priceIndex = 1;
                    }
                    else
                    {
                        Crawl.priceIndex = 0;
                    }
                }
                PopWindow.alert("选择:"+content);

            }
        });

        return comboBox;
    }

}
