package com.example.pinan.otoutiao.function.newstab.bean;

/**
 * @author pinan
 * @date 2017/11/27
 */

public class NewsContentBean {
    public CkBean _ck;
    public DataBean data;
    public boolean success;
    
    public static class CkBean {
    }
    
    public static class DataBean {
        public String detail_source;
        public MediaUserBean media_user;
        public int publish_time;
        public String title;
        public String url;
        public boolean is_original;
        public boolean is_pgc_article;
        public String content;
        public String source;
        public int comment_count;
        public long creator_uid;
        
        public static class MediaUserBean {
            public boolean no_display_pgc_icon;
            public String avatar_url;
            public String id;
            public String screen_name;
        }
    }
}
