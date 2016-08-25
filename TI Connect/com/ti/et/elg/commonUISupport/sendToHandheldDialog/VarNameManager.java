/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.commonUISupport.sendToHandheldDialog;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class VarNameManager {
    private static final ResourceBundle resources = CommonUISupportResourceBundle.BUNDLE;
    private static final String numberCustomNamePattern = "[A-Z[\u03b8]]";
    private static final String listCustomNamePattern = "[A-Za-z\u03b8\u0398\u03d1\u03f4\u1dbf][A-Za-z0-9\u03b8\u0398\u03d1\u03f4\u1dbf]{0,4}";
    private static final String programCustomNamePattern = "[A-Za-z\u03b8\u0398\u03d1\u03f4\u1dbf][A-Za-z0-9\u03b8\u0398\u03d1\u03f4\u1dbf]{0,7}";
    private static final String groupCustomNamePattern = "[A-Za-z\u03b8\u0398\u03d1\u03f4\u1dbf][A-Za-z0-9\u03b8\u0398\u03d1\u03f4\u1dbf]{0,7}";
    private static final String equationFunctionCustomNamePattern = "Y[\u2080-\u2089]";
    private static final String equationParametricCustomNamePattern = "[X-Y][\u2081-\u2086]\uf038";
    private static final String equationPolarCustomNamePattern = "r[\u2081-\u2086]";
    private static final String equationSequenceCustomNamePattern = "[[u][v][w]]";
    private static final int numberNameSizeLimit = 1;
    private static final int listNameSizeLimit = 5;
    private static final int programNameSizeLimit = 8;
    private static final int groupNameSizeLimit = 8;
    private static final String numberPredefinedNames = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,\u03b8";
    private static final String listPredefinedNames = "L\u2081,L\u2082,L\u2083,L\u2084,L\u2085,L\u2086";
    private static final String matrixPredefinedNames = "[A],[B],[C],[D],[E],[F],[G],[H],[I],[J]";
    private static final String picPredefinedNames = "Pic0,Pic1,Pic2,Pic3,Pic4,Pic5,Pic6,Pic7,Pic8,Pic9";
    private static final String imagePredefinedNames = "Image0,Image1,Image2,Image3,Image4,Image5,Image6,Image7,Image8,Image9";
    private static final String gbdPredefinedNames = "GDB0,GDB1,GDB2,GDB3,GDB4,GDB5,GDB6,GDB7,GDB8,GDB9";
    private static final String stringsPredefinedNames = "Str0,Str1,Str2,Str3,Str4,Str5,Str6,Str7,Str8,Str9";
    private static final String equationFunctionPredefinedNames = "Y\u2080,Y\u2081,Y\u2082,Y\u2083,Y\u2084,Y\u2085,Y\u2086,Y\u2087,Y\u2088,Y\u2089";
    private static final String equationParametricPredefinedNames = "X\u2081\uf038,X\u2082\uf038,X\u2083\uf038,X\u2084\uf038,X\u2085\uf038,X\u2086\uf038,Y\u2081\uf038,Y\u2082\uf038,Y\u2083\uf038,Y\u2084\uf038,Y\u2085\uf038,Y\u2086\uf038";
    private static final String equationPolarPredefinedNames = "r\u2081,r\u2082,r\u2083,r\u2084,r\u2085,r\u2086";
    private static final String equationSequencePredefinedNames = "u,v,w";
    private static final String locationArchive = resources.getString("table.location.archive.value");
    private static final String locationRAM = resources.getString("table.location.ram.value");
    private static Map<Integer, String> typeIdToCustomNamePattern = new HashMap<Integer, String>();
    private static Map<Integer, String> typeIdToPredefinedNames = new HashMap<Integer, String>();
    private static Map<Integer, Integer> typeIdToNameSizeLimit = new HashMap<Integer, Integer>();
    private static ArrayList<Integer> typesWithCustomNames = new ArrayList();
    private static Map<Integer, String> typeIdToLocation = new HashMap<Integer, String>();
    private static ArrayList<Integer> oSTypes = new ArrayList();

    public static boolean isCustomNameValid(int n, String string) {
        String string2 = typeIdToCustomNamePattern.get(n);
        if (string2 != null) {
            return string.matches(string2);
        }
        return false;
    }

    public static String[] getPredefinedNames(int n) {
        String string = typeIdToPredefinedNames.get(n);
        if (string != null) {
            return string.split(",");
        }
        return new String[0];
    }

    public static String[] getAllowedLocations(String string, int n, int n2) {
        String string2 = typeIdToLocation.get(n);
        if (string != null) {
            switch (n) {
                case 0: 
                case 12: {
                    if (!string.equalsIgnoreCase("X") && !string.equalsIgnoreCase("Y") && !string.equalsIgnoreCase("T") && !string.equalsIgnoreCase("\u03b8")) break;
                    string2 = locationRAM;
                    break;
                }
                case 1: 
                case 13: {
                    if (!string.equalsIgnoreCase("RESID") && !string.equalsIgnoreCase("IDList")) break;
                    string2 = locationRAM;
                    break;
                }
                case 7: {
                    if (n2 >= 10) break;
                    string2 = string2 + "," + locationRAM;
                    break;
                }
            }
        }
        if (string2 != null) {
            return string2.split(",");
        }
        return new String[0];
    }

    public static int getNameSizeLimit(int n) {
        return typeIdToNameSizeLimit.get(n);
    }

    public static String getRegularExpression(int n) {
        return typeIdToCustomNamePattern.get(n);
    }

    public static boolean isCustomNameAllowed(int n) {
        return typesWithCustomNames.contains(n);
    }

    public static boolean isOSType(int n) {
        return oSTypes.contains(n);
    }

    static {
        typeIdToCustomNamePattern.put(0, "[A-Z[\u03b8]]");
        typeIdToCustomNamePattern.put(12, "[A-Z[\u03b8]]");
        typeIdToCustomNamePattern.put(13, "[A-Za-z\u03b8\u0398\u03d1\u03f4\u1dbf][A-Za-z0-9\u03b8\u0398\u03d1\u03f4\u1dbf]{0,4}");
        typeIdToCustomNamePattern.put(1, "[A-Za-z\u03b8\u0398\u03d1\u03f4\u1dbf][A-Za-z0-9\u03b8\u0398\u03d1\u03f4\u1dbf]{0,4}");
        typeIdToCustomNamePattern.put(144, "Y[\u2080-\u2089]");
        typeIdToCustomNamePattern.put(145, "[X-Y][\u2081-\u2086]\uf038");
        typeIdToCustomNamePattern.put(146, "r[\u2081-\u2086]");
        typeIdToCustomNamePattern.put(147, "[[u][v][w]]");
        typeIdToCustomNamePattern.put(5, "[A-Za-z\u03b8\u0398\u03d1\u03f4\u1dbf][A-Za-z0-9\u03b8\u0398\u03d1\u03f4\u1dbf]{0,7}");
        typeIdToCustomNamePattern.put(6, "[A-Za-z\u03b8\u0398\u03d1\u03f4\u1dbf][A-Za-z0-9\u03b8\u0398\u03d1\u03f4\u1dbf]{0,7}");
        typeIdToCustomNamePattern.put(23, "[A-Za-z\u03b8\u0398\u03d1\u03f4\u1dbf][A-Za-z0-9\u03b8\u0398\u03d1\u03f4\u1dbf]{0,7}");
        typeIdToCustomNamePattern.put(27, "[A-Z[\u03b8]]");
        typeIdToCustomNamePattern.put(29, "[A-Z[\u03b8]]");
        typeIdToCustomNamePattern.put(30, "[A-Z[\u03b8]]");
        typeIdToCustomNamePattern.put(31, "[A-Z[\u03b8]]");
        typeIdToCustomNamePattern.put(28, "[A-Z[\u03b8]]");
        typeIdToCustomNamePattern.put(32, "[A-Z[\u03b8]]");
        typeIdToCustomNamePattern.put(33, "[A-Z[\u03b8]]");
        typeIdToNameSizeLimit.put(0, 1);
        typeIdToNameSizeLimit.put(12, 1);
        typeIdToNameSizeLimit.put(13, 5);
        typeIdToNameSizeLimit.put(1, 5);
        typeIdToNameSizeLimit.put(5, 8);
        typeIdToNameSizeLimit.put(6, 8);
        typeIdToNameSizeLimit.put(23, 8);
        typeIdToNameSizeLimit.put(27, 1);
        typeIdToNameSizeLimit.put(29, 1);
        typeIdToNameSizeLimit.put(30, 1);
        typeIdToNameSizeLimit.put(31, 1);
        typeIdToNameSizeLimit.put(28, 1);
        typeIdToNameSizeLimit.put(32, 1);
        typeIdToNameSizeLimit.put(33, 1);
        typeIdToPredefinedNames.put(0, "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,\u03b8");
        typeIdToPredefinedNames.put(12, "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,\u03b8");
        typeIdToPredefinedNames.put(13, "L\u2081,L\u2082,L\u2083,L\u2084,L\u2085,L\u2086");
        typeIdToPredefinedNames.put(1, "L\u2081,L\u2082,L\u2083,L\u2084,L\u2085,L\u2086");
        typeIdToPredefinedNames.put(2, "[A],[B],[C],[D],[E],[F],[G],[H],[I],[J]");
        typeIdToPredefinedNames.put(7, "Pic0,Pic1,Pic2,Pic3,Pic4,Pic5,Pic6,Pic7,Pic8,Pic9");
        typeIdToPredefinedNames.put(26, "Image0,Image1,Image2,Image3,Image4,Image5,Image6,Image7,Image8,Image9");
        typeIdToPredefinedNames.put(8, "GDB0,GDB1,GDB2,GDB3,GDB4,GDB5,GDB6,GDB7,GDB8,GDB9");
        typeIdToPredefinedNames.put(4, "Str0,Str1,Str2,Str3,Str4,Str5,Str6,Str7,Str8,Str9");
        typeIdToPredefinedNames.put(144, "Y\u2080,Y\u2081,Y\u2082,Y\u2083,Y\u2084,Y\u2085,Y\u2086,Y\u2087,Y\u2088,Y\u2089");
        typeIdToPredefinedNames.put(145, "X\u2081\uf038,X\u2082\uf038,X\u2083\uf038,X\u2084\uf038,X\u2085\uf038,X\u2086\uf038,Y\u2081\uf038,Y\u2082\uf038,Y\u2083\uf038,Y\u2084\uf038,Y\u2085\uf038,Y\u2086\uf038");
        typeIdToPredefinedNames.put(146, "r\u2081,r\u2082,r\u2083,r\u2084,r\u2085,r\u2086");
        typeIdToPredefinedNames.put(147, "u,v,w");
        typeIdToPredefinedNames.put(27, "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,\u03b8");
        typeIdToPredefinedNames.put(29, "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,\u03b8");
        typeIdToPredefinedNames.put(30, "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,\u03b8");
        typeIdToPredefinedNames.put(31, "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,\u03b8");
        typeIdToPredefinedNames.put(28, "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,\u03b8");
        typeIdToPredefinedNames.put(32, "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,\u03b8");
        typeIdToPredefinedNames.put(33, "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,\u03b8");
        typesWithCustomNames.add(13);
        typesWithCustomNames.add(1);
        typesWithCustomNames.add(5);
        typesWithCustomNames.add(6);
        typesWithCustomNames.add(23);
        typeIdToLocation.put(0, locationArchive + "," + locationRAM);
        typeIdToLocation.put(12, locationArchive + "," + locationRAM);
        typeIdToLocation.put(13, locationArchive + "," + locationRAM);
        typeIdToLocation.put(2, locationArchive + "," + locationRAM);
        typeIdToLocation.put(7, locationArchive);
        typeIdToLocation.put(26, locationArchive);
        typeIdToLocation.put(8, locationArchive + "," + locationRAM);
        typeIdToLocation.put(3, locationRAM);
        typeIdToLocation.put(4, locationArchive + "," + locationRAM);
        typeIdToLocation.put(5, locationArchive + "," + locationRAM);
        typeIdToLocation.put(6, locationArchive + "," + locationRAM);
        typeIdToLocation.put(36, locationArchive);
        typeIdToLocation.put(23, locationArchive);
        typeIdToLocation.put(21, locationArchive + "," + locationRAM);
        typeIdToLocation.put(1, locationArchive + "," + locationRAM);
        typeIdToLocation.put(16, locationRAM);
        typeIdToLocation.put(15, locationRAM);
        typeIdToLocation.put(17, locationRAM);
        typeIdToLocation.put(27, locationArchive + "," + locationRAM);
        typeIdToLocation.put(29, locationArchive + "," + locationRAM);
        typeIdToLocation.put(30, locationArchive + "," + locationRAM);
        typeIdToLocation.put(31, locationArchive + "," + locationRAM);
        typeIdToLocation.put(28, locationArchive + "," + locationRAM);
        typeIdToLocation.put(32, locationArchive + "," + locationRAM);
        typeIdToLocation.put(33, locationArchive + "," + locationRAM);
        oSTypes.add(35);
    }
}

