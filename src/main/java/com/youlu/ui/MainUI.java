package com.youlu.ui;

import com.youlu.FileWR;
import com.youlu.GetMessageThread;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainUI extends Application {

    public static ArrayList<ChromeDriver> driverList = new ArrayList<ChromeDriver>();

    @Override
    public void start(Stage primaryStage) throws Exception
    {
       primaryStage.setTitle("淘宝图书爬虫软件");
       TableView tableView=TableUI.getTableView();
       primaryStage.setWidth(1100);
       primaryStage.setHeight(750);

       Button inputExcelButton= ButtonUI.getInputExcelButton(primaryStage);
       Button startButton= ButtonUI.getStartButton();
       Button stopButton=ButtonUI.getStopButton();
       Button outputButton=ButtonUI.getOutputExcelButton();
        ComboBox comboBox=ButtonUI.getComboBox();

        VBox vBox=new VBox(60,inputExcelButton,comboBox,startButton,stopButton,outputButton);

        Separator separator1 = new Separator(Orientation.VERTICAL);
        separator1.setStyle("-fx-background-color: black;");

        HBox hBox=new HBox(tableView,separator1,vBox);

       Scene scene=new Scene(hBox);
       primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                for (ChromeDriver driver : driverList) {
                    if (driver != null)
                    {
                        try {
                            driver.quit();
                        } catch (Exception exception) {}
                    }

                }
                try {
                    FileWR.closeAll();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });
        primaryStage.show();
    }


    public static void main(String[] argv)
    {
        File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
        String desktopPath = desktopDir.getAbsolutePath();
        String driverURL=desktopPath+"\\Chrome\\chromedriver.exe";

        System.getProperties().setProperty("webdriver.chrome.driver", driverURL);


        GetMessageThread getMessageThread1 = new GetMessageThread();

        GetMessageThread getMessageThread2 = new GetMessageThread();
        getMessageThread1.start();
        getMessageThread2.start();

        MainUI.launch(argv);
    }
}
