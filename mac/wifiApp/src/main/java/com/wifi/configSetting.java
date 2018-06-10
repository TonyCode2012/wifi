package com.wifi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class configSetting {

    private static String rootPath = System.getProperty("user.dir") + "/config/spec.json";

    public static String getIdentityPath() {
        return getProperty("identityFPath");
    }

    public static String getProfileFPath() {
        return getProperty("profileFPath");
    }

    public static String getProfileWifiFPath() {
        return getProperty("profileWifiFPath");
    }

    public static String getPubKeyFPath() {
        return getProperty("pubKeyFPath");
    }

    public static String getPriKeyFPath() {
        return getProperty("priKeyFPath");
    }

    public static String getWpaConfFPath() {
        return getProperty("wpaConfFPath");
    }

    public static String getRegServerUrl() {
        return getProperty("regServerUrl");
    }

    public static String getWpaCmdPath() {
        return getProperty("wpaCmdPath");
    }

    public static String getFontSize() {
        return getProperty("fontSize");
    }

    public static String getWifiPubKeyPath() {
        return getProperty("wifiPubKeyPath");
    }

    public static String getWifiPriKeyPath() {
        return getProperty("wifiPriKeyPath");
    }

    public static String getWpaPubKeyPath() {
        return getProperty("wpaPubKeyPath");
    }

    public static String getWpaPriKeyPath() {
        return getProperty("wpaPriKeyPath");
    }

    public static String getProperty(String key) {
        String configure = "";
        try {
            ObjectMapper objMapper = new ObjectMapper();
            JsonNode jsonConf = objMapper.readTree(new File(rootPath));
            JsonNode identityNode = jsonConf.get(key);
            if(identityNode == null) {
                throw new FileNotFoundException("Can not find " + key + " property!");
            }
            configure = identityNode.asText();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return configure;
    }
}
