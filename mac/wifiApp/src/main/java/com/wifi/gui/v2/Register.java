package com.wifi.gui.v2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wifi.configSetting;
import org.apache.commons.io.FileUtils;
import sun.misc.BASE64Encoder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.*;

import static java.lang.Thread.sleep;


public class Register {
    private JPanel rootPanel;
    private JTabbedPane regTabbedPane;
    private JButton tencentBtn;
    private JButton alibabaBtn;
    private JButton regBackBtn;
    private JTextField nameText;
    private JTextField phoneText;
    private JButton infoBackBtn;
    private JButton personalBtn;
    private JButton editBtn;
    private JButton submitBtn;
    private JButton telecomBtn;
    private JPanel regPanel;
    private JPanel infoPanel;
    private JPanel testPanel;
    private JLabel infoTitleLabel;
    private JButton appleBtn;
    private JButton testBtn;
    private JTextField testComText;
    private JTextField testNameText;
    private JTextField testPhoneText;
    private JTextField testAccountText;
    private JLabel companyLabel;
    private JLabel nameLabel;
    private JLabel phoneLabel;
    private JLabel accountLabel;
    private JLabel pubKeyLabel;
    private JButton testSubmitBtn;
    private JButton testBackBtn;
    private JTextArea testPubKeyText;
    private JButton delProfileBtn;

    private JFrame fJFrame;
    private HomePage preHomePage;

    private String rootPath = System.getProperty("user.dir");
    private String companyId;
    private ObjectMapper objMapper = new ObjectMapper();
    private boolean breakNetwork = false;
    private String infoPanelTitle;
    private int connStatusCode;


    public void _init() {
        nameText.setEnabled(false);
        phoneText.setEnabled(false);
        regTabbedPane.remove(1);
        regTabbedPane.remove(1);
    }

