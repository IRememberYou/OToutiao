package com.example.pinan.otoutiao.root.bean;

/**
 * @author pinan
 * @date 2017/12/5
 */

public class SearchHistoryBean {
    public String id;
    public String keyword;
    public String time;
    
    public SearchHistoryBean(String id, String keyword, String time) {
        this.id = id;
        this.keyword = keyword;
        this.time = time;
    }
}
