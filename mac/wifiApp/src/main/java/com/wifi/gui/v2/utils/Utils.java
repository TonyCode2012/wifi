package com.wifi.gui.v2.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Utils {

    private static ObjectMapper objMapper = new ObjectMapper();
    private static boolean testFlag = false;

    public static void addCmdCode2File(int cmdCode,String filePath) {
        try {
            File file = new File(filePath);
            ObjectNode objNode = (ObjectNode) objMapper.readTree(file);
            objNode.put("command",Integer.toString(cmdCode));
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
            writer.write(objNode.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setIconByWidth(double ratio,double fWidth,ImageIcon icon) {
        if(ratio > 1) {
            System.out.println("[ERROR] Can't set ratio more than 1!");
            ratio = 1;
        }
        double iWidth = icon.getIconWidth();
        double iHeight = icon.getIconHeight();
        double iRatio = iHeight / iWidth;
        int iconWidth = (int) (fWidth * ratio);
        int iconHeight = (int) (iconWidth * iRatio);
        icon.setImage(icon.getImage().getScaledInstance(iconWidth,iconHeight,Image.SCALE_DEFAULT));
    }

    public static void setIconByWidth(double ratio,double fWidth,String iconPath) {
        if(ratio > 1) {
            System.out.println("[ERROR] Can't set ratio more than 1!");
            ratio = 1;
        }
        ImageIcon icon = new ImageIcon(iconPath);
        double iWidth = icon.getIconWidth();
        double iHeight = icon.getIconHeight();
        double iRatio = iHeight / iWidth;
        int iconWidth = (int) (fWidth * ratio);
        int iconHeight = (int) (iconWidth * iRatio);
        icon.setImage(icon.getImage().getScaledInstance(iconWidth,iconHeight,Image.SCALE_DEFAULT));
    }

    public static void setIconByHeight(double ratio,double fHeight,ImageIcon icon) {
        if(ratio > 1) {
            System.out.println("[ERROR] Can't set ratio more than 1!");
            ratio = 1;
        }
        double iWidth = icon.getIconWidth();
        double iHeight = icon.getIconHeight();
        double iRatio = iWidth / iHeight;
        int iconHeight = (int) (fHeight * ratio);
        int iconWidth = (int) (iconHeight * iRatio);
        icon.setImage(icon.getImage().getScaledInstance(iconWidth,iconHeight,Image.SCALE_DEFAULT));
    }

    public static void setIconByHeight(double ratio,double fHeight,String iconPath) {
        if(ratio > 1) {
            System.out.println("[ERROR] Can't set ratio more than 1!");
            ratio = 1;
        }
        ImageIcon icon = new ImageIcon(iconPath);
        double iWidth = icon.getIconWidth();
        double iHeight = icon.getIconHeight();
        double iRatio = iWidth / iHeight;
        int iconHeight = (int) (fHeight * ratio);
        int iconWidth = (int) (iconHeight * iRatio);
        icon.setImage(icon.getImage().getScaledInstance(iconWidth,iconHeight,Image.SCALE_DEFAULT));
    }

    public static boolean getTestFlag() {
        return testFlag;
    }

    public static void setTestFlag(boolean testFlag) {
        Utils.testFlag = testFlag;
    }
}
