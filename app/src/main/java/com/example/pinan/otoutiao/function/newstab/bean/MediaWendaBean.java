package com.example.pinan.otoutiao.function.newstab.bean;

import java.util.List;

/**
 * @author pinan
 * @date 2017/12/1
 */

public class MediaWendaBean {
    
    public String cursor;
    public int err_no;
    public String err_tips;
    public ApiParamBean api_param;
    //public boolean has_more;
    public LoginUserBean login_user;
    public int total;
    public UserDataBean user_data;
    public boolean can_ask;
    public List<AnswerQuestionBean> answer_question;
    
    public static class ApiParamBean {
        public String origin_from;
        public String enter_from;
    }
    
    public static class LoginUserBean {
    }
    
    public static class UserDataBean {
        public String all_brow_cnt_str;
        public int is_verify;
        public String curretn_month_digg_cnt_str;
        public String all_digg_cnt_str;
        public String current_month_brow_cnt;
        public String uname;
        public String all_brow_cnt;
        public int is_valid;
        public String current_month_brow_cnt_str;
        public String user_intro;
        public String current_month_digg_cnt;
        public String all_digg_cnt;
        public String user_profile_image_url;
        public String ming_ren_tang;
        public String schema;
        public List<?> youzhi_info;
        public List<?> laomo_info;
    }
    
    public static class AnswerQuestionBean {
        public AnswerBean answer;
        public QuestionBean question;
        
        public static class AnswerBean {
            public String show_time;
            public ContentAbstractBean content_abstract;
            public UserBean user;
            public String ans_url;
            public String ansid;
            public boolean is_show_bury;
            public String wap_url;
            public boolean is_buryed;
            public int bury_count;
            public String title;
            public int is_delete;
            public int digg_count;
            public String content;
            public int brow_count;
            public boolean is_digg;
            public String schema;
            
            public static class ContentAbstractBean {
                
                public String text;
                public List<ThumbImageListBean> thumb_image_list;
                public List<LargeImageListBean> large_image_list;
                
                public static class ThumbImageListBean {
                    public String url;
                    public String uri;
                    public int height;
                    public int width;
                    public int type;
                    public List<UrlListBean> url_list;
                    
                    public static class UrlListBean {
                        public String url;
                    }
                }
                
                public static class LargeImageListBean {
                    public String url;
                    public String uri;
                    public int height;
                    public int width;
                    public int type;
                    public List<UrlListBeanX> url_list;
                    
                    public static class UrlListBeanX {
                        public String url;
                    }
                }
            }
            
            public static class UserBean {
                public int is_verify;
                public String uname;
                public int create_time;
                public String user_auth_info;
                public String user_id;
                public String avatar_url;
                public int profit_amount;
                public String user_intro;
                public boolean profit_user;
                public String schema;
                public List<?> medals;
            }
        }
        
        public static class QuestionBean {
            public ContentBean content;
            public String tag_name;
            public int create_time;
            public int normal_ans_count;
            public UserBeanX user;
            public String title;
            public String qid;
            public int nice_ans_count;
            public int tag_id;
            public FoldReasonBean fold_reason;
            
            public static class ContentBean {
                public String text;
                public List<?> pic_uri_list;
                public List<?> thumb_image_list;
                public List<?> large_image_list;
            }
            
            public static class UserBeanX {
                public int is_verify;
                public String uname;
                public int create_time;
                public String user_auth_info;
                public String user_id;
                public String avatar_url;
                public int profit_amount;
                public String user_intro;
                public boolean profit_user;
                public String schema;
                public List<?> medals;
            }
            
            public static class FoldReasonBean {
                public String open_url;
                public String title;
            }
        }
    }
}
