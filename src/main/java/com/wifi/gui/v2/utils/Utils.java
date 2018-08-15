package com.wifi.gui.v2.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

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

    public static Object deepClone(Object obj){
        try {
            //将对象写到流里
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            //从流里读回来
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (IOException|ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void setMyTabPanel(JLabel[] labelArry,JScrollPane[] panelArry) {
        if(labelArry.length != panelArry.length) {
            System.out.println("[ERROR] tabPanel's tab label number must equal to panel number!");
            return;
        }
        int labelLength = labelArry.length;
        Color pressedColor = new Color(181,180,181);
        Color releasedColor = labelArry[0].getBackground();
        for(int i=0;i<labelLength;i++) {
            JLabel label = labelArry[i];
            JScrollPane panel = panelArry[i];
            label.setOpaque(true);
            if(i == 0) {
                label.setBackground(pressedColor);
                label.setBorder(BorderFactory.createRaisedBevelBorder());
                panel.setVisible(true);
            } else {
                label.setBackground(releasedColor);
                label.setBorder(BorderFactory.createEtchedBorder());
                panel.setVisible(false);
            }
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    for(int j=0;j<labelArry.length;j++) {
                        JLabel nLabel = labelArry[j];
                        JScrollPane nPanel = panelArry[j];
                        if(!nLabel.equals(label)) {
                            nLabel.setBorder(BorderFactory.createEtchedBorder());
                            nLabel.setBackground(releasedColor);
                            nPanel.setVisible(false);
                        }
                    }
                    panel.setVisible(true);
                    label.setBorder(BorderFactory.createRaisedBevelBorder());
                    label.setBackground(pressedColor);
                }
            });
        }
    }

    public static boolean getTestFlag() {
        return testFlag;
    }

    public static void setTestFlag(boolean testFlag) {
        Utils.testFlag = testFlag;
    }
}
