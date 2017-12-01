package com.example.pinan.otoutiao.function.newstab.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author pinan
 * @date 2017/11/29
 */

public class MediaProfileBean implements Serializable {
    public String message;
    public DataBean data;
    
    public static class DataBean implements Serializable {
        public int status;
        public boolean is_followed;
        public int current_user_id;
        public String media_id;
        public String description;
        public String apply_auth_url;
        public int article_limit_enable;
        public String verified_agency;
        public String bg_img_url;
        public String verified_content;
        public String screen_name;
        public boolean is_following;
        public int pgc_like_count;
        public int visit_count_recent;
        public StarChartBean star_chart;
        public boolean user_verified;
        public String user_auth_info;
        public int is_blocking;
        public int is_blocked;
        public long user_id;
        public String name;
        public String big_avatar_url;
        public Object area;
        public int private_letter_permission;
        public int gender;
        public Object industry;
        public String apply_auth_entry_title;
        public String share_url;
        public int show_private_letter;
        public long ugc_publish_media_id;
        public String avatar_url;
        public int followers_count;
        public String media_type;
        public int followings_count;
        public List<BottomTabBean> bottom_tab;
        public List<TopTabBean> top_tab;
        public List<?> medals;
        
        public static class StarChartBean implements Serializable {
        }
        
        public static class BottomTabBean implements Serializable {
            public String schema_href;
            public String type;
            public String value;
            public String name;
            public List<?> children;
        }
        
        public static class TopTabBean implements Serializable {
            public String url;
            public boolean is_default;
            public String show_name;
            public String type;
        }
    }
}
