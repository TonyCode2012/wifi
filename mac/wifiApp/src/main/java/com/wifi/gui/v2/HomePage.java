package com.wifi.gui.v2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class HomePage {
    private JPanel rootPanel;
    private JTabbedPane pageHomeTabbedPane;
    private JButton registerBtn;
    private JButton launchBtn;
    private JPanel recommandPanel;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JTextArea textArea4;
    private JTextArea textArea5;
    private JTextArea textArea6;
    private JTextArea textArea7;
    private JTextArea textArea8;
    private JTextArea textArea9;
    private JScrollPane newsScrollPanel;
    private JPanel underPanel;
    private JLabel n1pLabel;
    private JLabel n1tLabel;
    private JLabel n2pLabel;
    private JLabel n2tLabel;
    private JLabel n3pLabel;
    private JLabel n3tLabel;
    private JLabel n4pLabel;
    private JLabel n4tLabel;
    private JLabel n5pLabel;
    private JLabel n5tLabel;
    private JLabel n6pLabel;
    private JLabel n6tLabel;
    private JLabel launchTipLabel;
    private JLabel fourthWifiLabel;
    private JLabel thirdWifiLabel;
    private JLabel secondWifiLabel;
    private JLabel firstWifiLabel;
    private JButton breakConnBtn;
    private JLabel connectStatusL;
    private JLabel ad1Label;
    private JLabel ad2Label;
    private JLabel ad3Label;
    private JLabel ad4Label;
    private JLabel ad5Label;
    private JLabel ssidLabel;

    private JFrame fJFrame;
    private Register nextRegister;
    private Launch nextLaunch;
    private String rootPath = System.getProperty("user.dir");
    private int retStatus = -1;
    private ImageIcon launchIcon;
    private ImageIcon unLaunchIcon;
    private ImageIcon connectedIcon;
    private ImageIcon connectedOpaqueIcon;
    private ImageIcon unconnectedIcon;
    private ImageIcon connectingIcon;
    private Thread connStatusImgThread;
    private boolean networkConnected;

    public void setLaunchImg() {
        // reset launch img status
        resetLaunchImgStatus();

        // set recognised ssid
        Color backColor = rootPanel.getBackground();
        int backRed = backColor.getRed();
        int backGreen = backColor.getGreen();
        int backBlue = backColor.getBlue();
        new Thread(() -> {
            try {
                int j=backGreen,k=backBlue;
                for(int i=backRed;i>0&&retStatus==-1;i-=3){
                    j-=3;k-=3;
                    if(j < 0) j=0;
                    if(k < 0) k=0;
                    ssidLabel.setForeground(new Color(i,j,k));
                    sleep(100);
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }).start();

        // set break connection button to visible
        breakConnBtn.setVisible(true);

        registerBtn.setVisible(false);
        launchTipLabel.setVisible(true);
        launchTipLabel.setText("正在连接...");
        new Thread(() -> {
            try {
                // store img status icon thread to local
                connStatusImgThread = Thread.currentThread();
                // set advertisement icons
                JLabel adLabels[] = {ad1Label,ad2Label,ad3Label,ad4Label,ad5Label};
                for(JLabel adlabel : adLabels){
                    adlabel.setVisible(true);
                }
                // set wifi icons
                JLabel launchLabels[] = {firstWifiLabel,secondWifiLabel,thirdWifiLabel,fourthWifiLabel};

                int index = 0;
                ImageIcon connStatusIcon = connectingIcon;
                String connStr = "正在连接";
                connectStatusL.setVisible(true);
                connectStatusL.setText(connStr);
                while(retStatus == -1) {
                    JLabel llabel = launchLabels[index];
                    llabel.setVisible(true);
                    connectStatusL.setIcon(connStatusIcon);
                    Icon icon0 = adLabels[0].getIcon();
                    for(int i=0;i<adLabels.length;i++) {
                        if(i != adLabels.length - 1) {
                            adLabels[i].setIcon(adLabels[i+1].getIcon());
                        } else {
                            adLabels[i].setIcon(icon0);
                        }
                    }
                    connStatusIcon = (connStatusIcon == connectingIcon ? connectedOpaqueIcon : connectingIcon);
                    sleep(600);
                    llabel.setVisible(false);
                    index = (++index) % 4;
                }
                ssidLabel.setForeground(Color.black);
                if(retStatus == 0) {
                    setBreakdownStatus();
                } else if(retStatus == 64) {
                    launchTipLabel.setText("连接成功!");
                    launchLabels[3].setVisible(true);
                    connectStatusL.setIcon(connectedIcon);
                    connectStatusL.setText("已连接");
                } else {
                    launchTipLabel.setText("连接失败!");
                    launchLabels[3].setIcon(unLaunchIcon);
                    launchLabels[3].setVisible(true);
                    connectStatusL.setIcon(unconnectedIcon);
                    connectStatusL.setText("未连接");
                }

            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }).start();
    }

    public void testScrollPanel() {
//        JTextArea array[] = {textArea1,textArea2,textArea3,textArea4,textArea5,textArea6,textArea7,textArea8,textArea9};
//        for(JTextArea jta: array) {
//            jta.setText("我的东西在设么地方，我找不到了你知道么\n" +
//                    "请你教教我好么，哈哈哈哈，笨蛋，我就是不叫你\n" +
//                    "你能把握怎么办哈苏打粉，史蒂芬晚礼服骄傲是江东父老\n" +
//                    "你最终还是要靠你自己的努力来完成你的愿望");
//        }
        newsScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ImageIcon img1 = new ImageIcon(rootPath + "/img/tcn1.jpg");
        ImageIcon img2 = new ImageIcon(rootPath + "/img/tcn2.jpg");
        ImageIcon img3 = new ImageIcon(rootPath + "/img/tcn3.jpg");
        ImageIcon img4 = new ImageIcon(rootPath + "/img/tcn4.jpg");
        ImageIcon img5 = new ImageIcon(rootPath + "/img/tcn5.jpg");
        ImageIcon img6 = new ImageIcon(rootPath + "/img/tcn6.jpg");
        JLabel jtlarry[] = {n1tLabel,n2tLabel,n3tLabel,n4tLabel,n5tLabel,n6tLabel};
        JLabel jplarry[] = {n1pLabel,n2pLabel,n3pLabel,n4pLabel,n5pLabel,n6pLabel};
        ImageIcon icons[] = {img1,img2,img3,img4,img5,img6};
        int width = rootPanel.getWidth();
        int height = rootPanel.getHeight();
        double ratio = 0.2;
        underPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        for(int i=0;i<6;i++) {
            JLabel jtlabel = jtlarry[i];
            JLabel jplabel = jplarry[i];
            ImageIcon icon = icons[i];
            jtlabel.setVerticalTextPosition(JLabel.TOP);
            jtlabel.setVerticalAlignment(JLabel.TOP);
            jplabel.setText("");
            icon.setImage(icon.getImage().getScaledInstance((int) (width * ratio), (int) (height * ratio), Image.SCALE_DEFAULT));
            jplabel.setIcon(icon);
            jtlabel.setText("这仅仅是一个测试而已，测试看啊看我们的东西能在什么防霾的比较好哈哈哈，你都嗯" +
                    "我的意思么");
        }
    }

    public void resetLaunchImgStatus() {
        firstWifiLabel.setVisible(false);
        secondWifiLabel.setVisible(false);
        thirdWifiLabel.setVisible(false);
        fourthWifiLabel.setVisible(false);
        fourthWifiLabel.setIcon(launchIcon);
        ssidLabel.setForeground(rootPanel.getBackground());
        setRetStatus(-1);
    }

    private void setBreakdownStatus() {
        launchTipLabel.setVisible(false);
        fourthWifiLabel.setVisible(false);
        connectStatusL.setVisible(false);
        breakConnBtn.setVisible(false);
        nextRegister.setBreakNetwork(true);
        registerBtn.setVisible(true);
    }

    private void setLaunchImgStatus() {
        // get root panel width and height
        int width = rootPanel.getWidth();
        // set advertisement icon
        JLabel adLabels[] = {ad1Label,ad2Label,ad3Label,ad4Label,ad5Label};
        double raRatio = (1 - 0.35) / adLabels.length;
        for(int i=0;i<adLabels.length;i++){
            String iconPath = rootPath + "/img/tcn"+(i+1)+".jpg";
            JLabel tmpLabel = adLabels[i];
            tmpLabel.setVisible(false);
            tmpLabel.setText("");
            ImageIcon tmpImg = new ImageIcon(iconPath);
            double tmpImgH = tmpImg.getIconHeight();
            double tmpimgW = tmpImg.getIconWidth();
            double tmpRatio = tmpImgH / tmpimgW;
            int stmpImgW = (int) (width * raRatio);
            int stmpImgH = (int) (stmpImgW * tmpRatio);
            tmpImg.setImage(tmpImg.getImage().getScaledInstance(stmpImgW,stmpImgH,Image.SCALE_DEFAULT));
            tmpLabel.setIcon(tmpImg);
        }
        // set wifi icons
        ImageIcon launchIcon1 = new ImageIcon(rootPath + "/img/launch1.png");
        ImageIcon launchIcon2 = new ImageIcon(rootPath + "/img/launch2.png");
        ImageIcon launchIcon3 = new ImageIcon(rootPath + "/img/launch3.png");
        ImageIcon launchIcon4 = new ImageIcon(rootPath + "/img/launch4.png");
        JLabel wifiLaunchLabels[] = {firstWifiLabel,secondWifiLabel,thirdWifiLabel,fourthWifiLabel};
        ImageIcon wifiLaunchIcons[] = {launchIcon1,launchIcon2,launchIcon3,launchIcon4};
        double lWidth = launchIcon1.getIconWidth();
        double lHeight = launchIcon1.getIconHeight();
        double iRatio = lHeight / lWidth;
        double rlRatio = 0.3;
        int launchIconW = (int) (width * rlRatio);
        int launchIconH = (int) (launchIconW * iRatio);
        for(int i=0;i<wifiLaunchIcons.length;i++) {
            JLabel wifiLabel = wifiLaunchLabels[i];
            ImageIcon wifiIcon = wifiLaunchIcons[i];
            wifiLabel.setVisible(false);
            wifiLabel.setText("");
            wifiIcon.setImage(wifiIcon.getImage().getScaledInstance(launchIconW, launchIconH, Image.SCALE_DEFAULT));
            wifiLabel.setIcon(wifiIcon);
        }
        launchIcon = launchIcon4;
        unLaunchIcon = new ImageIcon(rootPath + "/img/unLaunch.png");
        unLaunchIcon.setImage(unLaunchIcon.getImage().getScaledInstance(launchIconW, launchIconH, Image.SCALE_DEFAULT));
        // set visibility
        launchTipLabel.setVisible(false);
        connectStatusL.setVisible(false);
        launchTipLabel.setText("");
        connectStatusL.setText("");
        // set connect status icon
        connectedIcon = new ImageIcon(rootPath + "/img/connected.png");
        connectedOpaqueIcon = new ImageIcon(rootPath + "/img/connected_opaque.png");
        unconnectedIcon = new ImageIcon(rootPath + "/img/unconnected.png");
        connectingIcon = new ImageIcon(rootPath + "/img/connecting.png");
        double cWidth = connectedIcon.getIconWidth();
        double cHeight = connectedIcon.getIconHeight();
        double cRatio = cHeight / cWidth;
        double rcRatio = 0.03;
        int connectIconW = (int) (width * rcRatio);
        int connectIconH = (int) (connectIconW * cRatio);
        ImageIcon connIcons[] = {connectedIcon,connectedOpaqueIcon,unconnectedIcon,connectingIcon};
        for(ImageIcon icon: connIcons) {
            icon.setImage(icon.getImage().getScaledInstance(connectIconW, connectIconH, Image.SCALE_DEFAULT));
        }
        // set icons
        connectStatusL.setIcon(connectedIcon);
    }

    private void _init() {
        this.rootPanel.setSize(360,500);
        underPanel.revalidate();
        breakConnBtn.setVisible(false);
        connectStatusL.setText("");
        connectStatusL.setVisible(false);
        networkConnected = false;
        // remove new panel
        pageHomeTabbedPane.remove(1);
        setLaunchImgStatus();
        // set recognise ssid label text to space
        ssidLabel.setText("blockchain");
        ssidLabel.setForeground(rootPanel.getBackground());
    }

    public HomePage() {
        _init();
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pageHomeTabbedPane.setVisible(false);
                nextRegister.getRegTabbedPane().setVisible(true);
                fJFrame.setContentPane(nextRegister.getRootPanel());
            }
        });
        launchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pageHomeTabbedPane.setVisible(false);
                nextLaunch.getLaunchTabbedPane().setVisible(true);
                fJFrame.setContentPane(nextLaunch.getRootPanel());
            }
        });
        breakConnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setRetStatus(0);
                if(connStatusImgThread == null || connStatusImgThread.getState() != Thread.State.RUNNABLE) {
                    setBreakdownStatus();
                }

                // disconnect network
                try {
                    ProcessBuilder pb = new ProcessBuilder(
                            "wlan.sh",
                            "disconnect"
                    );
//                    pb.redirectErrorStream(true);
                    pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
                    pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                    pb.redirectError(ProcessBuilder.Redirect.INHERIT);

                    Process process = pb.start();
                    process.waitFor();

                } catch (IOException|InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JTabbedPane getPageHomeTabbedPane() {
        return pageHomeTabbedPane;
    }

    public int getRetStatus() {
        return retStatus;
    }

    public void setfJFrame(JFrame fJFrame) {
        this.fJFrame = fJFrame;
        testScrollPanel();
    }

    public void setNextRegister(Register nextRegister) {
        this.nextRegister = nextRegister;
    }

    public void setNextLaunch(Launch nextLaunch) {
        this.nextLaunch = nextLaunch;
    }

    public void setRetStatus(int retStatus) {
        this.retStatus = retStatus;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
