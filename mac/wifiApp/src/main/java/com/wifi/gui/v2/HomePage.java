package com.wifi.gui.v2;

import com.wifi.gui.v2.utils.Arith;
import com.wifi.gui.v2.utils.Utils;
import com.wifi.configSetting;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.Thread.sleep;

public class HomePage {
    private JPanel rootPanel;
    private JTabbedPane pageHomeTabbedPane;
    private JButton registerBtn;
    private JButton launchBtn;
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
    private JLabel loginStatusLabel;
    private JPanel getCoinPanel;
    private JLabel ads1Label;
    private JLabel ads2Label;
    private JLabel ads3Label;
    private JScrollPane seeAdsScrollPane;
    private JLabel spacer1Label;
    private JLabel spacer2Label;
    private JLabel adsDetailIcon;
    private JLabel adsDetailText;
    private JButton adsPageRetBtn;
    private JLabel ads4Label;
    private JLabel ads5Label;
    private JScrollPane allAdsPane;
    private JScrollPane adsDetailPane;
    private JLabel historyLabel;
    private JLabel purchase1Label;
    private JLabel purchase2Label;
    private JLabel purchase3Label;
    private JLabel purchase4Label;
    private JLabel purchase5Label;
    private JPanel pLogoutPanel;
    private JPanel pLoginPanel;
    private JPanel mmLogoutPanel;
    private JPanel mmLoginPanel;
    private JPanel purchaseDetailPanel;
    private JScrollPane allPurchasePane;
    private JLabel purchaseIconLabel;
    private JLabel purchaseDesLabel;
    private JButton purchaseBtn;
    private JButton purchaseRetBtn;
    private JLabel purchasePriceLabel;
    private JScrollPane purchaseDetailPane;
    private JLabel addNumLabel;
    private JLabel minusNumLabel;
    private JTextField purchaseNumField;
    private JTabbedPane allPurchasePanel;
    private JScrollPane orderDetailPane;
    private JLabel orderItemIcon;
    private JLabel orderItemName;
    private JLabel orderItemPrice;
    private JLabel orderItemNum;
    private JLabel orderItemTotal;
    private JButton purchaseDetailRetBtn;
    private JLabel orderTimestamp;
    private JLabel orderSequence;
    private JPanel orderPanel;
    private JPanel orderListLoginPanel;
    private JPanel orderListLogoutPanel;
    private JPanel orderListPanel;
    private JScrollPane orderListScrollPane;
    private JLabel order1Label;
    private JLabel order2Label;
    private JLabel order3Label;
    private JLabel order4Label;
    private JLabel order5Label;
    private JLabel order6Label;
    private JLabel order7Label;
    private JPanel orderDetailPanel;
    private JLabel orderDetailIconLabel;
    private JLabel orderDetailTextLabel;
    private JButton orderDetailRetBtn;
    private JScrollPane orderDetailScrollPane;
    private JPanel connectPanel;
    private JPanel connectMainPanel;
    private JPanel loginAdsPanel;
    private JLabel loginAdsLabel;

    private JFrame fJFrame;
    private Register nextRegister;
    private Launch nextLaunch;
    private String rootPath = System.getProperty("user.dir");
    private int retStatus = -1;
    private double leftCoin = 0.0;
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

    // wallet page parameters
    private int historyReadlineNum = 30;

    // purchase page parameters
    private int purchaseNum = 1;
    private double purchasePrice = 1.0;

