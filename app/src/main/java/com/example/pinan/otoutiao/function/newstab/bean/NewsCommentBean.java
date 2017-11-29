package com.example.pinan.otoutiao.function.newstab.bean;

import java.util.List;


/**
 * @author pinan
 */
public class NewsCommentBean {
    public String message;
    public int total_number;
    public boolean has_more;
    public int fold_comment_count;
    public int detail_no_comment;
    public boolean ban_comment;
    public boolean ban_face;
    public int go_topic_detail;
    public int show_add_forum;
    public int stick_total_number;
    public boolean stick_has_more;
    public TabInfoBean tab_info;
    public boolean stable;
    public List<DataBean> data;
    public List<?> stick_comments;
    
    public static class TabInfoBean {
        public int current_tab_index;
        public List<String> tabs;
    }
    
    public static class DataBean {
        public CommentBean comment;
        public int cell_type;
        
        public static class CommentBean {
            public long id;
            public String text;
            public String content_rich_span;
            public int reply_count;
            public int digg_count;
            public int bury_count;
            public int create_time;
            public double score;
            public long user_id;
            public String user_name;
            public String remark_name;
            public String user_profile_image_url;
            public boolean user_verified;
            public int is_following;
            public int is_followed;
            public int is_blocking;
            public int is_blocked;
            public int is_pgc_author;
            public String verified_reason;
            public int user_bury;
            public int user_digg;
            public int user_relation;
            public String user_auth_info;
            public MediaInfoBean media_info;
            public String platform;
            public List<?> reply_list;
            public List<?> author_badge;
            
            public static class MediaInfoBean {
                public String name;
                public String avatar_url;
            }
        }
    }
}
