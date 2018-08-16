package com.wifi.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import com.wifi.configSetting;
import org.apache.commons.io.FileUtils;

public class launch extends JDialog {
    private JPanel contentPane;
    private JButton aliLaunch;
    private JButton tencentLaunch;
    private JButton baiduLaunch;
    private JButton backBtn;

    public launch() {
        setContentPane(contentPane);
        setModal(true);
//        getRootPane().setDefaultButton(backBtn);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        aliLaunch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getMessage("alibaba");
            }
        });
        tencentLaunch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getMessage("tencent");
            }
        });
        baiduLaunch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getMessage("baidu");
            }
        });
    }

    public void getMessage(String companyId) {
        try {
            String rootPath = System.getProperty("user.dir");
            ObjectMapper objMapper = new ObjectMapper();
            String profilePath = rootPath.concat("/") + configSetting.getProfileFPath();
            String profileComPath = rootPath.concat("/") + configSetting.getIdentityPath().concat("_").concat(companyId);
            File profile = new File(profilePath);
            File profileCompany = new File(profileComPath);
            ObjectNode config = (ObjectNode) objMapper.readTree(profileCompany);
            config.remove("company");
            config.put("company","TELECOM");
            profileCompany.delete();
            profileCompany.createNewFile();
            OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(profileCompany), "utf-8");
            oStreamWriter.append(config.toString());
            oStreamWriter.close();
            FileUtils.copyFile(profileCompany, profile);

            ProcessBuilder pb = new ProcessBuilder(
                "wpa_supplicant",
                    "-i",
                    "wlx70f11c0c2433",
                    "-c",
                    rootPath+"/config/wpa.conf",
                    "-d"
            );
//            pb.redirectErrorStream(true);
            pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);

            Process process = pb.start();
            if(process != null){
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                StringBuilder sb = new StringBuilder();
                while((line = reader.readLine()) != null){
                    sb.append(line);
                }
                System.out.println("Execute wpa_supplicant result:"+sb.toString());
            } else {
                System.out.println("No wpa_supplicant process found!");
            }

            // print company profile information
            String identityInfo = config.toString();
            System.out.println(identityInfo);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}