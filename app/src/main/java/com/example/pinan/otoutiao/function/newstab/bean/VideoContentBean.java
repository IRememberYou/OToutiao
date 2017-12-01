package com.example.pinan.otoutiao.function.newstab.bean;

/**
 * @author pinan
 * @date 2017/12/1
 */

public class VideoContentBean {
    public DataBean data;
    public String message;
    public int code;
    public int total;
    
    public static class DataBean {
        public int status;
        public String user_id;
        public String video_id;
        public String validate;
        public boolean enable_ssl;
        public String poster_url;
        public double video_duration;
        public String auto_definition;
        public VideoListBean video_list;
        
        public static class VideoListBean {
            public Video1Bean video_1;
            public Video2Bean video_2;
            
            public static class Video1Bean {
                public String definition;
                public String vtype;
                public int vwidth;
                public int vheight;
                public int bitrate;
                public int size;
                public String codec_type;
                public String logo_type;
                public String file_hash;
                public String main_url;
                public String backup_url_1;
                public int user_video_proxy;
                public int socket_buffer;
                public int preload_size;
                public int preload_interval;
                public int preload_min_step;
                public int preload_max_step;
            }
            
            public static class Video2Bean {
                public String definition;
                public String vtype;
                public int vwidth;
                public int vheight;
                public int bitrate;
                public int size;
                public String codec_type;
                public String logo_type;
                public String file_hash;
                public String main_url;
                public String backup_url_1;
                public int user_video_proxy;
                public int socket_buffer;
                public int preload_size;
                public int preload_interval;
                public int preload_min_step;
                public int preload_max_step;
            }
        }
    }
}
