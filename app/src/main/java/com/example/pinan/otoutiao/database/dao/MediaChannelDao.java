package com.example.pinan.otoutiao.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pinan.otoutiao.database.DatabaseHelper;
import com.example.pinan.otoutiao.database.table.MediaChannelTable;
import com.example.pinan.otoutiao.function.newstab.bean.MediaChannelBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pinan
 * @date 2017/11/30
 */

public class MediaChannelDao {
    
    private SQLiteDatabase db;
    
    public MediaChannelDao() {
        db = DatabaseHelper.getDatabase();
    }
    
    /**
     * 模拟增加数据
     */
    public void insert() {
        add("4377795668", "新华网", "http://p2.pstatp.com/large/3658/7378365093", "news",
            "", "传播中国，报道世界；权威声音，亲切表达。", "http://toutiao.com/m4377795668/");
        add("52445544609", "互联网的这点事", "http://p3.pstatp.com/large/ef300164e786ff295da", "news",
            "", "每天为你速递最新、最鲜、最有料的互联网科技资讯！", "http://toutiao.com/m52445544609/");
    }
    
    public boolean add(MediaChannelBean bean) {
        return add(bean.id, bean.name, bean.avatar, bean.followcount, bean.type, bean.url, bean.desctext);
    }
    
    public boolean add(String id, String name, String avatar, String followcount, String type, String url, String desctext) {
        ContentValues values = new ContentValues();
        values.put(MediaChannelTable.ID, id);
        values.put(MediaChannelTable.NAME, name);
        values.put(MediaChannelTable.AVATAR, avatar);
        values.put(MediaChannelTable.FOLLOWCOUNT, followcount);
        values.put(MediaChannelTable.TYPE, type);
        values.put(MediaChannelTable.URL, url);
        values.put(MediaChannelTable.DESCTEXT, desctext);
        long insertId = db.insert(MediaChannelTable.TABLE_NAME, null, values);
        return insertId != -1;
    }
    
    public boolean queryIsExist(String mediaId) {
        Cursor cursor = db.query(MediaChannelTable.TABLE_NAME, null, MediaChannelTable.ID + "=?", new String[]{mediaId}, null, null, null);
        while (cursor.moveToNext()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
    
    public List<MediaChannelBean> queryAll() {
        List<MediaChannelBean> list = new ArrayList<>();
        Cursor cursor = db.query(MediaChannelTable.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(MediaChannelTable.ID));
            String name = cursor.getString(cursor.getColumnIndex(MediaChannelTable.NAME));
            String avatar = cursor.getString(cursor.getColumnIndex(MediaChannelTable.AVATAR));
            String type = cursor.getString(cursor.getColumnIndex(MediaChannelTable.TYPE));
            String followcount = cursor.getString(cursor.getColumnIndex(MediaChannelTable.FOLLOWCOUNT));
            String desctext = cursor.getString(cursor.getColumnIndex(MediaChannelTable.DESCTEXT));
            String url = cursor.getString(cursor.getColumnIndex(MediaChannelTable.URL));
            MediaChannelBean bean = new MediaChannelBean(id, name, avatar, type, followcount, desctext, url);
            list.add(bean);
        }
        cursor.close();
        return list;
    }
    
    public boolean upData(MediaChannelBean bean) {
        return upData(bean.id, bean.name, bean.avatar, bean.followcount, bean.type, bean.url, bean.desctext);
        
    }
    
    public boolean upData(String id, String name, String avatar, String followcount, String type, String url, String desctext) {
        ContentValues values = new ContentValues();
        values.put(MediaChannelTable.ID, id);
        values.put(MediaChannelTable.NAME, name);
        values.put(MediaChannelTable.AVATAR, avatar);
        values.put(MediaChannelTable.FOLLOWCOUNT, followcount);
        values.put(MediaChannelTable.TYPE, type);
        values.put(MediaChannelTable.URL, url);
        values.put(MediaChannelTable.DESCTEXT, desctext);
        int updateId = db.update(MediaChannelTable.TABLE_NAME, values, MediaChannelTable.ID + "=?", new String[]{id});
        return updateId != -1;
    }
    
    public boolean delete(String mediaId) {
        int id = db.delete(MediaChannelTable.TABLE_NAME, MediaChannelTable.ID + "=?", new String[]{mediaId});
        return id != -1;
    }
}
