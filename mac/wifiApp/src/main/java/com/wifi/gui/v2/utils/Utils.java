package com.wifi.gui.v2.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Utils {

    private static ObjectMapper objMapper = new ObjectMapper();

    public static void addCmdCode2File(int cmdCode,String filePath) {
        try {
            File file = new File(filePath);
            ObjectNode objNode = (ObjectNode) objMapper.readTree(file);
            objNode.put("command",Integer.toString(cmdCode));
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
            writer.write(objNode.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
