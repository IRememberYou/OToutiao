package com.example.pinan.otoutiao.function.newstab.bean;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author pinan
 * @date 2017/11/29
 */

public class MultiMediaArticleBean {
    public long media_id;
    //    public long has_more;
    public NextBean next;
    public int page_type;
    public String message;
    public List<DataBean> data;
    
    public static class NextBean {
        public String max_behot_time;
    }
    
    public static class DataBean {
        public int detail_mode;
        public String play_effective_count;
        public int impression_count;
        public int item_status;
        public String datetime;
        public String str_item_id;
        public int group_status;
        public String keywords;
        public long creator_uid;
        public int original_media_id;
        public String city;
        public int bury_count;
        public String title;
        public int web_article_type;
        public String source;
        public int comment_count;
        public int natant_level;
        public boolean own_group;
        public int share_count;
        public int internal_visit_count;
        public int list_play_effective_count;
        public long media_id;
        public String go_detail_count;
        public int group_flags;
        public int total_read_count;
        public int detail_play_effective_count;
        public int visibility;
        public int ad_type;
        public int was_recommended;
        public String seo_url;
        public int level;
        public int display_status;
        public int repin_count;
        public int digg_count;
        public int is_key_item;
        public boolean ban_action;
        public int review_comment_mode;
        public String comments_count;
        public boolean has_inner_video;
        public boolean has_image;
        public long group_id;
        public String middle_image;
        public int play_effective_count_num;
        public String article_live_type;
        public int has_m3u8_video;
        public int ban_comment;
        public long pgc_id;
        @SerializedName("abstract")
        public String abstractX;
        public boolean middle_mode;
        public boolean is_original;
        public boolean ban_bury;
        public int external_visit_count;
        public int article_type;
        public boolean more_mode;
        public String tag;
        public int behot_time;
        public OptionalDataBean optional_data;
        public String app_url;
        public String book_info;
        public int article_sub_type;
        public String internal_visit_count_format;
        public boolean has_video;
        public int has_mp4_video;
        public int pgc_ad;
        public int gallery_pic_count;
        public String article_url;
        public int create_time;
        public int group_source;
        public String display_mode;
        public int composition;
        public String str_group_id;
        public int publish_time;
        public int wap_open;
        public long tag_id;
        public String source_url;
        public int pgc_article_type;
        public int display_type;
        public long item_id;
        public boolean good_voice;
        public String detail_source;
        public VerifyDetailBean verify_detail;
        public int max_comments;
        public String language;
        public String display_url;
        public String url;
        public int region;
        public int web_display_type;
        public String content_cards;
        public boolean has_gallery;
        public int modify_time;
        public int content_cntw;
        public boolean review_comment;
        public String external_visit_count_format;
        public List<ImageListBean> image_list;
        public List<VideoInfosBean> video_infos;
        public List<String> label;
        public List<String> categories;
        public List<ThumbImageBean> thumb_image;
        public List<ImageDetailBean> image_detail;
        public List<CoverImageInfosBean> cover_image_infos;
        public List<?> slave_infos;
        public List<?> gallery;
        public List<ImageInfosBean> image_infos;
        public String video_duration_str;
        
        public static class VideoInfosBean {
            public int thumb_height;
            public String sp;
            public String vid;
            public int thumb_width;
            public int video_partner;
            public int duration;
            public String thumb_url;
            public VideoSizeBean video_size;
            public String vu;
            
            
            public static class VideoSizeBean {
                
                public HighBean high;
                public NormalBean normal;
                
                public static class HighBean {
                    public int h;
                    public int subjective_score;
                    public int w;
                    public int file_size;
                    
                }
                
                public static class NormalBean {
                    public int h;
                    public int subjective_score;
                    public int w;
                    public int file_size;
                }
            }
        }
        
        public static class OptionalDataBean {
            public String originality;
            public String pgc_source;
        }
        
        public static class VerifyDetailBean {
            public String pass_time;
            public AutoBean auto;
            public EditorBean editor;
            
            public static class AutoBean {
                public int status;
                public int fail_reason;
            }
            
            public static class EditorBean {
                public int status;
            }
        }
        
        public static class ImageListBean {
            public String url;
            public String pc_url;
        }
        
        public static class ThumbImageBean {
            public String url;
            public int width;
            public String uri;
            public int height;
            public List<UrlListBean> url_list;
            
            public static class UrlListBean {
                public String url;
            }
        }
        
        public static class ImageDetailBean {
            public String url;
            public int width;
            public String uri;
            public int height;
            public List<UrlListBeanX> url_list;
            
            public static class UrlListBeanX {
                public String url;
            }
        }
        
        public static class CoverImageInfosBean {
            public String mimetype;
            public int image_type;
            public int height;
            public int width;
            public String web_uri;
            public String desc;
        }
        
        public static class ImageInfosBean {
            public String mimetype;
            public int image_type;
            public String uri;
            public int height;
            public int width;
            public String web_uri;
            public String desc;
        }
    }
}
