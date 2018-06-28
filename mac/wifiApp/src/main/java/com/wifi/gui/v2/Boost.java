package com.wifi.gui.v2;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

import com.wifi.configSetting;
import com.wifi.gui.v2.utils.Utils;

public class Boost {

    private static JFrame frame = new JFrame();

    private static void InitGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys();
             keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }

    public static void main(String[] args) {
        if(args.length != 0) {
            Utils.setTestFlag(Boolean.parseBoolean(args[0]));
        }
        InitGlobalFont(new Font(Font.DIALOG, Font.PLAIN, Integer.parseInt(configSetting.getFontSize())));
        frame.setTitle("中国电信wifi");
        Register register = new Register();
        Launch launch = new Launch();
        HomePage homePage = new HomePage();
        homePage.setfJFrame(frame);
        homePage.setNextRegister(register);
        homePage.setNextLaunch(launch);
        register.setfJFrame(frame);
        register.setPreHomePage(homePage);
        launch.setfJFrame(frame);
        launch.setPreHomePage(homePage);
        frame.setContentPane(homePage.getRootPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(600,200);
//        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
