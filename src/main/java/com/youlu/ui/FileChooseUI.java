package com.youlu.ui;

import javafx.stage.FileChooser;

public class FileChooseUI {
    private static FileChooser fileChooser = null;

    public static FileChooser getFileChoose() {
        if (fileChooser == null)
        {
            fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter[] { new FileChooser.ExtensionFilter("Excel Files", new String[] { "*.xlsx" }) });
        }
        return fileChooser;
    }
}
