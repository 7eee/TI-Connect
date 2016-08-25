/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.utils.longestStringGenerator;

import com.ti.et.utils.encodingUtils.EncodingUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LongestStringGenerator {
    static Map<String, String> longestString = new HashMap<String, String>();
    static final String[] LANG_CODES = new String[]{"_de", "_es", "_fr", "_nl", "_pt", "_sv"};
    static final String EN_SMART_STRING = "_en__SMART";
    static final String SMART_STRING = "__SMART";
    static final String LANG_CODE_XL = "_xl";
    static final String PROP_VAL_SEPARATOR = "=";
    static final String VAL_TRANSLATION_TAG = "@[en]@";

    public static void main(String[] arrstring) {
        if (arrstring.length == 1 && !arrstring[0].isEmpty()) {
            File file = new File(arrstring[0]);
            if (file != null && file.canRead()) {
                LongestStringGenerator.processFile(file);
                File[] arrfile = LongestStringGenerator.getRelatedFiles(file);
                if (arrfile != null) {
                    for (File file2 : arrfile) {
                        LongestStringGenerator.processFile(file2);
                    }
                }
                LongestStringGenerator.createXLFile(file);
            } else {
                System.out.println("ERROR: Root string file path is not readable!");
            }
        } else {
            System.out.println("ERROR: Specify the path to the root strings file!");
        }
    }

    private static File[] getRelatedFiles(File file) {
        String string = file.getName();
        String string2 = file.getParent();
        File[] arrfile = new File[LANG_CODES.length];
        for (int i = 0; i < LANG_CODES.length; ++i) {
            String string3 = LANG_CODES[i];
            String string4 = LongestStringGenerator.makeFilenameWithLanguage(string, string3);
            if (string4 == null) {
                return null;
            }
            arrfile[i] = new File(string2, string4);
        }
        return arrfile;
    }

    private static String makeFilenameWithLanguage(String string, String string2) {
        int n = string.lastIndexOf(46);
        if (n > 0) {
            String string3 = string.substring(0, n);
            String string4 = string.substring(n, string.length());
            int n2 = string.indexOf("_en__SMART");
            if (n2 > 0) {
                String string5 = string3.substring(0, n2) + string2 + "__SMART";
                return string5 + string4;
            }
            return string3 + string2 + string4;
        }
        System.out.println("ERROR: " + string + " has no file extension!");
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void processFile(File file) {
        System.out.println("Processing File: " + file.getAbsolutePath());
        BufferedReader bufferedReader = null;
        try {
            String string;
            bufferedReader = new BufferedReader(new FileReader(file));
            while ((string = bufferedReader.readLine()) != null) {
                string = LongestStringGenerator.multipleLineProperty(string, bufferedReader);
                LongestStringGenerator.addIfLongest(string);
            }
        }
        catch (IOException var2_4) {
            System.out.println("ERROR: IOException reading string file: " + file.getAbsolutePath());
        }
        finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch (IOException var2_5) {
                    System.out.println("ERROR: IOException reading string file: " + file.getAbsolutePath());
                }
            }
        }
    }

    private static String multipleLineProperty(String string, BufferedReader bufferedReader) throws IOException {
        if (string != null && string.endsWith("\\")) {
            string = string.substring(0, string.length() - 1);
            return string + LongestStringGenerator.multipleLineProperty(bufferedReader.readLine(), bufferedReader);
        }
        return string;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void createXLFile(File file) {
        String string = LongestStringGenerator.makeFilenameWithLanguage(file.getName(), "_xl");
        if (string != null) {
            File file2 = new File(file.getParent(), string);
            System.out.println("Generating xl file: " + file2.getAbsolutePath());
            BufferedWriter bufferedWriter = null;
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(file2));
                for (String string2 : longestString.keySet()) {
                    String string3 = longestString.get(string2);
                    bufferedWriter.write(string2 + "=" + string3);
                    bufferedWriter.newLine();
                }
            }
            catch (IOException var4_6) {
                System.out.println("ERROR: IOException writing _xl file!");
            }
            finally {
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    }
                    catch (IOException var4_7) {
                        System.out.println("ERROR: IOException writing _xl file!");
                    }
                }
            }
        } else {
            System.out.println("ERROR: Unable to create _xl file!");
        }
    }

    private static void addIfLongest(String string) {
        int n = string.indexOf("=");
        if (n > 0) {
            String string2 = string.substring(0, n);
            if (string.length() > n + 1) {
                String string3 = string.substring(n + 1, string.length());
                string3 = LongestStringGenerator.stripTranslationTag(string3);
                String string4 = EncodingUtils.parseEscapedUnicodeInString(string3);
                String string5 = EncodingUtils.parseEscapedUnicodeInString(longestString.get(string2));
                if (string5 == null || string5.length() < string4.length()) {
                    longestString.put(string2, string3);
                }
            } else {
                System.out.println("ERROR: No value after '=' in line: " + string);
            }
        }
    }

    private static String stripTranslationTag(String string) {
        if (string.startsWith("@[en]@")) {
            return string.substring("@[en]@".length());
        }
        return string;
    }
}

