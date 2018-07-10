package com.wifi.gui.v2;

import com.wifi.gui.v2.utils.Utils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

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
    private JPanel walletPanel;
    private JLabel leftTileLabel;
    private JLabel leftLabel;
    private JButton seeAdsBtn;
    private JButton exchangeBtn;
    private JLabel loginStatusLabel;
    private JPanel getCoinPanel;
    private JLabel ads1Label;
    private JLabel ads2Label;
    private JLabel ads3Label;
    private JScrollPane seeAdsScrollPane;
    private JLabel spacer1Label;
    private JLabel spacer2Label;
    private JPanel showAdsPanel;
    private JLabel adsDetail;

    private JFrame fJFrame;
    private Register nextRegister;
    private Launch nextLaunch;
    private String rootPath = System.getProperty("user.dir");
    private int retStatus = -1;
    private int leftCoin = -1;
    private ImageIcon launchIcon;
    private ImageIcon unLaunchIcon;
    private ImageIcon connectedIcon;
    private ImageIcon connectedOpaqueIcon;
    private ImageIcon unconnectedIcon;
    private ImageIcon connectingIcon;
    private ImageIcon goldCoinIcon;
    private Thread connStatusImgThread;
    private boolean networkConnected;
    private int loginStatus = 0;
    private Color bgColor = rootPanel.getBackground();
    private Cursor curCursor = rootPanel.getCursor();

    public void setLaunchImg() {
        // reset launch img status
        resetLaunchImgStatus();

        // set recognised ssid
        ssidLabel.setVisible(true);
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
                    connectStatusL.setText("已连接   ");
                    // set wallet left value
                    setLoginStatus(1);
                    setWalletStatus();
                } else {
                    launchTipLabel.setText("连接失败!");
                    launchLabels[3].setIcon(unLaunchIcon);
                    launchLabels[3].setVisible(true);
                    connectStatusL.setIcon(unconnectedIcon);
                    connectStatusL.setText("未连接   ");
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

    private void setIcon(double ratio,double fWidth,ImageIcon icon) {
        double iWidth = icon.getIconWidth();
        double iHeight = icon.getIconHeight();
        double iRatio = iHeight / iWidth;
        int iconWidth = (int) (fWidth * ratio);
        int iconHeight = (int) (iconWidth * iRatio);
        icon.setImage(icon.getImage().getScaledInstance(iconWidth,iconHeight,Image.SCALE_DEFAULT));
    }

    private void seeAds() {
        double rWidth = rootPanel.getWidth();
        // set wallet page layout
        spacer1Label.setBorder(new LineBorder(bgColor));
        spacer1Label.setPreferredSize(new Dimension(10,(int)rWidth/8));
        spacer2Label.setBorder(new LineBorder(bgColor));
        spacer2Label.setPreferredSize(new Dimension(10,(int)rWidth/8));
//        seeAdsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ImageIcon img1 = new ImageIcon(rootPath + "/img/tcn1.jpg");
        ImageIcon img2 = new ImageIcon(rootPath + "/img/tcn2.jpg");
        ImageIcon img3 = new ImageIcon(rootPath + "/img/tcn3.jpg");
        ImageIcon img4 = new ImageIcon(rootPath + "/img/tcn4.jpg");
        ImageIcon img5 = new ImageIcon(rootPath + "/img/tcn5.jpg");
        ImageIcon img6 = new ImageIcon(rootPath + "/img/tcn6.jpg");
        JLabel seeAdsLabels[] = {ads1Label,ads2Label,ads3Label};
        ImageIcon icons[] = {img1,img2,img3};
        for(int i=0;i<3;i++) {
            JLabel tmpLabel = seeAdsLabels[i];
            ImageIcon tmpIcon = icons[i];
            setIcon(0.2,rWidth,tmpIcon);
            tmpLabel.setIcon(tmpIcon);
            tmpLabel.setText("这仅仅是一个测试而已，测试看啊看我们的东西能在什么防霾的比较好哈哈哈，你都嗯" +
                    "我的意思么");
            tmpLabel.setVerticalTextPosition(SwingConstants.CENTER);
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
        ssidLabel.setVisible(false);
//        ssidLabel.setForeground(rootPanel.getBackground());
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
        // set coin image
        goldCoinIcon = new ImageIcon(rootPath + "/img/goldCoin.png");
        double gRatio = 0.08;
        double goldWidth = goldCoinIcon.getIconWidth();
        double goldHeight = goldCoinIcon.getIconHeight();
        double goldRatio = goldHeight / goldWidth;
        int goldCoinW = (int) (width * gRatio);
        int goldCoinH = (int) (goldCoinW * goldRatio);
        goldCoinIcon.setImage(goldCoinIcon.getImage().getScaledInstance(goldCoinW,goldCoinH,Image.SCALE_DEFAULT));
        leftLabel.setIcon(goldCoinIcon);
        leftLabel.setHorizontalTextPosition(SwingConstants.LEFT);
    }

    private void setWalletStatus() {
        // invisible ads and exchange
        seeAdsBtn.setVisible(false);
        exchangeBtn.setVisible(false);
        boolean status = false;
        if(loginStatus == 1) {
            status = true;
        }
        loginStatusLabel.setVisible(!status);
        leftLabel.setVisible(status);
        if(status) {
            leftLabel.setText(String.valueOf(leftCoin));
        }
        leftTileLabel.setVisible(status);
        getCoinPanel.setVisible(status);
        seeAdsScrollPane.setVisible(status);
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
        // set wallet status
        setWalletStatus();
        // users can get coins by reading ads
        seeAds();
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
                    ArrayList<String> cmds = new ArrayList<>();
                    if(Utils.getTestFlag()) {
                        cmds.add(rootPath.concat("/wpa_setup/testDisconnect.sh"));
                    } else {
                        cmds.add("wlan.sh");
                        cmds.add("disconnect");
                    }
                    ProcessBuilder pb = new ProcessBuilder(cmds);
//                    pb.redirectErrorStream(true);
                    pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
                    pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                    pb.redirectError(ProcessBuilder.Redirect.INHERIT);

                    Process process = pb.start();
                    process.waitFor();
                    setLoginStatus(0);
                    setWalletStatus();

                } catch (IOException|InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        JLabel adsLabels[] = {ads1Label,ads2Label,ads3Label};
        for(JLabel label: adsLabels) {
            changeCursor(label);
        }
    }

    private void changeCursor(Component component) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
                rootPanel.setCursor(cursor);
            }
        });
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                rootPanel.setCursor(curCursor);
            }
        });
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                pageHomeTabbedPane.setSelectedComponent(showAdsPanel);
                Component cmp = e.getComponent();
                if(cmp instanceof JLabel) {
                    JLabel label = (JLabel)cmp;
                    String text = label.getText();
                    Icon icon = label.getIcon();
                    adsDetail.setText(text);
                    adsDetail.setIcon(icon);
                    adsDetail.setVerticalTextPosition(SwingConstants.BOTTOM);
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

    public void setLeftCoin(int leftCoin) {
        this.leftCoin = leftCoin;
    }

    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
