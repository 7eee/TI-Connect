/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.event.EventType
 *  javafx.scene.control.Button
 *  javafx.scene.control.Label
 *  javafx.scene.control.TextField
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyEvent
 *  javafx.scene.layout.Pane
 */
package com.ti.et.elg.dataEditor.customComponents;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class TINumberPicker
extends Pane {
    private Double stride = null;
    private Double initial = null;
    private Double current = null;
    private Button lessBtn = null;
    private Button moreBtn = null;
    private TextField numberDisplay = null;
    private Label titleLbl = null;
    private Double minimum = null;
    private Double maximum = null;
    private static final String DEFAULT_GROUP_TITLE = "Title:";

    public TINumberPicker(Button button, Button button2, TextField textField, Label label) throws NullPointerException {
        if (null == button || null == button2 || null == textField || null == label) {
            throw new NullPointerException("Arguments must not be null.");
        }
        this.lessBtn = button;
        this.moreBtn = button2;
        this.numberDisplay = textField;
        this.titleLbl = label;
    }

    public void setTitle(String string) {
        if (null != this.titleLbl) {
            if (null != string && !string.isEmpty()) {
                this.titleLbl.setText(string);
            } else {
                this.titleLbl.setText("Title:");
            }
        } else {
            this.titleLbl = new Label("Title:");
        }
    }

    public void setInitialParameters(double d, double d2, double d3, double d4) {
        this.stride = d2;
        this.current = this.initial = Double.valueOf(d);
        this.minimum = d3;
        this.maximum = d4;
        if (null != this.lessBtn) {
            this.lessBtn.setText("<");
            this.lessBtn.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

                public void handle(ActionEvent actionEvent) {
                    TINumberPicker.this.current = Math.max(TINumberPicker.this.current - TINumberPicker.this.stride, TINumberPicker.this.minimum);
                    TINumberPicker.this.numberDisplay.setText(TINumberPicker.this.current.toString());
                }
            });
        }
        if (null != this.numberDisplay) {
            this.numberDisplay.setText(this.current.toString());
            this.numberDisplay.addEventHandler(KeyEvent.KEY_PRESSED, (EventHandler)new EventHandler<KeyEvent>(){

                public void handle(KeyEvent keyEvent) {
                    if (!(keyEvent.isConsumed() || keyEvent.isAltDown() || keyEvent.isShiftDown() || keyEvent.isControlDown() || keyEvent.isMetaDown() || keyEvent.isShortcutDown())) {
                        switch (keyEvent.getCode()) {
                            case DOWN: 
                            case KP_DOWN: 
                            case LEFT: 
                            case KP_LEFT: {
                                TINumberPicker.this.current = TINumberPicker.this.current - TINumberPicker.this.stride;
                                TINumberPicker.this.numberDisplay.setText(TINumberPicker.this.current.toString());
                                keyEvent.consume();
                                break;
                            }
                            case UP: 
                            case KP_UP: 
                            case RIGHT: 
                            case KP_RIGHT: {
                                TINumberPicker.this.current = TINumberPicker.this.current + TINumberPicker.this.stride;
                                TINumberPicker.this.numberDisplay.setText(TINumberPicker.this.current.toString());
                                keyEvent.consume();
                                break;
                            }
                            case DIGIT0: 
                            case DIGIT1: 
                            case DIGIT2: 
                            case DIGIT3: 
                            case DIGIT4: 
                            case DIGIT5: 
                            case DIGIT6: 
                            case DIGIT7: 
                            case DIGIT8: 
                            case DIGIT9: 
                            case NUMPAD0: 
                            case NUMPAD1: 
                            case NUMPAD2: 
                            case NUMPAD3: 
                            case NUMPAD4: 
                            case NUMPAD5: 
                            case NUMPAD6: 
                            case NUMPAD7: 
                            case NUMPAD8: 
                            case NUMPAD9: 
                            case DELETE: 
                            case BACK_SPACE: {
                                break;
                            }
                            default: {
                                keyEvent.consume();
                            }
                        }
                    }
                }
            });
        }
        if (null != this.moreBtn) {
            this.moreBtn.setText(">");
            this.moreBtn.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

                public void handle(ActionEvent actionEvent) {
                    TINumberPicker.this.current = Math.min(TINumberPicker.this.current + TINumberPicker.this.stride, TINumberPicker.this.maximum);
                    TINumberPicker.this.numberDisplay.setText(TINumberPicker.this.current.toString());
                }
            });
        }
    }

}

