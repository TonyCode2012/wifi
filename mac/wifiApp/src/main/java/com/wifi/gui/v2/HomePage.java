package com.wifi.gui.v2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wifi.configSetting;
import com.wifi.gui.v2.utils.Arith;
import com.wifi.gui.v2.utils.Utils;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
    private JButton breakConnBtn;
    private JLabel connectStatusL;
    private JLabel ssidLabel;
    private JPanel walletPanel;
    private JLabel leftTileLabel;
    private JLabel leftTokenLabel;
    private JLabel loginStatusLabel;
    private JPanel getCoinPanel;
    private JScrollPane setAdsScrollPane;
    private JLabel adsDetailIcon;
    private JLabel adsDetailText;
    private JButton adsPageRetBtn;
    private JTabbedPane allAdsPane;
    private JScrollPane adsDetailPane;
    private JLabel tokenHistoryLabel;
    private JPanel pLogoutPanel;
    private JPanel pLoginPanel;
    private JPanel mmLogoutPanel;
    private JPanel mmLoginPanel;
    private JPanel purchaseDetailPanel;
    private JTabbedPane allPurchasePane;
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
    private JLabel wifiIconLabel;
    private JLabel launchAdsLabel;
    private JLabel leftCoinLabel;
    private JPanel walletLogoutPanel;
    private JPanel walletLoginPanel;
    private JPanel allAdsPanel;
    private JPanel tokenAdsPanel;
    private JPanel coinAdsPanel;
    private JPanel allGoodsPanel;
    private JPanel tokenGoodsPanel;
    private JPanel coinGoodsPanel;
    private JLabel tokenSwitcherLabel;
    private JLabel coinSwitcherLabel;
    private JLabel coinHistoryLabel;
    private JPanel recordSwitcherPanel;

    private JFrame fJFrame;
    private Register nextRegister;
    private Launch nextLaunch;
    private String rootPath = System.getProperty("user.dir");
    private int retStatus = -1;
    private double leftToken = 0.0;
    private double leftCoin = 10.0;
    private ImageIcon launchIcon;
    private ImageIcon unLaunchIcon;
    private ImageIcon connectedIcon;
    private ImageIcon connectedOpaqueIcon;
    private ImageIcon unconnectedIcon;
    private ImageIcon connectingIcon;
    private Thread connStatusImgThread;
    // used to remind other pages of login status
    private int loginStatus = 0;
    private Color bgColor = rootPanel.getBackground();
    private Cursor curCursor = rootPanel.getCursor();
    private int rootWidth;
    private String historyFilePath = rootPath.concat("/config/records");

    // Login page status
    // wifi launch icon
    private ImageIcon wifiLaunchIcons[] = new ImageIcon[4];
    // advertisement icons
    private ImageIcon loginAdsIcons[] = new ImageIcon[5];

    // wallet page parameters
    private int historyReadlineNum = 30;
    
    // reward page
    private ArrayList<JLabel> allAdsLabels = new ArrayList<>();
    private ArrayList<JLabel> tokenAdsLabels = new ArrayList<>();
    private ArrayList<JLabel> coinAdsLabels = new ArrayList<>();
    private ArrayList<JLabel> giftAdsLabels = new ArrayList<>();

    // purchase page parameters
    private int purchaseNum = 1;
    private double purchasePrice = 1.0;
    private ArrayList<JLabel> allGoodsLabels = new ArrayList<>();
    private ArrayList<JLabel> tokenGoodsLabels = new ArrayList<>();
    private ArrayList<JLabel> coinGoodsLabels = new ArrayList<>();
    private ArrayList<JLabel> giftGoodsLabels = new ArrayList<>();

    // order page parameters
    private int orderSequenceNum = 0;
    private ArrayList<OrderSheet> myOrder = new ArrayList<>();

    public void setLaunchPage() {
        // reset launch img status
        resetLaunchPageStatus();

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

//        launchTipLabel.setVisible(true);
//        launchTipLabel.setText("正在连接...");
        // set launch status based on login info
        new Thread(() -> {
            try {
                // store img status icon thread to local
                connStatusImgThread = Thread.currentThread();
                // set wifi icons
                int wifiIconIndex = 0;
                int wifiIconLen = wifiLaunchIcons.length;
                ImageIcon connStatusIcon = connectingIcon;
                String connStr = "正在连接";
                connectStatusL.setVisible(true);
                connectStatusL.setText(connStr);
                while(retStatus == -1) {
                    connectStatusL.setIcon(connStatusIcon);
                    wifiIconLabel.setIcon(wifiLaunchIcons[wifiIconIndex]);
                    connStatusIcon = (connStatusIcon == connectingIcon ? connectedOpaqueIcon : connectingIcon);
                    sleep(600);
                    wifiIconIndex = (++wifiIconIndex) % wifiIconLen;
                }
                ssidLabel.setForeground(Color.black);
                if(retStatus == 0) {
                    setBreakdownStatus();
                } else if(retStatus == 64) {
//                    launchTipLabel.setText("连接成功!");
                    wifiIconLabel.setIcon(wifiLaunchIcons[3]);
                    connectStatusL.setIcon(connectedIcon);
                    connectStatusL.setText("已连接   ");
                    // set wallet left value
                    setLoginStatus(1);
                    //========== do deduction ==========//
                    File file = new File(configSetting.getWpaCmdPath().concat("/testLeftCoin"));
                    BufferedReader leftCoinReader = new BufferedReader(new FileReader(file));
                    String leftCoinStr = leftCoinReader.readLine();
                    String balanceRecord;
                    if(! leftCoinStr.contains("registerReward")) {
                        //====== read balance from blockchain =====//
                        try {
                            ObjectMapper objMapper = new ObjectMapper();
                            JsonNode jsonNode = objMapper.readTree(new File(configSetting.getWpaProfileFPath()));
                            String accountStr = jsonNode.get("account").asText();
                            try {
                                ProcessBuilder pb = new ProcessBuilder(
                                        "python3",
                                        rootPath.concat("/scripts/contactchain.py"),
                                        "0x01c96e4d9be1f4aef473dc5dcf13d8bd1d4133cd"
                                );
                                pb.redirectErrorStream(true);
                                Process process = pb.start();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                                StringBuilder sb = new StringBuilder();
                                String line;
                                System.out.println("========== get reward from blockchain ==========");
                                while ((line = reader.readLine()) != null) {
                                    sb.append(line);
                                    System.out.println("<reward>"+line+"<reward>");
                                }
                                process.waitFor();
                                leftToken = Double.valueOf(sb.toString());
                            } catch (IOException e) {
                                System.out.println(e.getMessage());
                            }
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        leftToken = leftToken - 10;
                        // record item
                        writeRecord("-10: 登陆",historyFilePath);
                        // deduct 10 coin back up
//                        new Thread(() -> {
//                            try {
//                                ArrayList<String> cmd = new ArrayList<>();
//                                cmd.add(rootPath.concat("/wpa_setup/client"));
//                                cmd.add("-app=deduction");
//                                cmd.add("0xf439bf68fc695b4a62f9e3322c75229ba5a0ff33");
//                                cmd.add("{\"address\":\"01c96e4d9be1f4aef473dc5dcf13d8bd1d4133cd\"," +
//                                        "\"crypto\":{\"cipher\":\"aes-128-ctr\"," +
//                                        "\"ciphertext\":\"a18f5974b74abe2712ca489432723049748439d888ab92ede2f9a94c613fad48\"," +
//                                        "\"cipherparams\":{\"iv\":\"1fd8f3ec4e2496e23f4963eae54ac1a5\"},\"kdf\":\"scrypt\"," +
//                                        "\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8," +
//                                        "\"salt\":\"6351e5dc8d2273d1f038bf0d770773cb82fc30d6bd3a623287584e24e44d086c\"}," +
//                                        "\"mac\":\"edb0e6a6075f7e1c1ef3347745aff2b928a49d9300d4cd627f3d0403e83bf086\"}," +
//                                        "\"id\":\"1812d540-a745-4174-bb67-bc69a33fb15c\",\"version\":3}");
//                                ProcessBuilder pb = new ProcessBuilder(cmd);
//                                Process process = pb.start();
//                                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                                String line;
//                                while ((line = reader.readLine()) != null) {
//                                    System.out.println(line);
//                                }
//                            } catch (IOException ex) {
//                                System.out.println(ex.getMessage());
//                            }
//                        }).start();
                    } else {
                        String[] tmpStr = leftCoinStr.split(":");
                        leftToken = Double.valueOf(tmpStr[0]);
                        writeRecord("+"+leftToken+": 注册",historyFilePath);
                    }
                    sleep(200);
                    String historyStr = readRecord(historyReadlineNum,historyFilePath);
                    tokenHistoryLabel.setText(historyStr);
                } else {
//                    launchTipLabel.setText("连接失败!");
                    wifiIconLabel.setIcon(unLaunchIcon);
                    connectStatusL.setIcon(unconnectedIcon);
                    connectStatusL.setText("未连接   ");
                }
                setPageStatus();
            } catch (InterruptedException|IOException ex) {
                System.out.println(ex.getMessage());
            }
        }).start();
        // set login page advertisement
        new Thread(() -> {
            try {
                int adsIndex = 0;
                int adsLen = loginAdsIcons.length;
                while (true) {
                    launchAdsLabel.setIcon(loginAdsIcons[adsIndex]);
                    sleep(2500);
                    adsIndex = ++adsIndex % adsLen;
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }).start();
    }
    
    /*
    * 钱包页面
    * */
    private void setWalletPage() {
        int rWidth = rootPanel.getWidth();
//        Color myBlue = new Color(47,147,250);
        tokenSwitcherLabel.setText("积分");
        tokenSwitcherLabel.setOpaque(true);
        tokenSwitcherLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        coinSwitcherLabel.setText("代币");
        coinSwitcherLabel.setOpaque(true);
        coinSwitcherLabel.setBorder(BorderFactory.createEtchedBorder());
        tokenSwitcherLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                tokenHistoryLabel.setVisible(true);
                coinHistoryLabel.setVisible(false);
                tokenSwitcherLabel.setBorder(BorderFactory.createLoweredBevelBorder());
                coinSwitcherLabel.setBorder(BorderFactory.createEtchedBorder());
            }
        });
        coinSwitcherLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                coinHistoryLabel.setVisible(true);
                tokenHistoryLabel.setVisible(false);
                coinSwitcherLabel.setBorder(BorderFactory.createLoweredBevelBorder());
                tokenSwitcherLabel.setBorder(BorderFactory.createEtchedBorder());
            }
        });
        // set token coin image
        setJLabelIcon(leftTokenLabel,rootPath + "/img/token.png",rWidth,0.08);
        leftTokenLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        // set real coin image
        setJLabelIcon(leftCoinLabel,rootPath + "/img/coin.png",rWidth,0.08);
        leftCoinLabel.setHorizontalTextPosition(SwingConstants.LEFT);
    }

    /*
    * 广告奖励页面
    * */
    private void setRewardPage() {
        // set token advertisement
        JSONArray tokenJsonarry = new JSONArray();
        tokenJsonarry.put(new JSONObject("{'name':'经典热巧克力','payBack':10,'imgPath':'"+rootPath.concat("/img/kendeji1.jpg")+"'}"));
        tokenJsonarry.put(new JSONObject("{'name':'密思朵咖啡','payBack':10,'imgPath':'"+rootPath.concat("/img/kendeji1.jpg")+"'}}"));
        tokenJsonarry.put(new JSONObject("{'name':'抹茶拿铁','payBack':15,'imgPath':'"+rootPath.concat("/img/kendeji1.jpg")+"'}}"));
        tokenJsonarry.put(new JSONObject("{'name':'下午茶套餐','payBack':14,'imgPath':'"+rootPath.concat("/img/kendeji1.jpg")+"'}}"));
        tokenJsonarry.put(new JSONObject("{'name':'鸡翅套餐','payBack':10,'imgPath':'"+rootPath.concat("/img/kendeji1.jpg")+"'}}"));
        // set coin advertisement
        JSONArray coinJsonarry = new JSONArray();
        coinJsonarry.put(new JSONObject("{'name':'经典热巧克力','payBack':10,'imgPath':'"+rootPath.concat("/img/kendeji2.jpg")+"'}"));
        coinJsonarry.put(new JSONObject("{'name':'密思朵咖啡','payBack':10,'imgPath':'"+rootPath.concat("/img/kendeji2.jpg")+"'}}"));
        coinJsonarry.put(new JSONObject("{'name':'抹茶拿铁','payBack':15,'imgPath':'"+rootPath.concat("/img/kendeji2.jpg")+"'}}"));
        coinJsonarry.put(new JSONObject("{'name':'下午茶套餐','payBack':14,'imgPath':'"+rootPath.concat("/img/kendeji2.jpg")+"'}}"));
        coinJsonarry.put(new JSONObject("{'name':'鸡翅套餐','payBack':10,'imgPath':'"+rootPath.concat("/img/kendeji2.jpg")+"'}}"));
        // set gift advertisement
        JSONArray giftJsonarry = new JSONArray();
        giftJsonarry.put(new JSONObject("{'name':'经典热巧克力','payBack':10,'imgPath':'"+rootPath.concat("/img/xingbake1.jpg")+"'}"));
        giftJsonarry.put(new JSONObject("{'name':'密思朵咖啡','payBack':10,'imgPath':'"+rootPath.concat("/img/xingbake1.jpg")+"'}}"));
        giftJsonarry.put(new JSONObject("{'name':'抹茶拿铁','payBack':15,'imgPath':'"+rootPath.concat("/img/xingbake1.jpg")+"'}}"));
        giftJsonarry.put(new JSONObject("{'name':'下午茶套餐','payBack':14,'imgPath':'"+rootPath.concat("/img/xingbake1.jpg")+"'}}"));
        giftJsonarry.put(new JSONObject("{'name':'鸡翅套餐','payBack':10,'imgPath':'"+rootPath.concat("/img/xingbake1.jpg")+"'}}"));
        JSONArray[] jsonArrys = {tokenJsonarry,coinJsonarry};
        ArrayList[] typeArrys = {tokenAdsLabels,coinAdsLabels};
        String[] rewardTypes = {"积分","代币"};
        // set labels
        for(int i=0;i<jsonArrys.length;i++) {
            JSONArray jsonArry = jsonArrys[i];
            ArrayList<JLabel> typeArry = typeArrys[i];
            String rewardType = rewardTypes[i];
            for(int j=0;j<jsonArry.length();j++) {
                JSONObject jsonObj = jsonArry.getJSONObject(j);
                // get properties
                String name = jsonObj.getString("name");
                double payBack = jsonObj.getDouble("payBack");
                String imgPath = jsonObj.getString("imgPath");
                JLabel label = new JLabel();
                setJLabelIcon(label,imgPath,rootPanel.getWidth(),0.3);
                label.setText("<html><p>"+name+"</p><br/><font color='#5C4033'>报酬 "+payBack+" "+rewardType+"</font></html>");
                label.setVerticalTextPosition(SwingConstants.TOP);
                typeArry.add(label);
                allAdsLabels.add(label);
            }
        }

        // add label to corresponding tab
        ArrayList[] typeLabelArrys = {tokenAdsLabels,coinAdsLabels};
        JPanel[] panels = {tokenAdsPanel, coinAdsPanel};
        int adsNum = 0;
        for(int i=0;i<typeLabelArrys.length;i++) {
            ArrayList<JLabel> typeArry = typeLabelArrys[i];
            adsNum += typeArry.size();
            JPanel panel = panels[i];
            panel.setLayout(new GridLayout(typeArry.size(),1));
        }
        allAdsPanel.setLayout(new GridLayout(adsNum,1));
        allAdsLabels.forEach(allAdsPanel::add);
    }

    /*
    * 商品页面
    * */
    private void setPurchasePage() {
        // set token goods
        JSONArray tokenJsonarry = new JSONArray();
        tokenJsonarry.put(new JSONObject("{'name':'经典热巧克力','price':10,'imgPath':'"+rootPath.concat("/img/purchase1.jpg")+"'}"));
        tokenJsonarry.put(new JSONObject("{'name':'密思朵咖啡','price':10,'imgPath':'"+rootPath.concat("/img/purchase1.jpg")+"'}}"));
        tokenJsonarry.put(new JSONObject("{'name':'抹茶拿铁','price':15,'imgPath':'"+rootPath.concat("/img/purchase1.jpg")+"'}}"));
        tokenJsonarry.put(new JSONObject("{'name':'下午茶套餐','price':14,'imgPath':'"+rootPath.concat("/img/purchase1.jpg")+"'}}"));
        tokenJsonarry.put(new JSONObject("{'name':'鸡翅套餐','price':10,'imgPath':'"+rootPath.concat("/img/purchase1.jpg")+"'}}"));
        // set coin goods
        JSONArray coinJsonarry = new JSONArray();
        coinJsonarry.put(new JSONObject("{'name':'经典热巧克力','price':10,'imgPath':'"+rootPath.concat("/img/purchase2.jpg")+"'}"));
        coinJsonarry.put(new JSONObject("{'name':'密思朵咖啡','price':10,'imgPath':'"+rootPath.concat("/img/purchase2.jpg")+"'}}"));
        coinJsonarry.put(new JSONObject("{'name':'抹茶拿铁','price':15,'imgPath':'"+rootPath.concat("/img/purchase2.jpg")+"'}}"));
        coinJsonarry.put(new JSONObject("{'name':'下午茶套餐','price':14,'imgPath':'"+rootPath.concat("/img/purchase2.jpg")+"'}}"));
        coinJsonarry.put(new JSONObject("{'name':'鸡翅套餐','price':10,'imgPath':'"+rootPath.concat("/img/purchase2.jpg")+"'}}"));
        // set gift goods
        JSONArray giftJsonarry = new JSONArray();
        giftJsonarry.put(new JSONObject("{'name':'经典热巧克力','price':10,'imgPath':'"+rootPath.concat("/img/purchase3.jpg")+"'}"));
        giftJsonarry.put(new JSONObject("{'name':'密思朵咖啡','price':10,'imgPath':'"+rootPath.concat("/img/purchase3.jpg")+"'}}"));
        giftJsonarry.put(new JSONObject("{'name':'抹茶拿铁','price':15,'imgPath':'"+rootPath.concat("/img/purchase3.jpg")+"'}}"));
        giftJsonarry.put(new JSONObject("{'name':'下午茶套餐','price':14,'imgPath':'"+rootPath.concat("/img/purchase3.jpg")+"'}}"));
        giftJsonarry.put(new JSONObject("{'name':'鸡翅套餐','price':10,'imgPath':'"+rootPath.concat("/img/purchase3.jpg")+"'}}"));
        // set labels
        JSONArray[] jsonArrys = {tokenJsonarry,coinJsonarry};
        ArrayList[] typeArrys = {tokenGoodsLabels,coinGoodsLabels};
        String[] expenseTypes = {"积分","代币"};
        for(int i=0;i<jsonArrys.length;i++) {
            JSONArray jsonArry = jsonArrys[i];
            ArrayList<JLabel> typeArry = typeArrys[i];
            String expenseType = expenseTypes[i];
            for(int j=0;j<jsonArry.length();j++) {
                JSONObject jsonObj = jsonArry.getJSONObject(j);
                // get properties
                String name = jsonObj.getString("name");
                double price = jsonObj.getDouble("price");
                String imgPath = jsonObj.getString("imgPath");
                JLabel label = new JLabel();
                setJLabelIcon(label,imgPath,rootWidth,0.3);
                label.setText("<html><p>"+name+"</p><br/><br/><font color='#8C7853'>价格 "+price+" "+expenseType+"</font></html>");
                label.setVerticalTextPosition(SwingConstants.TOP);
                typeArry.add(label);
                allGoodsLabels.add(label);
            }
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

        // add labels to corresponding tab
        ArrayList[] typeLabelArrys = {tokenGoodsLabels,coinGoodsLabels};
        JPanel[] panels = {tokenGoodsPanel,coinGoodsPanel};
        int goodsNum = 0;
        for(int i=0;i<typeLabelArrys.length;i++) {
            ArrayList<JLabel> typeLabelArry = typeLabelArrys[i];
            goodsNum += typeLabelArry.size();
            JPanel panel = panels[i];
            panel.setLayout(new GridLayout(typeLabelArry.size(),1));
        }
        allGoodsPanel.setLayout(new GridLayout(goodsNum,1));
        allGoodsLabels.forEach(allGoodsPanel::add);
    }

    private void setOrderListPage() {
        JLabel labels[] = {order1Label,order2Label,order3Label,order4Label,order5Label,order6Label,order7Label};
        for(JLabel label: labels) {
            label.setVisible(false);
        }
    }

    private Object deepClone(JLabel label){
        try {
            //将对象写到流里
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(label);
            //从流里读回来
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (IOException|ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void resetLaunchPageStatus() {
        wifiIconLabel.setVisible(true);
        ssidLabel.setForeground(rootPanel.getBackground());
        setRetStatus(-1);
    }

    private void setBreakdownStatus() {
        wifiIconLabel.setVisible(false);
        launchTipLabel.setVisible(false);
        connectStatusL.setVisible(false);
        breakConnBtn.setVisible(false);
        nextRegister.setBreakNetwork(true);
        registerBtn.setVisible(true);
        ssidLabel.setVisible(false);
//        ssidLabel.setForeground(rootPanel.getBackground());
    }

    private void setLaunchPageStatus() {
        // get root panel width and height
        double rWidth = rootPanel.getWidth();
        double rHeight = rootPanel.getHeight();
        // set login page advertisement icon
        loginAdsIcons[0] = new ImageIcon(rootPath.concat("/img/xingbake1.jpg"));
        loginAdsIcons[1] = new ImageIcon(rootPath.concat("/img/xingbake2.jpg"));
        loginAdsIcons[2] = new ImageIcon(rootPath.concat("/img/xingbake3.jpg"));
        loginAdsIcons[3] = new ImageIcon(rootPath.concat("/img/kendeji1.jpg"));
        loginAdsIcons[4] = new ImageIcon(rootPath.concat("/img/kendeji2.jpg"));
        double raRatio = 0.33;
        for(ImageIcon icon: loginAdsIcons) {
            Utils.setIconByHeight(raRatio,rHeight,icon);
        }
        launchAdsLabel.setText("");
        // set wifi icons
        wifiLaunchIcons[0] = new ImageIcon(rootPath + "/img/launch1.png");
        wifiLaunchIcons[1] = new ImageIcon(rootPath + "/img/launch2.png");
        wifiLaunchIcons[2] = new ImageIcon(rootPath + "/img/launch3.png");
        wifiLaunchIcons[3] = new ImageIcon(rootPath + "/img/launch4.png");
        double rlRatio = 0.2;
        for(ImageIcon icon: wifiLaunchIcons) {
            Utils.setIconByWidth(rlRatio,rWidth,icon);
        }
        launchIcon = wifiLaunchIcons[3];
        Utils.setIconByWidth(rlRatio,rWidth,rootPath.concat("/img/unLaunch.png"));
        wifiIconLabel.setText("");
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
        double rcRatio = 0.03;
        ImageIcon connIcons[] = {connectedIcon,connectedOpaqueIcon,unconnectedIcon,connectingIcon};
        for(ImageIcon icon: connIcons) {
            Utils.setIconByWidth(rcRatio,rWidth,icon);
        }
        // set icons
        connectStatusL.setIcon(connectedIcon);
    }

    private void setWalletStatus() {
        boolean status = (loginStatus == 1);
        if(status) {
            leftTokenLabel.setText(setLeftCoinFormat(leftToken));
            leftCoinLabel.setText(setLeftCoinFormat(10.0));
        }
        walletLoginPanel.setVisible(status);
        walletLogoutPanel.setVisible(!status);
    }

    private void setRewardStatus() {
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
        setRewardStatus();
        setPurchaseStatus();
        setOrderStatus();
    }

    private void showOrderList() {
        double rWidth = rootPanel.getWidth();
        JLabel labels[] = {order1Label,order2Label,order3Label,order4Label,order5Label,order6Label,order7Label};
        int i=labels.length-1;
        for(OrderSheet order: myOrder) {
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
        setLaunchPageStatus();
        // set recognise ssid label text to space
        ssidLabel.setText("blockchain");
        ssidLabel.setForeground(rootPanel.getBackground());
        adsDetailPane.setVisible(false);
        // get root panel width and height
        rootWidth = rootPanel.getWidth();
        // set wallet page
        setWalletPage();
        // set purchase page
        setPurchasePage();
        purchaseDetailPane.setVisible(false);
        // set order list page
        setOrderListPage();
        orderDetailScrollPane.setVisible(false);
        // set getting reward page status
        setRewardPage();
        // set all page display status
        setPageStatus();
        // set login advertisement
        new Thread(() -> {
            try {
                pageHomeTabbedPane.setVisible(false);
                ImageIcon loginAdsIcon = new ImageIcon(rootPath.concat("/img/preAds4.jpg"));
                int rWidth = rootPanel.getWidth();
                setJLabelIcon(loginAdsLabel, loginAdsIcon, rWidth, 1);
                Thread.sleep(2500);
                loginAdsPanel.setVisible(false);
                pageHomeTabbedPane.setVisible(true);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }).start();
        // deduct advertiser's coin when user login app
        new Thread(() -> {
            try {
                ArrayList<String> cmd = new ArrayList<>();
                cmd.add(rootPath.concat("/wpa_setup/client"));
                cmd.add("-app=commision");
                cmd.add("0x93f97961eb166e2d96972ca192a20fb29138e2dd");
                cmd.add("0x8580c4a0823a54539f9e14a84d7c56a636d20228");
                cmd.add("{\"address\":\"8580c4a0823a54539f9e14a84d7c56a636d20228\"," +
                        "\"crypto\":{\"cipher\":\"aes-128-ctr\"," +
                        "\"ciphertext\":\"b86816e061b9f68a00ced897d1a591e3cf26b749c0931578825509f91c67d237\"," +
                        "\"cipherparams\":{\"iv\":\"aa9dac093614ddc82555b749dcfe56ea\"},\"kdf\":\"scrypt\"," +
                        "\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8," +
                        "\"salt\":\"b0bc8eb46e76d4032d9df4f370ff9a2f9c2571483be81019f7565eccda549252\"}," +
                        "\"mac\":\"34241a59f057a9f15a12e714eb13c17e641ec0ba65f2ef2a6dc1a13879589f14\"}," +
                        "\"id\":\"9f0716b7-40f3-4555-af69-b29a5b35898a\",\"version\":3}");
                ProcessBuilder pb = new ProcessBuilder(cmd);
                Process process = pb.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
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
                //===== click return button to get watching ads reward =====//
                Document doc = Jsoup.parse(adsDetailText.getText());
                Elements adsText = doc.getElementsByTag("font");
                String[] desSession = adsText.text().split(" ");
                String price = desSession[1];
                String type = desSession[2];
                // write reward to local file
//                writeRecord("+"+price+": 看广告");
                writeRecord("+"+10+": 看广告",historyFilePath);
//                writeLeftCoin(Arith.add(leftToken,Double.valueOf(price)));
                switch (type) {
                    case "积分": writeLeftToken(Arith.add(leftToken,10));break;
                    case "代币": writeLeftCoin(Arith.add(leftCoin,10));break;
                    default: System.out.println("[ERROR] Unknown reward type!");
                }
                adsDetailPane.setVisible(false);
                allAdsPane.setVisible(true);
                String historyStr = readRecord(historyReadlineNum,historyFilePath);
                tokenHistoryLabel.setText(historyStr);
                // sync reward to blockchain
                new Thread(() -> {
                    try {
                        ArrayList<String> syncCmd = new ArrayList<>();
                        syncCmd.add(rootPath.concat("/wpa_setup/client"));
                        syncCmd.add("-app=watchAdv");
                        syncCmd.add("0xf439bf68fc695b4a62f9e3322c75229ba5a0ffbb");
                        syncCmd.add("{\"address\":\"01c96e4d9be1f4aef473dc5dcf13d8bd1d4133cd\"," +
                                "\"crypto\":{\"cipher\":\"aes-128-ctr\"," +
                                "\"ciphertext\":\"a18f5974b74abe2712ca489432723049748439d888ab92ede2f9a94c613fad48\"," +
                                "\"cipherparams\":{\"iv\":\"1fd8f3ec4e2496e23f4963eae54ac1a5\"},\"kdf\":\"scrypt\"," +
                                "\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8," +
                                "\"salt\":\"6351e5dc8d2273d1f038bf0d770773cb82fc30d6bd3a623287584e24e44d086c\"}," +
                                "\"mac\":\"edb0e6a6075f7e1c1ef3347745aff2b928a49d9300d4cd627f3d0403e83bf086\"}," +
                                "\"id\":\"1812d540-a745-4174-bb67-bc69a33fb15c\",\"version\":3}");
                        ProcessBuilder syncRewardPB = new ProcessBuilder(syncCmd);
                        Process syncRewardPS = syncRewardPB.start();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(syncRewardPS.getInputStream()));
                        String line;
                        System.out.println("========== get reward by watching ads ==========");
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }).start();
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
//                double cost = Arith.mul(purchasePrice,purchaseNum);
                double cost = 15;
                if (leftToken >= cost) {
                    // deduct left coin
                    String purchaseName = purchaseDesLabel.getText();
                    writeRecord("-"+cost+"="+purchasePrice+"x"+purchaseNum+": 购买"+purchaseName,historyFilePath);
                    tokenHistoryLabel.setText(readRecord(historyReadlineNum,historyFilePath));
                    System.out.println("[INFO] purchase successfully!");
                    writeLeftToken(Arith.sub(leftToken,cost));
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
                    // send purchase transaction to blockchain
                    new Thread(() -> {
                        try {
                            ArrayList<String> cmd = new ArrayList<>();
                            cmd.add(rootPath.concat("/wpa_setup/client"));
                            cmd.add("-app=buy");
                            cmd.add("{\"address\":\"01c96e4d9be1f4aef473dc5dcf13d8bd1d4133cd\"," +
                                    "\"crypto\":{\"cipher\":\"aes-128-ctr\"," +
                                    "\"ciphertext\":\"a18f5974b74abe2712ca489432723049748439d888ab92ede2f9a94c613fad48\"," +
                                    "\"cipherparams\":{\"iv\":\"1fd8f3ec4e2496e23f4963eae54ac1a5\"},\"kdf\":\"scrypt\"," +
                                    "\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8," +
                                    "\"salt\":\"6351e5dc8d2273d1f038bf0d770773cb82fc30d6bd3a623287584e24e44d086c\"}," +
                                    "\"mac\":\"edb0e6a6075f7e1c1ef3347745aff2b928a49d9300d4cd627f3d0403e83bf086\"}," +
                                    "\"id\":\"1812d540-a745-4174-bb67-bc69a33fb15c\",\"version\":3}");
                            cmd.add("0xf439bf68fc695b4a62f9e3322c75229ba5a00000");
                            ProcessBuilder pb = new ProcessBuilder(cmd);
                            Process process = pb.start();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                System.out.println(line);
                            }
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }).start();
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
        //========== set reward and purchase page ==========//
        // set advertisement events in "make money" page
        ArrayList[] adsArrys = {tokenAdsLabels,coinAdsLabels,giftAdsLabels};
//        for(ArrayList<JLabel> adsArry: adsArrys) {
        for(int i=0;i<adsArrys.length;i++) {
            ArrayList<JLabel> adsArry = adsArrys[i];
            adsArry.forEach(item->changeCursor(item,"advertisement"));
        }
        allAdsLabels.forEach(item->changeCursor(item,"advertisement"));
        // set purchase events in "purchase" page
        ArrayList[] goodsArrys = {tokenGoodsLabels,coinGoodsLabels,giftGoodsLabels};
        for(ArrayList<JLabel> goodsArry: goodsArrys) {
            goodsArry.forEach(item->changeCursor(item,"purchase"));
        }
        allGoodsLabels.forEach(item->changeCursor(item,"purchase"));
        //========== end ==========//
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
        allPurchasePane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int index = allPurchasePane.getSelectedIndex();
                switch (index) {
                    case 0: allGoodsLabels.forEach(allGoodsPanel::add);break;
                    case 1: tokenGoodsLabels.forEach(tokenGoodsPanel::add); break;
                    case 2: coinGoodsLabels.forEach(coinGoodsPanel::add);break;
                    default: System.out.println("[ERROR] Unknown error!");
                }
            }
        });
        allAdsPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int index = allAdsPane.getSelectedIndex();
                switch (index) {
                    case 0: allAdsLabels.forEach(allAdsPanel::add);break;
                    case 1: tokenAdsLabels.forEach(tokenAdsPanel::add); break;
                    case 2: coinAdsLabels.forEach(coinAdsPanel::add);break;
                    default: System.out.println("[ERROR] Unknown error!");
                }
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
            String[] desSession = payBackEle.text().split(" ");
            String payBackNum = desSession[1];
            String payBackType = desSession[2];
            text = "<html>"+name+"<br/><font color='#5C4033'>报酬 "+payBackNum+" "+payBackType+"<font><html/>";
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
        Utils.setIconByWidth(ratio,fWidth,icon);
        jLabel.setIcon(icon);
    }

    private void setJLabelIcon(JLabel jLabel,ImageIcon icon,double fWidth,double ratio){
        Utils.setIconByWidth(ratio,fWidth,icon);
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

    private void writeRecord(String record,String filePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true));
            writer.append(record.concat("\n"));
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String readRecord(int lineNum,String filePath) {
        try {
            int fontSize = 4;
            StringBuilder sb = new StringBuilder();
            String line;
            sb.append("<html><body><table cellspacing='0' width='315'>");
            ReversedLinesFileReader reader = new ReversedLinesFileReader(new File(filePath),100,"UTF-8");
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

    public void writeLeftToken(Double leftToken) {
        try {
            this.leftToken = leftToken;
            leftTokenLabel.setText(setLeftCoinFormat(leftToken));
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(new File(rootPath.concat("/wpa_setup/testLeftToken"))));
            writer.write(String.valueOf(leftToken));
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeLeftCoin(Double leftCoin) {
        try {
            this.leftCoin = leftCoin;
            leftCoinLabel.setText(setLeftCoinFormat(leftCoin));
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

    public void setLeftCoin(double leftToken) {
        this.leftToken = leftToken;
    }

    public void setLeftToken(double leftToken) {
        this.leftToken = leftToken;
    }

    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }

    private String setLeftCoinFormat(double leftToken) {
        return String.format("%-10.2f",leftToken);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
