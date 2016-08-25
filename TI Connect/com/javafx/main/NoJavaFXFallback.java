/*
 * Decompiled with CFR 0_115.
 */
package com.javafx.main;

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JTextPane;

public class NoJavaFXFallback
extends JApplet
implements ActionListener {
    boolean isInBrowser = false;
    boolean oldJRE = true;
    String requiredJavaFXVersion = null;
    boolean oldJavaFX = false;
    boolean doNotUseJNLPAPI = false;

    public NoJavaFXFallback() {
    }

    public NoJavaFXFallback(boolean needJREUpgrade, boolean needFXUpgrade, String requiredJavaFX) {
        this.isInBrowser = false;
        this.oldJavaFX = needFXUpgrade;
        this.requiredJavaFXVersion = requiredJavaFX;
        this.oldJRE = needJREUpgrade;
        this.doNotUseJNLPAPI = true;
        this.populate();
    }

    private static float getJavaVersionAsFloat() {
        int dash;
        String versionString = System.getProperty("java.version", "1.5.0");
        StringBuffer sb = new StringBuffer();
        int firstDot = versionString.indexOf(".");
        sb.append(versionString.substring(0, firstDot));
        int secondDot = versionString.indexOf(".", firstDot + 1);
        sb.append(versionString.substring(firstDot + 1, secondDot));
        int underscore = versionString.indexOf("_", secondDot + 1);
        if (underscore >= 0) {
            dash = versionString.indexOf("-", underscore + 1);
            if (dash < 0) {
                dash = versionString.length();
            }
            sb.append(versionString.substring(secondDot + 1, underscore)).append(".").append(versionString.substring(underscore + 1, dash));
        } else {
            dash = versionString.indexOf("-", secondDot + 1);
            if (dash < 0) {
                dash = versionString.length();
            }
            sb.append(versionString.substring(secondDot + 1, dash));
        }
        float version = 150.0f;
        try {
            version = Float.parseFloat(sb.toString());
        }
        catch (NumberFormatException e) {
            // empty catch block
        }
        return version;
    }

    private void test() {
        this.oldJRE = NoJavaFXFallback.getJavaVersionAsFloat() < 160.18f;
        try {
            Class jclass = Class.forName("netscape.javascript.JSObject");
            Class[] arrclass = new Class[1];
            Class class_ = Applet.class;
            arrclass[0] = class_;
            Method m = jclass.getMethod("getWindow", arrclass);
            this.isInBrowser = m.invoke(null, this) != null;
        }
        catch (Exception e) {
            // empty catch block
        }
    }

    String getText() {
        String text = "This application requires a newer version of the Java runtime. Please download and install the latest Java runtime from java.com.";
        text = this.isInBrowser ? text + " Then restart the browser." : text + " Then restart the application.";
        return text;
    }

    public void init() {
        this.requiredJavaFXVersion = this.getParameter("requiredFXVersion");
        this.test();
        this.populate();
    }

    private void populate() {
        Container pane = this.getContentPane();
        pane.setLayout(new BorderLayout());
        JTextPane l = new JTextPane();
        l.setText(this.getText());
        l.setEditable(false);
        pane.add((Component)l, "Center");
        if (NoJavaFXFallback.getJavaVersionAsFloat() > 160.0f || NoJavaFXFallback.getJavaVersionAsFloat() > 150.0f && !this.doNotUseJNLPAPI) {
            JButton installButton = new JButton("Install Now");
            installButton.addActionListener(this);
            pane.add((Component)installButton, "South");
        }
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            URL u = new URL("http://java.com/");
            if (this.isInBrowser) {
                this.getAppletContext().showDocument(u);
            } else if (!this.doNotUseJNLPAPI) {
                Class sm = Class.forName("javax.jnlp.ServiceManager");
                Class bs = Class.forName("javax.jnlp.BasicService");
                Class[] arrclass = new Class[1];
                Class class_ = String.class;
                arrclass[0] = class_;
                Method lookup = sm.getMethod("lookup", arrclass);
                Class[] arrclass2 = new Class[1];
                Class class_2 = URL.class;
                arrclass2[0] = class_2;
                Method showDoc = bs.getMethod("showDocument", arrclass2);
                Object s = lookup.invoke(null, "javax.jnlp.BasicService");
                showDoc.invoke(s, u);
            } else {
                Desktop d = Desktop.getDesktop();
                if (d != null) {
                    d.browse(u.toURI());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

