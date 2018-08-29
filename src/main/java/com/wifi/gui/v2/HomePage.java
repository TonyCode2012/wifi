package com.wifi.gui.v2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javafx.fxml.builder.URLBuilder;
import com.sun.org.apache.bcel.internal.generic.LADD;
import com.wifi.configSetting;
import com.wifi.gui.v2.utils.Arith;
import com.wifi.gui.v2.utils.Utils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.Inflater;
import org.apache.http.client.utils.URIBuilder;

import static java.lang.Thread.sleep;

public class HomePage {
    private JPanel rootPanel;
    private JTabbedPane pageHomeTabbedPane;
    private JButton registerBtn;
    private JButton launchBtn;
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
    private JLabel purchaseIconLabel;
    private JLabel purchaseDesLabel;
    private JButton purchaseBtn;
    private JLabel purchasePriceLabel;
    private JScrollPane purchaseDetailPane;
    private JLabel addNumLabel;
    private JLabel minusNumLabel;
    private JTextField purchaseNumField;
    private JScrollPane orderDetailPane;
    private JButton purchaseDetailRetBtn;
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
    private JPanel allAdvPanel;
    private JPanel tokenAdvPanel;
    private JPanel coinAdvPanel;
    private JPanel allGoodsPanel;
    private JPanel tokenGoodsPanel;
    private JPanel coinGoodsPanel;
    private JLabel tokenSwitcherLabel;
    private JLabel coinSwitcherLabel;
    private JLabel coinHistoryLabel;
    private JPanel recordSwitcherPanel;
    private JPanel testLayoutPanel;
    private JPanel goodsContainerPanel;
    private JLabel allGoodsTabLabel;
    private JLabel tokenGoodsTabLabel;
    private JLabel coinGoodsTabLabel;
    private JScrollPane allGoodsScrollPane;
    private JScrollPane tokenGoodsScrollPane;
    private JScrollPane coinGoodsScrollPane;
    private JLabel purchaseDetailRetLabel;
    private JPanel adsContainerPanel;
    private JLabel allAdsTabLabel;
    private JLabel tokenAdsTabLabel;
    private JLabel coinAdsTabLabel;
    private JScrollPane allAdsScrollPane;
    private JScrollPane tokenAdsScrollPane;
    private JScrollPane coinAdsScrollPane;
    private JPanel adsContainer;
    private JTabbedPane purchaseTabPane;
    private JLabel leftTokenIconLabel;
    private JLabel leftCoinIconLabel;
    private JLabel tokenGreenLabel;
    private JLabel coinGreenLabel;

    private JFrame fJFrame;
    private Register nextRegister;
    private Launch nextLaunch;
    private String rootPath = System.getProperty("user.dir");
    private int retStatus = -1;
    private double leftToken = 0.0;
    private double leftCoin = 10.0;
    private HashMap<String,Double> currency = new HashMap<>();
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
    private String tokenHistoryFP = rootPath.concat("/config/tokenRecords");
    private String coinHistoryFP = rootPath.concat("/config/coinRecords");

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
    private String purchaseExpenseType;
    private ArrayList<JLabel> allGoodsLabels = new ArrayList<>();
    private ArrayList<JLabel> tokenGoodsLabels = new ArrayList<>();
    private ArrayList<JLabel> coinGoodsLabels = new ArrayList<>();
    private ArrayList<JLabel> giftGoodsLabels = new ArrayList<>();
    private JPanel goodsTmpPanel;

    // order page parameters
    private int orderSequenceNum = 0;
    private ArrayList<OrderSheet> myOrder = new ArrayList<>();
    
