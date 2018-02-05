package com.example.pinan.otoutiao.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pinan.otoutiao.database.DatabaseHelper;
import com.example.pinan.otoutiao.database.table.SearchHistoryTable;
import com.example.pinan.otoutiao.root.bean.SearchHistoryBean;
import com.example.pinan.otoutiao.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pinan
 * @date 2017/12/5
 */

public class SearchHistoryDao {
    
    private final SQLiteDatabase mDb;
    
    public SearchHistoryDao() {
        mDb = DatabaseHelper.getDatabase();
    }
    
    public boolean insert(String keyword) {
        ContentValues values = new ContentValues();
        values.put(SearchHistoryTable.KEYWORD, keyword);
        values.put(SearchHistoryTable.TIME, String.valueOf(System.currentTimeMillis() / 1000));
        long insert = mDb.insert(SearchHistoryTable.TABLE_NAME, null, values);
        return insert != -1;
    }
    
    public List<SearchHistoryBean> queryAll() {
        Cursor query = mDb.query(SearchHistoryTable.TABLE_NAME, null, null, null, null, null, null);
        List<SearchHistoryBean> beans = new ArrayList<>();
        while (query.moveToNext()) {
            String id = query.getString(query.getColumnIndex(SearchHistoryTable.ID));
            String keyword = query.getString(query.getColumnIndex(SearchHistoryTable.KEYWORD));
            String time = query.getString(query.getColumnIndex(SearchHistoryTable.TIME));
            SearchHistoryBean bean = new SearchHistoryBean(id, keyword, time);
            beans.add(bean);
        }
        query.close();
        return beans;
    }
    
    public boolean deleteAll() {
        int delete = mDb.delete(SearchHistoryTable.TABLE_NAME, null, null);
        return delete != -1;
    }
    
    public List<SearchHistoryBean> queryPager(int pagerNow, int pagerSize) {
        Cursor query = mDb.query(SearchHistoryTable.TABLE_NAME, null, SearchHistoryTable.KEYWORD + " like '%item10%'", null, null, null, "time desc", pagerNow + "," + pagerSize);
        List<SearchHistoryBean> beans = new ArrayList<>();
        while (query.moveToNext()) {
            String id = query.getString(query.getColumnIndex(SearchHistoryTable.ID));
            String keyword = query.getString(query.getColumnIndex(SearchHistoryTable.KEYWORD));
            String time = query.getString(query.getColumnIndex(SearchHistoryTable.TIME));
            SearchHistoryBean bean = new SearchHistoryBean(id, keyword, time);
            beans.add(bean);
        }
        query.close();
        return beans;
    }
    
    public boolean delete(String keyword) {
        int delete = mDb.delete(SearchHistoryTable.TABLE_NAME, SearchHistoryTable.KEYWORD + " = ?", new String[]{keyword});
        return delete != -1;
    }
    
    public boolean queryIsExist(String keyword) {
        Cursor query = mDb.query(SearchHistoryTable.TABLE_NAME, null, SearchHistoryTable.KEYWORD + "=?", new String[]{keyword}, null, null, null);
        while (query.moveToNext()) {
            query.close();
            return true;
        }
        query.close();
        return false;
    }
    
    public boolean update(String keyword) {
        ContentValues values = new ContentValues();
        values.put(SearchHistoryTable.KEYWORD, keyword);
        values.put(SearchHistoryTable.TIME, TimeUtil.getCurrentTimeStamp());
        int result = mDb.update(SearchHistoryTable.TABLE_NAME, values, SearchHistoryTable.KEYWORD + "=?", new String[]{keyword});
        return result != -1;
    }
    
    public void add() {
        for (int i = 0; i < 10000; i++) {
            String word = "item" + i;
            insert(word);
        }
    }
}
