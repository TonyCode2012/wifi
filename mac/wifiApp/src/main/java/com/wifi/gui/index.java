package com.wifi.gui;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class index {
    private JButton regBtn;
    private JButton launchBtn;
    public JPanel rootPanel;
    private static JFrame frame;


    public index() {
        regBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                register dialog = new register(frame);
                dialog.pack();
                dialog.setLocation(600, 300);
                dialog.setVisible(true);
            }
        });
        launchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launch dialog = new launch();
                dialog.pack();
                dialog.setLocation(600, 300);
                dialog.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        frame = new JFrame("index");
        frame.setContentPane(new index().rootPanel);
        frame.setLocation(600, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

