package com.bzf.jianxin.chat.widget;

import android.graphics.Bitmap;

public class ChatItemListViewBean {

    /**
     * 发送的消息类型
     */
    public static final int SEND_TYPE = 0;

    /**
     * 接收的消息类型
     */
    public static final int RECEIVER_TYPE = 1;

    private int type;
    private String text;
    private Bitmap icon;

    public ChatItemListViewBean() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }
}
