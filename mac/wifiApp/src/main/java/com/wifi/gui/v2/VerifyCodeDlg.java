package com.wifi.gui.v2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wifi.configSetting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import static java.lang.Thread.sleep;

public class VerifyCodeDlg extends JDialog {
    private JPanel contentPane;
    private JButton okBtn;
    private JButton cancelBtn;
    private JTextField verifyCodeText;
    private JLabel tipLabel;
    private JLabel timeoutLabel;

    private String rootPath = System.getProperty("user.dir");
    private Register register;
    private ObjectMapper objMapper = new ObjectMapper();

    private void init() {
        tipLabel.setText("   ");
        timeoutLabel.setText("60s");
        // set timeout tips
        new Thread(() -> {
            try {
                for (int i = 59; i >= 0; i--) {
                    timeoutLabel.setText(i + "s");
                    sleep(1000);
                }
                register.setConnStatusCode(304);
                dispose();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }).start();
    }

    public VerifyCodeDlg() {
        init();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(okBtn);
        JDialog that = this;
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    try {
                        String pinCode = verifyCodeText.getText();
                        if(pinCode.equals("")) {
                            tipLabel.setText("请输入验证码");
                            tipLabel.setForeground(Color.red);
                            return;
                        }
                        // write pin code info to pin file
                        String pinCodePath = getPinCodePath();
                        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(pinCodePath));
                        ObjectNode objNode = objMapper.createObjectNode();
                        JsonNode profileNode = objMapper.readTree(new FileReader(getProfilePath()));
                        String accountStr = profileNode.get("account").asText();
                        String phone = profileNode.get("phone").asText();
                        objNode.put("account",accountStr);
                        objNode.put("pin",pinCode);
                        objNode.put("phone",phone);
                        writer.write(objNode.toString());
                        writer.close();
                        that.setVisible(false);
                        ProcessBuilder pb = new ProcessBuilder(
                                "wlan.sh",
                                "connect",
                                rootPath.concat("/").concat(configSetting.getWpaPriKeyPath()),
                                pinCodePath,
                                rootPath.concat("/").concat(configSetting.getWpaConfPath()),
                                rootPath.concat("/wpa_setup/wpa.log")
                        );
//                        ProcessBuilder pb = new ProcessBuilder(
//                                rootPath + "/wpa_setup/testPinCode.sh"
//                        );
                        Process process = pb.start();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String line;
                        StringBuilder sb = new StringBuilder();
                        while((line = reader.readLine()) != null) {
                            sb.append(line);
                            System.out.println(line);
                        }
                        process.waitFor();
                        String retStr = sb.toString();
                        int cStatusCode;
                        if(retStr.contains("main connect return code")) {
                            String retStrArry[] = retStr.split(" ");
                            cStatusCode = Integer.parseInt(retStrArry[retStrArry.length-1]);
                        } else {
                            cStatusCode = 404;
                        }
                        register.setConnStatusCode(cStatusCode);
                    } catch (IOException|InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                    dispose();
                }).start();
            }
        });
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register.setConnStatusCode(303);
                dispose();
            }
        });
    }

    public void setRegisterPage(Register register) {
        this.register = register;
    }

    private String getPinCodePath() {
        return rootPath.concat("/").concat(configSetting.getWpaPinCodePath());
    }

    private String getProfilePath() {
        return rootPath.concat("/").concat(configSetting.getProfileFPath());
    }
}
