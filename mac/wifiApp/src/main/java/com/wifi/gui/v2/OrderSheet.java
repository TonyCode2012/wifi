package com.wifi.gui.v2;

import javax.swing.*;

public class OrderSheet{
    private ImageIcon icon;
    private String goodsName;
    private double goodsPrice;
    private int goodsNum;
    private String dateStr;
    private String orderSeq;

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public String getDateStr() {
        return dateStr;
    }

    public String getOrderSeq() {
        return orderSeq;
    }
}