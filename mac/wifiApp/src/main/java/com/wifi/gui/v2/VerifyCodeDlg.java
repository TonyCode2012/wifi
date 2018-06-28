package com.wifi.gui.v2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wifi.configSetting;
import com.wifi.gui.v2.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class VerifyCodeDlg extends JDialog {
    private JPanel contentPane;
    private JButton okBtn;
    private JButton cancelBtn;
    private JTextField verifyCodeText;
    private JLabel tipLabel;
    private JLabel timeoutLabel;
    private JProgressBar waitUnregPb;
    private JLabel verifyCodeLabel;
    private JLabel unregStatLabel;

    private String rootPath = System.getProperty("user.dir");
    private Register register;
    private ObjectMapper objMapper = new ObjectMapper();
    private String dlgType;
    private int statusCode = -1;

    /*
    * -1: nothing to do, just show processing
    *  0: send requesting pin code message successfully
    *  1: send verifying unregister pin code process
    * */
    private int unregStatusCode = -1;

    private void init(String type) {
        dlgType = type;
        tipLabel.setText("   ");
        timeoutLabel.setText("60s");
        if(dlgType.equals("REGISTER")) {
            setRegStatus();
        } else {
            new Thread(() -> {
                try {
                    unregStatLabel.setText("正在发送请求...");
                    setUnregStatus();
                    while (unregStatusCode == -1) {
                        sleep(10);
                    }
                    if(unregStatusCode == 0) {
                        setRegStatus();
                    }
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }).start();
        }
    }

    private void setRegStatus() {
        okBtn.setVisible(true);
        cancelBtn.setVisible(true);
        tipLabel.setVisible(true);
        timeoutLabel.setVisible(true);
        verifyCodeText.setVisible(true);
        verifyCodeLabel.setVisible(true);
        waitUnregPb.setVisible(false);
        unregStatLabel.setVisible(false);
        showInvalidTimer();
        this.pack();
    }

    private void setUnregStatus() {
        okBtn.setVisible(false);
        cancelBtn.setVisible(false);
        tipLabel.setVisible(false);
        timeoutLabel.setVisible(false);
        verifyCodeText.setVisible(false);
        verifyCodeLabel.setVisible(false);
        waitUnregPb.setVisible(true);
        unregStatLabel.setVisible(true);
        viewBar();
        this.pack();
    }

    private void showInvalidTimer() {
        // set timeout tips
        new Thread(() -> {
            try {
                for (int i = 59; i >= 0 && statusCode == -1; i--) {
                    timeoutLabel.setText(i + "s");
                    sleep(1000);
                }
                if(statusCode == -1) {
                    register.setConnStatusCode(304);
                }
//                dispose();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }).start();
    }

    public VerifyCodeDlg(String type) {
        init(type);
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
                        if (pinCode.equals("")) {
                            tipLabel.setText("请输入验证码");
                            tipLabel.setForeground(Color.red);
                            return;
                        }
                        // write pin code info to pin file
                        String pinCodePath = getPinCodePath();
                        ObjectNode pinCodeNode = objMapper.createObjectNode();
                        JsonNode profileNode = objMapper.readTree(new FileReader(getProfilePath()));
                        String accountStr = profileNode.get("account").asText();
                        String phone = profileNode.get("phone").asText();
                        pinCodeNode.put("account", accountStr);
                        pinCodeNode.put("pin", pinCode);
                        pinCodeNode.put("phone", phone);
                        OutputStreamWriter pinCodeWriter = new OutputStreamWriter(new FileOutputStream(pinCodePath));
                        pinCodeWriter.write(pinCodeNode.toString());
                        pinCodeWriter.close();
                        if (dlgType.equals("REGISTER")) {
                            that.setVisible(false);
                            Utils.addCmdCode2File(3,pinCodePath);
                            ArrayList<String> cmds = new ArrayList<>();
                            if(Utils.getTestFlag()) {
                                cmds.add(rootPath + "/wpa_setup/testPinCode.sh");
                            } else {
                                cmds.add("wlan.sh");
                                cmds.add("connect");
                                cmds.add(rootPath.concat("/").concat(configSetting.getWpaPriKeyPath()));
                                cmds.add(pinCodePath);
                                cmds.add(rootPath.concat("/").concat(configSetting.getWpaConfPath()));
                                cmds.add(rootPath.concat("/wpa_setup/wpa.log"));
                            }
                            ProcessBuilder pb = new ProcessBuilder(cmds);
//                            ProcessBuilder pb = new ProcessBuilder(
//                                    "wlan.sh",
//                                    "connect",
//                                    rootPath.concat("/").concat(configSetting.getWpaPriKeyPath()),
//                                    pinCodePath,
//                                    rootPath.concat("/").concat(configSetting.getWpaConfPath()),
//                                    rootPath.concat("/wpa_setup/wpa.log")
//                            );
//                            ProcessBuilder pb = new ProcessBuilder(
//                                    rootPath + "/wpa_setup/testPinCode.sh"
//                            );
                            Process process = pb.start();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String line;
                            StringBuilder sb = new StringBuilder();
                            while ((line = reader.readLine()) != null) {
                                sb.append(line);
                                System.out.println(line);
                            }
                            process.waitFor();
                            String retStr = sb.toString();
                            if (retStr.contains("main connect return code")) {
                                String retStrArry[] = retStr.split(" ");
                                statusCode = Integer.parseInt(retStrArry[retStrArry.length - 1]);
                            } else {
                                statusCode = 404;
                            }
                        }
                        if(dlgType.equals("UNREGISTER")) {
                            String wpaCmdPath = getWpaCmdPath();
                            // send unregister pin code
//                            that.setVisible(false);
                            unregStatLabel.setText("正在注销...");
                            setUnregStatus();
                            Utils.addCmdCode2File(6,pinCodePath);
                            ArrayList<String> cmds = new ArrayList<>();
                            if(Utils.getTestFlag()) {
                                cmds.add(rootPath.concat("/wpa_setup/testSendUnregPin.sh"));
                            } else {
                                cmds.add("wlan.sh");
                                cmds.add("connect");
                                cmds.add(wpaCmdPath.concat("/config/prikey.pem"));
                                cmds.add(wpaCmdPath.concat("/config/pin"));
                                cmds.add(wpaCmdPath.concat("/config/wpa.conf"));
                                cmds.add(wpaCmdPath.concat("/wpa.log"));
                            }
                            ProcessBuilder pbSendPinCode = new ProcessBuilder(cmds);
//                            ProcessBuilder pbSendPinCode = new ProcessBuilder(
//                                    "wlan.sh",
//                                    "connect",
//                                    wpaCmdPath.concat("/config/prikey.pem"),
//                                    wpaCmdPath.concat("/config/pin"),
//                                    wpaCmdPath.concat("/config/wpa.conf"),
//                                    wpaCmdPath.concat("/wpa.log")
//                            );
                            Process processSendPinCode = pbSendPinCode.start();
                            BufferedReader sendPinCodeReader = new BufferedReader(new InputStreamReader(processSendPinCode.getInputStream()));
                            StringBuilder sbSendPinCode = new StringBuilder();
                            String line;
                            while((line = sendPinCodeReader.readLine()) != null) {
                                sbSendPinCode.append(line);
                                System.out.println(line);
                            }
                            processSendPinCode.waitFor();
                            String sendPinRetStr = sbSendPinCode.toString();
                            if(sendPinRetStr.contains("main connect return code")) {
                                String[] retStr = sendPinRetStr.split(" ");
                                statusCode = Integer.parseInt(retStr[retStr.length - 1]);
                                switch (statusCode) {
                                    case 15:
                                        System.out.println("[INFO] Unregister user successfully!");
                                        break;
                                    case 16:
                                        System.out.println("[ERROR] Unregister user failed! Verify phone num failed!!");
                                        break;
                                    case 17:
                                        System.out.println("[ERROR] Unregister user failed! User account doesn't exist!");
                                        break;
                                    case 18:
                                        System.out.println("[ERROR] Unregister user failed! Phone number doesn't exist!");
                                        break;
                                    case 19:
                                        System.out.println("[ERROR] Unregister user failed! Unknown error!");
                                        break;
                                    default:
                                        System.out.println("[ERROR] Unregister user failed! Wrong status code!");
                                }
                            } else {
                                System.out.println("[ERROR] Send verify request failed!");
                                statusCode = 303;
                            }
                            if(statusCode == 15) {
                                unregStatLabel.setText("注销成功！");
                            } else {
                                unregStatLabel.setText("注销失败！");
                            }
                            sleep(1500);
                            register.setUnregPinCode(statusCode);
                        }
                        register.setConnStatusCode(statusCode);
                        dispose();
                    } catch(IOException | InterruptedException ex){
                        System.out.println(ex.getMessage());
                    }
                }).start();
            }
        });
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dlgType.equals("REGISTER")) {
                    register.setConnStatusCode(303);
                }
                if(dlgType.equals("UNREGISTER")) {
                    register.setUnregPinCode(303);
                }
                dispose();
            }
        });
    }
    
    public void viewBar() {

//        waitUnregPb.setStringPainted(true);
        waitUnregPb.setIndeterminate(true);
//        waitUnregPb.setValue(0);

//        int timerDelay = 10;
//        new javax.swing.Timer(timerDelay , new ActionListener() {
//            private int index = 0;
//            private int maxIndex = 100;
//            public void actionPerformed(ActionEvent e) {
//                if (index < maxIndex) {
//                    waitUnregPb.setValue(index);
//                    index++;
//                } else {
//                    waitUnregPb.setValue(maxIndex);
//                    ((javax.swing.Timer)e.getSource()).stop(); // stop the timer
//                }
//            }
//        }).start();

//        waitUnregPb.setValue(waitUnregPb.getMinimum());
    }

    public void setRegisterPage(Register register) {
        this.register = register;
    }

    public void setDlgType(String dlgType) {
        this.dlgType = dlgType;
    }

    public void setUnregStatusCode(int unregStatusCode) {
        this.unregStatusCode = unregStatusCode;
    }

    private String getPinCodePath() {
        return rootPath.concat("/").concat(configSetting.getWpaPinCodePath());
    }

    private String getProfilePath() {
        return rootPath.concat("/").concat(configSetting.getProfileFPath());
    }

    private String getWpaCmdPath() {
        return rootPath.concat("/") + configSetting.getWpaCmdPath();
    }
}
