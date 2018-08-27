package com.wifi.gui.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wifi.configSetting;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import static java.lang.Thread.sleep;

public class Launch {
    private JTabbedPane launchTabbedPane;
    private JPanel rootPanel;
    private JButton telecomBtn;
    private JButton tencentBtn;
    private JButton alibabaBtn;
    private JButton backBtn;
    private JPanel launchPanel;
    private JButton appleBtn;

    private JFrame fJFrame;
    private HomePage preHomePage;

    public Launch() {
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchTabbedPane.setVisible(false);
                preHomePage.getPageHomeTabbedPane().setVisible(true);
                fJFrame.setContentPane(preHomePage.getRootPanel());
            }
        });
        telecomBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLaunch("TELECOM");
            }
        });
        tencentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLaunch("TENCENT");
            }
        });
        alibabaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLaunch("ALIBABA");
            }
        });
        appleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLaunch("APPLE");
            }
        });
    }

    private void doLaunch(String companyId) {
        try {
            String rootPath = System.getProperty("user.dir");
            ObjectMapper objMapper = new ObjectMapper();
            String profilePath = rootPath.concat("/") + configSetting.getProfileFPath();
            String profileComPath = rootPath.concat("/") + configSetting.getIdentityPath().concat("_").concat(companyId);
            File profile = new File(profilePath);
            File profileCompany = new File(profileComPath);
            ObjectNode config = (ObjectNode) objMapper.readTree(profileCompany);
//            config.remove("company");
//            config.put("company","TELECOM");
//            profileCompany.delete();
//            profileCompany.createNewFile();
//            OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(profileCompany), "utf-8");
//            oStreamWriter.append(config.toString());
//            oStreamWriter.close();
            FileUtils.copyFile(profileCompany, profile);

            // run wpa_supplicant as a daemon process
            new Thread(() -> {
                try {
//                        ProcessBuilder pb = new ProcessBuilder(
//                                "wpa_supplicant",
//                                "-i",
//                                "wlx70f11c0c2433",
//                                "-c",
//                                rootPath + "/config/wpa.conf",
//                                "-d"
//                        );
                    ProcessBuilder pb = new ProcessBuilder(
                            "run_dameon.sh"
                    );
//                    pb.redirectErrorStream(true);
                    pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
                    pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                    pb.redirectError(ProcessBuilder.Redirect.INHERIT);

                    Process process = pb.start();
                    if (process != null) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String line;
                        StringBuilder sb = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }
                        System.out.println("Execute wpa_supplicant result:" + sb.toString());
                    } else {
                        System.out.println("No wpa_supplicant process found!");
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }).start();

            // use wpa_cli to start up network
            ProcessBuilder wpaEnbleNetBuilder = new ProcessBuilder(
                    "wpa_cli",
                    "enable_network"
            );
            Process wpaEnableNetProcess = wpaEnbleNetBuilder.start();


            sleep(5000);
            // run wpa_cli status to check network connection status
            while(true) {
                ProcessBuilder pb = new ProcessBuilder(
                        "wpa_cli",
                        "status"
                );
                pb.redirectErrorStream(true);
                Process process = pb.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                System.out.println(sb.toString());
                sleep(5000);
            }

            // print company profile information
//            String identityInfo = config.toString();
//            System.out.println(identityInfo);
        } catch (IOException|InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JTabbedPane getLaunchTabbedPane() {
        return launchTabbedPane;
    }

    public void setfJFrame(JFrame fJFrame) {
        this.fJFrame = fJFrame;
    }

    public void setPreHomePage(HomePage preHomePage) {
        this.preHomePage = preHomePage;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
