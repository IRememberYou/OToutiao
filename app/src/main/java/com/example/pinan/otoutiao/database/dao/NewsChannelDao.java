package com.example.pinan.otoutiao.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.BaseApplication;
import com.example.pinan.otoutiao.base.Constant;
import com.example.pinan.otoutiao.database.DatabaseHelper;
import com.example.pinan.otoutiao.database.table.NewsChannelTable;
import com.example.pinan.otoutiao.newstab.bean.NewsChannelBean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pinan
 * @date 2017/11/16
 */

public class NewsChannelDao {
    
    private SQLiteDatabase mDb;
    
    public NewsChannelDao() {
        mDb = DatabaseHelper.getDatabase();
    }
    
    public void addInitData() {
        String[] categoryId = BaseApplication.sContext.getResources().getStringArray(R.array.mobile_news_id);
        String[] categoryName = BaseApplication.sContext.getResources().getStringArray(R.array.mobile_news_name);
        
        for (int i = 0; i < 8; i++) {
            add(categoryId[i], categoryName[i], Constant.NEWS_CHANNEL_ENABLE, i);
        }
        for (int i = 8; i < categoryId.length; i++) {
            add(categoryId[i], categoryName[i], Constant.NEWS_CHANNEL_DISABLE, i);
        }
    }
    
    private boolean add(String categoryId, String categoryName, int isEnable, int position) {
        ContentValues values = new ContentValues();
        values.put(NewsChannelTable.ID, categoryId);
        values.put(NewsChannelTable.NAME, categoryName);
        values.put(NewsChannelTable.IS_ENABLE, isEnable);
        values.put(NewsChannelTable.POSITION, position);
        long insert = mDb.insert(NewsChannelTable.TABLENAME, null, values);
        return insert != -1;
    }
    
    
    public List<NewsChannelBean> query(int isEnable) {
        Cursor cursor = mDb.query(NewsChannelTable.TABLENAME, null, NewsChannelTable.IS_ENABLE + "= ?", new String[]{isEnable + ""}, null, null, null);
        List<NewsChannelBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            NewsChannelBean bean = new NewsChannelBean();
            bean.channelId = cursor.getString(cursor.getColumnIndex(NewsChannelTable.ID));
            bean.channelName = cursor.getString(cursor.getColumnIndex(NewsChannelTable.NAME));
            bean.isEnable = cursor.getInt(cursor.getColumnIndex(NewsChannelTable.IS_ENABLE));
            bean.position = cursor.getInt(cursor.getColumnIndex(NewsChannelTable.POSITION));
            list.add(bean);
        }
        cursor.close();
        return list;
    }
    
    public List<NewsChannelBean> queryAll() {
        Cursor cursor = mDb.query(NewsChannelTable.TABLENAME, null, null, null, null, null, null);
        
        List<NewsChannelBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            NewsChannelBean bean = new NewsChannelBean();
            bean.channelId = cursor.getString(cursor.getColumnIndex(NewsChannelTable.ID));
            bean.channelName = cursor.getString(cursor.getColumnIndex(NewsChannelTable.NAME));
            bean.isEnable = cursor.getInt(cursor.getColumnIndex(NewsChannelTable.IS_ENABLE));
            bean.position = cursor.getInt(cursor.getColumnIndex(NewsChannelTable.POSITION));
            list.add(bean);
        }
        cursor.close();
        return list;
    }
    
    
    public boolean removeAll() {
        int delete = mDb.delete(NewsChannelTable.TABLENAME, null, null);
        return delete != -1;
    }
    
    
}
