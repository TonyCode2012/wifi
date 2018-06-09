package com.wifi.gui;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.impl.client.CloseableHttpClient;
import sun.misc.BASE64Encoder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.*;
import java.util.Base64;

import com.wifi.configSetting;

public class profile extends JDialog {
    private JPanel contentPane;
    private JTextField nameText;
    private JTextField phoneText;
    private JButton backBtn;
    private JButton editBtn;
    private JButton submitBtn;
    private JLabel regTitle;
    private JButton infoBtn;

    private ObjectMapper objMapper = new ObjectMapper();
    private String companyId;

    private static boolean finishReg = false;
    private String rootPath = System.getProperty("user.dir");


    CloseableHttpClient httpClient;

    private void init(String companyName) {
        regTitle.setText(companyName+"上网注册");
        switch (companyName) {
            case "阿里": companyId = "alibaba"; break;
            case "腾讯": companyId = "tencent"; break;
            case "百度": companyId = "baidu"; break;
        }

        // set gui attribute
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(submitBtn);
    }

    public profile(String companyName) {
        init(companyName);
        final JDialog that = this;
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = editBtn.getText();
                boolean editable = false;
                if (text == "取消编辑") {
                    editable = false;
                    text = "编辑";
                } else {
                    editable = true;
                    text = "取消编辑";
                }
                editBtn.setText(text);
                nameText.setEditable(editable);
                phoneText.setEditable(editable);
            }
        });
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nameText.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,"请输入姓名!");
                    return;
                }
                if(phoneText.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,"请输入手机号!");
                    return;
                }
                class RunMsgDialog implements Runnable{
                    private JDialog jDialog;
                    public RunMsgDialog(JDialog father){
                        jDialog = new JDialog(father);
                    }
                    @Override
                    public void run(){
//                        jDialog.pack();
//                        setContentPane(contentPane);
                        JPanel pan = new JPanel();
                        pan.setLayout(new FlowLayout());
                        pan.add(new JLabel("正在注册请稍等..."),BorderLayout.CENTER);
                        jDialog.setContentPane(pan);
//                        jDialog.getContentPane().add(pan,BorderLayout.CENTER);
//                        jDialog.getContentPane().add(new Button("wodedongxi"),BorderLayout.CENTER);
                        jDialog.setSize(new Dimension(350,100));
//                        jDialog.setLocationRelativeTo(father);
                        jDialog.setLocation(600, 300);
                        jDialog.setTitle("正在注册请稍等...");
                        jDialog.setModal(false);
                        jDialog.setVisible(true);
                    }
                }
                RunMsgDialog dialog = new RunMsgDialog(that);
                dialog.run();
                boolean flag = registerUser();
                dialog.jDialog.dispose();
                if(flag) {
                    JOptionPane.showMessageDialog(null,"注册成功!");
                } else {
                    JOptionPane.showMessageDialog(null,"注册失败!");
                }
            }
        });
        infoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ObjectMapper objMapper = new ObjectMapper();
                    File file = new File(getIdentityPath());
                    if(!file.exists()) {
                        JOptionPane.showMessageDialog(null, "没有注册信息!");
                        return;
                    }
                    JsonNode jsonData = objMapper.readTree(file);
                    StringBuilder sb = new StringBuilder();
                    sb.append("name: ").append(jsonData.get("name"))
                            .append("\nphone: ").append(jsonData.get("phone"))
                            .append("\ncompany: ").append(jsonData.get("company"))
                            .append("\naccount: ").append(jsonData.get("account"));
                    //.append("\npub_key").append(jsonData.get("pub_key"));
                    JOptionPane.showMessageDialog(null, sb.toString());
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    private boolean registerUser(){
        try {
            String name = nameText.getText();
            String phone = phoneText.getText();
            String pub_key = RSAPUBPRIKEYGEN();

            // set identity info
            ObjectNode identityData = objMapper.createObjectNode();
//            identityData.put("company",companyId);
            identityData.put("company","TELECOM");
            identityData.put("name",name);
            identityData.put("phone",phone);
            identityData.put("pub_key",pub_key);

            objMapper = new ObjectMapper();
            ObjectNode profileData = objMapper.createObjectNode();
            profileData.put("name",name);
            profileData.put("phone",phone);
            profileData.put("pub_key",pub_key);
            StringBuffer sb = new StringBuffer();
            File identityFile = new File(getIdentityPath());
            if(!identityFile.exists()){
                if(!identityFile.createNewFile()){
                    System.out.println("[ERROR] create identity file failed!");
                    return false;
                }
            }
            ProcessBuilder pb = new ProcessBuilder(
                    "curl",
                    "-s",
                    "-X POST",
                    configSetting.getRegServerUrl(),
                    "-H",
                    "cache-control: no-cache",
                    "-H",
                    "content-type: application/json",
                    "-d",
                    profileData.toString(),
                    "-k"

            );

            pb.redirectErrorStream(true);
//            pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
//            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
//            pb.redirectError(ProcessBuilder.Redirect.INHERIT);

            Process process = pb.start();
            if (process != null) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                String result = sb.toString();
                process.destroy();
                if(result.contains("company")) {
                    String resArry[] = result.split(" ");
                    int len = resArry.length;
                    String tStr = resArry[len-1].replace("\n","");
                    // set identity id info
                    identityData.put("account",tStr);
                    FileOutputStream ifos = new FileOutputStream(identityFile);
                    ifos.write(identityData.toString().getBytes());
                    ifos.close();
                    return true;
                }
//                process.waitFor();
            } else {
                System.out.println("There is no PID found.");
            }
        } catch (IOException  exp){
            System.out.println(exp.getMessage());
        }
        return false;
    }

    private String RSAPUBPRIKEYGEN(){
        StringBuilder pubKey_str = new StringBuilder();
        StringBuilder priKey_str = new StringBuilder();
        String base64encodedStr = "";
        try {
            File prikeyfile = new File(getPrikeyPath());
            File pubkeyfile = new File(getPubkeyPath());
            boolean flag = true;
            if(!prikeyfile.exists()){
                if(!prikeyfile.createNewFile()) {
                    System.out.println("[ERROR] create private key file failed!");
                    return "";
                }
                flag = false;
            }
            if(!pubkeyfile.exists()) {
                if(!pubkeyfile.createNewFile()) {
                    System.out.println("[ERROR] create public key file failed!");
                    return "";
                }
                flag = false;
            }
            if(flag) {
//                String hexStr = "0A";
//                String tag;
//                byte[] baKeyword = new byte[hexStr.length()/2];
//                for(int i=0;i<baKeyword.length;i++) {
//                    baKeyword[i] =(byte) (0xff & Integer.parseInt(hexStr.substring(
//                            i * 2, i * 2 + 2), 16));
//                }
//                tag = new String(baKeyword,"utf-8");
//                BufferedReader br = new BufferedReader(new FileReader(pubkeyfile));
//                String line;
//                StringBuilder sb = new StringBuilder();
//                while((line = br.readLine())!=null){
//                    sb.append(line).append(tag);
//                }
//                sb.delete(sb.length()-tag.length(),sb.length());
//                pubKey_str.append(sb.toString());
//                System.out.println("pubkey string is:" + pubKey_str);
                InputStreamReader isr = new InputStreamReader(new FileInputStream(pubkeyfile),"utf-8");
                Long filelength = pubkeyfile.length();
                char[] cbuf = new char[filelength.intValue()];
                isr.read(cbuf);
                pubKey_str.append(cbuf);

                System.out.println("filelenght is:"+filelength+",readed length:"+cbuf.length);
            } else {
                pubKey_str.append("-----BEGIN PUBLIC KEY-----\n");
                priKey_str.append("-----BEGIN RSA PRIVATE KEY-----\n");
                KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                kpg.initialize(1024);
                KeyPair kp = kpg.genKeyPair();
                PublicKey pubKey = kp.getPublic();
                PrivateKey priKey = kp.getPrivate();
                OutputStreamWriter pubosw = new OutputStreamWriter(new FileOutputStream(pubkeyfile),"utf-8");
                OutputStreamWriter priosw = new OutputStreamWriter(new FileOutputStream(prikeyfile),"utf-8");
                // there may be some risk by using base64 encode algorithm
                pubKey_str.append((new BASE64Encoder()).encode(pubKey.getEncoded()));
                priKey_str.append((new BASE64Encoder()).encode(priKey.getEncoded()));
                pubKey_str.append("-----END PUBLIC KEY-----");
                priKey_str.append("-----END RSA PRIVATE KEY-----");
                pubosw.append(pubKey_str);
                priosw.append(priKey_str);
            }
//            base64encodedStr = Base64.getEncoder().encodeToString(pubKey_str.toString().getBytes("utf-8"));
        } catch (IOException|NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

//        System.out.println("start======="+pubKey_str.toString()+"=========end");
//        return base64encodedStr;
        return pubKey_str.toString();
    }

    private String getIdentityPath() {
        return rootPath.concat("/") + configSetting.getIdentityPath().concat("_").concat(companyId);
    }

    private String getPrikeyPath() {
        return rootPath.concat("/") + configSetting.getPriKeyFPath();
    }

    private String getPubkeyPath() {
        return rootPath.concat("/") + configSetting.getPubKeyFPath();
    }
}