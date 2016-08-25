/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.ObjectProperty
 *  javafx.beans.property.SimpleObjectProperty
 *  javafx.beans.property.SimpleStringProperty
 *  javafx.beans.property.StringProperty
 */
package com.ti.et.elg.deviceExplorer;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.deviceExplorer.DeviceExplorerResourceBundle;
import com.ti.et.elg.deviceExplorer.NameTIVar;
import com.ti.et.elg.deviceExplorer.SizeTIVar;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableViewData {
    private final ObjectProperty<NameTIVar> name = new SimpleObjectProperty();
    private final StringProperty type = new SimpleStringProperty("");
    private final ObjectProperty<SizeTIVar> size = new SimpleObjectProperty();
    private final StringProperty location = new SimpleStringProperty("");
    private TIVar tiVar = null;
    private String iconFileSelected;
    private String iconFileUnselected;
    private ResourceBundle resources = CommonUISupportResourceBundle.BUNDLE;
    private static String sizeFormatByte = DeviceExplorerResourceBundle.BUNDLE.getString("tableView.columnSize.sizeFormat.Byte");
    private static String sizeFormatKByte = DeviceExplorerResourceBundle.BUNDLE.getString("tableView.columnSize.sizeFormat.KBytes");

    public TableViewData(String string, TIVar tIVar) {
        this.setTIVar(tIVar);
        int n = tIVar.getVarType();
        this.iconFileSelected = this.getIconFileFromVarType(n, true);
        this.iconFileUnselected = this.getIconFileFromVarType(n, false);
        NameTIVar nameTIVar = new NameTIVar(string, this.iconFileUnselected);
        this.setName(nameTIVar);
        String string2 = this.resources.getString("tivar.type." + tIVar.getNameFromVarType(n));
        this.setType(string2);
        Integer n2 = tIVar.getDataSize();
        String string3 = this.sizeFormat(n2);
        SizeTIVar sizeTIVar = new SizeTIVar(string3, n2);
        this.setSize(sizeTIVar);
        if (tIVar.isVarFlagArchive()) {
            this.setLocation(this.resources.getString("table.location.archive.value"));
        } else {
            this.setLocation(this.resources.getString("table.location.ram.value"));
        }
    }

    public TIVar getTiVar() {
        return this.tiVar;
    }

    public void setTIVar(TIVar tIVar) {
        this.tiVar = tIVar;
    }

    public ObjectProperty<NameTIVar> nameProperty() {
        return this.name;
    }

    public NameTIVar getName() {
        return (NameTIVar)this.name.get();
    }

    public void setName(NameTIVar nameTIVar) {
        this.name.set((Object)nameTIVar);
    }

    public StringProperty typeProperty() {
        return this.type;
    }

    public String getType() {
        return (String)this.type.get();
    }

    public void setType(String string) {
        this.type.set((Object)string);
    }

    public ObjectProperty<SizeTIVar> sizeProperty() {
        return this.size;
    }

    public SizeTIVar getSize() {
        return (SizeTIVar)this.size.get();
    }

    public void setSize(SizeTIVar sizeTIVar) {
        this.size.set((Object)sizeTIVar);
    }

    public StringProperty locationProterty() {
        return this.location;
    }

    public String getLocation() {
        return (String)this.location.get();
    }

    public void setLocation(String string) {
        this.location.set((Object)string);
    }

    public void changeIconFile(boolean bl) {
        if (bl) {
            this.getName().setIconFile(this.iconFileSelected);
        } else {
            this.getName().setIconFile(this.iconFileUnselected);
        }
        this.getName().setIsSelectedProperty(bl);
    }

    public String getIconFileFromVarType(int n, boolean bl) {
        switch (n) {
            case 21: 
            case 36: {
                if (!bl) {
                    return "icons/app.png";
                }
                return "icons/app_f.png";
            }
            case 37: 
            case 39: {
                if (!bl) {
                    return "icons/certificate.png";
                }
                return "icons/certificate_f.png";
            }
            case 0: 
            case 12: 
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: 
            case 32: 
            case 33: {
                if (!bl) {
                    return "icons/number.png";
                }
                return "icons/number_f.png";
            }
            case 1: 
            case 13: {
                if (!bl) {
                    return "icons/list.png";
                }
                return "icons/list_f.png";
            }
            case 3: 
            case 11: {
                if (!bl) {
                    return "icons/equation.png";
                }
                return "icons/equation_f.png";
            }
            case 15: 
            case 16: 
            case 17: {
                if (!bl) {
                    return "icons/range.png";
                }
                return "icons/range_f.png";
            }
            case -1: 
            case 4: 
            case 22: 
            case 41: {
                if (!bl) {
                    return "icons/string.png";
                }
                return "icons/string_f.png";
            }
            case 8: {
                if (!bl) {
                    return "icons/graph database.png";
                }
                return "icons/graph database_f.png";
            }
            case 23: {
                if (!bl) {
                    return "icons/group.png";
                }
                return "icons/group_f.png";
            }
            case 2: {
                if (!bl) {
                    return "icons/matrix.png";
                }
                return "icons/matrix_f.png";
            }
            case 35: {
                if (!bl) {
                    return "icons/os.png";
                }
                return "icons/os_f.png";
            }
            case 7: 
            case 26: {
                if (!bl) {
                    return "icons/image.png";
                }
                return "icons/image_f.png";
            }
            case 5: 
            case 6: {
                if (!bl) {
                    return "icons/program.png";
                }
                return "icons/program_f.png";
            }
        }
        if (!bl) {
            return "icons/string.png";
        }
        return "icons/string_f.png";
    }

    public String sizeFormat(int n) {
        if (n < 1000) {
            return "" + n + sizeFormatByte;
        }
        return "" + n / 1000 + sizeFormatKByte;
    }
}