    // global parameters
    private Dimension boxLayoutFiler = new Dimension(0,5);
    private final Object objLock = new Object();

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
                    wifiIconLabel.setIcon(wifiLaunchIcons[3]);
                    connectStatusL.setIcon(connectedIcon);
                    connectStatusL.setText("已连接   ");
                    // set wallet left value
                    setLoginStatus(1);
                    //========== do deduction ==========//
                    File file = new File(configSetting.getWpaCmdPath().concat("/testLeftToken"));
                    BufferedReader leftCoinReader = new BufferedReader(new FileReader(file));
                    String leftCoinStr = leftCoinReader.readLine();
                    if(! leftCoinStr.contains("registerReward")) {
                        //====== read balance from blockchain =====//
                        if(Utils.getTestChain()) {
                            BufferedReader tokenReader = new BufferedReader(new FileReader(rootPath.concat("/wpa_setup/testLeftToken")));
                            BufferedReader coinReader = new BufferedReader(new FileReader(rootPath.concat("/wpa_setup/testLeftCoin")));
                            leftToken = Double.valueOf(tokenReader.readLine());
                            leftCoin = Double.valueOf(coinReader.readLine());
//                            currency.put("leftToken",Double.valueOf(tokenReader.readLine()));
//                            currency.put("leftCoin",Double.valueOf(coinReader.readLine()));
                            tokenReader.close();
                            coinReader.close();
                        } else {
                            try {
                                ProcessBuilder pb = new ProcessBuilder(
                                        "node",
                                        rootPath.concat("/js_contact_chain/get_value.js")
                                );
                                pb.redirectErrorStream(true);
                                Process process = pb.start();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                                StringBuilder sb = new StringBuilder();
                                String line;
                                System.out.println("========== get reward from blockchain ==========");
                                // tricky getting coin and token
                                while ((line = reader.readLine()) != null) {
                                    sb.append(line);
                                    if (Pattern.matches("Token:[0-9]*",line)) {
                                        leftToken = Double.valueOf(line.split(":")[1]);
//                                        currency.put("leftToken",Double.valueOf(line.split(":")[1]));
                                    }
                                    if (Pattern.matches("Coin:[0-9]*",line)) {
                                        leftCoin = Double.valueOf(line.split(":")[1]);
//                                        currency.put("leftCoin",Double.valueOf(line.split(":")[1]));
                                    }
                                    System.out.println("<reward>" + line + "<reward>");
                                }
                                process.waitFor();
                            } catch (IOException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        leftToken = leftToken - 10;
                        // record item
                        writeRecord("-10: 登陆",tokenHistoryFP);
                        // deduct 10 coin in backend
                        new Thread(() -> {
                            int tryout = 3;
                            while(tryout > 0) {
                                try {
                                    ArrayList<String> cmd = new ArrayList<>();
                                    cmd.add("node");
                                    cmd.add(rootPath.concat("/js_contact_chain/client.js"));
                                    cmd.add("DeductionToken");
                                    cmd.add("0x01c96e4d9be1f4aef473dc5dcf13d8bd1d4133cd");
                                    cmd.add("e16a1130062b37f038b9df02f134d7ddd9009c54c62bd92d4ed42c0dba1189a8");
                                    cmd.add("0xf439bf68fc695b4a62f9e3322c75229ba5a0ff33");
                                    ProcessBuilder pb = new ProcessBuilder(cmd);
                                    Process process = pb.start();
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                                    String line;
                                    // tricky getting data
                                    boolean isSuccess = false;
                                    while ((line = reader.readLine()) != null) {
                                        System.out.println(line);
                                        if (line.contains("status")) {
                                            String[] tmpArry = line.split(":");
                                            String status = tmpArry[1];
                                            status = status.substring(1, status.length() - 1);
                                            isSuccess = Boolean.valueOf(status);
                                        }
                                    }
                                    if(isSuccess) {
                                        break;
                                    }
                                    System.out.println("[ERROR] Transaction failed! Deduct token failed!");
                                    process.waitFor();
                                } catch (IOException|InterruptedException e) {
                                    System.out.println(e.getMessage());
                                }
                                tryout--;
                            }
                        }).start();
                    } else {
                        String[] tmpStr = leftCoinStr.split(":");
                        leftToken = Double.valueOf(tmpStr[1]);
                        leftCoin = 0;
//                        currency.put("leftToken",Double.valueOf(tmpStr[1]));
//                        currency.put("leftCoin",0.0);
                        writeRecord("+"+leftToken+": 注册",tokenHistoryFP);
                    }
                    sleep(200);
                    String historyStr = readRecord(historyReadlineNum,tokenHistoryFP);
                    tokenHistoryLabel.setText(historyStr);
                } else {
                    wifiIconLabel.setIcon(unLaunchIcon);
                    connectStatusL.setIcon(unconnectedIcon);
                    connectStatusL.setText("未连接   ");
                }
                setPageStatus();
                // set login page advertisement
                new Thread(() -> {
                    try {
                        int adsIndex = 0;
                        int adsLen = loginAdsIcons.length;
                        while (loginStatus == 1) {
                            launchAdsLabel.setIcon(loginAdsIcons[adsIndex]);
                            sleep(2500);
                            adsIndex = ++adsIndex % adsLen;
                        }
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }).start();
            } catch (InterruptedException|IOException ex) {
                System.out.println(ex.getMessage());
            }
        }).start();
    }
    
    /*
    * 钱包页面
    * */
    private void setWalletPage() {
        int rWidth = rootPanel.getWidth();
//        Color myBlue = new Color(47,147,250);
        Color pressedColor = new Color(181,180,181);
        Color releasedColor = tokenSwitcherLabel.getBackground();
        tokenSwitcherLabel.setText("积分");
        tokenSwitcherLabel.setOpaque(true);
        tokenSwitcherLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        tokenSwitcherLabel.setBackground(pressedColor);
        coinSwitcherLabel.setText("代币");
        coinSwitcherLabel.setOpaque(true);
        coinSwitcherLabel.setBorder(BorderFactory.createEtchedBorder());
        coinHistoryLabel.setVisible(false);
        coinHistoryLabel.setText(readRecord(historyReadlineNum,coinHistoryFP));
        // add switcher actions
        tokenSwitcherLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                tokenHistoryLabel.setVisible(true);
                coinHistoryLabel.setVisible(false);
                tokenSwitcherLabel.setBorder(BorderFactory.createLoweredBevelBorder());
                tokenSwitcherLabel.setBackground(pressedColor);
                coinSwitcherLabel.setBorder(BorderFactory.createEtchedBorder());
                coinSwitcherLabel.setBackground(releasedColor);
            }
        });
        coinSwitcherLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                coinHistoryLabel.setVisible(true);
                tokenHistoryLabel.setVisible(false);
                coinSwitcherLabel.setBorder(BorderFactory.createLoweredBevelBorder());
                coinSwitcherLabel.setBackground(pressedColor);
                tokenSwitcherLabel.setBorder(BorderFactory.createEtchedBorder());
                tokenSwitcherLabel.setBackground(releasedColor);
            }
        });
        // set token coin image
        leftTokenIconLabel.setText("积分");
        setJLabelIcon(leftTokenIconLabel,rootPath + "/img/token.png",rWidth,0.08);
        leftTokenIconLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        // set real coin image
        leftCoinIconLabel.setText("代币");
        setJLabelIcon(leftCoinIconLabel,rootPath + "/img/coin.png",rWidth,0.08);
        leftCoinIconLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        // set green currency label
        tokenGreenLabel.setText("");
        coinGreenLabel.setText("");
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
                setJLabelIcon(label,imgPath,rootWidth,0.3);
                label.setText("<html><p>"+name+"</p><br/><font color='#5C4033'>报酬 "+payBack+" "+rewardType+"</font></html>");
                label.setVerticalTextPosition(SwingConstants.TOP);
                typeArry.add(label);
                allAdsLabels.add(label);
            }
        }

        // set tab format
        JLabel[] tabLabelArry = {allAdsTabLabel,tokenAdsTabLabel,coinAdsTabLabel};
        JScrollPane[] tabPanelArry = {allAdsScrollPane,tokenAdsScrollPane,coinAdsScrollPane};
        JPanel[] innerPanelArry = {allAdvPanel,tokenAdvPanel,coinAdvPanel};
        ArrayList[] dataLabelArry = {allAdsLabels,tokenAdsLabels,coinAdsLabels};
        setMyTabPanel(tabLabelArry,tabPanelArry,dataLabelArry,innerPanelArry, adsContainer);
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
        purchaseDetailRetLabel.setVisible(false);
        purchaseDetailRetLabel.setBorder(BorderFactory.createRaisedBevelBorder());
        purchaseDetailRetLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
                rootPanel.setCursor(cursor);
                purchaseDetailRetLabel.setOpaque(true);
                purchaseDetailRetLabel.setForeground(Color.blue);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                rootPanel.setCursor(curCursor);
                purchaseDetailRetLabel.setForeground(Color.black);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                purchaseDetailPane.setVisible(false);
                goodsContainerPanel.setVisible(true);
                purchaseDetailRetLabel.setVisible(false);
                orderDetailPane.setVisible(false);
                changePurchaseNum(1);
            }
        });
        // set order detail pane
        orderDetailPane.setVisible(false);

        // set tab format
        JLabel[] tabLabelArry = {allGoodsTabLabel,tokenGoodsTabLabel, coinGoodsTabLabel};
        JScrollPane[] tabPanelArry = {allGoodsScrollPane,tokenGoodsScrollPane,coinGoodsScrollPane};
        ArrayList[] dataLabelArry = {allGoodsLabels,tokenGoodsLabels,coinGoodsLabels};
        JPanel[] innerPanelArry = {allGoodsPanel,tokenGoodsPanel,coinGoodsPanel};
        setMyTabPanel(tabLabelArry,tabPanelArry,dataLabelArry,innerPanelArry, goodsContainerPanel);
    }

    private void setOrderListPage() {
        JLabel labels[] = {order1Label,order2Label,order3Label,order4Label,order5Label,order6Label,order7Label};
        for(JLabel label: labels) {
            label.setVisible(false);
        }
    }

    private void resetLaunchPageStatus() {
        wifiIconLabel.setVisible(true);
        ssidLabel.setForeground(rootPanel.getBackground());
        setRetStatus(-1);
    }

    private void setBreakdownStatus() {
        wifiIconLabel.setVisible(false);
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
        connectStatusL.setVisible(false);
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
            leftCoinLabel.setText(setLeftCoinFormat(leftCoin));
//            leftTokenLabel.setText(setLeftCoinFormat(currency.get("leftToken")));
//            leftCoinLabel.setText(setLeftCoinFormat(currency.get("leftCoin")));
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
        // initial currency value
//        currency.put("leftToken",0.0);
//        currency.put("leftCoin",0.0);
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
        // set getting reward page status
        setRewardPage();
        // set purchase page
        setPurchasePage();
        purchaseDetailPane.setVisible(false);
        // set order list page
        setOrderListPage();
        orderDetailScrollPane.setVisible(false);
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
            int tryout = 3;
            while(tryout > 0) {
                try {
                    ArrayList<String> cmd = new ArrayList<>();
                    cmd.add("node");
                    cmd.add(rootPath.concat("/js_contact_chain/client.js"));
                    cmd.add("CommissionToken");
                    cmd.add("0x01c96e4d9be1f4aef473dc5dcf13d8bd1d4133cd");
                    cmd.add("e16a1130062b37f038b9df02f134d7ddd9009c54c62bd92d4ed42c0dba1189a8");
                    cmd.add("0xf439bf68fc695b4a62f9e3322c75229ba5a0ff33");
                    cmd.add("0xeff1ec3378b7ee6dd1891b52ad46f281c7b9244e");
                    ProcessBuilder pb = new ProcessBuilder(cmd);
                    Process process = pb.start();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    boolean isSuccess = false;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                        if (line.contains("status")) {
                            String[] tmpArry = line.split(":");
                            String status = tmpArry[1];
                            status = status.substring(1, status.length() - 1);
                            isSuccess = Boolean.valueOf(status);
                        }
                    }
                    if (isSuccess) {
                        break;
                    }
                    System.out.println("[ERROR] Transaction failed! Commission token failed!");
                    process.waitFor();
                } catch (IOException|InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
                tryout--;
            }
        }).start();

        // test
        adsContainer.setPreferredSize(new Dimension(purchaseTabPane.getWidth(),purchaseTabPane.getHeight()));

        // get advertisement
        getAdvertisement("");
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
//                writeLeftCoin(Arith.add(leftToken,Double.valueOf(price)));
                String adsPaybackType;
                String testAdsAdress;
                String testApAdress;
                switch (type) {
                    case "积分": {
//                        writeLeftToken(Arith.add(currency.get("leftToken"), 10));
//                        writeLeftToken(Arith.add(leftToken, 10));
//                        writeRecord("+" + 10 + ": 看广告", tokenHistoryFP);
//                        String historyStr = readRecord(historyReadlineNum, tokenHistoryFP);
//                        tokenHistoryLabel.setText(historyStr);
                        adsPaybackType = "WatchAdvToken";
                        testAdsAdress = "0xf439bf68fc695b4a62f9e3322c75229ba5a0ffbb";
                        testApAdress = "0xf439bf68fc695b4a62f9e3322c75229ba5a0ff33";
                        // add token to green area
                        changeGreenLabel("token","add",10,true);
                    }
                    break;
                    case "代币": {
//                        writeLeftCoin(Arith.add(currency.get("leftCoin"), 10));
//                        writeLeftCoin(Arith.add(leftCoin, 10));
//                        writeRecord("+" + 10 + ": 看广告", coinHistoryFP);
//                        String historyStr = readRecord(historyReadlineNum, coinHistoryFP);
//                        coinHistoryLabel.setText(historyStr);
                        adsPaybackType = "WatchAdvCoin";
                        testAdsAdress = "0xf439bf68fc695b4a62f9e3322c75229ba5a0ffcc";
                        testApAdress = "0xf439bf68fc695b4a62f9e3322c75229ba5a0ff44";
                        // add coin to green area
                        changeGreenLabel("coin","add",10,true);
                    }
                    break;
                    default:
                        System.out.println("[ERROR] Unknown reward type!");return;
                }
                adsDetailPane.setVisible(false);
                adsContainer.setVisible(true);
                // sync reward to blockchain
                new Thread(() -> {
                    int tryout = 3;
                    boolean isSuccess = false;
                    while(tryout > 0) {
                        try {
                            ArrayList<String> syncCmd = new ArrayList<>();
                            syncCmd.add("node");
                            syncCmd.add(rootPath.concat("/js_contact_chain/client.js"));
                            syncCmd.add(adsPaybackType);
                            syncCmd.add("0x01c96e4d9be1f4aef473dc5dcf13d8bd1d4133cd");
                            syncCmd.add("e16a1130062b37f038b9df02f134d7ddd9009c54c62bd92d4ed42c0dba1189a8");
                            syncCmd.add(testAdsAdress);
                            syncCmd.add(testApAdress);
                            ProcessBuilder syncRewardPB = new ProcessBuilder(syncCmd);
                            Process syncRewardPS = syncRewardPB.start();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(syncRewardPS.getInputStream()));
                            String line;
                            System.out.println("========== get reward by watching ads ==========");
                            while ((line = reader.readLine()) != null) {
                                System.out.println(line);
                                if (line.contains("status: true")) {
                                    isSuccess = true;
                                }
                            }
                            if (isSuccess) {
                                break;
                            }
                            System.out.println("[ERROR] Transaction failed! Get payback failed!");
                            syncRewardPS.waitFor();
                        } catch (IOException|InterruptedException ex) {
                            System.out.println(ex.getMessage());
                        }
                        tryout--;
                    }
                    switch (type) {
                        case "积分":
                            changeGreenLabel("token","sub",10,isSuccess);
                            break;
                        case "代币":
                            changeGreenLabel("coin","sub",10,isSuccess);
                            break;
                        default:
                            System.out.println("[ERROR] Unknown advertisement type");
                    }
                }).start();
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
//              double cost = Arith.mul(purchasePrice,purchaseNum);
                double cost = 15;
                // deduct left currency
                String purchaseName = purchaseDesLabel.getText();
                String buyGoodsCurrencyType;
                String testThingAddress;
                String rType;
                switch (purchaseExpenseType) {
                    case "积分":{
                        if(leftToken < cost) {
                            System.out.println("[ERROR] Left token is not enough");
                            return;
                        }
                        writeRecord("-"+cost+"="+purchasePrice+"x"+purchaseNum+": 购买"+purchaseName,tokenHistoryFP);
                        tokenHistoryLabel.setText(readRecord(historyReadlineNum,tokenHistoryFP));
                        writeLeftToken(Arith.sub(leftToken,cost));
                        buyGoodsCurrencyType = "BuyThing";
                        testThingAddress = "0xf439bf68fc695b4a62f9e3322c75229ba5a0ff88";
                        rType = "leftToken";
                        tokenGreenLabel.setText("+10");
                        tokenGreenLabel.setOpaque(true);
                        tokenGreenLabel.setForeground(Color.GREEN);
                    }break;
                    case "代币":{
                        if(leftCoin < cost) {
                            System.out.println("[ERROR] Left coin is not enough");
                            return;
                        }
                        writeRecord("-"+cost+"="+purchasePrice+"x"+purchaseNum+": 购买"+purchaseName,coinHistoryFP);
                        coinHistoryLabel.setText(readRecord(historyReadlineNum,coinHistoryFP));
                        writeLeftCoin(Arith.sub(leftCoin,cost));
                        buyGoodsCurrencyType = "BuyThingCoin";
                        testThingAddress = "0xf439bf68fc695b4a62f9e3322c75229ba5a0ff88";
                        rType = "leftCoin";
                        coinGreenLabel.setText("+10");
                        coinGreenLabel.setOpaque(true);
                        coinGreenLabel.setForeground(Color.GREEN);
                    }break;
                    default: System.out.println("[ERROR] Unknown currency type!");return;
                }
                System.out.println("[INFO] purchase successfully!");
                orderSequenceNum++;
                // generate order detail page
                ImageIcon icon = (ImageIcon) purchaseIconLabel.getIcon();
                String orderItemNameStr = purchaseDesLabel.getText();
                double totalCost = Arith.mul(purchasePrice,purchaseNum);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String dateStr = dateFormat.format(new Date());
                String orderSeqNum = String.format("%04d",orderSequenceNum);
                JLabel tmpOrderIcon = new JLabel();
                tmpOrderIcon.setIcon(icon);
                JLabel tmpOrderName = new JLabel("商品名称："+orderItemNameStr);
                JLabel tmpOrderPirce = new JLabel("商品价格："+String.valueOf(purchasePrice));
                JLabel tmpOrderNum = new JLabel("购买数量："+String.valueOf(purchaseNum));
                JLabel tmpOrderTotal = new JLabel("总额："+String.valueOf(totalCost));
                JLabel tmpOrderTimestamp = new JLabel("订单时间："+dateStr);
                JLabel tmpOrderSequence = new JLabel("订单号："+orderSeqNum);
                JPanel tmpOrderDetailPanel = new JPanel();
                tmpOrderDetailPanel.setLayout(new BoxLayout(tmpOrderDetailPanel,BoxLayout.PAGE_AXIS));
                tmpOrderDetailPanel.add(tmpOrderIcon);
                tmpOrderDetailPanel.add(tmpOrderName);
                tmpOrderDetailPanel.add(tmpOrderPirce);
                tmpOrderDetailPanel.add(tmpOrderNum);
                tmpOrderDetailPanel.add(tmpOrderTotal);
                tmpOrderDetailPanel.add(tmpOrderTimestamp);
                tmpOrderDetailPanel.add(tmpOrderSequence);
                orderDetailPane.setVisible(true);
                orderDetailPane.setViewportView(tmpOrderDetailPanel);
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
                    int tryout = 3;
                    while(tryout > 0) {
                        try {
                            ArrayList<String> cmd = new ArrayList<>();
                            cmd.add("node");
                            cmd.add(rootPath.concat("/js_contact_chain/client.js"));
                            cmd.add(buyGoodsCurrencyType);
                            cmd.add("0x01c96e4d9be1f4aef473dc5dcf13d8bd1d4133cd");
                            cmd.add("e16a1130062b37f038b9df02f134d7ddd9009c54c62bd92d4ed42c0dba1189a8");
                            cmd.add(testThingAddress);
                            ProcessBuilder pb = new ProcessBuilder(cmd);
                            Process process = pb.start();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String line;
                            boolean isSuccess = false;
                            while ((line = reader.readLine()) != null) {
                                System.out.println(line);
                                if (line.contains("status")) {
                                    String[] tmpArry = line.split(":");
                                    String status = tmpArry[1];
                                    status = status.substring(1, status.length() - 1);
                                    isSuccess = Boolean.valueOf(status);
                                }
                            }
                            if (isSuccess) {
                                currency.put(rType,currency.get(rType)+10);
                                break;
                            }
                            System.out.println("[ERROR] Transaction failed! Purchase failed!");
                            process.waitFor();
                        } catch (IOException|InterruptedException ex) {
                            System.out.println(ex.getMessage());
                        }
                        tryout--;
                    }
                }).start();
            }
        });
        //========== set reward and purchase page ==========//
        // set advertisement events in "make money" page
        ArrayList[] adsArrys = {tokenAdsLabels,coinAdsLabels,giftAdsLabels};
