package com.example.pinan.otoutiao.function.newstab.bean;

import java.io.Serializable;

/**
 * @author pinan
 * @date 2017/11/16
 */

public class MultiNewsArticleDataBean implements Serializable {
    
    public LogPbBean log_pb;
    public int read_count;
    public String media_name;
    public int ban_comment;
    @com.google.gson.annotations.SerializedName("abstract")
    public String abstractX;
    public UgcRecommendBean ugc_recommend;
    public int article_type;
    public String tag;
    public ForwardInfoBean forward_info;
    public int has_m3u8_video;
    public String rid;
    public boolean show_portrait_article;
    public int user_verified;
    public int aggr_type;
    public int cell_type;
    public int article_sub_type;
    public int group_flags;
    public int bury_count;
    public String title;
    public int ignore_web_transform;
    public int source_icon_style;
    public int tip;
    public int hot;
    public String share_url;
    public int has_mp4_video;
    public String source;
    public int comment_count;
    public String article_url;
    public int share_count;
    public int publish_time;
    public int gallary_flag;
    public int gallary_image_count;
    public int cell_layout_style;
    public long tag_id;
    public int gallary_style;
    public int video_style;
    public String verified_content;
    public String display_url;
    public MediaInfoBean media_info;
    public long item_id;
    public boolean is_subject;
    public boolean show_portrait;
    public int repin_count;
    public int cell_flag;
    public UserInfoBean user_info;
    public String source_open_url;
    public int level;
    public int digg_count;
    public String behot_time;
    public long cursor;
    public String url;
    public int preload_web;
    public int user_repin;
    public boolean has_image;
    public int item_version;
    public boolean has_video;
    public long group_id;
    public MiddleImageBean middle_image;
    public java.util.List<ImageListBean> image_list;
    public java.util.List<FilterWordsBean> filter_words;
    public java.util.List<ActionListBean> action_list;
    public java.util.List<?> large_image_list;
    
 
    
    public static class ActionListBean {
        public int action;
        public ExtraBean extra;
        public String desc;
    }
    
    public static class ExtraBean {
    
    }
    
    public static class LogPbBean {
        public String impr_id;
    }
    
    public static class UgcRecommendBean {
        
        public String reason;
        public String activity;
    }
    
    public static class ForwardInfoBean {
        public int forward_count;
    }
    
    public static class MediaInfoBean {
        public long user_id;
        public String verified_content;
        public String avatar_url;
        public long media_id;
        public String name;
        public int recommend_type;
        public boolean follow;
        public String recommend_reason;
        public boolean is_star_user;
        public boolean user_verified;
    }
    
    public static class UserInfoBean {
        
        public String verified_content;
        public String avatar_url;
        public long user_id;
        public String name;
        public int follower_count;
        public boolean follow;
        public String user_auth_info;
        public boolean user_verified;
        public String description;
    }
    
    public static class MiddleImageBean {
    }
    
    public static class ImageListBean {
        
        public String url;
        public int width;
        public String uri;
        public int height;
        public java.util.List<UrlListBean> url_list;
        
        public static class UrlListBean {
            public String url;
        }
    }
    
    public static class FilterWordsBean {
        public String id;
        public String name;
        public boolean is_selected;
    }
}
