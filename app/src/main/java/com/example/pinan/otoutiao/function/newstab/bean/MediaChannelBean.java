package com.example.pinan.otoutiao.function.newstab.bean;

/**
 * @author pinan
 * @date 2017/11/30
 */

public class MediaChannelBean {
    public String id;
    public String name;
    public String avatar;
    public String type;
    public String followcount;
    public String desctext;
    public String url;
    
    public MediaChannelBean(String id, String name, String avatar, String type, String followcount, String desctext, String url) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.type = type;
        this.followcount = followcount;
        this.desctext = desctext;
        this.url = url;
    }
}
