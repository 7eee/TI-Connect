/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.collections.ObservableList
 *  javafx.scene.Node
 *  javafx.scene.control.ComboBox
 *  javafx.scene.control.Label
 *  javafx.scene.control.ListCell
 *  javafx.scene.control.ListView
 *  javafx.scene.image.Image
 *  javafx.scene.image.ImageView
 *  javafx.scene.layout.HBox
 *  javafx.util.Callback
 */
package com.ti.et.elg.commonUISupport.customComponents;

import com.ti.et.elg.commonUISupport.utils.CustomComboImage;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class TIComboBoxImage<T extends CustomComboImage>
extends ComboBox<T> {
    public void setCustomItems(ObservableList<T> observableList) {
        this.setButtonCell(this.addCellButton());
        this.setCellFactory(this.addCellFactory());
        this.setItems(observableList);
    }

    private Callback<ListView<T>, ListCell<T>> addCellFactory() {
        return new Callback<ListView<T>, ListCell<T>>(){

            public ListCell<T> call(ListView<T> listView) {
                ListCell listCell = new ListCell<T>(){
                    ImageView imageview;

                    public void updateItem(T t, boolean bl) {
                        super.updateItem(t, bl);
                        if (t != null && !bl) {
                            HBox hBox = new HBox();
                            hBox.setSpacing(5.0);
                            this.imageview.setImage(t.getImage().getImage());
                            hBox.getChildren().add((Object)this.imageview);
                            hBox.getChildren().add((Object)new Label(t.getText()));
                            this.setGraphic((Node)(hBox == null ? null : hBox));
                        }
                    }
                };
                return listCell;
            }

        };
    }

    private ListCell<T> addCellButton() {
        ListCell listCell = new ListCell<T>(){

            protected void updateItem(T t, boolean bl) {
                super.updateItem(t, bl);
                this.setText(t == null ? "" : t.getText());
                this.setGraphic((Node)(t == null ? null : t.getImage()));
            }
        };
        return listCell;
    }

}

