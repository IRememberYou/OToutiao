package com.example.pinan.otoutiao.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pinan.otoutiao.base.init.BaseApplication;
import com.example.pinan.otoutiao.database.table.MediaChannelTable;
import com.example.pinan.otoutiao.database.table.NewsChannelTable;
import com.example.pinan.otoutiao.database.table.SearchHistoryTable;

/**
 *
 * @author pinan
 * @date 2017/11/16
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    /**
     * 数据库名
     */
    private static final String DB_NAME = "otoutiao";
    /**
     * 数据库版本
     */
    private static final int DB_VERSION = 1;
    /**
     * 获取db
     */
    private static SQLiteDatabase db = null;

//    private static final String DROP_TABLE = "drop table if exists ";
//    private static final String CLEAR_TABLE_DATA = "delete from ";
    /**
     * 设置为单态
     */
    private static DatabaseHelper instance = null;
    
    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    
    public synchronized static DatabaseHelper getInstance() {
        synchronized (DatabaseHelper.class) {
            if (instance == null) {
                instance = new DatabaseHelper(BaseApplication.sContext, DB_NAME, null, DB_VERSION);
            }
            return instance;
        }
    }
    
    public synchronized static SQLiteDatabase getDatabase() {
        if (db == null) {
            db = getInstance().getWritableDatabase();
        }
        return db;
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        db.execSQL(NewsChannelTable.CREATE_TABLE);
        db.execSQL(MediaChannelTable.CREATE_TABLE);
        db.execSQL(SearchHistoryTable.CREATE_TABLE);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                break;
            default:
                break;
        }
        
    }
}
