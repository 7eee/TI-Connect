/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.geometry.Point2D
 *  javafx.scene.Node
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyEvent
 */
package com.ti.et.elg.commonUISupport.UINavigation;

import com.ti.et.elg.commonUISupport.UINavigation.FXKeysetDefinitions;
import com.ti.et.elg.commonUISupport.utils.DisplaysAsSelected;
import com.ti.et.elg.commonUISupport.utils.FXNodeInterface;
import com.ti.et.elg.commonUISupport.utils.FXNodeUtils;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public abstract class FXSelectableRowBasedListActionHandler {
    private List<FXNodeInterface> itemList;

    private Point2D positionInParent(List<FXNodeInterface> list, int n) {
        Node node = list.get(n).getRootNode();
        return FXNodeUtils.originToParent(node);
    }

    private int numColumns() {
        int n;
        int n2 = this.itemList.size();
        if (n2 < 2) {
            n = n2;
        } else {
            n = 0;
            double d = this.positionInParent(this.itemList, n).getY();
            if (d == this.positionInParent(this.itemList, n2 - 1).getY()) {
                n = n2;
            } else if (n2 == 2) {
                n = 1;
            } else {
                ++n;
                while (d == this.positionInParent(this.itemList, n).getY() && ++n < n2) {
                }
            }
        }
        return n;
    }

    private void setDirectivity(int n) {
        boolean bl = this.getNoDirectivity();
        int[] arrn = this.getSelection();
        int n2 = arrn[0];
        int n3 = arrn[1];
        arrn = null;
        if (n == 0) {
            if (!bl) {
                this.setSelection(n2, n3, true);
            }
            return;
        }
        if (bl && n2 != n3 && n > 0 != n2 < n3) {
            n2 ^= (n3 ^= (n2 ^= n3));
        }
        if (bl) {
            this.setSelection(n2, n3, false);
        }
    }

    public void handle(KeyEvent keyEvent) {
        int n;
        int n2;
        KeyCode keyCode;
        int n3;
        int n4;
        boolean bl;
        boolean bl2 = false;
        boolean bl3 = keyEvent.isControlDown();
        KeyCode keyCode2 = keyEvent.getCode();
        if (!FXKeysetDefinitions.shiftable_isSimpleKeyEvent(keyEvent) && (keyCode2 != KeyCode.HOME && keyCode2 != KeyCode.END || keyEvent.isConsumed() || keyEvent.isAltDown() || keyEvent.isMetaDown() || !bl3) && !(bl2 = FXKeysetDefinitions.isMacOnlyNavKeyEvent(keyEvent))) {
            return;
        }
        if (bl2) {
            switch (keyCode2) {
                case UP: {
                    keyCode = KeyCode.HOME;
                    bl = true;
                    break;
                }
                case DOWN: {
                    keyCode = KeyCode.END;
                    bl = true;
                    break;
                }
                case LEFT: {
                    keyCode = KeyCode.HOME;
                    bl = false;
                    break;
                }
                case RIGHT: {
                    keyCode = KeyCode.END;
                    bl = false;
                    break;
                }
                default: {
                    keyCode = keyCode2;
                    bl = bl3;
                    break;
                }
            }
        } else {
            keyCode = keyCode2;
            bl = bl3;
        }
        this.itemList = this.getItemList();
        boolean bl4 = this.getSelectedItemList().isEmpty();
        int n5 = this.itemList.size();
        switch (keyCode) {
            case HOME: {
                if (bl4) {
                    n = 0;
                    n3 = 1;
                    break;
                }
                n2 = this.getSelection()[1];
                n4 = this.numColumns();
                if (n4 <= 0) {
                    return;
                }
                n = (bl ? 0 : n2 / n4 * n4) - n2;
                n3 = -1;
                break;
            }
            case END: {
                if (bl4) {
                    n = 0;
                    n3 = -1;
                    break;
                }
                n2 = this.getSelection()[1];
                n4 = this.numColumns();
                if (n4 <= 0) {
                    return;
                }
                n = (bl ? n5 - 1 : n2 / n4 * n4 + (n4 - 1)) - n2;
                n3 = 1;
                break;
            }
            case UP: {
                n = - this.numColumns();
                n3 = -1;
                break;
            }
            case DOWN: {
                n = this.numColumns();
                n3 = 1;
                break;
            }
            case LEFT: {
                n = -1;
                n3 = -1;
                break;
            }
            case RIGHT: {
                n = 1;
                n3 = 1;
                break;
            }
            default: {
                return;
            }
        }
        n2 = (int)keyEvent.isShiftDown() ? 1 : 0;
        this.setDirectivity(n2 != 0 ? n3 : 0);
        n4 = this.getSelection()[1];
        int n6 = n4 + n;
        if (n > 0 && n6 >= n5) {
            int n7 = n6 = n2 != 0 ? n5 - 1 : n4;
        }
        if (n6 < 0) {
            int n8 = n6 = n2 != 0 ? 0 : n4;
        }
        if (bl4) {
            int n9 = n6 = n3 > 0 ? 0 : n5 - 1;
        }
        if (bl4 || n6 != n4 || n2 == 0 && n5 > 1) {
            FXNodeInterface fXNodeInterface = this.itemList.get(n6);
            if (bl4 || n2 == 0) {
                this.selectItem(fXNodeInterface);
            } else {
                this.shiftSelectItem(fXNodeInterface);
            }
            this.onSelectionEndChange(n6);
        }
    }

    protected abstract void shiftSelectItem(FXNodeInterface var1);

    protected abstract void selectItem(FXNodeInterface var1);

    protected abstract List<FXNodeInterface> getItemList();

    protected abstract List<FXNodeInterface> getSelectedItemList();

    protected abstract void onSelectionEndChange(int var1);

    protected abstract void setSelection(int var1, int var2, boolean var3);

    protected abstract boolean getNoDirectivity();

    protected abstract int[] getSelection();

    public static int selectItem(List list, List list2, DisplaysAsSelected displaysAsSelected, Runnable runnable) {
        List list3 = list;
        List list4 = list2;
        boolean bl = false;
        if (list4 != null) {
            if (!list4.isEmpty()) {
                for (DisplaysAsSelected displaysAsSelected2 : list4) {
                    if (displaysAsSelected2 != displaysAsSelected) {
                        displaysAsSelected2.setSelected(false);
                        continue;
                    }
                    bl = true;
                }
                list4.clear();
            }
            list4.add(displaysAsSelected);
        }
        if (!bl) {
            displaysAsSelected.setSelected(true);
        }
        int n = list3.indexOf(displaysAsSelected);
        if (list4 != null && runnable != null) {
            runnable.run();
        }
        return n;
    }

    public static int[] shiftSelectItem(List list, List list2, int n, int n2, DisplaysAsSelected displaysAsSelected, boolean[] arrbl) {
        DisplaysAsSelected displaysAsSelected2;
        int n3;
        List list3 = list;
        List list4 = list2;
        boolean bl = arrbl != null && arrbl.length > 0 ? arrbl[0] : true;
        int n4 = list3.indexOf(displaysAsSelected);
        boolean bl2 = !list4.contains(list3.get(n));
        int[] arrn = null;
        int[] arrn2 = null;
        int[] arrn3 = null;
        if (n != n2) {
            if (n4 > n2) {
                if (n < n2) {
                    arrn2 = new int[]{n2 + 1, n4};
                } else {
                    arrn = new int[]{n2, Math.min(n, n4) - 1};
                }
            } else if (n4 < n2) {
                if (n < n2) {
                    arrn = new int[]{Math.max(n, n4) + 1, n2};
                } else {
                    arrn2 = new int[]{n4, n2 - 1};
                }
            }
            if (n4 < n && n < n2) {
                arrn3 = new int[]{n4, n - 1};
            } else if (n < n4 && n2 < n) {
                arrn3 = new int[]{n + 1, n4};
            }
        }
        if (arrn != null) {
            for (void var15_14 = arrn[0]; var15_14 <= arrn[1]; ++var15_14) {
                displaysAsSelected2 = (DisplaysAsSelected)list3.get((int)var15_14);
                displaysAsSelected2.setSelected(false);
                list4.remove(displaysAsSelected2);
            }
            displaysAsSelected2 = null;
        }
        for (int[] arrn4 : new int[][]{arrn2, arrn3}) {
            if (arrn4 == null) continue;
            for (n3 = arrn4[0]; n3 <= arrn4[1]; ++n3) {
                displaysAsSelected2 = (DisplaysAsSelected)list3.get(n3);
                displaysAsSelected2.setSelected(true);
                list4.add(displaysAsSelected2);
            }
            displaysAsSelected2 = null;
        }
        if (bl2) {
            displaysAsSelected2 = (DisplaysAsSelected)list3.get(n);
            displaysAsSelected2.setSelected(true);
            list4.add(displaysAsSelected2);
            displaysAsSelected2 = null;
        }
        boolean bl3 = arrn2 != null || arrn3 != null;
        int n5 = n2;
        n2 = n4;
        int[] arrn5 = FXSelectableRowBasedListActionHandler.getSelectionRange(list3, list4, n, n2);
        int n6 = arrn5[0];
        n3 = arrn5[1];
        arrn5 = null;
        boolean bl4 = bl = n == n2 && n6 == n && n3 == n;
        if (!bl) {
            if (n < n2) {
                bl3 = bl3 || n3 > n4;
                n = n6;
                n2 = n3;
            } else {
                bl3 = bl3 || n6 < n4;
                n = n3;
                n2 = n6;
            }
        }
        if (bl3) {
            FXSelectableRowBasedListActionHandler.reselectRange(list3, list4, n, n2);
        } else if (arrn == null && n5 != n2) {
            FXSelectableRowBasedListActionHandler.selectItems(list3, list4, n5 + (n5 > n2 ? -1 : 1), n2);
        }
        if (arrbl != null && arrbl.length > 0) {
            arrbl[0] = bl;
        }
        return new int[]{n, n2};
    }

    public static int[] ctrlCmdSelectItem(List list, List list2, int n, int n2, DisplaysAsSelected displaysAsSelected, boolean[] arrbl) {
        List list3 = list;
        List list4 = list2;
        boolean bl = arrbl != null && arrbl.length > 0 ? arrbl[0] : true;
        int n3 = list4.indexOf(displaysAsSelected);
        if (n3 == -1) {
            n3 = list3.indexOf(displaysAsSelected);
            displaysAsSelected.setSelected(true);
            list4.add(displaysAsSelected);
            int[] arrn = FXSelectableRowBasedListActionHandler.getSelectionRange(list3, list4, n3);
            int n4 = arrn[0];
            int n5 = arrn[1];
            arrn = null;
            if (n4 == n5) {
                bl = true;
                n2 = n = n4;
            } else {
                if (n4 != n3 && n5 != n3) {
                    bl = true;
                    n = n4;
                    n2 = n5;
                } else {
                    bl = false;
                    n2 = n3;
                    n = n4 == n3 ? n5 : n4;
                }
                FXSelectableRowBasedListActionHandler.reselectRange(list3, list4, n, n2);
            }
        } else {
            boolean bl2 = false;
            displaysAsSelected.setSelected(false);
            n3 = list3.indexOf(list4.remove(n3));
            if (n2 == n3) {
                if (n == n2) {
                    if (list4.isEmpty()) {
                        bl = true;
                        n = 0;
                        n2 = 0;
                    } else {
                        int n6 = n3;
                        n3 = FXSelectableRowBasedListActionHandler.findAdjacentSelectionIndexFrontBack(list3, list4, n6);
                        int[] arrn = FXSelectableRowBasedListActionHandler.getSelectionRange(list3, list4, n3);
                        int n7 = arrn[0];
                        int n8 = arrn[1];
                        arrn = null;
                        if (n6 > n8) {
                            n = n7;
                            n2 = n8;
                        } else {
                            n = n8;
                            n2 = n7;
                        }
                        if (n2 != list3.indexOf(list4.get(list4.size() - 1))) {
                            FXSelectableRowBasedListActionHandler.reselectRange(list3, list4, n, n2);
                        }
                        bl = n == n2;
                    }
                } else {
                    n2 += n < n2 ? -1 : 1;
                    bl2 = true;
                }
            } else if (n == n3) {
                n += n > n2 ? -1 : 1;
                bl2 = true;
            } else if (n > n2 && n > n3 && n3 > n2 || n < n2 && n < n3 && n3 < n2) {
                n = n3 + (n > n2 ? -1 : 1);
                bl2 = true;
            }
            if (bl2 && n == n2) {
                bl = true;
            }
        }
        if (arrbl != null && arrbl.length > 0) {
            arrbl[0] = bl;
        }
        return new int[]{n, n2};
    }

    private static int findAdjacentSelectionIndexFrontBack(List<DisplaysAsSelected> list, List<DisplaysAsSelected> list2, int n) {
        int n2;
        block2 : {
            int n3;
            n2 = -1;
            for (n3 = n - 1; n3 >= 0; --n3) {
                if (list2.indexOf(list.get(n3)) < 0) continue;
                n2 = n3;
                break block2;
            }
            int n4 = list.size();
            for (n3 = n + 1; n3 < n4; ++n3) {
                if (list2.indexOf(list.get(n3)) < 0) continue;
                n2 = n3;
                break;
            }
        }
        return n2;
    }

    private static int[] getSelectionRange(List<DisplaysAsSelected> list, List<DisplaysAsSelected> list2, int n, int n2) {
        if (n2 < n) {
            n ^= (n2 ^= (n ^= n2));
        }
        return FXSelectableRowBasedListActionHandler.spc_getSelectionRange(list, list2, n, n2);
    }

    private static int[] getSelectionRange(List<DisplaysAsSelected> list, List<DisplaysAsSelected> list2, int n) {
        return FXSelectableRowBasedListActionHandler.spc_getSelectionRange(list, list2, n, n);
    }

    private static int[] spc_getSelectionRange(List<DisplaysAsSelected> list, List<DisplaysAsSelected> list2, int n, int n2) {
        int[] arrn = new int[]{n, n2};
        int n3 = list.size();
        int n4 = n - 1;
        while (n4 >= 0 && list2.indexOf(list.get(n4)) >= 0) {
            arrn[0] = n4--;
        }
        n4 = n2 + 1;
        while (n4 < n3 && list2.indexOf(list.get(n4)) >= 0) {
            arrn[1] = n4++;
        }
        n4 = arrn[0];
        if (n4 == arrn[1] && (n4 < 0 || n4 >= n3 || list2.indexOf(list.get(n4)) < 0)) {
            arrn[0] = 0;
            arrn[1] = 0;
        }
        return arrn;
    }

    private static void reselectRange(List<DisplaysAsSelected> list, List<DisplaysAsSelected> list2, int n, int n2) {
        FXSelectableRowBasedListActionHandler.reselectItems(list, list2, n, n2, true);
    }

    private static void selectItems(List<DisplaysAsSelected> list, List<DisplaysAsSelected> list2, int n, int n2) {
        FXSelectableRowBasedListActionHandler.reselectItems(list, list2, n, n2, false);
    }

    private static void reselectItems(List<DisplaysAsSelected> list, List<DisplaysAsSelected> list2, int n, int n2, boolean bl) {
        int n3;
        int n4 = n3 = n > n2 ? -1 : 1;
        while (n != (n2 += n3)) {
            DisplaysAsSelected displaysAsSelected = list.get(n);
            displaysAsSelected.setSelected(true);
            if (bl) {
                list2.remove(displaysAsSelected);
            }
            list2.add(displaysAsSelected);
            n += n3;
        }
    }

}