    public Register() {
        _init();
        telecomBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerInfo("电信联盟");
            }
        });
        tencentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerInfo("腾讯联盟");
            }
        });
        alibabaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerInfo("阿里联盟");
            }
        });
        appleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerInfo("苹果联盟");
            }
        });
        infoBackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regTabbedPane.remove(0);
                regTabbedPane.add(regPanel);
                regTabbedPane.setTitleAt(0,"登录");
            }
        });
        testBackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regTabbedPane.remove(0);
                regTabbedPane.add(infoPanel);
                regTabbedPane.setTitleAt(0,infoPanelTitle);
            }
        });
        regBackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regTabbedPane.setVisible(false);
                preHomePage.getPageHomeTabbedPane().setVisible(true);
                fJFrame.setContentPane(preHomePage.getRootPanel());
            }
        });
        testBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regTabbedPane.remove(0);
                regTabbedPane.add(testPanel);
                regTabbedPane.setTitleAt(0,"测试");
                try {
                    File file = new File(getIdentityPath());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    String line;
                    StringBuilder sb = new StringBuilder();
                    while((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    JsonNode profileData = objMapper.readTree(sb.toString());
                    String company = profileData.get("company").asText();
                    String name = profileData.get("name").asText();
                    String phone = profileData.get("phone").asText();
                    String account = profileData.get("account").asText();
                    String pubKey = profileData.get("pub_key").asText();
                    testComText.setText(company);
                    testNameText.setText(name);
                    testPhoneText.setText(phone);
                    testAccountText.setText(account);
                    testPubKeyText.setText(pubKey);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonText = editBtn.getText();
                if(buttonText.equals("编辑")){
                    nameText.setEnabled(true);
                    phoneText.setEnabled(true);
                    editBtn.setText("取消编辑");
                } else {
                    nameText.setEnabled(false);
                    phoneText.setEnabled(false);
                    editBtn.setText("编辑");
                }

            }
        });
        testSubmitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JsonNode profileData = objMapper.createObjectNode();
                String company = testComText.getText();
                String name = testNameText.getText();
                String phone = testPhoneText.getText();
                String account = testAccountText.getText();
                String pubKey = testPubKeyText.getText();
                ((ObjectNode) profileData).put("company",company);
                ((ObjectNode) profileData).put("name",name);
                ((ObjectNode) profileData).put("phone",phone);
                ((ObjectNode) profileData).put("account",account);
                ((ObjectNode) profileData).put("pub_key",pubKey);
                try {
                    File file = new File(getTestIdentityPath());
                    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file),"utf-8");
                    writer.append(profileData.toString());
                    writer.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                launch(true);
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
                launch(false);
            }
        });
        personalBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File(getIdentityPath());
                if(!file.exists()) {
                    JOptionPane.showMessageDialog(null,"没有信息!");
                    return;
                }
                try {
                    JsonNode jsonData = objMapper.readTree(file);
                    StringBuilder sb = new StringBuilder();
                    sb.append("name: ").append(jsonData.get("name"))
                            .append("\nphone: ").append(jsonData.get("phone"))
                            .append("\ncompany: ").append(jsonData.get("company"))
                            .append("\naccount: ").append(jsonData.get("account"));
                    //.append("\npub_key").append(jsonData.get("pub_key"));
                    JOptionPane.showMessageDialog(null, sb.toString());
                } catch (IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
        });
        delProfileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File profile = new File(getIdentityPath());
                File testProfile = new File(getTestIdentityPath());
                profile.delete();
                testProfile.delete();
                nameText.setText("");
                phoneText.setText("");
            }
        });
    }

    private void setLaunchPage() {
        breakNetwork = false;
        regTabbedPane.setVisible(false);
        preHomePage.getPageHomeTabbedPane().setVisible(true);
        preHomePage.setLaunchImg();
        fJFrame.setContentPane(preHomePage.getRootPanel());
    }

    private void launch(boolean testFlag) {
        if(!genProfileData()) {
            System.out.println("Generate profile data failed! Login failed!");
            return;
        }
        try {
            String wpaCmdPath = getWpaCmdPath();
            // copy profile to wpa configure directory
            String profilePath = rootPath.concat("/") + configSetting.getProfileFPath();
            String profileComPath = getIdentityPath();
            String profileTestPath = getTestIdentityPath();
            File profileUpload = new File(profilePath);
            File profileCompany = new File(profileComPath);
            File profileTest = new File(profileTestPath);
            if(testFlag) {
                FileUtils.copyFile(profileTest,profileUpload);
            } else {
                FileUtils.copyFile(profileCompany, profileUpload);
            }
            // copy public and private key to wpa configure directory
            String wifiPubKeyPath = rootPath.concat("/") + configSetting.getWifiPubKeyPath();
            String wifiPriKeyPath = rootPath.concat("/") + configSetting.getWifiPriKeyPath();
            String wpaPubKeyPath = rootPath.concat("/") + configSetting.getWpaPubKeyPath();
            String wpaPriKeyPath = rootPath.concat("/") + configSetting.getWpaPriKeyPath();
            File wifiPubFile = new File(wifiPubKeyPath);
            File wifiPriFile = new File(wifiPriKeyPath);
            if(!wifiPubFile.exists() || !wifiPriFile.exists()) {
                System.out.println("[ERROR] public key or private key not exists!");
                return;
            }
            FileUtils.copyFile(wifiPubFile,new File(wpaPubKeyPath));
            FileUtils.copyFile(wifiPriFile,new File(wpaPriKeyPath));

            Register thisRegister = this;
            new Thread(() -> {
                setLaunchPage();
                // run wpa_supplicant as a daemon process
                try {
//                    ProcessBuilder pb = new ProcessBuilder(
//                            "wlan.sh",
//                            "connect",
//                            wpaCmdPath + "/config/prikey.pem",
//                            wpaCmdPath + "/config/profile",
//                            wpaCmdPath + "/config/wpa.conf",
//                            wpaCmdPath + "/wpa.log"
//                    );
                    ProcessBuilder pb = new ProcessBuilder(
                            rootPath + "/wpa_setup/testConnect.sh"
                    );
                    pb.redirectErrorStream(true);
//                    pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
//                    pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
//                    pb.redirectError(ProcessBuilder.Redirect.INHERIT);

                    Process process = pb.start();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    StringBuilder sb = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        if(preHomePage.getRetStatus() == 0) {
                            process.destroy();
                        }
                        sb.append(line);
                        System.out.println(line);
                    }
                    process.waitFor();
                    String retStr = sb.toString();
                    if(retStr.contains("main connect return code")) {
                        String retStrArry[] = retStr.split(" ");
                        connStatusCode = Integer.parseInt(retStrArry[retStrArry.length-1]);
                    } else {
                        connStatusCode = 404;
                    }
                    // if return code is 3,pop up a dialog to require pin code.
                    if(connStatusCode == 9) {
                        System.out.println("Register successfully,please input pin code.");
                        // if register successfully show login page
                        VerifyCodeDlg vcDlg = new VerifyCodeDlg();
                        vcDlg.setRegisterPage(thisRegister);
                        vcDlg.setLocation(650,250);
                        vcDlg.pack();
                        vcDlg.setVisible(true);
                        while(connStatusCode == 9) { sleep(100);}
                    }
                    switch (connStatusCode) {
                        case 1:
                            System.out.println("[ERROR] Launch failed! User doesn't exist!");
                            break;
                        case 2:
                            System.out.println("[ERROR] Launch failed! User info doesn't match!");
                            break;
                        case 4:
                            System.out.println("[ERROR] Launch failed! Phone num authentication failed!");
                            break;
                        case 5:
                            System.out.println("[ERROR] Launch failed! User account registration failed!!");
                            break;
                        case 8:
                            System.out.println("[ERROR] Launch failed! phone verify failed!");
                            break;
                        case 9:
                            System.out.println("[INFO] phone sending service successfully!");
                            break;
                        case 10:
                            System.out.println("[ERROR] Launch failed! phone sending service failed!");
                            break;
                        case 11:
                            System.out.println("[ERROR] Launch failed! Phone sending fail to many!");
                            break;
                        case 64:
                            System.out.println("[INFO] Launch successfully!");
                            break;
                        case 65:
                            System.out.println("[ERROR] Launch failed! Associate failed!");
                            break;
                        case 66:
                            System.out.println("[ERROR] Launch failed! dhcp failed!");
                            break;
                        case 67:
                            System.out.println("[ERROR] Launch failed! Internet cannot be accessed!");
                            break;
                        case 68:
                            System.out.println("[ERROR] Launch failed! no wlan interface!");
                            break;
                        case 69:
                            System.out.println("[ERROR] Launch failed! wpa timeout!");
                            break;
                        case 70:
                            System.out.println("[ERROR] Launch failed! no suitable ssid!");
                            break;
                        case 71:
                            System.out.println("[ERROR] Launch failed! Need root privilege!");
                            break;
                        case 72:
                            System.out.println("[ERROR] Launch failed! Parameter error!");
                            break;
                        case 128:
                            System.out.println("[ERROR] Launch failed! Profile is in wrong format!");
                            break;
                        case 129:
                            System.out.println("[ERROR] Launch failed! RSA encryption failed!");
                            break;
                        case 130:
                            System.out.println("[ERROR] Launch failed! RSA verification failed!");
                            break;
                        case 156:
                            System.out.println("[ERROR] Launch failed! No password!");
                            break;
                        case 157:
                            System.out.println("[ERROR] Launch failed! Datagram failed!");
                            break;
                        case 158:
                            System.out.println("[ERROR] Challenge content failed!");
                            break;
                        case 159:
                            System.out.println("[ERROR] Launch failed! Read profile data failed!");
                            break;
                        case 160:
                            System.out.println("[ERROR] Launch failed! Open private key file failed!");
                            break;
                        case 161:
                            System.out.println("[ERROR] Launch failed! Read private key failed!");
                            break;
                        case 162:
                            System.out.println("[ERROR] Launch failed! decryption failed!");
                            break;
                        case 163:
                            System.out.println("[ERROR] Launch failed! Supplicant content error!");
                            break;
                        case 192:
                            System.out.println("[ERROR] Launch failed! HTTP content error!");
                            break;
                        case 193:
                            System.out.println("[ERROR] Launch failed! CURLE_UNSUPPORTED_PROTOCOL, 1: unsupported protocol");
                            break;
                        case 194:
                            System.out.println("[ERROR] Launch failed! CURLE_FAILED_INIT, 2: failed init");
                            break;
                        case 195:
                            System.out.println("[ERROR] Launch failed! CURLE_URL_MALFORMAT, 3: URL using bad/illegal format or missing URL");
                            break;
                        case 196:
                            System.out.println("[ERROR] Launch failed! CURLE_URL_MALFORMAT_USER, 4: unknown error");
                            break;
                        case 197:
                            System.out.println("[ERROR] Launch failed! CURLE_COULDNT_RESOLVE_PROXY, 5: couldn’t resolve proxy name");
                            break;
                        case 198:
                            System.out.println("[ERROR] Launch failed! CURLE_COULDNT_RESOLVE_HOST, 6: couldn’t resolve host name");
                            break;
                        case 199:
                            System.out.println("[ERROR] Launch failed! CURLE_COULDNT_CONNECT, 7: couldn’t connect to server");
                            break;
                        case 201:
                            System.out.println("[ERROR] Launch failed! CURLE_FTP_WEIRD_SERVER_REPLY, 8: FTP: weird server reply");
                            break;
                        case 202:
                            System.out.println("[ERROR] Launch failed! CURLE_FTP_ACCESS_DENIED,9");
                            break;
                        case 203:
                            System.out.println("[ERROR] Launch failed! CURLE_FTP_USER_PASSWORD_INCORRECT, 10: unknown error");
                            break;
                        case 204:
                            System.out.println("[ERROR] Launch failed! CURLE_FTP_WEIRD_PASS_REPLY, 11: FTP: unknown PASS reply");
                            break;
                        case 205:
                            System.out.println("[ERROR] Launch failed! CURLE_FTP_WEIRD_USER_REPLY, 12: FTP: unknown USER reply");
                            break;
                        case 206:
                            System.out.println("[ERROR] Launch failed! CURLE_FTP_WEIRD_PASV_REPLY, 13: FTP: unknown PASV reply");
                            break;
                        case 207:
                            System.out.println("[ERROR] Launch failed! CURLE_FTP_WEIRD_227_FORMAT, 14: FTP: unknown 227 response format");
                            break;
                        case 208:
                            System.out.println("[ERROR] Launch failed! CURLE_FTP_CANT_GET_HOST, 15: FTP: can’t figure out the host in the PASV response");
                            break;
                        case 209:
                            System.out.println("[ERROR] Launch failed! CURLE_FTP_CANT_RECONNECT, 16: FTP: can’t connect to server the response code is unknown");
                            break;
                        case 210:
                            System.out.println("[ERROR] Launch failed! CURLE_FTP_COULDNT_SET_BINARY, 17: FTP: couldn’t set binary mode");
                            break;
                        case 211:
                            System.out.println("[ERROR] Launch failed! CURLE_PARTIAL_FILE, 18: Transferred a partial file");
                            break;
                        case 212:
                            System.out.println("[ERROR] Launch failed! CURLE_FTP_COULDNT_RETR_FILE, 19: FTP: couldn’t retrieve (RETR failed) the specified file");
                            break;
                        case 213:
                            System.out.println("[ERROR] Launch failed! CURLE_FTP_WRITE_ERROR, 20: FTP: the post-transfer acknowledge response was not OK");
                            break;
                        case 214:
                            System.out.println("[ERROR] Launch failed! CURLE_FTP_QUOTE_ERROR, 21: FTP: a quote command returned error");
                            break;
                        case 215:
                            System.out.println("[ERROR] Launch failed! CURLE_HTTP_RETURNED_ERROR, 22: HTTP response code said error");
                            break;
                        case 216:
                            System.out.println("[ERROR] Launch failed! CURLE_WRITE_ERROR, 23: failed writing received data to disk/application");
                            break;
                        case 217:
                            System.out.println("[ERROR] Launch failed! CURLE_MALFORMAT_USER, 24: unknown error");
                            break;
                        case 218:
                            System.out.println("[ERROR] Launch failed! CURLE_UPLOAD_FAILED, 25: upload failed (at start/before it took off)");
                            break;
                        case 219:
                            System.out.println("[ERROR] Launch failed! CURLE_READ_ERROR, 26: failed to open/read local data from file/application");
                            break;
                        case 220:
                            System.out.println("[ERROR] Launch failed! CURLE_OUT_OF_MEMORY, 27: out of memory");
                            break;
                        case 221:
                            System.out.println("[ERROR] Launch failed! CURLE_OPERATION_TIMEOUTED, 28: a timeout was reached");
                            break;
                        case 222:
                            System.out.println("[ERROR] Launch failed! CURLE_FTP_COULDNT_SET_ASCII, 29: FTP could not set ASCII mode (TYPE A)");
                            break;
                        case 223:
                            System.out.println("[ERROR] Launch failed! CURLE_FTP_PORT_FAILED, 30: FTP command PORT failed");
                            break;
                        case 224:
                            System.out.println("[ERROR] Launch failed! CURLE_FTP_COULDNT_USE_REST, 31: FTP command REST failed");
                            break;
                        case 225:
                            System.out.println("[ERROR] Launch failed! CURLE_FTP_COULDNT_GET_SIZE, 32: FTP command SIZE failed");
                            break;
                        case 226:
                            System.out.println("[ERROR] Launch failed! CURLE_HTTP_RANGE_ERROR, 33: a range was requested but the server did not deliver it");
                            break;
                        case 227:
                            System.out.println("[ERROR] Launch failed! CURLE_HTTP_POST_ERROR, 34: internal problem setting up the POST");
                            break;
                        case 228:
                            System.out.println("[ERROR] Launch failed! CURLE_SSL_CONNECT_ERROR, 35: SSL connect error");
                            break;
                        case 229:
                            System.out.println("[ERROR] Launch failed! CURLE_BAD_DOWNLOAD_RESUME, 36: couldn’t resume download");
                            break;
                        case 230:
                            System.out.println("[ERROR] Launch failed! CURLE_FILE_COULDNT_READ_FILE, 37: couldn’t read a file 231 # CURLE_LDAP_CANNOT_BIND, 38: LDAP: cannot bind");
                            break;
                        case 232:
                            System.out.println("[ERROR] Launch failed! CURLE_LDAP_SEARCH_FAILED, 39: LDAP: search failed");
                            break;
                        case 233:
                            System.out.println("[ERROR] Launch failed! CURLE_LIBRARY_NOT_FOUND, 40: a required shared library was not found");
                            break;
                        case 303:
                            System.out.println("[ERROR] Launch failed! please input pin code!");
                            break;
                        case 304:
                            System.out.println("[ERROR] Launch failed! Input pin code time out!");
                            break;
                        default:
                            System.out.println("[ERROR] Launch failed! Unknown return status code("+connStatusCode+")!");
                            break;

                    }
                    preHomePage.setRetStatus(connStatusCode);
                } catch (IOException|InterruptedException e) {
                    preHomePage.setRetStatus(404);
                    System.out.println(e.getMessage());
                }
            }).start();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private boolean genProfileData(){
        try {
            String name = nameText.getText();
            String phone = phoneText.getText();
            String pub_key = RSAPUBPRIKEYGEN();
            String account = genAccount(companyId,phone);
            System.out.println(account);

            objMapper = new ObjectMapper();
            ObjectNode profileData = objMapper.createObjectNode();
            profileData.put("name",name);
            profileData.put("phone",phone);
            profileData.put("company",companyId);
            profileData.put("account",account);
            profileData.put("pub_key",pub_key);
            StringBuffer sb = new StringBuffer();
            File identityFile = new File(getIdentityPath());
            if(!identityFile.exists()){
                if(!identityFile.createNewFile()){
                    System.out.println("[ERROR] create identity file failed!");
                    return false;
                }
            }
            FileOutputStream ifos = new FileOutputStream(identityFile);
            ifos.write(profileData.toString().getBytes());
            ifos.close();
        } catch (IOException exp){
            System.out.println(exp.getMessage());
            return false;
        }
        return true;
    }

    private String RSAPUBPRIKEYGEN(){
        StringBuilder pubKey_str = new StringBuilder();
        StringBuilder priKey_str = new StringBuilder();
        String base64encodedStr = "";
        try {
            File prikeyfile = new File(getPrikeyPath());
            File pubkeyfile = new File(getPubkeyPath());
            boolean isKeyExist = true;
            if(!prikeyfile.exists()){
                if(!prikeyfile.createNewFile()) {
                    System.out.println("[ERROR] create private key file failed!");
                    return "";
                }
                isKeyExist = false;
            }
            if(!pubkeyfile.exists()) {
                if(!pubkeyfile.createNewFile()) {
                    System.out.println("[ERROR] create public key file failed!");
                    return "";
                }
                isKeyExist = false;
            }
            if(isKeyExist && (pubkeyfile.length() != 0 && prikeyfile.length() != 0)) {
                InputStreamReader pubisr = new InputStreamReader(new FileInputStream(pubkeyfile),"utf-8");
                InputStreamReader priisr = new InputStreamReader(new FileInputStream(prikeyfile),"utf-8");
                Long pubFileLen = pubkeyfile.length();
                Long priFIleLen = prikeyfile.length();
                char[] pubKeyBuf = new char[pubFileLen.intValue()];
                char[] priKeyBuf = new char[priFIleLen.intValue()];
                pubisr.read(pubKeyBuf);
                priisr.read(priKeyBuf);
                pubKey_str.append(pubKeyBuf);
                priKey_str.append(priKeyBuf);

                System.out.println("filelenght is:" + pubFileLen + ",readed length:" + pubKeyBuf.length);
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

    private String genAccount(String companyId,String phone) {
        StringBuilder result = new StringBuilder();
        String cNameHexString = SHAENCRYPT(companyId,"SHA-256");
        String phoneHexString = SHAENCRYPT(phone,"MD5");
        result.append("0x")
                .append(cNameHexString.substring(0,8))
                .append(phoneHexString);
        return result.toString();
    }

    private String SHAENCRYPT(String str,String method) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance(method);
            md.update(str.getBytes("utf-8"));
            byte byteBuffer[] = md.digest();
            StringBuilder strHexString = new StringBuilder();
            for (int i = 0; i < byteBuffer.length; i++)
            {
                String hex = Integer.toHexString(0xff & byteBuffer[i]);
                if (hex.length() == 1)
                {
                    strHexString.append('0');
                }
                strHexString.append(hex);
            }
            result = strHexString.toString();
        } catch (NoSuchAlgorithmException|UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private void registerInfo(String companyName) {
        switch (companyName){
            case "电信联盟": companyId="TELECOM";break;
            case "腾讯联盟": companyId="TENCENT";break;
            case "阿里联盟": companyId="ALIBABA";break;
            case "苹果联盟": companyId="APPLE";break;
        }
        try {
            File file = new File(getIdentityPath());
            if (file.exists()) {
                JsonNode jsonNode = objMapper.readTree(file);
                String name = jsonNode.get("name").asText();
                String phone = jsonNode.get("phone").asText();
                nameText.setText(name);
                phoneText.setText(phone);
            } else {
                nameText.setText("");
                phoneText.setText("");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        infoPanelTitle = companyName + "信息登录";
        regTabbedPane.remove(0);
        regTabbedPane.add(infoPanel);
        regTabbedPane.setTitleAt(0,infoPanelTitle);
    }

    private String getIdentityPath() {
        return rootPath.concat("/") + configSetting.getIdentityPath().concat("_").concat(companyId);
    }

    public String getTestIdentityPath() {
        return rootPath.concat("/") + configSetting.getIdentityPath().concat("_").concat(companyId).concat("_test");
    }

    private String getPrikeyPath() {
        return rootPath.concat("/") + configSetting.getPriKeyFPath();
    }

    private String getPubkeyPath() {
        return rootPath.concat("/") + configSetting.getPubKeyFPath();
    }

    private String getWpaCmdPath() {
        return rootPath.concat("/") + configSetting.getWpaCmdPath();
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JTabbedPane getRegTabbedPane() {
        return regTabbedPane;
    }

    public void setfJFrame(JFrame fJFrame) {
        this.fJFrame = fJFrame;
    }

    public void setPreHomePage(HomePage preHomePage) {
        this.preHomePage = preHomePage;
    }

    public void setBreakNetwork(boolean breakNetwork) {
        this.breakNetwork = breakNetwork;
        regTabbedPane.remove(0);
        regTabbedPane.add(regPanel);
        regTabbedPane.setTitleAt(0,"登录");
    }

    public void setConnStatusCode(int connStatusCode) {
        this.connStatusCode = connStatusCode;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
