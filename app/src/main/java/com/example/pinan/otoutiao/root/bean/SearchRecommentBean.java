package com.example.pinan.otoutiao.root.bean;

import java.util.List;

/**
 * @author pinan
 * @date 2017/12/6
 */

public class SearchRecommentBean {
    public DataBean data;
    public String message;
    
    public static class DataBean {
        public Object ab_fields;
        public List<SuggestWordListBean> suggest_word_list;
        
        public static class SuggestWordListBean {
            public String type;
            public String word;
        }
    }
}
