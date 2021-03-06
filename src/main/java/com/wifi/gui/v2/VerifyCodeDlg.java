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
    private int regStatusCode = -1;
    private HomePage preHomePage;

    /*
    * 1: process -- disconnect network
    * 2: disconnect network failed
    * 3: process -- disconnect network successfully,send unregister request
    * 4: send unregister request failed
    * 5: pin code -- send unregister request successfully,please input pinCode
    * */
    private int unregStatusCode = 1;

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
                    while (unregStatusCode == 1 || unregStatusCode == 3) {
                        sleep(10);
                    }
                    switch (unregStatusCode) {
                        case 2:
                            System.out.println("[ERROR] Disconnect network failed!");
                            unregStatLabel.setText("断开网络失败！");
                            sleep(1500);
                            dispose();
                            break;
                        case 4:
                            System.out.println("[ERROR] Send unregister request failed!");
                            unregStatLabel.setText("发送注销请求失败！");
                            sleep(1500);
                            dispose();
                            break;
                        case 5:
                            setRegStatus(); // send unregister request
                            break;
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
        setPCodeTimeout();
        this.pack();        // reshape window
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
        this.pack();        // reshape window
    }

    private void setPCodeTimeout() {
        // set timeout tips
        new Thread(() -> {
            try {
                for (int i = 59; i >= 0; i--) {
                    if(dlgType.equals("UNREGISTER") && unregStatusCode != 5) break;
                    else if(regStatusCode != -1) break;
                    timeoutLabel.setText(i + "s");
                    sleep(1000);
                }
                if((dlgType.equals("UNREGISTER") && unregStatusCode == 5) ||
                        (dlgType.equals("REGISTER")) && regStatusCode != -1){
                    setTimeout();
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }).start();
    }

    private void setTimeout() throws InterruptedException {
        tipLabel.setText("输入验证码超时！");
        tipLabel.setForeground(Color.red);
        verifyCodeText.setEnabled(false);
        register.setConnStatusCode(304);
        sleep(1500);
        dispose();
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
                        // registration process
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
                            Process process = pb.start();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String line;
                            String leftCoinLine = "";
                            StringBuilder sb = new StringBuilder();
                            while ((line = reader.readLine()) != null) {
                                if(line.contains("left coin")){
                                    leftCoinLine = line;
                                }
                                sb.append(line);
                                System.out.println(line);
                            }
                            process.waitFor();
                            String retStr = sb.toString();
                            if (retStr.contains("main connect return code")) {
                                String retStrArry[] = retStr.split(" ");
                                regStatusCode = Integer.parseInt(retStrArry[retStrArry.length - 1]);
                                if(regStatusCode == 64) {
                                    // get user's left coin number
                                    if(leftCoinLine.contains("left coin")){
                                        String[] tmpStr = leftCoinLine.split(" ");
                                        int leftToken = Integer.parseInt(tmpStr[tmpStr.length-1]);
                                        preHomePage.setLeftToken(leftToken);
                                    }
                                    // get registration coin number
                                    if(Utils.getTestChain()) {
                                        OutputStreamWriter tokenWriter = new OutputStreamWriter(new FileOutputStream(rootPath.concat("/wpa_setup/testLeftToken")));
                                        OutputStreamWriter coinWriter = new OutputStreamWriter(new FileOutputStream(rootPath.concat("/wpa_setup/testLeftCoin")));
                                        tokenWriter.write("registerReward:100");
                                        coinWriter.write("0");
                                        tokenWriter.close();
                                        coinWriter.close();
                                    } else {
                                        try {
                                            ArrayList<String> regCmd = new ArrayList<>();
                                            regCmd.add("node");
                                            regCmd.add(rootPath.concat("/js_contact_chain/client.js"));
                                            regCmd.add("UserRegister");
                                            regCmd.add("0x01c96e4d9be1f4aef473dc5dcf13d8bd1d4133cd");
                                            regCmd.add("e16a1130062b37f038b9df02f134d7ddd9009c54c62bd92d4ed42c0dba1189a8");
                                            ProcessBuilder regRewardPB = new ProcessBuilder(regCmd);
                                            Process regRewardPS = regRewardPB.start();
                                            BufferedReader regRewardReader = new BufferedReader(new InputStreamReader(regRewardPS.getInputStream()));
                                            String regRewardLine;
//                                            StringBuilder regRewardSB = new StringBuilder();
                                            String regRewardCoin = "";
                                            System.out.println("========== register user ==========");
                                            while ((regRewardLine = regRewardReader.readLine()) != null) {
                                                if (regRewardLine.contains("registerCoin")) {
                                                    String[] tmpArry = regRewardLine.split(":");
                                                    regRewardCoin = tmpArry[tmpArry.length - 1];
                                                    File leftCoinFile = new File(rootPath.concat("/wpa_setup/testLeftCoin"));
                                                    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(leftCoinFile));
                                                    writer.write(regRewardCoin.concat(":registerReward"));
                                                    writer.close();
                                                }
//                                                regRewardSB.append(regRewardLine);
                                                System.out.println(regRewardLine);
                                            }
                                            regRewardPS.waitFor();
                                            if (regRewardCoin.compareTo("") == 0) {
                                                System.out.println("[ERROR] Get register reward failed!");
                                            }
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    }
                                }
                            } else {
                                regStatusCode = 404;
                            }
                            register.setConnStatusCode(regStatusCode);
                        }
                        // unregistration process
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
                                unregStatusCode = Integer.parseInt(retStr[retStr.length - 1]);
                            } else {
                                System.out.println("[ERROR] Send verify request failed!");
                                unregStatusCode = 303;
                            }
                            switch (unregStatusCode) {
                                case 15:
                                    System.out.println("[INFO] Unregister user successfully!");
                                    new File(rootPath.concat("/config/tokenRecords")).delete();
                                    new File(rootPath.concat("/config/coinRecords")).delete();
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
                            if(unregStatusCode == 15) {
                                unregStatLabel.setText("注销成功！");
                            } else {
                                unregStatLabel.setText("注销失败！");
                            }
                            sleep(1500);
                            // delete history record file
                            File historyFile = new File(rootPath.concat("/config/records"));
                            if(historyFile.exists()) {
                                historyFile.delete();
                            }
                            register.setUnregStatusCode(unregStatusCode);
                        }
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
                    register.setUnregStatusCode(303);
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

    public void setPreHomePage(HomePage preHomePage) {
        this.preHomePage = preHomePage;
    }
}
