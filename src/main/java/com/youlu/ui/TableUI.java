package com.youlu.ui;

import com.youlu.Book;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class TableUI
{
    private static javafx.scene.control.TableView tableView = new javafx.scene.control.TableView();

    protected static TableView getTableView()
    {
        tableView.setEditable(true);
        TableColumn<Book, String> column1 = new TableColumn("isbn");
        column1.setCellValueFactory(new PropertyValueFactory("isbn"));
        column1.setCellFactory(TextFieldTableCell.forTableColumn());
        column1.setMinWidth(200.0D);
        column1.setEditable(true);
        TableColumn<Object, Object> column2 = new TableColumn("价格");
        column2.setCellValueFactory(new PropertyValueFactory("price"));
        column2.setMinWidth(50.0D);
        TableColumn<Object, Object> column3 = new TableColumn("销量");
        column3.setCellValueFactory(new PropertyValueFactory("deal"));
        column3.setMinWidth(50.0D);
        TableColumn<Object, Object> column4 = new TableColumn("标题");
        column4.setCellValueFactory(new PropertyValueFactory("title"));
        column4.setMinWidth(600.0D);
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        tableView.setMinHeight(680);
        tableView.setMinWidth(900);
        return tableView;
    }

    public static void addBook(Book book)
    {
        tableView.getItems().add(book);
    }


}
