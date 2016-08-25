/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.IntegerProperty
 *  javafx.beans.property.ObjectProperty
 *  javafx.beans.property.Property
 *  javafx.beans.property.SimpleBooleanProperty
 *  javafx.beans.property.SimpleIntegerProperty
 *  javafx.beans.property.SimpleObjectProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 */
package com.ti.et.elg.commonUISupport.sendToHandheldDialog;

import com.ti.et.elg.commonUISupport.imageUtils.TIConvertedImageVarMultiplexer;
import com.ti.et.elg.commonUISupport.imageUtils.TIVarMultiplexer;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.TIVarHolder;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.VarNameChangedListener;
import com.ti.et.elg.connectServer.exports.TIVar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class VarTableData {
    private BooleanProperty deletedProperty = new SimpleBooleanProperty(false);
    private final ObjectProperty<TIVarHolder> tiVarHolder = new SimpleObjectProperty();
    private static Map<Integer, ArrayList<TIVarHolder>> varNames = new HashMap<Integer, ArrayList<TIVarHolder>>();
    private static IntegerProperty numberOfDuplicatesProperty = new SimpleIntegerProperty(0);
    private static final HashMap<VarTableData, VarNameChangedListener> mapListeners = new HashMap();

    public VarTableData(TIVarMultiplexer tIVarMultiplexer) {
        this.tiVarHolder.set((Object)new TIVarHolder(tIVarMultiplexer));
        ((TIVarHolder)this.tiVarHolder.get()).duplicatedProperty().addListener((ChangeListener)new ChangeListener<Boolean>(){

            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                if (bl2.booleanValue()) {
                    numberOfDuplicatesProperty.set(numberOfDuplicatesProperty.get() + 1);
                } else {
                    numberOfDuplicatesProperty.set(numberOfDuplicatesProperty.get() - 1);
                }
            }
        });
        this.deletedProperty.addListener((ChangeListener)new ChangeListener<Boolean>(){

            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                if (bl2.booleanValue()) {
                    VarTableData.removeNameFromList((TIVarHolder)VarTableData.this.tiVarHolder.get());
                }
            }
        });
        this.deletedProperty.bindBidirectional((Property)((TIVarHolder)this.tiVarHolder.get()).deletedProperty());
        ((TIVarHolder)this.tiVarHolder.get()).setParent(this);
        VarTableData.addToListOfNames((TIVarHolder)this.tiVarHolder.get());
    }

    public ObjectProperty<TIVarHolder> tiVarHolderProperty() {
        return this.tiVarHolder;
    }

    public BooleanProperty deletedProperty() {
        return this.deletedProperty;
    }

    public static IntegerProperty numberOfDuplicatesProperty() {
        return numberOfDuplicatesProperty;
    }

    public static void addToListOfNames(TIVarHolder tIVarHolder) {
        int n = tIVarHolder.getTIVar().getVarType();
        if (!varNames.containsKey(n)) {
            varNames.put(n, new ArrayList());
        }
        if (VarTableData.isNameDuplicated(tIVarHolder)) {
            tIVarHolder.duplicatedProperty().set(true);
        }
        varNames.get(n).add(tIVarHolder);
    }

    public static void removeNameFromList(TIVarHolder tIVarHolder) {
        int n = tIVarHolder.getTIVar().getVarType();
        if (varNames.containsKey(n)) {
            varNames.get(n).remove(tIVarHolder);
        }
        tIVarHolder.duplicatedProperty().set(false);
        for (TIVarHolder tIVarHolder2 : varNames.get(n)) {
            if (!tIVarHolder2.duplicatedProperty().get() || VarTableData.isNameDuplicated(tIVarHolder2)) continue;
            tIVarHolder2.duplicatedProperty().set(false);
        }
    }

    private static boolean isNameDuplicated(TIVarHolder tIVarHolder) {
        int n = tIVarHolder.getTIVar().getVarType();
        for (TIVarHolder tIVarHolder2 : varNames.get(n)) {
            if (!tIVarHolder2.getTIVar().getDeviceFileName().equals(tIVarHolder.getTIVar().getDeviceFileName()) || tIVarHolder2 == tIVarHolder) continue;
            return true;
        }
        return false;
    }

    public static boolean containsDuplicatedNames() {
        return numberOfDuplicatesProperty.get() > 0;
    }

    public static boolean containsConvertedImageVars() {
        List list = varNames.get(26);
        if (list != null) {
            for (TIVarHolder tIVarHolder : list) {
                if (!(tIVarHolder.getTIVarMux() instanceof TIConvertedImageVarMultiplexer)) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean containsColorScreenImageVars() {
        List list = varNames.get(26);
        if (list != null) {
            for (TIVarHolder tIVarHolder : list) {
                if (!(tIVarHolder.getTIVarMux() instanceof TIConvertedImageVarMultiplexer) || ((TIConvertedImageVarMultiplexer)tIVarHolder.getTIVarMux()).getImageKind() != TIConvertedImageVarMultiplexer.ImageKind.FROM_COLOR_SCREEN_SHOT) continue;
                return true;
            }
        }
        return false;
    }

    public static void clearListOfNames() {
        varNames.clear();
        numberOfDuplicatesProperty.set(0);
    }

    public static void addVarNameChangedListener(VarTableData varTableData, VarNameChangedListener varNameChangedListener) {
        mapListeners.put(varTableData, varNameChangedListener);
    }

    public static void removeAllVarNameChangedListener() {
        mapListeners.clear();
    }

    public static void removeVarNameChangedListener(VarTableData varTableData) {
        mapListeners.remove(varTableData);
    }

    public static void fireVarNameChanged(VarTableData varTableData) {
        if (mapListeners.containsKey(varTableData)) {
            mapListeners.get(varTableData).onVarNameChanged((TIVarHolder)varTableData.tiVarHolderProperty().get());
        }
    }

}

