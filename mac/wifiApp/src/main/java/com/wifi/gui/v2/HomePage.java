package com.wifi.gui.v2;

import com.wifi.gui.v2.utils.Utils;
import com.wifi.configSetting;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
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
    private JLabel adsDetailIcon;
    private JLabel adsDetailText;
    private JButton adsPageRetBtn;
    private JPanel connectPanel;
    private JLabel ads4Label;
    private JLabel ads5Label;
    private JLabel historyLabel;
    private JLabel recommendIcon;
    private JLabel recommendText;

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
    private Thread connStatusImgThread;
    private int loginStatus = 0;
    private Color bgColor = rootPanel.getBackground();
    private Cursor curCursor = rootPanel.getCursor();
    private int rootWidth;
    private String historyFilePath = rootPath.concat("/config/records");

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
                    // record item
                    writeRecord("登陆: -1");
                    sleep(200);
                    String historyStr = readRecord(3);
                    historyLabel.setText(historyStr);
                } else {
                    launchTipLabel.setText("连接失败!");
                    launchLabels[3].setIcon(unLaunchIcon);
                    launchLabels[3].setVisible(true);
                    connectStatusL.setIcon(unconnectedIcon);
                    connectStatusL.setText("未连接   ");
                }
//                fJFrame.setSize(tWidth,rootPanel.getHeight());
//                fJFrame.pack();
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
        double ratio = 0.2;
        underPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        for(int i=0;i<6;i++) {
            JLabel jtlabel = jtlarry[i];
            JLabel jplabel = jplarry[i];
            ImageIcon icon = icons[i];
            jtlabel.setVerticalTextPosition(JLabel.TOP);
            jtlabel.setVerticalAlignment(JLabel.TOP);
            jplabel.setText("");
            setJLabelIcon(jplabel,icon,width,ratio);
            jtlabel.setText("<html>这仅仅是一个测试而已，测试看啊看我们的东西能在什么防霾的比较好哈哈哈，你都嗯" +
                    "我的意思么<br/><br/>价格：0.1</html>");
        }
    }

    private void setIcon(double ratio,double fWidth,ImageIcon icon) {
        if(ratio > 1) {
            System.out.println("[ERROR] Can't set ratio more than 1!");
            ratio = 1;
        }
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
        ImageIcon img1 = new ImageIcon(rootPath + "/img/xingbake1.jpg");
        ImageIcon img2 = new ImageIcon(rootPath + "/img/xingbake2.jpg");
        ImageIcon img3 = new ImageIcon(rootPath + "/img/xingbake3.jpg");
        ImageIcon img4 = new ImageIcon(rootPath + "/img/kendeji1.jpg");
        ImageIcon img5 = new ImageIcon(rootPath + "/img/kendeji2.jpg");
        JLabel seeAdsLabels[] = {ads1Label,ads2Label,ads3Label,ads4Label,ads5Label};
        ImageIcon icons[] = {img1,img2,img3,img4,img5};
        for(int i=0;i<seeAdsLabels.length;i++) {
            JLabel tmpLabel = seeAdsLabels[i];
            ImageIcon tmpIcon = icons[i];
            setIcon(0.3,rWidth,tmpIcon);
            tmpLabel.setIcon(tmpIcon);
            tmpLabel.setText("<html><p>这仅仅是一个测试而已，测试看啊看我们的东西能在什么防霾的比较好哈哈哈，你都嗯" +
                    "我的意思么</p><br/><font color='#32CD99'>￥ 0.1</font></html>");
            tmpLabel.setVerticalTextPosition(SwingConstants.TOP);
        }
    }
    
    private void setRecommend() {
        String text = "Nike Air VaporMax Flyknit 2 男子跑步鞋配備最新的 Max Air 革命性足底，帶來全新的設計元素。" +
                "鞋跟設計帶來更出色的承托，同時 Flyknit 物料亦能帶來包覆雙足的觸感。充滿未來感的鞋底設計打造完美造型，" +
                "切合所有需要，無論是外出還是快跑都格外型格。";
        recommendText.setSize((int)(rootWidth*0.9),0);
//        recommendText.setSize(recommandPanel.getWidth(),0);
        setJLabelText(recommendText,text);
        setJLabelIcon(recommendIcon,rootPath.concat("/img/nike.png"),rootWidth,0.8);
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
            setJLabelIcon(tmpLabel,iconPath,rootPanel.getWidth(),raRatio);
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
            setJLabelIcon(wifiLabel,wifiIcon,width,rlRatio);
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
        setJLabelIcon(leftLabel,rootPath + "/img/goldCoin.png",width,0.08);
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
        historyLabel.setVisible(status);
    }

    private void _init() {
        this.rootPanel.setSize(360,500);
        underPanel.revalidate();
        breakConnBtn.setVisible(false);
        connectStatusL.setText("");
        connectStatusL.setVisible(false);
        // remove new panel
        pageHomeTabbedPane.remove(newsScrollPanel);
        setLaunchImgStatus();
        // set recognise ssid label text to space
        ssidLabel.setText("blockchain");
        ssidLabel.setForeground(rootPanel.getBackground());
        // set wallet status
        setWalletStatus();
        // users can get coins by reading ads
        seeAds();
        pageHomeTabbedPane.remove(showAdsPanel);
        // get root panel width and height
        rootWidth = rootPanel.getWidth();
        // set recommend ads
        setRecommend();
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
        adsPageRetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pageHomeTabbedPane.remove(showAdsPanel);
                    leftCoin++;
                    leftLabel.setText(String.valueOf(leftCoin));
                    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(new File(rootPath.concat("/wpa_setup/testLeftCoin"))));
                    writer.write(String.valueOf(leftCoin));
                    writer.close();
                    setIcon(0.2,rootWidth,(ImageIcon) adsDetailIcon.getIcon());
                    // record consume item
                    writeRecord("看广告: +1");
                    String historyStr = readRecord(3);
                    historyLabel.setText(historyStr);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        JLabel adsLabels[] = {ads1Label,ads2Label,ads3Label,ads4Label,ads5Label};
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
                pageHomeTabbedPane.add(showAdsPanel);
                pageHomeTabbedPane.setSelectedComponent(showAdsPanel);
                pageHomeTabbedPane.setTitleAt(pageHomeTabbedPane.getSelectedIndex(),"广告");
                Component cmp = e.getComponent();
                if(cmp instanceof JLabel) {
                    JLabel label = (JLabel)cmp;
                    // get indicated text
                    String text = label.getText();
                    Document doc = Jsoup.parse(text);
                    Elements element = doc.getElementsByTag("p");
                    text = element.text();
                    // reset image size
                    ImageIcon icon = (ImageIcon) label.getIcon();
                    String filePath = icon.getDescription();
                    adsDetailText.setSize((int)(rootPanel.getWidth()*0.95),0);
                    setJLabelIcon(adsDetailIcon,filePath,rootWidth,0.93);
                    setJLabelText(adsDetailText,text);
                }
            }
        });
    }

    private void setJLabelIcon(JLabel jLabel,String iconPath,double fWidth,double ratio){
        ImageIcon icon = new ImageIcon(iconPath);
        setIcon(ratio,fWidth,icon);
        jLabel.setIcon(icon);
    }

    private void setJLabelIcon(JLabel jLabel,ImageIcon icon,double fWidth,double ratio){
        setIcon(ratio,fWidth,icon);
        jLabel.setIcon(icon);
    }

    private void setJLabelText(JLabel jLabel, String longString) {
        if(jLabel.getWidth() <= 0) {
            System.out.println("[ERROR] Label width can't be 0!");
            return;
        }
        StringBuilder builder = new StringBuilder("<html>");
        char[] chars = longString.toCharArray();
        FontMetrics fontMetrics = jLabel.getFontMetrics(jLabel.getFont());
        int start = 0;
        int len = 0;
        while (start + len < longString.length()) {
            while (true) {
                len++;
                if (start + len > longString.length()) break;
                if (fontMetrics.charsWidth(chars, start, len) > jLabel.getWidth()) {
                    break;
                }
            }
            builder.append(chars, start, len - 1).append("<br/>");
            start = start + len - 1;
            len = 0;
        }
        builder.append(chars, start, longString.length() - start);
        builder.append("</html>");
        jLabel.setText(builder.toString());
    }

    private void writeRecord(String record) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(historyFilePath,true));
            writer.append(record.concat("\n"));
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String readRecord(int lineNum) {
        try {
            int fontSize = Integer.parseInt(configSetting.getFontSize());
            fontSize = 4;
            StringBuilder sb = new StringBuilder();
            String line;
            sb.append("<html><font size='"+fontSize+"' color="+"'#4A766E'>");
            ReversedLinesFileReader reader = new ReversedLinesFileReader(new File(historyFilePath),100,"UTF-8");
            while(lineNum > 0) {
                line = reader.readLine();
                if(line == null) break;
                sb.append(line).append("<br/>");
                lineNum--;
            }
            sb.append("</font></html>");
            return sb.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
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
