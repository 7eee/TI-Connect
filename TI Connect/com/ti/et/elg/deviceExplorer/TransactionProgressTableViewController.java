/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.collections.FXCollections
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.scene.Node
 *  javafx.scene.control.Button
 *  javafx.scene.control.TableCell
 *  javafx.scene.control.TableColumn
 *  javafx.scene.control.TableRow
 *  javafx.scene.control.TableView
 *  javafx.scene.control.cell.PropertyValueFactory
 *  javafx.util.Callback
 */
package com.ti.et.elg.deviceExplorer;

import com.ti.et.elg.transactionManager.TransactionManager;
import com.ti.et.elg.transactionManager.TransactionProgressData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class TransactionProgressTableViewController {
    private TransactionManager transactionManager = null;
    Callback<TableColumn<TransactionProgressData, Boolean>, TableCell<TransactionProgressData, Boolean>> buttonCellFactory;

    public TransactionProgressTableViewController(TransactionManager transactionManager) {
        this.buttonCellFactory = new Callback<TableColumn<TransactionProgressData, Boolean>, TableCell<TransactionProgressData, Boolean>>(){

            public TableCell<TransactionProgressData, Boolean> call(TableColumn<TransactionProgressData, Boolean> tableColumn) {
                final Button button = new Button("Cancel");
                TableCell<TransactionProgressData, Boolean> tableCell = new TableCell<TransactionProgressData, Boolean>(){

                    public void updateItem(Boolean bl, boolean bl2) {
                        super.updateItem((Object)bl, bl2);
                        final TransactionProgressData transactionProgressData = (TransactionProgressData)this.getTableRow().getItem();
                        if (bl == null || bl.booleanValue() || transactionProgressData.isDone()) {
                            button.setDisable(true);
                        } else {
                            button.setDisable(false);
                            button.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

                                public void handle(ActionEvent actionEvent) {
                                    transactionProgressData.setCancel(true);
                                }
                            });
                        }
                    }

                };
                tableCell.setGraphic((Node)button);
                return tableCell;
            }

        };
        this.transactionManager = transactionManager;
    }

    public void initTransactionProgressTableView(TableView<TransactionProgressData> tableView) {
        ObservableList observableList = FXCollections.observableArrayList();
        TableColumn tableColumn = new TableColumn("File Name");
        tableColumn.setMinWidth(100.0);
        tableColumn.setCellValueFactory((Callback)new PropertyValueFactory("fileName"));
        TableColumn tableColumn2 = new TableColumn("Percentage");
        tableColumn2.setMinWidth(100.0);
        tableColumn2.setCellValueFactory((Callback)new PropertyValueFactory("percentage"));
        TableColumn tableColumn3 = new TableColumn("Items Done");
        tableColumn3.setMinWidth(100.0);
        tableColumn3.setCellValueFactory((Callback)new PropertyValueFactory("itemsDone"));
        TableColumn tableColumn4 = new TableColumn("Items Total");
        tableColumn4.setMinWidth(100.0);
        tableColumn4.setCellValueFactory((Callback)new PropertyValueFactory("itemsTotal"));
        TableColumn tableColumn5 = new TableColumn("Status Code");
        tableColumn5.setMinWidth(100.0);
        tableColumn5.setCellValueFactory((Callback)new PropertyValueFactory("status"));
        TableColumn tableColumn6 = new TableColumn("Cancel");
        tableColumn6.setMinWidth(100.0);
        tableColumn6.setCellValueFactory((Callback)new PropertyValueFactory("cancel"));
        tableColumn6.setCellFactory(this.buttonCellFactory);
        if (tableView != null) {
            tableView.setItems(observableList);
            tableView.getColumns().addAll((Object[])new TableColumn[]{tableColumn, tableColumn2, tableColumn3, tableColumn4, tableColumn5, tableColumn6});
            tableView.setPrefHeight(300.0);
        }
        this.transactionManager.setTransactionProgressDataList(observableList);
    }

}