    // order page parameters
    private int orderSequenceNum = 0;
    private ArrayList<OrderSheet> myOrder = new ArrayList<>();

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
                    // read balance from blockchain
                    try {
                        ProcessBuilder pb = new ProcessBuilder(
                                "python3",
                                rootPath.concat("/scripts/contactchain.py")
                        );
                        pb.redirectErrorStream(true);
                        Process process = pb.start();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while((line = reader.readLine()) != null) {
                            sb.append(line);
                            System.out.println(line);
                        }
                        String priceStr = sb.toString();
                        priceStr = priceStr.split(":")[1];
                        leftCoin = Double.valueOf(priceStr);
                        process.waitFor();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    // record item
                    writeRecord("-1: 登陆");
                    sleep(200);
                    String historyStr = readRecord(historyReadlineNum);
                    historyLabel.setText(historyStr);
                } else {
                    launchTipLabel.setText("连接失败!");
                    launchLabels[3].setIcon(unLaunchIcon);
                    launchLabels[3].setVisible(true);
                    connectStatusL.setIcon(unconnectedIcon);
                    connectStatusL.setText("未连接   ");
                }
                setPageStatus();
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }).start();
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
        spacer1Label.setPreferredSize(new Dimension(10,(int)rWidth/15));
        spacer2Label.setBorder(new LineBorder(bgColor));
        spacer2Label.setPreferredSize(new Dimension(10,(int)rWidth/15));
        ImageIcon img1 = new ImageIcon(rootPath + "/img/xingbake1.jpg");
        ImageIcon img2 = new ImageIcon(rootPath + "/img/xingbake2.jpg");
        ImageIcon img3 = new ImageIcon(rootPath + "/img/xingbake3.jpg");
        ImageIcon img4 = new ImageIcon(rootPath + "/img/kendeji1.jpg");
        ImageIcon img5 = new ImageIcon(rootPath + "/img/kendeji2.jpg");
        JLabel seeAdsLabels[] = {ads1Label,ads2Label,ads3Label,ads4Label,ads5Label};
        ImageIcon icons[] = {img1,img2,img3,img4,img5};
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(new JSONObject("{'name':'经典热巧克力','payBack':0.3}"));
        jsonArray.put(new JSONObject("{'name':'密思朵咖啡','payBack':0.2}"));
        jsonArray.put(new JSONObject("{'name':'抹茶拿铁','payBack':0.4}"));
        jsonArray.put(new JSONObject("{'name':'下午茶套餐','payBack':0.5}"));
        jsonArray.put(new JSONObject("{'name':'鸡翅套餐','payBack':0.3}"));
        for(int i=0;i<seeAdsLabels.length;i++) {
            JLabel label = seeAdsLabels[i];
            setJLabelIcon(label,icons[i],rootPanel.getWidth(),0.3);
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            String name = jsonObj.getString("name");
            double payBack = jsonObj.getDouble("payBack");
            label.setText("<html><p>"+name+"</p><br/><font color='#5C4033'>报酬 "+payBack+"</font></html>");
            label.setVerticalTextPosition(SwingConstants.TOP);
        }
    }

    private void setPurchasePage() {
        // set all purchase page
        ImageIcon pIcon1 = new ImageIcon(rootPath.concat("/img/purchase1.jpg"));
        ImageIcon pIcon2 = new ImageIcon(rootPath.concat("/img/purchase2.jpg"));
        ImageIcon pIcon3 = new ImageIcon(rootPath.concat("/img/purchase3.jpg"));
        ImageIcon pIcon4 = new ImageIcon(rootPath.concat("/img/purchase4.jpg"));
        ImageIcon pIcon5 = new ImageIcon(rootPath.concat("/img/purchase5.jpg"));
        ImageIcon purchaseIcons[] = {pIcon1,pIcon2,pIcon3,pIcon4,pIcon5};
        JLabel purchaseLabels[] = {purchase1Label,purchase2Label,purchase3Label,purchase4Label,purchase5Label};
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(new JSONObject("{'des':'焦糖咖啡星冰乐','price':'3'}"));
        jsonArray.put(new JSONObject("{'des':'密思朵咖啡','price':'2.5'}"));
        jsonArray.put(new JSONObject("{'des':'抹茶拿铁','price':'3'}"));
        jsonArray.put(new JSONObject("{'des':'英式红茶','price':'4'}"));
        jsonArray.put(new JSONObject("{'des':'经典热巧克力','price':'3.5'}"));
        for(int i=0;i<purchaseLabels.length;i++) {
            ImageIcon icon = purchaseIcons[i];
            JLabel label = purchaseLabels[i];
            // set icon
            setJLabelIcon(label,icon,rootWidth,0.3);
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            // set text
            String des = jsonObj.getString("des");
            double price = jsonObj.getDouble("price");
            String text = "<html><p>"+des+"</p><br/><br/><font color='#8C7853'>价格 "+price+"</font></html>";
            label.setText(text);
            label.setVerticalTextPosition(SwingConstants.TOP);
        }
        // set detail purchase page
        int rWidth = rootPanel.getWidth();
        double addSubIconRatio = 0.05;
        String addNumIconPath = rootPath.concat("/img/arrowUp.png");
        String minusNumIconPath = rootPath.concat("/img/arrowDown.png");
        setJLabelIcon(addNumLabel,addNumIconPath,rWidth,addSubIconRatio);
        setJLabelIcon(minusNumLabel,minusNumIconPath,rWidth,addSubIconRatio);
        purchaseNumField.setText(String.valueOf(purchaseNum));
        purchaseNumField.setColumns(2);
        // set order detail pane
        orderDetailPane.setVisible(false);
    }

    private void setOrderListPage() {
        JLabel labels[] = {order1Label,order2Label,order3Label,order4Label,order5Label,order6Label,order7Label};
        for(JLabel label: labels) {
            label.setVisible(false);
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
        ImageIcon ads1Icon = new ImageIcon(rootPath.concat("/img/xingbake1.jpg"));
        ImageIcon ads2Icon = new ImageIcon(rootPath.concat("/img/xingbake2.jpg"));
        ImageIcon ads3Icon = new ImageIcon(rootPath.concat("/img/xingbake3.jpg"));
        ImageIcon ads4Icon = new ImageIcon(rootPath.concat("/img/kendeji1.jpg"));
        ImageIcon ads5Icon = new ImageIcon(rootPath.concat("/img/kendeji2.jpg"));
        JLabel adLabels[] = {ad1Label,ad2Label,ad3Label,ad4Label,ad5Label};
        ImageIcon adsIcons[] = {ads1Icon,ads2Icon,ads3Icon,ads4Icon,ads5Icon};
        double raRatio = (1 - 0.25) / adLabels.length;
        for(int i=0;i<adLabels.length;i++){
            ImageIcon icon = adsIcons[i];
            JLabel tmpLabel = adLabels[i];
            tmpLabel.setVisible(false);
            tmpLabel.setText("");
            setJLabelIcon(tmpLabel,icon,rootPanel.getWidth(),raRatio);
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

    private void setMakeMoneyStatus() {
        boolean status = (loginStatus == 1);
        mmLoginPanel.setVisible(status);
        mmLogoutPanel.setVisible(!status);
    }

    private void setPurchaseStatus() {
        boolean status = (loginStatus == 1);
        pLoginPanel.setVisible(status);
        pLogoutPanel.setVisible(!status);
    }

    private void setOrderStatus() {
        boolean status = (loginStatus ==1);
        orderListLoginPanel.setVisible(status);
        orderListLogoutPanel.setVisible(!status);
    }

    private void setPageStatus() {
        setWalletStatus();
        setMakeMoneyStatus();
        setPurchaseStatus();
        setOrderStatus();
    }

    private void showOrderList() {
        double rWidth = rootPanel.getWidth();
        JLabel labels[] = {order1Label,order2Label,order3Label,order4Label,order5Label,order6Label,order7Label};
        int i=labels.length-1;
        for(OrderSheet order: myOrder) {
//            JLabel label = new JLabel();
            if(i < 0) break;
            JLabel label = labels[i];i--;
            if(label.getIcon() != null) continue;
            label.setVisible(true);
            Icon icon = order.getIcon();
            String iconPath = ((ImageIcon) icon).getDescription();
            setJLabelIcon(label,iconPath,rWidth,0.3);
            StringBuilder sb = new StringBuilder();
            sb.append("<html><body>")
                    .append("订单日期：").append(order.getDateStr()).append("<br/>")
                    .append("商品名称：").append(order.getGoodsName()).append("<br/>")
                    .append("<!--div >商品价格：").append(order.getGoodsPrice()).append("<br>")
                    .append("商品数量：").append(order.getGoodsNum()).append("<br>")
                    .append("商品总价：").append(Arith.mul(order.getGoodsNum(),order.getGoodsPrice())).append("<br/>")
                    .append("</div--!>订单号：").append(order.getOrderSeq())
              .append("</body></html>");
            label.setText(sb.toString());
            changeCursor(label,"orderList");
        }
    }

    private void _init() {
        this.rootPanel.setSize(360,500);
        breakConnBtn.setVisible(false);
        connectStatusL.setText("");
        connectStatusL.setVisible(false);
        // remove new panel
        setLaunchImgStatus();
        // set recognise ssid label text to space
        ssidLabel.setText("blockchain");
        ssidLabel.setForeground(rootPanel.getBackground());
        // users can get coins by reading ads
        seeAds();
        adsDetailPane.setVisible(false);
        // get root panel width and height
        rootWidth = rootPanel.getWidth();
        // set purchase page
        setPurchasePage();
        purchaseDetailPane.setVisible(false);
        // set order list page
        setOrderListPage();
        orderDetailScrollPane.setVisible(false);
        // set all page status
        setPageStatus();
        // set login advertisement
        new Thread(() -> {
            try {
                pageHomeTabbedPane.setVisible(false);
                ImageIcon loginAdsIcon = new ImageIcon(rootPath.concat("/img/preAds4.jpg"));
                int rWidth = rootPanel.getWidth();
                setJLabelIcon(loginAdsLabel, loginAdsIcon, rWidth, 1);
                Thread.sleep(3000);
                loginAdsPanel.setVisible(false);
                pageHomeTabbedPane.setVisible(true);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }).start();
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
                    setPageStatus();

                } catch (IOException|InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        adsPageRetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Document doc = Jsoup.parse(adsDetailText.getText());
                Elements adsText = doc.getElementsByTag("font");
                String price = adsText.text();
                price = price.split(" ")[1];
                writeRecord("+"+price+": 看广告");
                writeLeftCoin(Arith.add(leftCoin,Double.valueOf(price)));
                adsDetailPane.setVisible(false);
                allAdsPane.setVisible(true);
                String historyStr = readRecord(historyReadlineNum);
                historyLabel.setText(historyStr);
            }
        });
        purchaseRetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                purchaseDetailPane.setVisible(false);
                allPurchasePane.setVisible(true);
                changePurchaseNum(1);
            }
        });
        addNumLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                purchaseNum++;
                purchaseNumField.setText(String.valueOf(purchaseNum));
            }
        });
        minusNumLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(purchaseNum > 1) {
                    purchaseNum--;
                }
                purchaseNumField.setText(String.valueOf(purchaseNum));
            }
        });
        purchaseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // set left coin
                double cost = Arith.mul(purchasePrice,purchaseNum);
                if (leftCoin > cost) {
                    String purchaseName = purchaseDesLabel.getText();
                    writeRecord("-"+cost+"="+purchasePrice+"x"+purchaseNum+": 购买"+purchaseName);
                    historyLabel.setText(readRecord(historyReadlineNum));
                    System.out.println("[INFO] purchase successfully!");
                    writeLeftCoin(Arith.sub(leftCoin,cost));
                    orderSequenceNum++;
                    // set order page
                    ImageIcon icon = (ImageIcon) purchaseIconLabel.getIcon();
                    String orderItemNameStr = purchaseDesLabel.getText();
                    double totalCost = Arith.mul(purchasePrice,purchaseNum);
                    orderItemIcon.setIcon(icon);
                    orderItemName.setText("商品名称："+orderItemNameStr);
                    orderItemPrice.setText("商品价格："+String.valueOf(purchasePrice));
                    orderItemNum.setText("购买数量："+String.valueOf(purchaseNum));
                    orderItemTotal.setText("总额："+String.valueOf(totalCost));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    String dateStr = dateFormat.format(new Date());
                    orderTimestamp.setText("订单时间："+dateStr);
                    String orderSeqNum = String.format("%04d",orderSequenceNum);
                    orderSequence.setText("订单号："+orderSeqNum);
                    // store order info
                    OrderSheet order = new OrderSheet();
                    order.setDateStr(dateStr);
                    order.setGoodsName(orderItemNameStr);
                    order.setGoodsNum(purchaseNum);
                    order.setGoodsPrice(purchasePrice);
                    order.setOrderSeq(orderSeqNum);
                    order.setIcon(icon);
                    myOrder.add(order);
                    changePurchaseNum(1);
                    // generate order list
                    showOrderList();
                    purchaseDetailPane.setVisible(false);
                    orderDetailPane.setVisible(true);
                } else {
                    System.out.println("[ERROR] left coin not enough");
                }
            }
        });
        purchaseDetailRetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderDetailPane.setVisible(false);
                allPurchasePane.setVisible(true);
            }
        });
        // set advertisement events in "make money" page
        JLabel adsLabels[] = {ads1Label,ads2Label,ads3Label,ads4Label,ads5Label};
        for(JLabel label: adsLabels) {
            changeCursor(label,"advertisement");
        }
        // set purchase events in "purchase" page
        JLabel purchaseLabels[] = {purchase1Label,purchase2Label,purchase3Label,purchase4Label,purchase5Label};
        for(JLabel label: purchaseLabels) {
            changeCursor(label,"purchase");
        }
        orderListPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
            }
        });
        orderListPanel.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }
        });
        orderDetailRetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderDetailScrollPane.setVisible(false);
                orderListScrollPane.setVisible(true);
            }
        });
    }

    private void changeCursor(Component component, String pageType) {
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
                switch (pageType) {
                    case "advertisement": pressAds(e);break;
                    case "purchase": pressPurchase(e);break;
                    case "orderList": pressOrders(e);break;
                    default: System.out.println("[ERROR] invalid page type!");
                }
            }
        });
    }

    private void pressAds(MouseEvent e) {
        allAdsPane.setVisible(false);
        Component cmp = e.getComponent();
        if(cmp instanceof JLabel) {
            JLabel label = (JLabel)cmp;
            // get indicated text
            String text = label.getText();
            Document doc = Jsoup.parse(text);
            Elements nameEle = doc.getElementsByTag("p");
            String name = nameEle.text();
            Elements payBackEle = doc.getElementsByTag("font");
            String payBack = payBackEle.text();
            payBack = payBack.split(" ")[1];
            text = "<html>"+name+"<br/><font color='#5C4033'>报酬 "+payBack+"<font><html/>";
            // reset image size
            ImageIcon icon = (ImageIcon) label.getIcon();
            String filePath = icon.getDescription();
            adsDetailText.setSize((int)(rootPanel.getWidth()*0.95),0);
            setJLabelIcon(adsDetailIcon,filePath,rootWidth,0.93);
            adsDetailText.setText(text);
            adsDetailPane.setVisible(true);
            fJFrame.pack();
        }
    }

    private void pressPurchase(MouseEvent e) {
        allPurchasePane.setVisible(false);
        Component cmp = e.getComponent();
        if(cmp instanceof JLabel) {
            JLabel label = (JLabel)cmp;
            // get indicated text
            String text = label.getText();
            Document doc = Jsoup.parse(text);
            Elements desElement = doc.getElementsByTag("p");
            Elements priceElement = doc.getElementsByTag("font");
            String des = desElement.text();
            String price = priceElement.text();
            String priceNum = price.split(" ")[1];
            purchasePrice = Double.valueOf(priceNum);
            purchaseDesLabel.setText(des);
            purchasePriceLabel.setText("<html><font color='#8C7853'>"+price+"</font></html>");
            // reset image size
            ImageIcon icon = (ImageIcon) label.getIcon();
            String filePath = icon.getDescription();
            setJLabelIcon(purchaseIconLabel,filePath,rootWidth,0.82);
            purchaseDetailPane.setVisible(true);
            fJFrame.pack();
        }
    }

    private void pressOrders(MouseEvent e) {
        orderListScrollPane.setVisible(false);
        Component cmp = e.getComponent();
        if(cmp instanceof JLabel) {
            JLabel label = (JLabel)cmp;
            // get order detail info
            double rWidth = rootPanel.getWidth();
            ImageIcon icon = (ImageIcon) label.getIcon();
            String iconPath = icon.getDescription();
            setJLabelIcon(orderDetailIconLabel,iconPath,rWidth,0.85);
            String desStr = label.getText();
            desStr = desStr.replaceAll("!--","");
            desStr = desStr.replaceAll("--!","");
            orderDetailTextLabel.setText(desStr);
            orderDetailScrollPane.setVisible(true);
            fJFrame.pack();
        }
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

    private void changePurchaseNum(int purchaseNum) {
        this.purchaseNum = purchaseNum;
        purchaseNumField.setText(String.valueOf(purchaseNum));
    }

    private void writeRecord(String record) {
        try {
            File file = new File(historyFilePath);
            if(file.length() == 0) {
                record = "+10: 注册";
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(historyFilePath,true));
            writer.append(record.concat("\n"));
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String readRecord(int lineNum) {
        try {
            int fontSize = 4;
            StringBuilder sb = new StringBuilder();
            String line;
            sb.append("<html><body><table cellspacing='0' width='315'>");
            ReversedLinesFileReader reader = new ReversedLinesFileReader(new File(historyFilePath),100,"UTF-8");
            while(lineNum > 0) {
                line = reader.readLine();
                if(line == null) break;
                sb.append("<tr><td style='border:1px solid black;'><font size='"+fontSize+"' color='#4A766E'>");
                sb.append(line);
                sb.append("<font size='"+fontSize+"' color='#4A766E'></td></tr>");
                lineNum--;
            }
            sb.append("</table></font><body></html>");
            return sb.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    private void writeLeftCoin(Double leftCoin) {
        try {
            this.leftCoin = leftCoin;
            leftLabel.setText(String.valueOf(leftCoin));
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(new File(rootPath.concat("/wpa_setup/testLeftCoin"))));
            writer.write(String.valueOf(leftCoin));
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
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

    public void setLeftCoin(double leftCoin) {
        this.leftCoin = leftCoin;
    }

    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
