package com.example.pinan.otoutiao.function.newstab.bean;

import java.util.List;

/**
 * @author pinan
 * @date 2017/11/16
 */

public class MultiNewsArticleBean {
    
    public String message;
    public int total_number;
    public boolean has_more;
    public int login_status;
    public int show_et_status;
    public String post_content_hint;
    public boolean has_more_to_refresh;
    public int action_to_last_stick;
    public int feed_flag;
    public TipsBean tips;
    public List<DataBean> data;
    
    public static class TipsBean {
        public String type;
        public int display_duration;
        public String display_info;
        public String display_template;
        public String open_url;
        public String web_url;
        public String download_url;
        public String app_name;
        public String package_name;
    }
    
    public static class DataBean {
        public String content;
        public String code;
    }
}
