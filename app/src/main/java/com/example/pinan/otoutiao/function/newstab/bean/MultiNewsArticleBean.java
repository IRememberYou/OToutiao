package com.example.pinan.otoutiao.function.newstab.bean;

import java.util.List;

/**
 * @author pinan
 * @date 2017/11/16
 */

public class MultiNewsArticleBean {
    
    public int login_status;
    public int total_number;
    public boolean has_more;
    public String post_content_hint;
    public int show_et_status;
    public int feed_flag;
    public String message;
    public boolean has_more_to_refresh;
    public TipsBean tips;
    public List<DataBean> data;
    
    public static class TipsBean {
        public String display_info;
        public String open_url;
        public String web_url;
        public String app_name;
        public String package_name;
        public String display_template;
        public String type;
        public int display_duration;
        public String download_url;
    }
    
    public static class DataBean {
        public String content;
        public String code;
    }
}