//        for(int i=0;i<adsArrys.length;i++) {
        for(ArrayList<JLabel> adsArry: adsArrys) {
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
//        allAdsPane.setVisible(false);
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
            adsContainer.setVisible(false);
            fJFrame.pack();
        }
    }

    private void pressPurchase(MouseEvent e) {
        goodsContainerPanel.setVisible(false);
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
            String[] purchasePriceArry = price.split(" ");
            purchasePrice = Double.valueOf(purchasePriceArry[1]);
            purchaseExpenseType = purchasePriceArry[2];
            purchaseDesLabel.setText(des);
            purchasePriceLabel.setText("<html><font color='#8C7853'>"+price+"</font></html>");
            // reset image size
            ImageIcon icon = (ImageIcon) label.getIcon();
            String filePath = icon.getDescription();
            setJLabelIcon(purchaseIconLabel,filePath,rootWidth,0.82);
            purchaseDetailPane.setVisible(true);
            fJFrame.pack();
            purchaseDetailRetLabel.setVisible(true);
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
            String colorStr = "";
            sb.append("<html><body><table cellspacing='0' width='315'>");
            ReversedLinesFileReader reader = new ReversedLinesFileReader(new File(filePath),100,"UTF-8");
            while(lineNum > 0) {
                line = reader.readLine();
                if(line == null) break;
                colorStr = line.contains("失败") ? "#da7262" : "#4A766E";
                sb.append("<tr><td style='border:1px solid black;'><font size='"+fontSize+"' color='"+colorStr+"'>");
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
//            currency.put("leftToken",leftToken);
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
//            currency.put("leftCoin",leftCoin);
            leftCoinLabel.setText(setLeftCoinFormat(leftCoin));
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(new File(rootPath.concat("/wpa_setup/testLeftCoin"))));
            writer.write(String.valueOf(leftCoin));
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setMyTabPanel(JLabel[] labelArry,JScrollPane[] scrollPanelArry,ArrayList[] dataLabelArrys,JPanel[] innerPanelArry,JPanel outerPanel) {
        if(labelArry.length != scrollPanelArry.length) {
            System.out.println("[ERROR] tabPanel's tab label number must equal to panel number!");
            return;
        }
        int labelLength = labelArry.length;
        Color pressedColor = new Color(181,180,181);
        Color releasedColor = labelArry[0].getBackground();
        for(int i=0;i<labelLength;i++) {
            JLabel label = labelArry[i];
            JScrollPane scrollPanel = scrollPanelArry[i];
            ArrayList<JLabel> dataLabelArry = dataLabelArrys[i];
            JPanel innerPanel = innerPanelArry[i];
            // set component properties
            label.setOpaque(true);
            label.setBackground(releasedColor);
            label.setBorder(BorderFactory.createEtchedBorder());
            scrollPanel.setVisible(false);
//            innerPanel.setLayout(new GridLayout(3,1));
//            innerPanel.setLayout(new GridLayout(dataLabelArry.size(),1));
            innerPanel.setLayout(new BoxLayout(innerPanel,BoxLayout.PAGE_AXIS));
            // add component listener
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mouseClicked(e);
                    innerPanel.removeAll();
                    for(int j=0;j<labelArry.length;j++) {
                        JLabel nLabel = labelArry[j];
                        JScrollPane nScrollPanel = scrollPanelArry[j];
                        nScrollPanel.setVisible(false);
                        nLabel.setBorder(BorderFactory.createEtchedBorder());
                        nLabel.setBackground(releasedColor);
                    }
                    scrollPanel.setVisible(true);
                    dataLabelArry.forEach(item ->{
                        innerPanel.add(item);
                        innerPanel.add(Box.createRigidArea(boxLayoutFiler));
                    });
                    label.setBorder(BorderFactory.createRaisedBevelBorder());
                    label.setBackground(pressedColor);
                    outerPanel.updateUI();
                }
            });
        }
        ((ArrayList<JLabel>)dataLabelArrys[0]).forEach(item -> {
            innerPanelArry[0].add(item);
            innerPanelArry[0].add(Box.createRigidArea(boxLayoutFiler));
        });
        labelArry[0].setBackground(pressedColor);
        labelArry[0].setBorder(BorderFactory.createRaisedBevelBorder());
        scrollPanelArry[0].setVisible(true);
    }

    private void getAdvertisement(String timestamp) {
        new Thread(()->{
            String orgTimestamp = timestamp;
            String orgPath = "/advertisement/get_by_ap/fjkdjfa";
            while(true) {
                try {
//                String urlStr = "http://120.27.237.152:5000/advertisement/get_by_ap/stream";
//                String urlStr = "http://120.27.237.152:5000/advertisement/get_by_ap/xxxxxx";
//                String urlStr = "http://120.27.237.152:5000/advertisement/get_by_ap/fjkdjfa";
                    String path = orgPath;
                    if (orgTimestamp != null && !orgTimestamp.equals("")) {
                        path = orgPath.concat("/").concat(orgTimestamp);
                    }
                    URIBuilder urlb = new URIBuilder()
                            .setScheme("http")
                            .setHost("120.27.237.152:5000")
                            .setPath(path);
                    URL url = new URL(urlb.toString());
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    int statusCode = con.getResponseCode();
                    if (statusCode != 200) {
                        System.out.println("[ERROR] Get advertisement failed!");
                        return;
                    }
                    ObjectMapper objMapper = new ObjectMapper();
                    JsonNode jsonNode = objMapper.readTree(con.getInputStream());
                    JSONArray jsonArry = new JSONArray(jsonNode.toString());
                    String lastTimestamp = "";
                    for (int i = 0; i < jsonArry.length(); i++) {
                        JSONObject jsonObj = jsonArry.getJSONObject(i);
                        try {
                            String picStr = jsonObj.getString("picture");
                            String name = jsonObj.getString("itemName");
                            String imgName = jsonObj.getString("imgName");
                            String imgPath = zlibDecompress(picStr.getBytes(), imgName);
                            String rewardType = "积分";
                            Double payBack = jsonObj.getDouble("price");
                            lastTimestamp = jsonObj.getString("timestamp");
                            //set got advertisement
                            JLabel label = new JLabel();
                            setJLabelIcon(label, imgPath, rootWidth, 0.3);
                            label.setText("<html><p>" + name + "</p><br/><font color='#5C4033'>报酬 " + payBack + " " + rewardType + "</font></html>");
                            label.setVerticalTextPosition(SwingConstants.TOP);
                            if(tokenAdsScrollPane.isVisible()) {
                                tokenAdvPanel.add(label);
                                tokenAdvPanel.add(Box.createRigidArea(boxLayoutFiler));
                            }
                            if(allAdsScrollPane.isVisible()) {
                                allAdvPanel.add(label);
                                allAdvPanel.add(Box.createRigidArea(boxLayoutFiler));
                            }
                            tokenAdsLabels.add(label);
                            allAdsLabels.add(label);
//                            adsContainer.updateUI();
                            changeCursor(label, "advertisement");
                        } catch (JSONException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    if(! lastTimestamp.equals("")) {
                        lastTimestamp = lastTimestamp.substring(0, lastTimestamp.length() - 3);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                        DateTime dateTime = new DateTime(dateFormat.parse(lastTimestamp));
                        orgTimestamp = dateFormat.format(dateTime.plusMillis(1).toDate());
                    }
                    sleep(1000);
                } catch (IOException|InterruptedException|ParseException e) {
                    System.out.println(e.getMessage());
                }
            }
        }).start();
    }

    private String zlibDecompress(byte[] data, String imgName) {
//        byte[] output = new byte[0];
//
//        Inflater decompresser = new Inflater();
//        decompresser.reset();
//        decompresser.setInput(data);
//
//        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
//        try {
//            byte[] buf = new byte[1024];
//            while (!decompresser.finished()) {
//                int i = decompresser.inflate(buf);
//                o.write(buf, 0, i);
//            }
//            output = o.toByteArray();
//        } catch (Exception e) {
//            output = data;
//            e.printStackTrace();
//        } finally {
//            try {
//                o.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        decompresser.end();

        try {
            byte[] base64DecodeBytes = Base64.getDecoder().decode(data);
            String filePath = rootPath.concat("/config/"+imgName);
            File file = new File(filePath);
            FileUtils.writeByteArrayToFile(file,base64DecodeBytes);
            return filePath;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void changeGreenLabel(String currencyType, String operator, double value, boolean isSuccess) {
        String curVal;
        JLabel greenLabel;
        double notCommitVal;
        String desp;
        String historyStr;
        synchronized (objLock) {
            switch (currencyType) {
                case "token":
                    curVal = tokenGreenLabel.getText();
                    greenLabel = tokenGreenLabel;
                    break;
                case "coin":
                    curVal = coinGreenLabel.getText();
                    greenLabel = coinGreenLabel;
                    break;
                default:
                    System.out.println("[ERROR] Currency type must be token or coin!");
                    return;
            }
            if (! Pattern.matches("\\+[0-9]+\\.?[0-9]*",curVal)) {
                curVal = "0";
            }
            switch (operator) {
                case "add":
                    notCommitVal = Arith.add(value, Double.valueOf(curVal));
                    break;
                case "sub": {
                    notCommitVal = Arith.sub(Double.valueOf(curVal), value);
                    switch (currencyType) {
                        case "token":
                            if(isSuccess) {
                                leftToken = Arith.add(value, leftToken);
                                writeLeftToken(leftToken);
                                desp = "+" + 10 + ": 看广告";
                            } else {
                                desp = "看广告失败";
                            }
                            writeRecord(desp, tokenHistoryFP);
                            historyStr = readRecord(historyReadlineNum, tokenHistoryFP);
                            tokenHistoryLabel.setText(historyStr);
                            break;
                        case "coin":
                            if(isSuccess) {
                                leftCoin = Arith.add(value, leftCoin);
                                writeLeftCoin(leftCoin);
                                desp = "+" + 10 + ": 看广告";
                            } else {
                                desp = "看广告失败";
                            }
                            writeRecord(desp, coinHistoryFP);
                            historyStr = readRecord(historyReadlineNum, coinHistoryFP);
                            coinHistoryLabel.setText(historyStr);
                            break;
                    }
                }
                break;
                default:
                    System.out.println("[ERROR] Green value's operator must be add or sub!");
                    return;
            }
            if(notCommitVal != 0) {
                greenLabel.setText("+" + notCommitVal);
                greenLabel.setOpaque(true);
                greenLabel.setForeground(new Color(55,175,88));
            } else {
                greenLabel.setText("");
            }
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
