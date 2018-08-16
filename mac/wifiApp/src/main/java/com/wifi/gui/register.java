package com.wifi.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class register extends JDialog {
    private JPanel contentPane;
    private JButton aliReg;
    private JButton tencentReg;
    private JButton baiduReg;
    private JButton backBtn;
    private JButton buttonOK;

    public register(JFrame mainFrame) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(true);
                dispose();
            }
        });
        aliReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createRegDialog("阿里");
            }
        });
        tencentReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createRegDialog("腾讯");
            }
        });
        baiduReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createRegDialog("百度");
            }
        });
    }
    private void createRegDialog(String companyName) {
        profile dialog = new profile(companyName);
        dialog.pack();
        dialog.setLocation(600, 300);
        dialog.setVisible(true);
    }
}
