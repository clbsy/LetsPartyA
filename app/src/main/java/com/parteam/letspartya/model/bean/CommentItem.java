package com.parteam.letspartya.model.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by xulin on 16-2-5.
 */
public class CommentItem implements Serializable {
    private long partnerId;
    private String name;
    private String nickName;
    private String content;

    public long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(long partnerId) {
        this.partnerId = partnerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDisplayName() {
        if (!TextUtils.isEmpty(name)) {
            return name;
        } else if (!TextUtils.isEmpty(nickName)) {
            return nickName;
        } else {
            return String.valueOf(partnerId);
        }
    }
}
